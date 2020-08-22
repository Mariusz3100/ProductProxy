package mariusz.ambroziak.kassistant.webclients.webknox;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import mariusz.ambroziak.kassistant.hibernate.cache.model.Morrisons_Response;
import mariusz.ambroziak.kassistant.hibernate.parsing.model.IngredientLearningCase;
import mariusz.ambroziak.kassistant.hibernate.parsing.repository.IngredientPhraseLearningCaseRepository;
import mariusz.ambroziak.kassistant.hibernate.cache.repositories.WebknoxResponseRepository;
import mariusz.ambroziak.kassistant.webclients.morrisons.Morrisons_Product;
import mariusz.ambroziak.kassistant.webclients.rapidapi.RapidApiClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
public class RecipeSearchApiClient extends RapidApiClient {
	private static final String PRODUCTS_URL_START = "https://webknox-recipes.p.rapidapi.com/recipes/";


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

			List<IngredientLearningCase> toSave=new ArrayList<>();
			ingredientCasesForRecipe.forEach(ilc-> {
				if(ilc.getOriginalPhrase().toLowerCase().indexOf(phrase.toLowerCase())>0)
					toSave.add(ilc);

				});
			Iterable<IngredientLearningCase> ingredientLearningCases = ingredientPhraseLearningCaseRepository.saveAll(toSave);

			retValue.addAll(toSave);
		}

		return retValue;
	}


	private String getProxiedResponse(String phrase) {

		List<WebknoxResponse> byQuery = this.webknoxResponseRepository.findByQuery(phrase);

		if(byQuery==null||byQuery.isEmpty()){
			String response = getResponse(phrase);
			if(response!=null&&!response.trim().isEmpty()) {
				WebknoxResponse wkr = new WebknoxResponse();
				wkr.setQuery(phrase);
				wkr.setResponse(response);

				this.webknoxResponseRepository.save(wkr);
			}
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
		queryParams.add("number", "70");




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




	public List<IngredientLearningCase> saveInDbAllCachedIngredients(){
		List<IngredientLearningCase> retValue=new ArrayList<>();
		List<WebknoxResponse> byStartingWith = this.webknoxResponseRepository.findByUrlStartingWith(PRODUCTS_URL_START);


		for(WebknoxResponse wkr:byStartingWith){
			List<IngredientLearningCase> ingredientLearningCases = recipeDetailsApiClient.parseResponseIntoIngredientCases(wkr.getResponse());
			retValue.addAll(ingredientLearningCases);
		}

		this.ingredientPhraseLearningCaseRepository.saveAll(retValue);
		return  retValue;
	}


	public List<IngredientLearningCase> saveInDbAllCachedIngredientsFor(String phrase){
		List<IngredientLearningCase> retValue=new ArrayList<>();
		List<WebknoxResponse> byStartingWith = this.webknoxResponseRepository.findByUrlStartingWith(PRODUCTS_URL_START);


		for(WebknoxResponse wkr:byStartingWith){
			List<IngredientLearningCase> ingredientLearningCases = recipeDetailsApiClient.parseResponseIntoIngredientCases(wkr.getResponse());
			retValue.addAll(ingredientLearningCases);
		}

		retValue=retValue.stream().filter(ilc->ilc.getOriginalPhrase().contains(phrase)).collect(Collectors.toList());

		this.ingredientPhraseLearningCaseRepository.saveAll(retValue);
		return  retValue;
	}


}
