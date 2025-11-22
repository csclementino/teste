package com.arc.lince.domains;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@With
@Getter
@Entity(name = "LC_EQUIPE")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

public class Equipe {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String nome;
    private String descricao;

    @OneToMany(
            mappedBy = "equipe",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private List<Supervisor> supervisores;

    @OneToMany(
            mappedBy = "equipe",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private List<Operador> operadores;
}
