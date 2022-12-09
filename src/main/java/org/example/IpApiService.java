package org.example;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.graphql.client.GraphQlClient;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class IpApiService {

    @Value("${graphqlApiUrl}")
    private String graphqlApiUrl;

    GraphQlClient client;

    @PostConstruct
    void init() {
        WebClient wc = WebClient.create(graphqlApiUrl);
        client = HttpGraphQlClient.create(wc);
    }

    public LocationDTO findIpDetails(String ipOrDomainName) {
        String document = """
            query ipQuery($ip: String!) {
              ipApi_location(ip: $ip) {
                ip
                city
                country
                countryCode
                isp
                lat
                lon
              }
            }
        """;

        return client.document(document)
                .variable("ip", ipOrDomainName)
                .retrieve("ipApi_location")
                .toEntity(LocationDTO.class)
                .block();
    }
}
