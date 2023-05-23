package uz.jaloliddin.bankomat.service.dto;

import uz.jaloliddin.bankomat.domain.enumeration.Type;

import javax.validation.constraints.NotNull;
import java.time.Instant;

public class CardDTO {

    private Long id;

    @NotNull
    private String cardNumber;

    @NotNull
    private Integer cvv;

    private String lastName;

    private String firstName;

    @NotNull
    private Instant expiryDate;

    @NotNull
    private String password;

    private Type type;

    private Boolean active;

}
