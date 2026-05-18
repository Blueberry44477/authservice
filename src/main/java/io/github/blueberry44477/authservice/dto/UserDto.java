package io.github.blueberry44477.authservice.dto;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
    private String email;
    private Gender sex;
    private LocalDate dob;
    private String phone;
    private String country;
    private String city;
    private byte[] avatar;

    // @JsonIgnoreProperties("friends")
    // private Set<UserDto> friends = new HashSet<>();
}
