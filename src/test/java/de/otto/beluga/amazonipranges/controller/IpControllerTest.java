package de.otto.beluga.amazonipranges.controller;

import de.otto.beluga.amazonipranges.TestHelper;
import de.otto.beluga.amazonipranges.awsips.model.AwsIpRanges;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IpControllerTest {

    @LocalServerPort
    private int serverPort;

    private final String awsIpRangeUrl = "https://ip-ranges.amazonaws.com/ip-ranges.json";

    private String getUrl() {return "http://localhost:" + serverPort + "/api/iprange";}

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @DisplayName("get Ip ranges returns list of ip ranges")
    public void getIpRangesReturnsListOfIpRanges(){
        // Given
        when(restTemplate.getForEntity(awsIpRangeUrl, AwsIpRanges.class)).thenReturn(ResponseEntity.ok(TestHelper.getAwsIpRanges()));

        // When
        ResponseEntity<String> response = testRestTemplate.getForEntity(getUrl() + "?region=ALL", String.class);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is("3.5.140.0/22\n15.230.56.104/31\n35.180.0.0/16"));
    }

    @Test
    @DisplayName("get Ip ranges for specific region return list of ip ranges")
    public void getIpRangesWithRequestParamReturnsFilteredList(){
        // Given
        when(restTemplate.getForEntity(awsIpRangeUrl, AwsIpRanges.class)).thenReturn(ResponseEntity.ok(TestHelper.getAwsIpRanges()));

        // When
        ResponseEntity<String> response = testRestTemplate.getForEntity(getUrl() + "?region=EU", String.class);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), is("35.180.0.0/16"));
    }

    @Test
    @DisplayName("get Ip ranges for invalid request param throws error")
    public void getIpRangesWithInvalidParamThrowsError(){
        // Given
        when(restTemplate.getForEntity(awsIpRangeUrl, AwsIpRanges.class)).thenReturn(ResponseEntity.ok(TestHelper.getAwsIpRanges()));

        // When
        ResponseEntity<String> response = testRestTemplate.getForEntity(getUrl() + "?region=cookies", String.class);

        // Then
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

}