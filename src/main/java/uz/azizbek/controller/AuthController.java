package uz.azizbek.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.azizbek.common.ResponseData;
import uz.azizbek.config.jwt.JwtProvider;
import uz.azizbek.model.Users;
import uz.azizbek.payload.AuthDto;
import uz.azizbek.service.impl.AuthService;

import javax.validation.Valid;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private AuthService userDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<ResponseData<String>> authRequest(@Valid @RequestBody AuthDto authDto) {

        Users details = userDetailsService.loadUserByUsername(authDto.getUsername());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authDto.getUsername(),
                            authDto.getPassword()
                    )

            );
            return ResponseData.response(jwtProvider.generateToken(details));
            
        } catch (BadCredentialsException e){
            return ResponseData.response("Bad credentials", HttpStatus.BAD_REQUEST);
        }
    }

}
