package mariusz.ambroziak.kassistant.webclients.usda;

import mariusz.ambroziak.kassistant.hibernate.repository.UsdaResponseRepository;
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


    private int dbCount=0;
    private int apiCount=0;

    @Autowired
    UsdaResponseRepository usdaResponseRepository;

    @Autowired
    public UsdaApiClient(RestTemplateBuilder restTemplateBuilder) {

        this.restTemplate = restTemplateBuilder.build();
    }



    public UsdaResponse findInApi(String query,int size) {
        List<Usda_Response> byQuery = usdaResponseRepository.findByQuery(query);

        if(byQuery!=null&&!byQuery.isEmpty()){
            dbCount++;
         //   System.out.print(dbCount+" ");
            Usda_Response usda_response = byQuery.get(0);
            String response = usda_response.getResponse();

            UsdaResponse usdaResponse = UsdaResponse.fromJsonString(response);
            return  usdaResponse;
        }else {
            apiCount++;
            System.err.print(apiCount+" ");
            JSONObject bodyJson = new JSONObject();
            bodyJson.put("query", query);
            bodyJson.put("pageSize", size);

            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            final HttpEntity<String> entity = new HttpEntity<String>(bodyJson.toString(), headers);
            try {
                ResponseEntity<UsdaResponse> retValue = this.restTemplate.exchange(baseUrl, HttpMethod.POST, entity, UsdaResponse.class);

                UsdaResponse fromApi = retValue.getBody();
                Usda_Response toSave=new Usda_Response();
                toSave.setQuery(query);
                toSave.setResponse(fromApi.toJsonString());
                usdaResponseRepository.save(toSave);
                return fromApi;
            } catch (Throwable err) {
                err.printStackTrace();
            }
        }
        return UsdaResponse.createEmpty();
    }




}
