package com.ubp.streamingmultiplatform.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class AdvertisementDTO {
    private Integer advertisement_id;
    private Integer agency_id;
    private Date start_date;
    private Date end_date;
    private String url_image;
    private String file_name;
    private String status;
    private String provided_url;
    private List<InterestDTO> interests;
    private Date created_at;
    private Date updated_at;
    private Date deleted_at;
}
