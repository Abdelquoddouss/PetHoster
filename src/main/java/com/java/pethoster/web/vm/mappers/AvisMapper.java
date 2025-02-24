package com.java.pethoster.web.vm.mappers;

import com.java.pethoster.domain.Avis;
import com.java.pethoster.web.vm.response.AvisResponse;
import org.springframework.stereotype.Component;

@Component
public class AvisMapper {
    public AvisResponse toResponse(Avis avis) {
        return AvisResponse.builder()
                .id(avis.getId())
                .note(avis.getNote())
                .commentaire(avis.getCommentaire())
                .dateAvis(avis.getDateAvis())
                .proprietaireId(avis.getProprietaire().getId())
                .hebergeurId(avis.getHebergeur().getId())
                .build();
    }
}