package br.com.ff.arch_beaver.modules.user.domain.entity;

import br.com.ff.arch_beaver.common.audit.AuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "tipo_perfil", schema = "usuario")
public class ProfileTypeEntity extends AuditEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "tipo_perfil_generator")
    @SequenceGenerator(name = "tipo_perfil_generator",
            sequenceName = "usuario.tipo_perfil_id_tipo_perfil_seq",
            allocationSize = 1)
    @Column(name = "id_tipo_perfil")
    private Long id;

    @Column(name = "descricao")
    private String description;

    @Column(name = "ativo")
    private Boolean active;

    @Column(name = "chave")
    private String key;

}
