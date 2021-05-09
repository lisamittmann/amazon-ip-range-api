package de.otto.beluga.amazonipranges.model;

import org.springframework.stereotype.Repository;

public enum AcceptedRegions {
    EU("EU"),
    US("US"),
    AP("AP"),
    CN("CN"),
    SA("SA"),
    AF("AF"),
    CA("CA"),
    ALL("ALL");

    public final String region;

    private AcceptedRegions(String region) {
        this.region = region;
    }

    public static boolean isValidRegion(String region) {
        for(AcceptedRegions regions : values()){
            if(regions.region.equals(region)) {
                return true;
            }
        }

        return false;
    }

}
