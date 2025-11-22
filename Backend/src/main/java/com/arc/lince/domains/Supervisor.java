package com.arc.lince.domains;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@With
@Getter
@Entity(name = "LC_SUPERVISOR")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

public class Supervisor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String nome;
    private String email;
    private String telefone;

    @ManyToOne
    private Equipe equipe;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Usuario usuario;
}
