package mariusz.ambroziak.kassistant.webclients.tesco;


import java.util.*;

import mariusz.ambroziak.kassistant.hibernate.model.ProductData;
import mariusz.ambroziak.kassistant.hibernate.repository.ProductRepository;
import mariusz.ambroziak.kassistant.hibernate.repository.TescoProductRepository;
import mariusz.ambroziak.kassistant.pojos.quantity.PreciseQuantity;
import mariusz.ambroziak.kassistant.utils.ProblemLogger;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;


@Component
public class TescoDetailsApiClientService {
    private static final String DETAILS_BASE_URL = "https://dev.tescolabs.com/product/?tpnb=";
//	private static final String DETAILS_BASE_URL = "";
//	private static final String DETAILS_BASE_URL = "http://localhost:8085/proxy/product?url=https://dev.tescolabs.com/product/?tpnb=";


    private static final String PROXY_URL = "";
    private static final int productsReturnedLimit = 100;

    private static final String headerName = "Ocp-Apim-Subscription-Key";
    private static final String headerValue = "bb40509242724f799153796d8718c3f3";


    private ResourceLoader resourceLoader;
    private Resource inputFileResource;
    private TescoApiClientService searchService;
    private TescoProductRepository productRepository;
    public Map<String, String> map;

    @Autowired
    public TescoDetailsApiClientService(ResourceLoader resourceLoader, TescoApiClientService searchService, TescoProductRepository productRepository) {
        this.resourceLoader = resourceLoader;
        this.inputFileResource = this.resourceLoader.getResource("classpath:/teachingResources/tomatoProducts");
        this.searchService = searchService;
        this.productRepository = productRepository;
        map = new HashMap<>();

    }

    private String getResponse(String url) {
        ClientConfig cc = new DefaultClientConfig();
        cc.getProperties().put(ClientConfig.PROPERTY_FOLLOW_REDIRECTS, true);

        Client c = Client.create();
        WebResource client = c.resource(url);

        Builder clientWithParamsAndHeader = client.header(headerName, headerValue);

        String response1 = "";

        try {
            response1 = clientWithParamsAndHeader.accept("application/json").get(String.class);
            return response1;

        } catch (com.sun.jersey.api.client.UniformInterfaceException e) {
            ProblemLogger.logProblem("UniformInterfaceException for url: " + url + ". Waiting and retrying");
            sleep(3000);
            try {
                response1 = clientWithParamsAndHeader.accept("application/json").get(String.class);
                return response1;

            } catch (com.sun.jersey.api.client.UniformInterfaceException ex) {
                System.err.println("Double: " + ex);
                ProblemLogger.logProblem("Double: " + ex);
                ex.printStackTrace();

            }
        }


        return response1;
    }


    public Tesco_Product getProduktByUrl(String url) {
        String response = this.getResponse(url);

        if (response == null || response.equals(""))
            return null;

        JSONObject root = new JSONObject(response);

        JSONArray products = root.getJSONArray("products");

        if (products.length() > 0) {
            JSONObject actualProduct = products.getJSONObject(0);
            Tesco_Product createParticularProduct = createParticularProduct(actualProduct);

            return createParticularProduct;

        }
        return null;
    }


    private float getPrice(JSONObject ApiProdukt, String url) {
        String minPrice = (String) ApiProdukt.get("minimumPrice");
        String maxPrice = (String) ApiProdukt.get("maximumPrice");

        if (minPrice == null || minPrice.equals("") || maxPrice == null || maxPrice.equals(""))
            ProblemLogger.logProblem("Problem with missing price(s) for produkt: " + url);

        if (!minPrice.equals(maxPrice))
            ProblemLogger.logProblem("Problem with max and min price not matching for produkt: " + url);

        float maxFloat = extractFloatPrice(maxPrice);

        return maxFloat;
    }


    private float extractFloatPrice(String stringPrice) {
        stringPrice = stringPrice.replace("$", "");
        float floatPrice = Float.parseFloat(stringPrice);
        return floatPrice;
    }


    private ArrayList<Tesco_Product> parseResponse(String response) {
        JSONObject jsonRoot = new JSONObject(response);


        JSONObject ukJson = jsonRoot.getJSONObject("uk");

        JSONObject jsonGhs = ukJson.getJSONObject("ghs");

        JSONObject jsonProducts = jsonGhs.getJSONObject("products");

        JSONArray jsonProductResultsArray = jsonProducts.getJSONArray("results");

        ArrayList<Tesco_Product> resultProductList = calculateProductList(jsonProductResultsArray);
        return resultProductList;
    }


    private ArrayList<Tesco_Product> calculateProductList(JSONArray jsonProductResultsArray) {
        ArrayList<Tesco_Product> resultList = new ArrayList<Tesco_Product>();


        for (int i = 0; i < jsonProductResultsArray.length(); i++) {
            JSONObject singleProductJson = jsonProductResultsArray.getJSONObject(i);
            Tesco_Product result = createParticularProduct(singleProductJson);

            resultList.add(result);
        }

        return resultList;
    }


    private Tesco_Product createParticularProduct(JSONObject singleProductJson) {
        //String name =singleProductJson.has("name")?singleProductJson.getString("name"):"";
        String detailsUrl = "";
        long tpnb = -1;
        if (singleProductJson.has("tpnb")) {
            tpnb = singleProductJson.getLong("tpnb");
            detailsUrl = DETAILS_BASE_URL + tpnb;

        }
//		String metadata=createMetadata(singleProductJson);
        String description = calculateDescription(singleProductJson);
        float price = singleProductJson.has("price") ? (float) singleProductJson.getDouble("price") : 0;
        String actualDesc = singleProductJson.has("marketingText") ? singleProductJson.getString("marketingText") : "";
        String quantityString = calculateQuantityJspString(singleProductJson, detailsUrl);
        String department = singleProductJson.has("department") ? singleProductJson.getString("department") : "";
        String superDepartment = singleProductJson.has("superDepartment") ? singleProductJson.getString("superDepartment") : "";
//		Tesco_Product result=new Tesco_Product(description,detailsUrl,actualDesc,quantityString,department,superDepartment,tpnb);
        String ingredients = singleProductJson.has("ingredients") ? singleProductJson.getJSONArray("ingredients").getString(0) : "";

        Tesco_Product result = new Tesco_Product(description, detailsUrl, actualDesc, department, quantityString, superDepartment, Long.toString(tpnb), ingredients);

        setQuantities(singleProductJson, result);


        return result;
    }

    private void setQuantities(JSONObject singleProductJson, Tesco_Product result) {
        if (singleProductJson.has("qtyContents")) {
            JSONObject qtyContents = singleProductJson.getJSONObject("qtyContents");
            String quantityMeasure = qtyContents.getString("quantityUom");
            PreciseQuantity resultingQuantity = extractQuantity(qtyContents, quantityMeasure);
            PreciseQuantity resultingTotalQuantity = extractTotalQuantity(qtyContents, quantityMeasure);
//			result.setTotalQuantity(resultingTotalQuantity);
//			result.setQuantity(resultingQuantity);
        }
    }

    private PreciseQuantity extractQuantity(JSONObject qtyContents, String quantityMeasure) throws JSONException {
        if (qtyContents.has("quantity")) {
            float fQuantity = qtyContents.getFloat("quantity");
            //float fQuantity = Float.parseFloat(quantity);
            return TescoQuantityExtractor.getResultingQuantity(fQuantity, quantityMeasure);
        } else {
            return null;
        }

    }

    private PreciseQuantity extractTotalQuantity(JSONObject qtyContents, String quantityMeasure) throws JSONException {
        if (qtyContents.has("totalQuantity")) {
            float fQuantity = qtyContents.getFloat("totalQuantity");
            //float fQuantity = Float.parseFloat(quantity);
            return TescoQuantityExtractor.getResultingQuantity(fQuantity, quantityMeasure);
        } else {
            return null;
        }
    }

    private String calculateDescription(JSONObject singleProductJson) {
        if (singleProductJson.has("description")) {

//		JSONArray jsonArray = singleProductJson.getString("description");
//		String retValue="";
//		for(int i=0;i<jsonArray.length();i++) {
//			String line = jsonArray.getString(i);
//			retValue+=line+"\n";
//		}

//		return retValue;
            return singleProductJson.getString("description");
        } else {
            return "";
        }
    }

    //qtyContents={"quantity":360,"totalQuantity":360,"quantityUom":"g","unitQty":"KG","netContents":"360g"}
    private String calculateQuantityJspString(JSONObject singleProductJson, String detailsUrl) {
        if (singleProductJson.has("qtyContents")) {
            JSONObject qty = singleProductJson.getJSONObject("qtyContents");
            float f = qty.getFloat("quantity");
            String qt = qty.getString("quantityUom");

            return f + " " + qt;
        }
        return "";
    }

    public Tesco_Product getProduktByShopId(String id) {
        return null;
    }

    private static void sleep(long milis) {
        try {
            Thread.sleep(milis);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Tesco_Product getFullDataFromDbOrApi(String url) {

        List<Tesco_Product> found = this.productRepository.findByUrl(url);

        if (!found.isEmpty() && found.get(0) != null) {
            Tesco_Product foundInDb = found.get(0);
            return foundInDb;
        } else {
            Tesco_Product fromApi = getDetailAndSearchDataProductByUrl(url);
            this.productRepository.save(fromApi);
            return fromApi;
        }

    }
//	Tesco Finest Sugardrop Tomatoes 220G

    public Tesco_Product getDetailAndSearchDataProductByUrl(String url) {
        Tesco_Product productDetails = this.getProduktByUrl(url);
        Optional<Tesco_Product> found = null;
        ArrayList<Tesco_Product> productsFor = null;


        found = findProduct_SearchFor_Information(productDetails);

        if (!found.isPresent()) {
            ProblemLogger.logProblem("We could not search/find product with tbnp=" + productDetails.getTbnp());
        } else {
            updateDetailsFromSearchResult(productDetails, found.get());
        }
        return productDetails;

    }

    private Optional<Tesco_Product> findProduct_SearchFor_Information(Tesco_Product productDetails) {
        ArrayList<Tesco_Product> productsFor;
        Optional<Tesco_Product> found;
        productsFor = this.searchService.getProduktsFor(productDetails.getName());
        found = productsFor.stream().filter(pf -> pf.equals(productDetails)).findAny();

        //if not found, try for splitted name
        if (found.isPresent()) {
            return found;
        }

        Iterator<String> parts = Arrays.asList(productDetails.getName().split(" ")).iterator();
        while (parts.hasNext() && !found.isPresent()) {
            productsFor = this.searchService.getProduktsFor(parts.next());
            found = productsFor.stream().filter(pf -> pf.equals(productDetails)).findAny();
        }
        //just in case, keep looking with bigger limit
		if (found.isPresent()) {
			return found;
		}
        productsFor = this.searchService.getProduktsFor(productDetails.getName(), 100);
        found = productsFor.stream().filter(pf -> pf.equals(productDetails)).findAny();
		if (found.isPresent()) {
			return found;
		}
        if (!found.isPresent()) {
            parts = Arrays.asList(productDetails.getName().split(" ")).iterator();
            while (parts.hasNext() && !found.isPresent()) {
                productsFor = this.searchService.getProduktsFor(parts.next(), 100);
                found = productsFor.stream().filter(pf -> pf.equals(productDetails)).findAny();
            }
        }
		if (found.isPresent()) {
			return found;
		}
        productsFor = this.searchService.getProduktsFor(productDetails.getName(), 1000);
        found = productsFor.stream().filter(pf -> pf.equals(productDetails)).findAny();
		if (found.isPresent()) {
			return found;
		}
        if (!found.isPresent()) {
            parts = Arrays.asList(productDetails.getName().split(" ")).iterator();
            while (parts.hasNext() && !found.isPresent()) {
                productsFor = this.searchService.getProduktsFor(parts.next(), 1000);
                found = productsFor.stream().filter(pf -> pf.equals(productDetails)).findAny();
            }
        }
        return found;

    }

    private void updateDetailsFromSearchResult(Tesco_Product productDetails, Tesco_Product tp) {
        if (productDetails.getDepartment() == null || productDetails.getDepartment().isEmpty()) {
            productDetails.setDepartment(tp.getDepartment());
        }

        if (productDetails.getSuperdepartment() == null || productDetails.getSuperdepartment().isEmpty()) {
            productDetails.setSuperdepartment(tp.getSuperdepartment());
        }

        if (productDetails.getDescription() == null || productDetails.getDescription().isEmpty()) {
            productDetails.setDescription(tp.getDescription());
        }

        if (productDetails.getIngredients() == null || productDetails.getIngredients().isEmpty()) {
            productDetails.setIngredients(tp.getIngredients());
        }

    }

    public String returnOrRetrieveProductResponseFor(String url) {
        String resp = map.get(url);
        if (resp == null) {
            resp = getResponse(url);
            map.put(url, resp);
        }
        return resp;
    }


}
