package com.java.pethoster.web.vm.mappers;

import com.java.pethoster.domain.Utilisateur;
import com.java.pethoster.web.vm.UtilisateurVM;
import com.java.pethoster.web.vm.request.RegisterRequest;
import com.java.pethoster.web.vm.response.UtilisateurResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UtilisateurMapper {
    Utilisateur toEntity(RegisterRequest request);
    UtilisateurResponse toResponse(Utilisateur utilisateur);
    UtilisateurVM toVM(Utilisateur utilisateur);
}
