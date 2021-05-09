package de.otto.beluga.amazonipranges.awsips.service;

import de.otto.beluga.amazonipranges.TestHelper;
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
        when(restTemplate.getForEntity(ipRangeUrl, AwsIpRanges.class)).thenReturn(ResponseEntity.ok(TestHelper.getAwsIpRanges()));

        // When
        AwsIpRanges ipRanges = awsIpRangesService.getIpRanges();

        // Then
        assertThat(ipRanges, is(TestHelper.getAwsIpRanges()));

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


}