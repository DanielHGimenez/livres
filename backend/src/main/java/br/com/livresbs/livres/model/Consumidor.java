package br.com.livresbs.livres.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "tb_consumidor")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Consumidor {

    @Id
    @NotBlank
    private String cpf;

    @NotBlank
    private String nome;
    @NotBlank
    private String sobrenome;
    @NotBlank
    @JsonIgnore
    private String senha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "precomunidade_id")
    @NotNull
    @JsonBackReference
    private PreComunidade precomunidade;

    @ManyToMany
    @JoinTable(
        name = "consumidor_endereco",
        joinColumns = @JoinColumn(name = "consumidor_id"),
        inverseJoinColumns = @JoinColumn(name = "endereco_id")
    )
    @JsonManagedReference
    private List<EnderecoEntrega> enderecos;
    
}
