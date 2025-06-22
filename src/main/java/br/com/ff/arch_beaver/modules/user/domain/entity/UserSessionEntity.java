package br.com.ff.arch_beaver.modules.user.domain.entity;

import br.com.ff.arch_beaver.common.audit.AuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.net.InetAddress;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "usuario_sessao", schema = "usuario")
public class UserSessionEntity extends AuditEntity implements Serializable {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "usuario_sessao_generator")
    @SequenceGenerator(name = "usuario_sessao_generator",
            sequenceName = "usuario.usuario_sessao_id_usuario_sessao_seq",
            allocationSize = 1)
    @Column(name = "id_usuario_sessao")
    private Long id;

    @ManyToOne
    @JoinColumn(name="id_usuario")
    private UserEntity user;

    @Column(name = "inicio")
    private LocalDateTime start;

    @Column(name = "termino")
    private LocalDateTime term;

    @Column(name = "token")
    private String token;

    @Column(name = "endereco")
    private InetAddress address;

    @Column(name = "ultimo_acesso")
    private LocalDate lastAccess;

}
