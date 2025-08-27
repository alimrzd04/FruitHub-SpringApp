package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class Quantity {

    @NotBlank(message = "Name must not be empty")
    private String name;
    @NotBlank(message = "Abbreviation must not be empty")
    private String abbreviation;
    @NotBlank
    private UUID statusId;
}
