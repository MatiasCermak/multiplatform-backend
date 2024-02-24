package com.ubp.streamingmultiplatform.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PartnerDTO {
    private Integer partner_id;
    private String name;
    private String protocol;
    private String url_service;
    private Long login_fee;
    private Long register_fee;
    private String secret_token;
    private String image_url;
    private Boolean active;
    private String federation_page;
    private Date created_at;
    private Date updated_at;
    private Date deleted_at;
}
