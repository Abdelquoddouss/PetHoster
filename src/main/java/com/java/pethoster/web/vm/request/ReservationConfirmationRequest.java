package com.java.pethoster.web.vm.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationConfirmationRequest {
    private boolean confirmer; // true pour confirmer, false pour refuser
    private String message; // Message optionnel (par exemple, une raison de refus)
}