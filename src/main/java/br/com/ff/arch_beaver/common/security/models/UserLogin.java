package br.com.ff.arch_beaver.common.security.models;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLogin implements Serializable {

    private Long id;
    private String email;
    private String profile;
    private String profileTypeKey;

}
