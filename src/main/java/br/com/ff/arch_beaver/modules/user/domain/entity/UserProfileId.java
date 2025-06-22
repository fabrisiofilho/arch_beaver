package br.com.ff.arch_beaver.modules.user.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileId implements Serializable {

    @Column(name = "id_usuario")
    private Long userId;

    @Column(name = "id_perfil")
    private Long profileId;

}
