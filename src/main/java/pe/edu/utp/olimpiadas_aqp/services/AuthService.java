package pe.edu.utp.olimpiadas_aqp.services;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pe.edu.utp.olimpiadas_aqp.dto.UserDTO;
import pe.edu.utp.olimpiadas_aqp.entities.UserEntity;
import pe.edu.utp.olimpiadas_aqp.models.requests.AuthRequest;
import pe.edu.utp.olimpiadas_aqp.models.responses.AuthResponse;
import pe.edu.utp.olimpiadas_aqp.repositories.UserRepository;
import pe.edu.utp.olimpiadas_aqp.security.SecurityConstants;

import java.security.Key;
import java.util.Date;

@Service
public class AuthService implements AuthServiceInterface {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        UserEntity userEntity = userRepository.findByEmail(authRequest.getEmail());
        AuthResponse response = new AuthResponse();
        if (userEntity == null) {
            response.setStatus(400);
            response.setMessage("El usuario no existe.");
        } else {
            if (bCryptPasswordEncoder.matches(authRequest.getPassword(), userEntity.getPassword())) {
                response.setStatus(200);
                response.setMessage("Correcto.");
                UserDTO userDTO = new UserDTO();
                BeanUtils.copyProperties(userEntity, userDTO);
                response.setUser(userDTO);
                Key key = Jwts.SIG.HS512.key().build();
                String token = Jwts.builder()
                        .subject(userEntity.getEmail())
                        .expiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_DATE))
                        .signWith(key)
                        .compact();
                response.setToken(SecurityConstants.TOKEN_PREFIX + token);
            } else {
                response.setStatus(404);
                response.setMessage("Credenciales incorrectas.");
            }
        }
        return response;
    }
}