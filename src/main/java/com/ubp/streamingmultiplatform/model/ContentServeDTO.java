package com.ubp.streamingmultiplatform.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ContentServeDTO {
    private Integer content_id;
    private String eidr_number;
    private String name;
    private String actors;
    private String genre;
    private String director;
    private String year;
    private String image_url;
    private Date created_at;
    private Date updated_at;
    private Integer total_views;
    private String partners;
}
