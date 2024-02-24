package com.ubp.streamingmultiplatform.controller;

import com.ubp.streamingmultiplatform.model.request.ContentFilterRequest;
import com.ubp.streamingmultiplatform.service.ContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/contents")
@RequiredArgsConstructor
public class ContentController {

    private final ContentService contentService;

    @RequestMapping(value = "new-entries/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> serve_content_new_entries(@PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(contentService.serve_content_new_entries(userId));
    }

    @RequestMapping(value = "promoted/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> serve_content_promoted(@PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(contentService.serve_content_promoted(userId));
    }

    @RequestMapping(value = "most-watched/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> serve_content_most_watched(@PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(contentService.serve_content_most_watched(userId));
    }

    @RequestMapping(value = "{contentId}/user/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> retrieve_content(@PathVariable("contentId") Integer contentId, @PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(contentService.retrieve_content(contentId, userId));
    }

    @RequestMapping(value = "filter/user/{userId}", method = RequestMethod.POST)
    public ResponseEntity<?> filter_content(@PathVariable("userId") Integer userId, @RequestBody ContentFilterRequest contentFilterRequest) {
        return ResponseEntity.ok(contentService.filter_content(userId, contentFilterRequest));
    }

    @RequestMapping(value = "{contentId}/clicked/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> advertisementClicked(@PathVariable("contentId") Integer contentId, @PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(contentService.clicked(contentId, userId));
    }
}
