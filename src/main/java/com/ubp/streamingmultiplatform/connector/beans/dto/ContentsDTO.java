package com.ubp.streamingmultiplatform.connector.beans.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class ContentsDTO {
    private String eidr_number;
    private String name;
    private String actors;
    private String genre;
    private String director;
    private String year;
    private String image_url;
    private Boolean promoted;
    private Boolean recently_added;
    private Date created_at;
    private Date updated_at;
    private Date deleted_at;
}
