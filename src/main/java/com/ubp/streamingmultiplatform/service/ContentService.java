package com.ubp.streamingmultiplatform.service;

import com.ubp.streamingmultiplatform.model.ContentServeDTO;
import com.ubp.streamingmultiplatform.model.request.ContentFilterRequest;
import com.ubp.streamingmultiplatform.model.response.ContentResponse;
import com.ubp.streamingmultiplatform.repository.ContentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@AllArgsConstructor
public class ContentService {

    private ContentRepository contentRepository;

    public List<ContentResponse> serve_content_new_entries(Integer userId) {
        List<ContentServeDTO> contents = contentRepository.serve_content_new_entries(userId);
        return contents.stream().map(this::convertToResponse).toList();
    }

    public List<ContentResponse> serve_content_promoted(Integer userId) {
        List<ContentServeDTO> contents = contentRepository.serve_content_promoted(userId);
        return contents.stream().map(this::convertToResponse).toList();
    }

    public List<ContentResponse> serve_content_most_watched(Integer userId) {
        List<ContentServeDTO> contents = contentRepository.serve_content_most_watched(userId);
        return contents.stream().map(this::convertToResponse).toList();
    }

    public ContentResponse retrieve_content(Integer contentId) {
        ContentServeDTO content = contentRepository.retrieve_content(contentId);
        return convertToResponse(content);
    }

    public List<ContentResponse> filter_content(Integer userId, ContentFilterRequest contentFilterRequest) {
        String wrappedValue = "'" + contentFilterRequest.getValue() + "'";
        String cfrQuery = contentFilterRequest.getField() + "=" + wrappedValue;
        List<ContentServeDTO> contents = contentRepository.filter_content(userId, cfrQuery);
        return contents.stream().map(this::convertToResponse).toList();
    }

    private ContentResponse convertToResponse(ContentServeDTO contentServeDTO) {
        ContentResponse contentResponse = new ContentResponse();
        contentResponse.setContent_id(contentServeDTO.getContent_id());
        contentResponse.setEidr_number(contentServeDTO.getEidr_number());
        contentResponse.setName(contentServeDTO.getName());
        contentResponse.setGenre(contentServeDTO.getGenre());
        contentResponse.setDirector(contentServeDTO.getDirector());
        contentResponse.setYear(contentServeDTO.getYear());
        contentResponse.setTotal_views(contentServeDTO.getTotal_views());
        contentResponse.setImage_url(contentServeDTO.getImage_url());
        contentResponse.setCreated_at(contentServeDTO.getCreated_at());
        contentResponse.setUpdated_at(contentServeDTO.getUpdated_at());

        if (!StringUtils.isEmpty(contentServeDTO.getActors())) {
            String[] actors = contentServeDTO.getActors().split(",");
            contentResponse.setActors(new LinkedList<>(Arrays.asList(actors)));
        }

        if (!StringUtils.isEmpty(contentServeDTO.getActors())) {
            String[] partners = contentServeDTO.getPartners().split(",");
            contentResponse.setPartners(new LinkedList<>(Arrays.asList(partners)));
        }

        return contentResponse;
    }
}
