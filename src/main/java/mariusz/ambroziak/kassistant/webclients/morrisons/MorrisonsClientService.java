package mariusz.ambroziak.kassistant.webclients.morrisons;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import mariusz.ambroziak.kassistant.constants.MetadataConstants;
import mariusz.ambroziak.kassistant.hibernate.cache.model.Morrisons_Response;
import mariusz.ambroziak.kassistant.hibernate.parsing.repository.MorrisonProductRepository;
import mariusz.ambroziak.kassistant.hibernate.cache.repositories.MorrisonsResponseRepository;
import mariusz.ambroziak.kassistant.utils.ProblemLogger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;


@Service
public class MorrisonsClientService {
    private static final String DETAILS_BASE_URL = "https://groceries.morrisons.com/webshop/api/v1/products/__sku__/details";
    private static final String SEARCH_BASE_URL = "https://groceries.morrisons.com/webshop/api/v1/search?searchTerm=";
    private static final String PRODUCTS_URL_START = "https://groceries.morrisons.com/webshop/api/v1/products/";
//	private static final String DETAILS_BASE_URL = "";
//	private static final String DETAILS_BASE_URL = "http://localhost:8085/proxy/product?url=https://dev.tescolabs.com/product/?tpnb=";

    @Autowired
    MorrisonsResponseRepository morrisonsResponseRepository;

    @Autowired
    MorrisonProductRepository morrisonProductRepository;

    private int count=0;
    private String getProxiedResponse(String url) {
        List<Morrisons_Response> byUrl = this.morrisonsResponseRepository.findByUrl(url);

        if(byUrl==null||byUrl.isEmpty()){
            String response=getResponse(url);
            Morrisons_Response mr=new Morrisons_Response();
            mr.setUrl(url);
            mr.setResponse(response);

            this.morrisonsResponseRepository.save(mr);

            return response;
        }else{
            return byUrl.get(0).getResponse();
        }

    }



    private String getResponse(String url) {
        ClientConfig cc = new DefaultClientConfig();
        cc.getProperties().put(ClientConfig.PROPERTY_FOLLOW_REDIRECTS, true);

        Client c = Client.create();
        WebResource client = c.resource(url);


        String response1 = "";

        try {

            int randomWait=(int)(Math.random()*5000)+10000;
            sleep(randomWait);
            ++count;
            response1 = client.accept("application/json").get(String.class);
            return response1;

        } catch (com.sun.jersey.api.client.UniformInterfaceException e) {
         //   ProblemLogger.logProblem("UniformInterfaceException for url: " + url + ". Waiting and retrying");
            sleep(3000);
            try {
                response1 = client.accept("application/json").get(String.class);
                return response1;

            } catch (com.sun.jersey.api.client.UniformInterfaceException ex) {
                System.err.println("Double: " + ex);
                ProblemLogger.logProblem("Double: " + ex);
                ex.printStackTrace();

            }
        }


        return response1;
    }

    private static void sleep(long milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Map<String,String> getPotentialSkus(String phrase) throws UnsupportedEncodingException {
        String url=SEARCH_BASE_URL+URLEncoder.encode(phrase, StandardCharsets.UTF_8.toString());
        Map<String,String> retValue=new HashMap<>();
        String response=getProxiedResponse(url);

        JSONObject root=new JSONObject(response);
        JSONObject mainFopCollection = root.getJSONObject("mainFopCollection");

        JSONArray sections = mainFopCollection.getJSONArray("sections");

        List<String> expectedWords = Arrays.asList(phrase.split(" "));
        for(int i=0;i<sections.length();i++){
            JSONArray fops=sections.getJSONObject(i).getJSONArray("fops");

            for(int j=0;j<fops.length();j++){
                if(!fops.getJSONObject(j).has("product")) {
                    System.out.println();
                }else{

                    JSONObject product = fops.getJSONObject(j).getJSONObject("product");
                    ;
                    String sku = product.getString("sku");
                    String name = product.getString("name");

                    if (expectedWords.stream().filter(ew -> !name.toLowerCase().contains(ew.toLowerCase())).count() == 0) {
                        retValue.put(name, sku);
                    }
                }

            }

        }

        return retValue;

    }


    public List<Morrisons_Product> searchInDbAndApiFor(String phrase){
        String[] phraseSplit=phrase.split(" ");
        List<Morrisons_Product> retValue=new ArrayList<>();
        Arrays.asList(phraseSplit).forEach(s->retValue.addAll(this.morrisonProductRepository.findByNameContaining(s)));

        if(retValue.isEmpty()){
            retValue.addAll(this.searchInApiFor(phrase));

            retValue.forEach(mp-> saveMorrisonsProduct(mp));
        }

        return retValue;


    }

    private Morrisons_Product saveMorrisonsProduct(Morrisons_Product mp) {
        return this.morrisonProductRepository.save(mp);
    }

    public List<Morrisons_Product> saveInDbAllCachedProducts(){
        List<Morrisons_Product> retValue=new ArrayList<>();
        List<Morrisons_Response> byStartingWith = this.morrisonsResponseRepository.findByUrlStartingWith(PRODUCTS_URL_START);


        for(Morrisons_Response mr:byStartingWith){
            Morrisons_Product product = parseResponseIntoProductObject(mr.getUrl(), mr.getResponse());
            this.morrisonProductRepository.save(product);
            retValue.add(product);
        }

        return  retValue;
    }

    public List<Morrisons_Product> searchInApiFor(String phrase){
        List<Morrisons_Product> retValue=new ArrayList<>();

        try {
            Map<String, String> potentialSkus = getPotentialSkus(phrase);
            for(String sku:potentialSkus.values()){
                String url=DETAILS_BASE_URL.replace("__sku__",sku);
                Morrisons_Product productForUrl = this.getProductForUrl(url);
                retValue.add(productForUrl);
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return retValue;
    }

    public Morrisons_Product getProductForUrl(String url) {
        String response=getProxiedResponse(url);

        Morrisons_Product retValue = parseResponseIntoProductObject(url, response);
        return retValue;


    }

    private Morrisons_Product parseResponseIntoProductObject(String url, String response) {
        JSONObject root=new JSONObject(response);

        JSONObject product = root.getJSONObject("product");
        String sku=product.getString("sku");
        String name = product.getString("name");

        String brand = product.getJSONObject("brand").getString("name");

        String categoryList = product.getString("mainCategory");
        String[] split = categoryList.split("/");
        String lastCategory=split[split.length-1];

        JSONObject backOfPack = root.getJSONObject("backOfPack");


        JSONObject bopFields = backOfPack.getJSONObject("bopFields");
        String ingredients = "";
        if(bopFields.has("ingredients")) {
            ingredients=bopFields.getJSONArray("ingredients").getJSONObject(0).getString("content");
        }
        String description="";
        if(bopFields.has("description")) {
            JSONArray description1 = bopFields.getJSONArray("description");
            for(int i=0;i<description1.length();i++){
                if("".equals(description1.getJSONObject(i).getString("title")))
                description+=description1.getJSONObject(i).getString("content")+"\n";
            }
        }
        String packageType="";
        String prepAndUsage="";

        if(bopFields.has("storageAndUsage")) {
            JSONArray json = bopFields.getJSONArray("storageAndUsage");
            for(int i=0;i<json.length();i++){
                if("Package Type".equals(json.getJSONObject(i).getString("title")))
                    packageType+=json.getJSONObject(i).getString("content")+ MetadataConstants.stringListSeparator;
            }

            for(int i=0;i<json.length();i++){
                if("Preparation and Usage".equals(json.getJSONObject(i).getString("title")))
                    prepAndUsage+=json.getJSONObject(i).getString("content")+ MetadataConstants.stringListSeparator;
            }

        }

        Morrisons_Product retValue=new Morrisons_Product(name,url,description,lastCategory,categoryList,sku,ingredients);

        retValue.setDepartmentList(categoryList);
        retValue.setBrand(brand);
        retValue.setPackageType(packageType);
        retValue.setPrepAndUsage(prepAndUsage);
        return retValue;
    }

    public static void main(String[] args){
        String url="https://groceries.morrisons.com/webshop/api/v1/products/218005011/details";
        String x=new MorrisonsClientService().getProxiedResponse(url);

        System.out.println(x);

    }

}
