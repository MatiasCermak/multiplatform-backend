package com.ubp.streamingmultiplatform.controller;

import com.ubp.streamingmultiplatform.model.AdvertisementDTO;
import com.ubp.streamingmultiplatform.model.AdvertisementPlanDTO;
import com.ubp.streamingmultiplatform.service.AdvertisementPlanService;
import com.ubp.streamingmultiplatform.service.AdvertisementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/advertisements")
@RequiredArgsConstructor
public class AdvertisementController {
    private final AdvertisementService advertisementService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(advertisementService.list());
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody AdvertisementDTO advertisement) {
        return ResponseEntity.ok(advertisementService.create(advertisement));
    }

    @RequestMapping(value = "{advertisementId}", method = RequestMethod.GET)
    public ResponseEntity<?> retrieve(@PathVariable("advertisementId") Integer advertisementId) {
        return ResponseEntity.ok(advertisementService.retrieve(advertisementId));
    }

    @RequestMapping(value = "serve", method = RequestMethod.GET)
    public ResponseEntity<?> serve(@RequestParam("userId") Integer userId, @RequestParam("pageType") String pageType) {
        return ResponseEntity.ok(advertisementService.serve(userId, pageType));
    }

    @RequestMapping(value = "{advertisementId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> update(@PathVariable("advertisementId") Integer advertisementId, @RequestBody AdvertisementDTO advertisement) {
        return ResponseEntity.ok(advertisementService.update(advertisementId, advertisement));
    }

    @RequestMapping(value = "{advertisementId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("advertisementId") Integer advertisementId) {
        return ResponseEntity.ok(advertisementService.delete(advertisementId));
    }

    @RequestMapping(value = "{advertisementId}/clicked/{userId}", method = RequestMethod.GET)
    public ResponseEntity<?> advertisementClicked(@PathVariable("advertisementId") Integer advertisementId, @PathVariable("userId") Integer userId) {
        return ResponseEntity.ok(advertisementService.clicked(advertisementId, userId));
    }
}
