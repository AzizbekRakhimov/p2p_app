package uz.azizbek.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AuthDto {
    @NotNull(message = "username can not be empty")
    private String username;
    @NotNull(message = "password can not be empty")
    private String password;
}
