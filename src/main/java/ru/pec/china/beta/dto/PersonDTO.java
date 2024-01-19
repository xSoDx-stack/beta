package ru.pec.china.beta.dto;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class PersonDTO {
        private int id;
        private String fullName;
        private String role;
    }