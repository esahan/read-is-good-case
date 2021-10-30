package com.ege.readingisgood.web.model;

import com.ege.readingisgood.web.model.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

    @Null
    private Long id;

    @NotBlank(message = "{validation.customer.name.notBlank}")
    @Size(min = 2, max = 50,
            message = "{validation.customer.name.size}")
    @Pattern(regexp = "[a-zA-Z]+", message = "{validation.customer.name.notAlphanumeric}")
    private String name;

    @NotBlank(message = "{validation.customer.surname.notBlank}")
    @Size(min = 2, max = 50,
            message = "{validation.customer.surname.size}")
    @Pattern(regexp = "[a-zA-Z]+", message = "{validation.customer.surname.notAlphanumeric}")
    private String surname;

    @NotBlank(message = "{validation.customer.gender.notBlank}")
    private String gender;

    @NotBlank(message = "{validation.customer.email.notBlank}")
    @Email
    private String email;

    @JsonIgnore
    @AssertTrue(message = "{validation.customer.gender.isValid}")
    public boolean isGenderValid() {
        for (Gender genderType: Gender.values()) {
            if (genderType.getKey().equals(this.gender))
                return true;
        }
        return false;
    }

}
