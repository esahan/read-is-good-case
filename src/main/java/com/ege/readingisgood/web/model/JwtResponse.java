package com.ege.readingisgood.web.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtResponse {
    private String token;
    private Long id;
    private String name;
    private String surname;
    private String email;
    private List<String> roles;
    private String tokenType = "Bearer";
}
