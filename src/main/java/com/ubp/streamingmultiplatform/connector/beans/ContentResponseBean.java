package com.ubp.streamingmultiplatform.connector.beans;

import com.ubp.streamingmultiplatform.connector.beans.dto.ContentsDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;

@NoArgsConstructor
@Getter
@Setter
public class ContentResponseBean extends BaseResponseBean {
    private LinkedList<ContentsDTO> programming;
}
