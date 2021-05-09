package de.otto.beluga.amazonipranges.awsips.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AwsIpRanges {

    private String syncToken;
    private AwsIpPrefix[] prefixes;
}
