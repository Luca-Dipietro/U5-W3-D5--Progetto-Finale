package lucadipietro.U5_W3_D5__Progetto_Finale.services;
import lucadipietro.U5_W3_D5__Progetto_Finale.entities.User;
import lucadipietro.U5_W3_D5__Progetto_Finale.exceptions.UnauthorizedException;
import lucadipietro.U5_W3_D5__Progetto_Finale.payloads.UserLoginDTO;
import lucadipietro.U5_W3_D5__Progetto_Finale.security.JWTTokenConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UsersService usersService;

    @Autowired
    private JWTTokenConfiguration jwtTokenConfiguration;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String authenticateUserAndGenerateToken(UserLoginDTO body){
        User user = this.usersService.findByEmail(body.email());
        if(passwordEncoder.matches(body.password(), user.getPassword())){
            return jwtTokenConfiguration.createToken(user);
        } else {
            throw new UnauthorizedException("Credenziali non corrette!");
        }
    }
}
