package com.arc.lince.domains;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@With
@Getter
@Entity(name = "LC_SETOR")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

public class Setor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String nome;
    private String descricao;

    @OneToMany(
            mappedBy = "setor",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private List<Camera> cameras;
}
