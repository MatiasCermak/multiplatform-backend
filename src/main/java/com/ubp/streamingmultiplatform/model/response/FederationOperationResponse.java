package com.ubp.streamingmultiplatform.model.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FederationOperationResponse {
    private Integer status;
    private String message;
    private Integer partnerAdded;

}
