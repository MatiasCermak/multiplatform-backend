package com.ubp.streamingmultiplatform.connector.beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class TransactionIdResponseBean extends BaseResponseBean{
    private String transaction_id;
}
