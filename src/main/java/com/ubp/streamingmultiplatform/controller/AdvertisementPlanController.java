package com.ubp.streamingmultiplatform.controller;

import com.ubp.streamingmultiplatform.model.AdvertisementPlanDTO;
import com.ubp.streamingmultiplatform.service.AdvertisementPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/advertisementPlans")
@RequiredArgsConstructor
public class AdvertisementPlanController {
    private final AdvertisementPlanService advertisementPlanService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(advertisementPlanService.list());
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody AdvertisementPlanDTO advertisementPlan) {
        return ResponseEntity.ok(advertisementPlanService.create(advertisementPlan));
    }

    @RequestMapping(value = "{advertisementPlanId}", method = RequestMethod.GET)
    public ResponseEntity<?> retrieve(@PathVariable("advertisementPlanId") Integer advertisementPlanId) {
        return ResponseEntity.ok(advertisementPlanService.retrieve(advertisementPlanId));
    }

    @RequestMapping(value = "{advertisementPlanId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> update(@PathVariable("advertisementPlanId") Integer advertisementPlanId, @RequestBody AdvertisementPlanDTO advertisementPlan) {
        return ResponseEntity.ok(advertisementPlanService.update(advertisementPlanId, advertisementPlan));
    }

    @RequestMapping(value = "{advertisementPlanId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("advertisementPlanId") Integer advertisementPlanId) {
        return ResponseEntity.ok(advertisementPlanService.delete(advertisementPlanId));
    }
}
