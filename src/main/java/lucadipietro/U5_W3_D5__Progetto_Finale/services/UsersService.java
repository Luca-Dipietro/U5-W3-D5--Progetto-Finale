package lucadipietro.U5_W3_D5__Progetto_Finale.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lucadipietro.U5_W3_D5__Progetto_Finale.entities.User;
import lucadipietro.U5_W3_D5__Progetto_Finale.enums.Role;
import lucadipietro.U5_W3_D5__Progetto_Finale.exceptions.BadRequestException;
import lucadipietro.U5_W3_D5__Progetto_Finale.exceptions.NotFoundException;
import lucadipietro.U5_W3_D5__Progetto_Finale.payloads.NewUserDTO;
import lucadipietro.U5_W3_D5__Progetto_Finale.payloads.RoleDTO;
import lucadipietro.U5_W3_D5__Progetto_Finale.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Page<User> getUser(int pageNumber, int pageSize, String sortBy){
        if(pageSize > 50) pageSize = 50;
        Pageable pageable = PageRequest.of(pageNumber,pageSize, Sort.by(sortBy));
        return usersRepository.findAll(pageable);
    }

    public User save(NewUserDTO body){
        this.usersRepository.findByEmail(body.email()).ifPresent(
                dipendente -> {
                    throw new BadRequestException("Esiste già un dipendente con questa email " + body.email());
                }
        );
        User newDipendente = new User(body.name(), body.surname(), body.email(),passwordEncoder.encode(body.password()));
        newDipendente.setAvatar("https://ui-avatars.com/api/?name=" + body.name() + "+" + body.surname());
        return this.usersRepository.save(newDipendente);
    }

    public User findById(UUID userId) {
        return this.usersRepository.findById(userId).orElseThrow(() -> new NotFoundException(userId));
    }

    public User findByEmail(String email) {
        return this.usersRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Utente con email " + email + " non trovato!"));
    }

    public User findByIdAndUpdate(UUID userId, NewUserDTO body){
        User found = this.findById(userId);
        found.setName(body.name());
        found.setSurname(body.surname());
        found.setPassword(passwordEncoder.encode(body.password()));
        found.setEmail(body.email());
        found.setAvatar("https://ui-avatars.com/api/?name=" + body.name() + "+" + body.surname());
        return this.usersRepository.save(found);
    }

    public void findByIdAndDelete(UUID userId) {
        User found = this.findById(userId);
        this.usersRepository.delete(found);
    }

    public User uploadImage(UUID userId, MultipartFile file) throws IOException {
        User found = this.findById(userId);
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setAvatar(url);
        return this.usersRepository.save(found);
    }

    public User updateRole(UUID id, RoleDTO role) {
        User found = findById(id);
        found.setRole(Role.valueOf(role.role().toUpperCase()));
        return usersRepository.save(found);
    }

}
