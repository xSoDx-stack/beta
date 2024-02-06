package ru.pec.china.beta.dto;


import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class PersonDTO {
        private Integer id;
        private String login;
        private String fullName;
        private String role;
    }
