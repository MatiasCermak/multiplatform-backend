package com.ubp.streamingmultiplatform.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private Integer user_id;
    private String password;
    private String name;
    private String last_name;
    private String email;
    private String role_name;
    private Integer role_id;
    private Integer agency_id;
    private Date verified_at;
    private String partners;
}
