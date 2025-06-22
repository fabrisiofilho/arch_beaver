package br.com.ff.arch_beaver.common.security.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {
    private String token;
    private String refreshToken;
}
