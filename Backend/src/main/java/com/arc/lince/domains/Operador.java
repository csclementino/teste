package com.arc.lince.domains;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@With
@Getter
@Entity(name = "LC_OPERADOR")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

public class Operador {
    @Id
    @GeneratedValue (strategy = GenerationType.UUID)
    private String id;
    private String nome;
    private String funcao;

    @ManyToOne
    private Equipe equipe;

    @OneToMany(
            mappedBy = "operador",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private List<Alerta> alertas;
}
