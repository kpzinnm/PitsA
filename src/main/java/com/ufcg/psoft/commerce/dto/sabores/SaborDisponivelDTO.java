package com.ufcg.psoft.commerce.dto.sabores;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaborDisponivelDTO {
    @JsonProperty("disponivel")
    @NotNull(message = "O valor nao pode ser nulo")
    private Boolean disponivel;

    public Boolean isDisponivel(){
        return getDisponivel();
    }
}
