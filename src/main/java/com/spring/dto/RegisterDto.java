package com.spring.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterDto {


    @Column(unique = true, nullable = false)
    @NotBlank(message = "Username cannot be blank")
    private String username;

    @NotBlank(message = "Email cannot be blank")
    @Column(unique = true, nullable = false)
    @Email(message = "Email should be valid")
    private String email;

    @Column(nullable = false)
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must contain at least 8 characters, " +
                    "including one uppercase letter, one lowercase letter," +
                    " one number, and one special character"
    )
    private String password;

}
