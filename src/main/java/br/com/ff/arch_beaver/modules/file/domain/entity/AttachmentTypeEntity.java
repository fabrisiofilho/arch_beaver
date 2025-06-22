package br.com.ff.arch_beaver.modules.file.domain.entity;

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
@Table(name = "tipo_anexo", schema = "arquivo")
public class AttachmentTypeEntity extends AuditEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "tipo_anexo_generator")
    @SequenceGenerator(name = "tipo_anexo_generator",
            sequenceName = "arquivo.tipo_anexo_id_tipo_anexo_seq",
            allocationSize = 1)
    @Column(name = "id_tipo_anexo")
    private Long id;

    @Column(name = "descricao")
    private String description;

    @Column(name = "ativo")
    private Boolean active;

    @Column(name = "chave")
    private String key;

}

