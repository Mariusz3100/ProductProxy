package mariusz.ambroziak.kassistant.webclients.webknox;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import mariusz.ambroziak.kassistant.enums.AmountTypes;
import mariusz.ambroziak.kassistant.enums.ProductType;
import mariusz.ambroziak.kassistant.hibernate.parsing.model.IngredientLearningCase;
import mariusz.ambroziak.kassistant.hibernate.cache.repositories.WebknoxResponseRepository;
import mariusz.ambroziak.kassistant.pojos.quantity.QuantityTranslation;
import mariusz.ambroziak.kassistant.webclients.rapidapi.RapidApiClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class RecipeDetailsApiClient extends RapidApiClient {

	public static final String baseUrl="https://webknox-recipes.p.rapidapi.com/recipes/__id__/information";
	private static final String header1Value="webknox-recipes.p.rapidapi.com";


	@Autowired
	WebknoxResponseRepository webknoxResponseRepository;

	private static String getResponseForUrl(String url) {
		ClientConfig cc = new DefaultClientConfig();
		cc.getProperties().put(ClientConfig.PROPERTY_FOLLOW_REDIRECTS, true);

		Client c = Client.create();
		WebResource client = c.resource(url);



		Builder clientWithHeaders = client.header(header1Name, header1Value).header(header2Name, header2Value);

		String response1 ="";

		try{
	//		response1 = clientWithHeaders.accept("application/json").get(String.class);
			return response1;

		}catch( com.sun.jersey.api.client.UniformInterfaceException e){
			e.printStackTrace();

		}


		return response1;
	}

	private String getProxiedResponse(String url) {

		List<WebknoxResponse> byUrl = this.webknoxResponseRepository.findByUrl(url);

		if(byUrl==null||byUrl.isEmpty()){
			String response = getResponseForUrl(url);
			if(response!=null&&!response.trim().isEmpty()) {
				WebknoxResponse wkr = new WebknoxResponse();
				wkr.setUrl(url);
				wkr.setResponse(response);

				this.webknoxResponseRepository.save(wkr);
			}
			return response;
		}else {
			return byUrl.get(0).getResponse();
		}
	}

	public List<IngredientLearningCase> getIngredientCasesForRecipe(String url) {
		List<IngredientLearningCase> retValue=new ArrayList<>();

		
		String response= getProxiedResponse(url);

		List<IngredientLearningCase> ingredientLearningCases = parseResponseIntoIngredientCases(response);
		retValue.addAll(ingredientLearningCases);

		return  retValue;

	}

	public List<IngredientLearningCase> parseResponseIntoIngredientCases(String response) {
		JSONObject root=new JSONObject(response);
		List<IngredientLearningCase> retValue=new ArrayList<>();

		JSONArray results = root.getJSONArray("extendedIngredients");

		for(int i=0;i<results.length();i++){
			JSONObject jsonObject = results.getJSONObject(i);
			String originalString = jsonObject.getString("originalString");
			String original = jsonObject.getString("originalString");
			if(!original.equals(originalString)){
				System.err.println("originals do not match");
			}else{
				String result = jsonObject.getString("originalName");
				float amount = jsonObject.getFloat("amount");
				String unit = jsonObject.getString("unit");

				IngredientLearningCase ilc = new IngredientLearningCase(original, amount, unit, result, ProductType.unknown);
				ilc.setSource("Webknox");
				retValue.add(ilc);


			}
		}

		return retValue;

	}



	private static QuantityTranslation checkLocalCache(String phrase) {
		if("tsp".equals(phrase))
			return new QuantityTranslation(AmountTypes.ml,(float)4.92892159375);
		
		if("oz".equals(phrase))
			return new QuantityTranslation(AmountTypes.ml,(float)28.3);
		
		if("tbsp".equals(phrase))
			return new QuantityTranslation(AmountTypes.ml,(float)15);
		
		return null;
	}

}
