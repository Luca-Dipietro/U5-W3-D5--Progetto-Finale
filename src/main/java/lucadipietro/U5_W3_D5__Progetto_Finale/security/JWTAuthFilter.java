package lucadipietro.U5_W3_D5__Progetto_Finale.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lucadipietro.U5_W3_D5__Progetto_Finale.entities.User;
import lucadipietro.U5_W3_D5__Progetto_Finale.exceptions.UnauthorizedException;
import lucadipietro.U5_W3_D5__Progetto_Finale.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {
    @Autowired
    private JWTTokenConfiguration jwtTokenConfiguration;
    @Autowired
    private UsersService usersService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")) throw new UnauthorizedException("Il token inserito è sbagliato, per favore inserisci quello corretto!");
        String accessToken= authHeader.substring(7);
        jwtTokenConfiguration.verifyToken(accessToken);
        String userId = jwtTokenConfiguration.extractIdFromToken(accessToken);
        User current = usersService.findById(UUID.fromString(userId));
        Authentication authentication = new UsernamePasswordAuthenticationToken(current,null,current.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request,response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException{
        return new AntPathMatcher().match("/auth/**", request.getServletPath());
    }
}
