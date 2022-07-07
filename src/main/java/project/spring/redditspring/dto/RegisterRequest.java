package project.spring.redditspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    @NotNull(message = "Username must not be blanked.")
    private String username;

    @Email(message = "Please provide a valid email.")
    private String email;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,20}",
            message = "Password should be 8-20 characters length and must have at least 1 letter, 1 number and 1 special character!")
    private String password;

}
