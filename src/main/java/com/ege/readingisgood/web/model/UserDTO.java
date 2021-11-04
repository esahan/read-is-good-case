package com.ege.readingisgood.web.model;

import com.ege.readingisgood.domain.UserRoles;
import com.ege.readingisgood.web.model.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @Null
    private Long id;

    @NotBlank
    private String type;

    @NotBlank(message = "{validation.user.name.notBlank}")
    @Size(min = 2, max = 50,
            message = "{validation.user.name.size}")
    @Pattern(regexp = "[a-zA-Z]+", message = "{validation.user.name.notAlphanumeric}")
    private String name;

    @NotBlank(message = "{validation.user.surname.notBlank}")
    @Size(min = 2, max = 50,
            message = "{validation.user.surname.size}")
    @Pattern(regexp = "[a-zA-Z]+", message = "{validation.user.surname.notAlphanumeric}")
    private String surname;

    @NotBlank(message = "{validation.user.gender.notBlank}")
    private String gender;

    @NotBlank(message = "{validation.user.email.notBlank}")
    @Email
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @JsonIgnore
    @AssertTrue(message = "{validation.user.gender.isValid}")
    public boolean isGenderValid() {
        return Gender.getByKey(gender).isPresent();
    }

    @JsonIgnore
    @AssertTrue(message = "{validation.user.type.isValid}")
    public boolean isTypeValid() {
        return UserRoles.getByType(type).isPresent();
    }

}
