package mariusz.ambroziak.kassistant.webclients.tesco;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TescoFromFileService {
    @Autowired
    private ResourceLoader resourceLoader;
    private Resource inputFileResource;
    private Resource idsFileResource;


    public Map<Long, Tesco_Product> idsToProducts;
    public Map<String, Tesco_Product> nameToProducts;

    public static String baseUrl = "https://www.tesco.com/groceries/en-GB/products/";

    @Autowired
    public TescoFromFileService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        this.inputFileResource = this.resourceLoader.getResource("classpath:/inputs/productsAll.json");
        idsFileResource = this.resourceLoader.getResource("classpath:/productIds");
        try {
            initializeProductMap();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public int initializeProductMap() throws IOException {
        idsToProducts = new HashMap<>();
        nameToProducts= new HashMap<>();
        String input = getFileContent();

        JSONObject root = new JSONObject(input);
        JSONArray list = root.getJSONArray("data");

        for (int i = 0; i < list.length(); i++) {
            JSONObject jsonProduct = list.getJSONObject(i);
            Tesco_Product tp = parseSingleProduct(jsonProduct);
            int id = jsonProduct.getInt("id");
            tp.setTbnp(id + "");
            tp.setUrl(baseUrl + id);
            idsToProducts.put((long) id, tp);
            nameToProducts.put(tp.getName(),tp);

        }
        return idsToProducts.keySet().size();
    }

    private Tesco_Product parseSingleProduct(JSONObject jsonProduct) {
        JSONArray jsonProductData = jsonProduct.getJSONArray("data");
        Tesco_Product result = new Tesco_Product();
        String department = "";
        String superDepartment = "";
        for (int j = 0; j < jsonProductData.length(); j++) {
            JSONObject jsonProductProperty = jsonProductData.getJSONObject(j);
            String type = jsonProductProperty.getString("@type");
            if (type.equals("Product")) {
                String name = jsonProductProperty.getString("name");
                String description = jsonProductProperty.getString("description");
                JSONObject brandOuter = jsonProductProperty.getJSONObject("brand");
                String brand = brandOuter.getString("name");

                JSONArray imageList = jsonProductProperty.getJSONArray("image");
                String imageUrl = imageList.getString(0);

                result.setBrand(brand);
                result.setImageUrl(imageUrl);
                result.setName(name);
                result.setDescription(description);

            } else if (type.equals("BreadcrumbList")) {
                JSONArray itemListElement = jsonProductProperty.getJSONArray("itemListElement");
                for (int k = 0; k < itemListElement.length(); k++) {
                    JSONObject jsonObjectOfCategory = itemListElement.getJSONObject(k);
                    if ("ListItem".equals(jsonObjectOfCategory.getString("@type")) && jsonObjectOfCategory.getInt("position") == 3) {
                        superDepartment = jsonObjectOfCategory.getJSONObject("item").getString("name");
                        result.setSuperdepartment(superDepartment);
                    }
                    if ("ListItem".equals(jsonObjectOfCategory.getString("@type")) && jsonObjectOfCategory.getInt("position") == 4) {
                        department = jsonObjectOfCategory.getJSONObject("item").getString("name");
                        result.setDepartment(department);
                    }
                }

            }

        }
        return result;
    }

    private String getFileContent() throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = inputFileResource.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));


        String line = br.readLine();

        while (line != null) {
            sb.append(line);
            line = line = br.readLine();
        }


        return sb.toString();

    }

    public Tesco_Product getProduct(long id) {
        if (idsToProducts == null)
            return null;
        else
            return idsToProducts.get(id);
    }

    public Tesco_Product getProductByName(String name) {
        if (nameToProducts == null)
            return null;
        else
            return nameToProducts.get(name);
    }

    public Map<Long, Tesco_Product> getProductCases() {
        Map<Long, Tesco_Product> retValue = new HashMap<>();
        try {

            InputStream inputStream = inputFileResource.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String line = null;

            line = br.readLine();

            while (line != null) {
                long id = Long.parseLong(line);
                retValue.put(id, getProduct(id));
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return retValue;

    }




}
