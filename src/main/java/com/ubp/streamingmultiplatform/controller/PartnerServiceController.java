package com.ubp.streamingmultiplatform.controller;

import com.ubp.streamingmultiplatform.model.PartnerDTO;
import com.ubp.streamingmultiplatform.service.PartnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/partner-service")
@RequiredArgsConstructor
public class PartnerServiceController {
    private final PartnerService partnerService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> list() {
        return ResponseEntity.ok(partnerService.list());
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody PartnerDTO partner) {
        return ResponseEntity.ok(partnerService.create(partner));
    }

    @RequestMapping(value = "{partnerId}", method = RequestMethod.GET)
    public ResponseEntity<?> retrieve(@PathVariable("partnerId") Integer partnerId) {
        return ResponseEntity.ok(partnerService.retrieve(partnerId));
    }

    @RequestMapping(value = "{partnerId}", method = RequestMethod.PATCH)
    public ResponseEntity<?> update(@PathVariable("partnerId") Integer partnerId, @RequestBody PartnerDTO partner) {
        return ResponseEntity.ok(partnerService.update(partnerId, partner));
    }

    @RequestMapping(value = "{partnerId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable("partnerId") Integer partnerId) {
        return ResponseEntity.ok(partnerService.delete(partnerId));
    }

}
