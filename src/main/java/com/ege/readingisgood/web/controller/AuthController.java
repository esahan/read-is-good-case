package com.ege.readingisgood.web.controller;

import com.ege.readingisgood.security.JwtUtils;
import com.ege.readingisgood.security.UserDetailsImpl;
import com.ege.readingisgood.service.user.UserService;
import com.ege.readingisgood.web.model.JwtResponse;
import com.ege.readingisgood.web.model.LoginRequest;
import com.ege.readingisgood.web.model.ResponseModel;
import com.ege.readingisgood.web.model.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.ege.readingisgood.web.model.ResponseModel.ResponseMessages.CREATED_SUCCESSFULLY;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final JwtUtils jwtUtils;

    @PostMapping(value = "/user/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        JwtResponse build = JwtResponse.builder().id(userDetails.getId()).token(jwt)
                .email(userDetails.getUsername()).roles(roles).name(userDetails.getName())
                .surname(userDetails.getSurname()).build();

        return new ResponseEntity<>(build,HttpStatus.OK);
    }

    @PostMapping("/user/signup")
    public ResponseEntity<ResponseModel<Object>> registerUser(@Valid @RequestBody UserDTO user, UriComponentsBuilder ucBuilder) {

        Long customerAuth = userService.createUserAuth(user);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ucBuilder.path("api/v1/customers/customer/{id}").buildAndExpand(customerAuth).toUri());
        return ResponseModel.buildResponseModel(null, HttpStatus.CREATED,httpHeaders, CREATED_SUCCESSFULLY.getMessage());
    }



}
