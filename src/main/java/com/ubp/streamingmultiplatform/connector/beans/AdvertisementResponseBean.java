package com.ubp.streamingmultiplatform.connector.beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AdvertisementResponseBean extends BaseResponseBean{
    private String image_url;
    private String redirect_url;

}
