package br.com.ff.arch_beaver.common.audit;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditListener.class)
public class AuditEntity {

    @Column(name = "id_registro", insertable = false, updatable = false)
    private Long registryId;

    @Column(name = "id_criador", updatable = false)
    private Long creator;

    @Column(name = "id_aplicacao_criador", updatable = false)
    private Long creatorApplication;

    @Column(name = "id_alterador")
    private Long changer;

    @Column(name = "id_aplicacao_alterador")
    private Long changerApplication;

    @Column(name = "criacao", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "alteracao", insertable = false, updatable = false)
    private LocalDateTime updatedAt;
}
