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
@Table(name = "perfil", schema = "usuario")
public class ProfileEntity extends AuditEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "perfil_generator")
    @SequenceGenerator(name = "perfil_generator",
            sequenceName = "usuario.perfil_id_perfil_seq",
            allocationSize = 1)
    @Column(name = "id_perfil")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_tipo_perfil")
    private ProfileTypeEntity profileType;

    @Column(name = "nome")
    private String name;

    @Column(name = "descricao")
    private String description;

    @Column(name = "ativo")
    private Boolean active;

}
