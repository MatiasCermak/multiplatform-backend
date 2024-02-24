package com.ubp.streamingmultiplatform.model.response;

import com.ubp.streamingmultiplatform.model.UserDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class JWTResponse implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;
    private final String jwttoken;
    private final UserDTO user;
    private final List<String> partners;
}