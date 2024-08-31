package com.travelgo.backend.domain.user.dto.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class KakaoGeoResponse {
    public List<Document> documents;

    @Getter
    @Setter
    public static class Document{
        private Address address;

        @Getter
        @Setter
        public static class Address{
            @JsonProperty("address_name")
            private String addressName;
        }
        public Double x;
        public Double y;
    }

    /*@Getter
    @Setter
    public static class Region{
        private String regionType;
        private String region1depthName;
    }*/
}
