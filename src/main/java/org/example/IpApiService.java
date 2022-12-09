package org.example;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.graphql.client.GraphQlClient;
import org.springframework.graphql.client.HttpGraphQlClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class IpApiService {

    @Value("${stepzensource}")
    private String stepzenSourceUrl;

    GraphQlClient client;

    @PostConstruct
    void init() {
        WebClient wc = WebClient.create(stepzenSourceUrl);
        HttpGraphQlClient graphQlClient = HttpGraphQlClient.create(wc);
        client = graphQlClient;
    }

    public LocationDTO findIpDetails(String ipOrDomainName) {
        String document = """
            {
                  ipApi_location(ip: "ARG") {
                ip
                city
                country
                countryCode
                isp
                lat
                lon
              }
            }
        """.replace("ARG", ipOrDomainName);

        return client.document(document)
                .retrieve("ipApi_location")
                .toEntity(LocationDTO.class)
                .block();
    }
}
