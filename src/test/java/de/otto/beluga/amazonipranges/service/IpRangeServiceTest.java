package de.otto.beluga.amazonipranges.service;

import de.otto.beluga.amazonipranges.awsips.model.AwsIpPrefix;
import de.otto.beluga.amazonipranges.awsips.model.AwsIpRanges;
import de.otto.beluga.amazonipranges.awsips.service.AwsIpRangesService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class IpRangeServiceTest {

    private final AwsIpRangesService awsIpRangesService = mock(AwsIpRangesService.class);

    private final IpRangeService ipRangeService = new IpRangeService(awsIpRangesService);

    @Test
    @DisplayName("Get Ip ranges returns list of ip ranges")
    public void getIpRangesReturnsListOfIpRanges(){
        // Given
        when(awsIpRangesService.getIpRanges()).thenReturn(getAwsIpRanges());

        // When
        List<String> ipRanges = ipRangeService.getIpRanges("EU");

        // Then
        assertThat(ipRanges, is(List.of("35.180.0.0/16")));

    }

    @Test
    @DisplayName("get Ip ranges returns list of all ip ranges for keyword ALL")
    public void getIpRangesReturnListOfAllIpRanges(){
        // Given
        when(awsIpRangesService.getIpRanges()).thenReturn(getAwsIpRanges());

        // When
        List<String> ipRanges = ipRangeService.getIpRanges("ALL");

        // Then
        assertThat(ipRanges, is(List.of(
                "3.5.140.0/22",
                "15.230.56.104/31",
                "35.180.0.0/16"
        )));
    }

    @Test
    @DisplayName("Get ip ranges throw errors for invalid region value")
    public void getIpRangesThrowErrorForInvalidRegion(){
        // Given
        when(awsIpRangesService.getIpRanges()).thenReturn(getAwsIpRanges());

        // Then
        assertThrows(ResponseStatusException.class, () -> {
           ipRangeService.getIpRanges("Invalid string");
        });
    }

    private static AwsIpRanges getAwsIpRanges() {
        return AwsIpRanges
                .builder()
                .syncToken("awesomeSyncToken")
                .prefixes(new AwsIpPrefix[]{
                        AwsIpPrefix
                                .builder()
                                .ipPrefix("3.5.140.0/22")
                                .region("ap-northeast-2")
                                .networkBorderGroup("ap-northeast-2")
                                .build(),
                        AwsIpPrefix
                                .builder()
                                .ipPrefix("15.230.56.104/31")
                                .region("us-east-1")
                                .networkBorderGroup("us-east-1")
                                .build(),
                        AwsIpPrefix
                                .builder()
                                .ipPrefix("35.180.0.0/16")
                                .region("eu-west-3")
                                .networkBorderGroup("eu-west-3")
                                .build()
                })
                .build();
    }

}