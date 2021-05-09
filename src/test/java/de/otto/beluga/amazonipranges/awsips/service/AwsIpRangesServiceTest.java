package de.otto.beluga.amazonipranges.awsips.service;

import de.otto.beluga.amazonipranges.awsips.model.AwsIpPrefix;
import de.otto.beluga.amazonipranges.awsips.model.AwsIpRanges;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AwsIpRangesServiceTest {

    private final RestTemplate restTemplate = mock(RestTemplate.class);
    private final String ipRangeUrl = "https://ip-ranges.amazonaws.com/ip-ranges.json";
    private final AwsIpRangesService awsIpRangesService = new AwsIpRangesService(restTemplate);

    @Test
    @DisplayName("Get ip ranges should return return aws ip ranges")
    public void getIpRangesShouldReturnAwsIpRanges() {
        // Given
        when(restTemplate.getForEntity(ipRangeUrl, AwsIpRanges.class)).thenReturn(ResponseEntity.ok(getAwsIpRanges()));

        // When
        AwsIpRanges ipRanges = awsIpRangesService.getIpRanges();

        // Then
        assertThat(ipRanges, is(getAwsIpRanges()));

    }

    @Test
    @DisplayName("Get ip ranges should throw error when aws ip ranges unavailable")
    public void getIpRangesShouldThrowErrorWhenAwsIpRangesNotAvailable() {
        // Given
        when(restTemplate.getForEntity(ipRangeUrl, AwsIpRanges.class)).thenThrow(new RestClientException("AWS IP ranges not available"));

        // Then
        assertThrows(ResponseStatusException.class, () -> {
            awsIpRangesService.getIpRanges();
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