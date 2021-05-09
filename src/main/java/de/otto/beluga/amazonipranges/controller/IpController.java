package de.otto.beluga.amazonipranges.controller;

import de.otto.beluga.amazonipranges.service.IpRangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/iprange")
public class IpController {

    private final IpRangeService ipRangeService;

    @Autowired
    public IpController(IpRangeService ipRangeService) {
        this.ipRangeService = ipRangeService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> getIpRanges(@RequestParam String region){
        String ipRanges = ipRangeService.getIpRanges(region);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(new MediaType("text", "plain"));
        return new ResponseEntity<>(ipRanges, responseHeaders, HttpStatus.OK);
    }
}
