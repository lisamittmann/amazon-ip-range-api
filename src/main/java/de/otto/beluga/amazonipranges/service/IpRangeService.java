package de.otto.beluga.amazonipranges.service;

import de.otto.beluga.amazonipranges.awsips.model.AwsIpPrefix;
import de.otto.beluga.amazonipranges.awsips.service.AwsIpRangesService;
import de.otto.beluga.amazonipranges.model.AcceptedRegions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IpRangeService {

    private AwsIpRangesService awsIpRangesService;

    @Autowired
    public IpRangeService(AwsIpRangesService awsIpRangesService) {
        this.awsIpRangesService = awsIpRangesService;
    }

    public String getIpRanges(String region){
        if(!AcceptedRegions.isValidRegion(region)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid region");
        }

        AwsIpPrefix[] ipRanges = awsIpRangesService.getIpRanges().getPrefixes();

        if(region.equals("ALL")){
            return Arrays
                    .stream(ipRanges)
                    .map(prefix -> prefix.getIpPrefix())
                    .collect(Collectors.joining("\n"));

        }

        return Arrays
                .stream(ipRanges)
                .filter(prefix -> prefix.getRegion().contains(region.toLowerCase()))
                .map(prefix -> prefix.getIpPrefix())
                .collect(Collectors.joining("\n"));

    }
}
