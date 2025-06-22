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
@Table(name = "anexo", schema = "arquivo")
public class AttachmentEntity extends AuditEntity implements Serializable {

    @Id
    @GeneratedValue(strategy =
            GenerationType.SEQUENCE,
            generator = "anexo_generator")
    @SequenceGenerator(name = "anexo_generator",
            sequenceName = "arquivo.anexo_id_anexo_seq",
            allocationSize = 1)
    @Column(name = "id_anexo")
    private Long id;

    @ManyToOne
    @JoinColumn(name="id_tipo_anexo")
    private AttachmentTypeEntity attachmentType;

    @Column(name = "arquivo")
    private String name;

    @Column(name = "tamanho")
    private Long size;

    @Column(name = "extensao")
    private String extension;

}
