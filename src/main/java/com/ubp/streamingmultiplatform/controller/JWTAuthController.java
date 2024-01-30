package com.ubp.streamingmultiplatform.controller;

import com.ubp.streamingmultiplatform.model.UserDTO;
import com.ubp.streamingmultiplatform.model.request.JWTRequest;
import com.ubp.streamingmultiplatform.model.request.RegisterRequest;
import com.ubp.streamingmultiplatform.model.response.JWTResponse;
import com.ubp.streamingmultiplatform.service.DefaultUserDetailsService;
import com.ubp.streamingmultiplatform.service.ProcUserDetailsService;
import com.ubp.streamingmultiplatform.util.JWTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;


@RestController
@CrossOrigin
@RequestMapping("/auth")
@RequiredArgsConstructor
public class JWTAuthController {
    private final AuthenticationManager authenticationManager;

    private final JWTokenUtil jwtTokenUtil;

    private final ProcUserDetailsService userDetailsService;
    private final DefaultUserDetailsService defaultUserDetailsService;

    @RequestMapping(value = "authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JWTRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getEmail());

        final String token = jwtTokenUtil.generateToken(userDetails);
        final UserDTO user = userDetailsService.loadUserByMail(authenticationRequest.getEmail());

        return ResponseEntity.ok(new JWTResponse(token, user));
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody RegisterRequest user) {
        return ResponseEntity.ok(defaultUserDetailsService.save(user));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
