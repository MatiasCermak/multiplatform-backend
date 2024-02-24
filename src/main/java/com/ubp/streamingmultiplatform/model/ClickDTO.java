package com.ubp.streamingmultiplatform.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class ClickDTO {
    private Integer click_id;
    private Integer user_id;
    private Integer advertisement_id;
    private Integer content_id;
    private String type;
    private Date created_at;
    private Date updated_at;
    private Date deleted_at;
}
