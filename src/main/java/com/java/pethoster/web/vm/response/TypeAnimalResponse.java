package com.java.pethoster.web.vm.response;

import lombok.Data;

import java.util.UUID;

@Data
public class TypeAnimalResponse {
    private UUID id;
    private String animalType;
}