package mariusz.ambroziak.kassistant.webclients.usda;

import mariusz.ambroziak.kassistant.webclients.edamam.nlp.EdamamNlpResponseData;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UsdaApiClient {

    private final RestTemplate restTemplate;
    private final String baseUrl="https://api.nal.usda.gov/fdc/v1/foods/search?api_key=tNW25AtYE2mUbstXNtt64HKw3bb4Gog6ndZiymxV";

    @Autowired
    public UsdaApiClient(RestTemplateBuilder restTemplateBuilder) {

        this.restTemplate = restTemplateBuilder.build();
    }



    public UsdaResponse findInApi(String query,int size) {

        JSONObject bodyJson=new JSONObject();
        bodyJson.put("query", query);
        bodyJson.put("pageSize", size);

        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final HttpEntity<String> entity = new HttpEntity<String>(bodyJson.toString(),headers);

        ResponseEntity<UsdaResponse> retValue=this.restTemplate.exchange(baseUrl, HttpMethod.POST,entity, UsdaResponse.class);

        return retValue.getBody();
    }




}
