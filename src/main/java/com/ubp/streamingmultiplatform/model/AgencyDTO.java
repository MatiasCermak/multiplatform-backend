package com.ubp.streamingmultiplatform.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class AgencyDTO {
    private Integer agency_id;
    private String name;
    private String protocol;
    private String url_service;
    private String secret_token;
    private Boolean active;
    private Integer advertisement_plan_id;
    private Date created_at;
    private Date updated_at;
    private Date deleted_at;
}
