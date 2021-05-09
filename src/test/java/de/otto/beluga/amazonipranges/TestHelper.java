package de.otto.beluga.amazonipranges;

import de.otto.beluga.amazonipranges.awsips.model.AwsIpPrefix;
import de.otto.beluga.amazonipranges.awsips.model.AwsIpRanges;

public class TestHelper {

    public static AwsIpRanges getAwsIpRanges() {
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
