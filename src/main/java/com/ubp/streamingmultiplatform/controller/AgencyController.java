package com.ubp.streamingmultiplatform.controller;

import com.ubp.streamingmultiplatform.model.AdvertisementPlanDTO;
import com.ubp.streamingmultiplatform.model.AgencyDTO;
import com.ubp.streamingmultiplatform.service.AdvertisementPlanService;
import com.ubp.streamingmultiplatform.service.AgencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/agencies")
@RequiredArgsConstructor
public class AgencyController {
    private final AgencyService agencyService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(agencyService.list());
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody AgencyDTO agency) {
        return ResponseEntity.ok(agencyService.create(agency));
    }

    @RequestMapping(value = "{agencyId}", method = RequestMethod.GET)
    public ResponseEntity<?> retrieve(@PathVariable("agencyId") Integer agencyId) {
        return ResponseEntity.ok(agencyService.retrieve(agencyId));
    }

    @RequestMapping(value = "{agencyId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> update(@PathVariable("agencyId") Integer agencyId, @RequestBody AgencyDTO agency) {
        return ResponseEntity.ok(agencyService.update(agencyId, agency));
    }

    @RequestMapping(value = "{agencyId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("agencyId") Integer agencyId) {
        return ResponseEntity.ok(agencyService.delete(agencyId));
    }
}
