package io.github.blueberry44477.authservice.dto.response;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import io.github.blueberry44477.authservice.model.Gender;

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
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Gender sex;
    private String email;
    private LocalDate dob;
    private String phone;
    private String country;
    private String city;
    private byte[] avatar;
    private Set<UserDto> friends = new HashSet<>();
    private String token;
}
