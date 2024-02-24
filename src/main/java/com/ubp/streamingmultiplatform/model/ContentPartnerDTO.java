package com.ubp.streamingmultiplatform.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ContentPartnerDTO {
    private Integer content_id;
    private Integer partner_id;
    private Boolean recently_added;
    private Boolean promoted;
    private Integer views;
    private Date created_at;
}
