package com.ubp.streamingmultiplatform.connector.beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AgencyBillBean extends BaseResponseBean{
    private String owed_amount;
}
