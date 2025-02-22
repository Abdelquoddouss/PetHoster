package com.java.pethoster.web.vm.mappers;

import com.java.pethoster.domain.Utilisateur;
import com.java.pethoster.web.vm.request.HebergeurRequest;
import com.java.pethoster.web.vm.response.HebergeurResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HebergeurMapper {

    @Mapping(target = "id", ignore = true) // L'ID est généré automatiquement
    @Mapping(target = "role", constant = "HEBERGEUR") // Le rôle est toujours HEBERGEUR
    @Mapping(target = "typeAnimauxAcceptes", ignore = true) // Géré manuellement dans le service
    Utilisateur toEntity(HebergeurRequest request);

    HebergeurResponse toResponse(Utilisateur utilisateur);
}