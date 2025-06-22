package br.com.ff.arch_beaver.modules.user.domain.entity;

import br.com.ff.arch_beaver.common.audit.AuditEntity;
import br.com.ff.arch_beaver.modules.file.domain.entity.AttachmentEntity;
import br.com.ff.arch_beaver.modules.general.domain.entity.EmailEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Formula;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "usuario", schema = "usuario")
public class UserEntity extends AuditEntity implements Serializable {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "usuario_generator")
    @SequenceGenerator(name = "usuario_generator",
            sequenceName = "usuario.usuario_id_usuario_seq",
            allocationSize = 1)
    @Column(name = "id_usuario")
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_email")
    private EmailEntity email;

    @Column(name = "senha")
    private String password;

    @Column(name = "ativo")
    private Boolean active;

    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "id_avatar")
    private AttachmentEntity avatar;

    @Formula("(SELECT up.id_perfil FROM usuario.usuario_perfil up WHERE up.id_usuario = id_usuario LIMIT 1)")
    private Long profileId;

    @Formula("(SELECT p.descricao " +
            " FROM usuario.usuario_perfil up " +
            " JOIN usuario.perfil p ON p.id_perfil = up.id_perfil " +
            " WHERE up.id_usuario = id_usuario LIMIT 1)")
    private String profileDescription;

    @Formula("(SELECT tp.chave " +
            " FROM usuario.usuario_perfil up " +
            " JOIN usuario.perfil p ON p.id_perfil = up.id_perfil " +
            " JOIN usuario.tipo_perfil tp ON tp.id_tipo_perfil = p.id_tipo_perfil " +
            " WHERE up.id_usuario = id_usuario LIMIT 1)")
    private String profileTypeKey;

}
