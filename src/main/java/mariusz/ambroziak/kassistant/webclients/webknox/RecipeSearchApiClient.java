package mariusz.ambroziak.kassistant.webclients.webknox;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import mariusz.ambroziak.kassistant.enums.AmountTypes;
import mariusz.ambroziak.kassistant.hibernate.model.IngredientLearningCase;
import mariusz.ambroziak.kassistant.hibernate.repository.IngredientPhraseLearningCaseRepository;
import mariusz.ambroziak.kassistant.hibernate.repository.IngredientWordOccurenceRepository;
import mariusz.ambroziak.kassistant.hibernate.repository.WebknoxResponseRepository;
import mariusz.ambroziak.kassistant.pojos.quantity.QuantityTranslation;
import mariusz.ambroziak.kassistant.webclients.rapidapi.RapidApiClient;
import mariusz.ambroziak.kassistant.webclients.wordsapi.WordNotFoundException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;
import java.util.List;


@Component
public class RecipeSearchApiClient extends RapidApiClient {

	public static final String baseUrl="https://webknox-recipes.p.rapidapi.com/recipes/search";
	private static final String header1Value="webknox-recipes.p.rapidapi.com";


	@Autowired
	IngredientPhraseLearningCaseRepository	ingredientPhraseLearningCaseRepository;



	@Autowired
	WebknoxResponseRepository webknoxResponseRepository;

	@Autowired
	RecipeDetailsApiClient recipeDetailsApiClient;




	public List<IngredientLearningCase> getandSaveIngredientsFor(String phrase){
		List<Integer> ids=getPotentianIds(phrase);
		List<IngredientLearningCase> retValue=new ArrayList<>();
		for(Integer id:ids){
			String baseUrl = RecipeDetailsApiClient.baseUrl;
			String url=baseUrl.replaceAll("__id__",id.toString());
			List<IngredientLearningCase> ingredientCasesForRecipe = recipeDetailsApiClient.getIngredientCasesForRecipe(url);
			Iterable<IngredientLearningCase> ingredientLearningCases = ingredientPhraseLearningCaseRepository.saveAll(ingredientCasesForRecipe);
			retValue.addAll(ingredientCasesForRecipe);
		}

		return retValue;
	}


	private String getProxiedResponse(String phrase) {

		List<WebknoxResponse> byQuery = this.webknoxResponseRepository.findByQuery(phrase);

		if(byQuery==null||byQuery.isEmpty()){
			String response = getResponse(phrase);

			WebknoxResponse wkr=new WebknoxResponse();
			wkr.setQuery(phrase);
			wkr.setResponse(response);

			this.webknoxResponseRepository.save(wkr);
			return response;
		}else {
			return byQuery.get(0).getResponse();
		}
	}

	private static String getResponse(String phrase) {
		ClientConfig cc = new DefaultClientConfig();
		cc.getProperties().put(ClientConfig.PROPERTY_FOLLOW_REDIRECTS, true);

		Client c = Client.create();
		WebResource client = c.resource(baseUrl);

		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		queryParams.add("query", phrase);

		WebResource clientWithParams = client.queryParams(queryParams);
		Builder clientWithParamsAndHeader = clientWithParams.header(header1Name, header1Value).header(header2Name, header2Value);

		String response1 ="";



		try{
			response1 = clientWithParamsAndHeader.accept("application/json").get(String.class);
			return response1;

		}catch( com.sun.jersey.api.client.UniformInterfaceException e){
			e.printStackTrace();

		}


		return response1;
	}



	public List<Integer> getPotentianIds(String phrase) {
		List<Integer> retValue=new ArrayList<>();

		
		String response=getProxiedResponse(phrase);

		JSONObject root=new JSONObject(response);

		JSONArray results = root.getJSONArray("results");

		for(int i=0;i<results.length();i++){
			JSONObject jsonObject = results.getJSONObject(i);
			int id = jsonObject.getInt("id");
			retValue.add(id);
		}

		return  retValue;

	}


}
