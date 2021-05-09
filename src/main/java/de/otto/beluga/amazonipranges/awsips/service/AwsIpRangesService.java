package de.otto.beluga.amazonipranges.awsips.service;

import de.otto.beluga.amazonipranges.awsips.model.AwsIpRanges;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AwsIpRangesService {

    private static final String ipRangeUrl = "https://ip-ranges.amazonaws.com/ip-ranges.json";
    private RestTemplate restTemplate;

    @Autowired
    public AwsIpRangesService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public AwsIpRanges getIpRanges(){
        try{
            ResponseEntity<AwsIpRanges> response = restTemplate.getForEntity(ipRangeUrl, AwsIpRanges.class);
            return response.getBody();
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "AWS IP range snot available");
        }
    }
}
