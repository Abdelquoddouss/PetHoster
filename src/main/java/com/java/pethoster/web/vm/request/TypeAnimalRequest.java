package com.java.pethoster.web.vm.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TypeAnimalRequest {

    @NotBlank(message = "ce type est obligatoire")
    private String animalType;
}
