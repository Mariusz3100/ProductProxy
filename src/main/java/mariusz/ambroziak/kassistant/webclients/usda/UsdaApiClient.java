package mariusz.ambroziak.kassistant.webclients.usda;

import mariusz.ambroziak.kassistant.hibernate.cache.repositories.UsdaResponseRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UsdaApiClient {

    private final RestTemplate restTemplate;
    private final String baseUrl="https://api.nal.usda.gov/fdc/v1/foods/search?api_key=tNW25AtYE2mUbstXNtt64HKw3bb4Gog6ndZiymxV";

    @Value("${apis.used.usda}")
    private String useUsdaApi;

    private int dbCount=0;
    private int apiCount=0;

    @Autowired
    UsdaResponseRepository usdaResponseRepository;

    @Autowired
    public UsdaApiClient(RestTemplateBuilder restTemplateBuilder) {

        this.restTemplate = restTemplateBuilder.build();
    }

    public UsdaResponse findInApi(String query,int size) {
        return findInApi(query,size,null);
    }


    private boolean doWeUseUsdaApi(){
        if(this.useUsdaApi!=null&&this.useUsdaApi.equalsIgnoreCase("true")){
            return true;
        }else{
            return false;
        }
    }

    public UsdaResponse findInApi(String query,int size,List<String> types) {
        if (doWeUseUsdaApi()) {

            if (query.contains("(") || query.contains(")") || query.contains(":") || query.contains("/") || query.contains("$") || query.contains("-"))
                return UsdaResponse.createEmpty();


            JSONObject bodyJson = new JSONObject();
            bodyJson.put("query", query);
            bodyJson.put("pageSize", size);


            if (types != null && !types.isEmpty()) {
                JSONArray dataTypes = new JSONArray();

                types.forEach(t -> dataTypes.put(t));

                bodyJson.put("dataType", dataTypes);
            }


            List<Usda_Response> byQuery = usdaResponseRepository.findByQueryJson(bodyJson.toString());

            if (byQuery != null && !byQuery.isEmpty()) {
                dbCount++;
                //   System.out.print(dbCount+" ");
                Usda_Response usda_response = byQuery.get(0);
                String response = usda_response.getResponse();

                UsdaResponse usdaResponse = UsdaResponse.fromJsonString(response);
                return usdaResponse;
            } else {
                apiCount++;
                System.err.print(apiCount + " ");

                final HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                final HttpEntity<String> entity = new HttpEntity<String>(bodyJson.toString(), headers);
                try {
                    ResponseEntity<UsdaResponse> retValue = this.restTemplate.exchange(baseUrl, HttpMethod.POST, entity, UsdaResponse.class);

                    UsdaResponse fromApi = retValue.getBody();
                    Usda_Response toSave = new Usda_Response();
                    toSave.setQueryJson(bodyJson.toString());
                    toSave.setResponse(fromApi.toJsonString());
                    usdaResponseRepository.save(toSave);
                    return fromApi;
                } catch (Throwable err) {
                    System.err.println("usda error for: " + query);
                    err.printStackTrace();
                }
            }
        }
        return UsdaResponse.createEmpty();
    }




}
