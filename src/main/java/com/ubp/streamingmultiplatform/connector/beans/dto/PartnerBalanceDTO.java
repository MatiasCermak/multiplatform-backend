package com.ubp.streamingmultiplatform.connector.beans.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PartnerBalanceDTO {
    private Float register_amount;
    private Float login_amount;
    private Float owed_amount;
}
