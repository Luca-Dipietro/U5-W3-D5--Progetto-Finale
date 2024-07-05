package lucadipietro.U5_W3_D5__Progetto_Finale.controllers;
import lucadipietro.U5_W3_D5__Progetto_Finale.exceptions.BadRequestException;
import lucadipietro.U5_W3_D5__Progetto_Finale.payloads.NewUserDTO;
import lucadipietro.U5_W3_D5__Progetto_Finale.payloads.NewUserResponseDTO;
import lucadipietro.U5_W3_D5__Progetto_Finale.payloads.UserLoginDTO;
import lucadipietro.U5_W3_D5__Progetto_Finale.payloads.UserLoginResponseDTO;
import lucadipietro.U5_W3_D5__Progetto_Finale.services.AuthService;
import lucadipietro.U5_W3_D5__Progetto_Finale.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UsersService usersService;

    @PostMapping("/login")
    public UserLoginResponseDTO login(@RequestBody UserLoginDTO body){
        return new UserLoginResponseDTO(authService.authenticateUserAndGenerateToken(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public NewUserResponseDTO saveUser(@RequestBody @Validated NewUserDTO body, BindingResult validationResult){
        if(validationResult.hasErrors()){
            throw new BadRequestException(validationResult.getAllErrors());
        } else {
            return new NewUserResponseDTO(this.usersService.save(body).getId());
        }
    }
}
