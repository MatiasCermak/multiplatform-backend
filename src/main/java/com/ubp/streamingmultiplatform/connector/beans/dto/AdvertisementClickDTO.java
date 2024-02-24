package com.ubp.streamingmultiplatform.connector.beans.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class AdvertisementClickDTO {
    private String file_name;
    private Date clicked_date;
}

