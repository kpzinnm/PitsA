package com.ufcg.psoft.commerce.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/*Eu, enquanto administrador do sistema, quero utilizar o sistema para criar,  editar e remover um estabelecimento.
Um estabelecimento deverá possuir um código de acesso ao sistema (com 6 dígitos).
O código de acesso deve ser informado sempre que se faz alguma operação enquanto estabelecimento.
Se o código de acesso não for informado ou estiver incorreto,	 a operação irá obrigatoriamente falhar.
Não há limite para o número de operações com inserção de código incorreto.*/

@Entity(name = "estabelecimento")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Estabelecimento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "pk_id_estabelecimento")
    private Long id;

    @JsonProperty("codigo")
    @Column(name = "codigo", length = 6, nullable = false)
    private String codigoAcesso;

}