package io.github.blueberry44477.authservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FriendshipRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Wrong email format")
    @JsonProperty(value = "friend_email")
    private String friendEmail;
}
