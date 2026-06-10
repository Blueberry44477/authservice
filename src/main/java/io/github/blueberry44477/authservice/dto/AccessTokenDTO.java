package io.github.blueberry44477.authservice.dto;

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
public class AccessTokenDTO {
    private String accessToken;
    private String tokenType;
    private Long expiresIn; // In seconds.
}
