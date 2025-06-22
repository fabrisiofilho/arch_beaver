package br.com.ff.arch_beaver.common.security.models;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginWeb implements Serializable {

    private String token;
    private String refreshToken;
    private UserLogin user;

}