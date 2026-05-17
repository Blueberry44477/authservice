package io.github.blueberry44477.dto.request;

import java.time.LocalDate;

import io.github.blueberry44477.model.Gender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CreateUserRequest {
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 20, message = "First name must be between 2 and 20 symbols")
    private String firstName;

    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Wrong email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 10, message = "Password must be at least 10 symbols long")
    private String password;

    @NotBlank(message = "Sex is required")
    private Gender sex;
    
    @NotBlank(message = "Date of birth is required")
    @Past(message = "Date of birth must be in past")
    private LocalDate dob;

    private Integer age;
    private String phone;
    private String country;
    private String city;
    // private byte[] avatar;
    // private Set<Long> friendIds;
    // private Set<Friendship> friends = new HashSet();
}
