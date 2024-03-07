package ru.pec.china.beta.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class PersonDTO {

        private Integer id;

        @NotBlank(message = "Логин не может быть пустым")
        @Size(min = 3, max = 20, message = "Логин должен быть от 3 до 20 символов")
        @Pattern(regexp = "^[a-zA-Z.]+$", message = "Логин может состоять только из английских букв")
        private String username;

        @NotBlank(message = "Пароль не может быть пустым")
        @Size(min = 6, max = 8, message = "Длина пароля должна быть не короче 6 и не длиннее 8 символов")
        private String password;

        @NotBlank(message = "Фамилия и инициалы не может быть пустой")
        @Pattern(regexp = "[А-ЯЁа-яё]+ [А-ЯЁ]\\.[А-ЯЁ]\\.",
                message = "Запись в неверном формате, должно быть Фамилия И.О.")
        private String fullName;

        private boolean isActive;

        private ZonedDateTime dateTimeActive;

        @NotEmpty(message = "Выберете роль, она не может быть пустой")
        private List<String> roleId;

        private List<String> roleName;
}
