package com.arc.lince.domains;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@With
@Getter
@Entity(name = "LC_ALERTA")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

public class Alerta {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String motivo;
    private String nivelAlerta;
    private LocalDateTime dataHora;

    @ManyToOne
    private Operador operador;

    @ManyToOne
    private Camera camera;
}
