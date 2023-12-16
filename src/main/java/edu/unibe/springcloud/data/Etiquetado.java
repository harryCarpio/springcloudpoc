package edu.unibe.springcloud.data;


import jakarta.persistence.*;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
public class Etiquetado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "imagen")
    @Setter
    private String imagen;

    @Column(name = "confidence")
    @Setter
    private BigDecimal confidence;

    @Column(name = "tag")
    @Setter
    private String tag;

}
