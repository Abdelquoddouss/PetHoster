package com.java.pethoster.web.vm.mappers;

import com.java.pethoster.domain.Reservation;
import com.java.pethoster.web.vm.response.ReservationResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReservationMapper {
    @Mapping(target = "proprietaireId", source = "proprietaire.id")
    @Mapping(target = "hebergeurId", source = "hebergeur.id")
    ReservationResponse toResponse(Reservation reservation);
}