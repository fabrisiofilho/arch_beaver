package br.com.ff.arch_beaver.common.security.models;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDevice implements Serializable {

    private String token;
    private UserLogin user;

}