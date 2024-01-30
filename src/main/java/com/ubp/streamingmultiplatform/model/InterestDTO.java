package com.ubp.streamingmultiplatform.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class InterestDTO {
    private Integer interest_id;
    private String name;
    private String description;
    private Date created_at;
    private Date updated_at;
    private Date deleted_at;
}
