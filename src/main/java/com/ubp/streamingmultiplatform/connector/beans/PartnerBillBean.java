package com.ubp.streamingmultiplatform.connector.beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PartnerBillBean extends BaseResponseBean{
    private String register_amount;
    private String login_amount;
    private String owed_amount;
}
