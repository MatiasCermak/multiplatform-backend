package com.ubp.streamingmultiplatform.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContentFilterRequest {
    private String field;
    private String value;
}
