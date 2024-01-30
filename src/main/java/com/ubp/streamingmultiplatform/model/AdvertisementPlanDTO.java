package com.ubp.streamingmultiplatform.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class AdvertisementPlanDTO {
    private String advertisement_plan_id;
    private String name;
    private String description;
    private String features;
    private Long price;
    private Date created_at;
    private Date updated_at;
    private Date deleted_at;
}
