package com.ubp.streamingmultiplatform.service;

import com.ubp.streamingmultiplatform.model.UserDTO;
import com.ubp.streamingmultiplatform.model.request.RegisterRequest;
import com.ubp.streamingmultiplatform.repository.InterestRepository;
import com.ubp.streamingmultiplatform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultUserDetailsService {

    private final UserRepository userRepository;
    private final InterestRepository interestRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDTO save(RegisterRequest user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setLast_name(user.getLast_name());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(passwordEncoder.encode(user.getPassword()));
        userDTO.setRole_id(3);
        userDTO.setAgency_id(null);
        userDTO.setVerified_at(new Date());
        UserDTO createdUser = userRepository.create(userDTO);
        user.getInterests().forEach(interestId -> {
            interestRepository.createInterestUser(interestId, createdUser.getUser_id());
        });
        return createdUser;
    }

}