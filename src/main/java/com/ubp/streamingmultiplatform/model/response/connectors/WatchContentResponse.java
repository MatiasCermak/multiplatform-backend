package com.ubp.streamingmultiplatform.model.response.connectors;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WatchContentResponse {
    Integer status;
    String message;
    String visualisationLink;
}
