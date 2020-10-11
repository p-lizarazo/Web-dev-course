package co.edu.javeriana.pw.back.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Forum {
       
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String titulo;
    private String descripcion;
    private Boolean moderado;

    @OneToMany(mappedBy = "foro")
    private List<Topic> temas;

    // List<User> moderadores;
}
