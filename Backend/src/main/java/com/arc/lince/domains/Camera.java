package com.arc.lince.domains;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@With
@Getter
@Entity(name = "LC_CAMERA")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

public class Camera {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String localizacao;
    private String descricao;
    private String status;

    @OneToMany(
            mappedBy = "camera",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private List<Alerta> alertas;

    @ManyToOne
    private Setor setor;
}
