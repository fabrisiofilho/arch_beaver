package br.com.ff.arch_beaver.modules.user.domain.entity;

import br.com.ff.arch_beaver.common.audit.AuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "usuario_perfil", schema = "usuario")
public class UserProfileEntity extends AuditEntity implements Serializable {

    @EmbeddedId
    private UserProfileId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "id_usuario")
    private UserEntity user;

    @ManyToOne
    @MapsId("profileId")
    @JoinColumn(name = "id_perfil")
    private ProfileEntity profile;

}
