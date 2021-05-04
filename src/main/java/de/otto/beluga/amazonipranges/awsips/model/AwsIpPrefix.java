package de.otto.beluga.amazonipranges.awsips.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class AwsIpPrefix {

    @JsonProperty("ip_prefix")
    private String ipPrefix;
    private String region;
    @JsonProperty("network_border_group")
    private String networkBorderGroup;

}
