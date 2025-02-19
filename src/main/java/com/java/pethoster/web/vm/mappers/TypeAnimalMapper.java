package com.java.pethoster.web.vm.mappers;

import com.java.pethoster.domain.TypeAnimal;
import com.java.pethoster.web.vm.request.TypeAnimalRequest;
import com.java.pethoster.web.vm.response.TypeAnimalResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TypeAnimalMapper {

    TypeAnimalResponse toResponse(TypeAnimal typeAnimal);

    TypeAnimal toEntity(TypeAnimalRequest request);
}