package co.edu.javeriana.pw.back.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Forum {
       
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    

}