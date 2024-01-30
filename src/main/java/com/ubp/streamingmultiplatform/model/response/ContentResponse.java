package com.ubp.streamingmultiplatform.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.LinkedList;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContentResponse {
    private Integer content_id;
    private String eidr_number;
    private String name;
    private LinkedList<String> actors;
    private String genre;
    private String director;
    private String year;
    private Date created_at;
    private Date updated_at;
    private String image_url;
    private Integer total_views;
    private LinkedList<String> partners;
}
