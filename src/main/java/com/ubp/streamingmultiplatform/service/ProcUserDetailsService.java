package com.ubp.streamingmultiplatform.service;

import java.util.Date;
import java.util.List;

import com.ubp.streamingmultiplatform.config.WebSecurityConfig;
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

@Service
@RequiredArgsConstructor
public class ProcUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDTO> userDTOList = userRepository.getUserByEmail(username);
        if(userDTOList == null || userDTOList.isEmpty()) {
            throw new UsernameNotFoundException("The username " + username + " was not found");
        }

        List<SimpleGrantedAuthority> authorities = userDTOList.stream().map( c -> new SimpleGrantedAuthority(c.getRole_name())).toList();
        UserDTO userDTO = userDTOList.get(0);
        return new User(userDTO.getEmail(), userDTO.getPassword(), authorities);
    }

    public UserDTO loadUserByMail(String mail) {
        List<UserDTO> userDTOList = userRepository.getUserByEmail(mail);
        if(userDTOList == null || userDTOList.isEmpty()) {
            throw new UsernameNotFoundException("The mail " + mail + " was not found");
        }

        UserDTO userDTO = userDTOList.get(0);
        userDTO.setPassword(null);
        return userDTO;
    }

}