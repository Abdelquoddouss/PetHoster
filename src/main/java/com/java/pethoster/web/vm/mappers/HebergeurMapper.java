package com.java.pethoster.web.vm.mappers;

import com.java.pethoster.domain.TypeAnimal;
import com.java.pethoster.domain.Utilisateur;
import com.java.pethoster.web.vm.request.HebergeurRequest;
import com.java.pethoster.web.vm.response.HebergeurResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface HebergeurMapper {

    @Mapping(target = "id", ignore = true) // L'ID est généré automatiquement
    @Mapping(target = "role", constant = "HEBERGEUR") // Le rôle est toujours HEBERGEUR
    @Mapping(target = "typeAnimauxAcceptes", ignore = true) // Géré manuellement dans le service
    Utilisateur toEntity(HebergeurRequest request);

    @Mapping(target = "typeAnimauxAcceptesIds", source = "typeAnimauxAcceptes", qualifiedByName = "mapTypeAnimauxAcceptes")
    HebergeurResponse toResponse(Utilisateur utilisateur);

    @Named("mapTypeAnimauxAcceptes")
    default List<UUID> mapTypeAnimauxAcceptes(List<TypeAnimal> typeAnimauxAcceptes) {
        if (typeAnimauxAcceptes == null) {
            return List.of();
        }
        return typeAnimauxAcceptes.stream()
                .map(TypeAnimal::getId)
                .collect(Collectors.toList());
    }
}