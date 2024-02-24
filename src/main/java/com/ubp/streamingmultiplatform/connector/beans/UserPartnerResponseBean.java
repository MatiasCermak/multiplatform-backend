package com.ubp.streamingmultiplatform.connector.beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserPartnerResponseBean extends BaseResponseBean {
    String federation_type;
    String identity_key;
}
