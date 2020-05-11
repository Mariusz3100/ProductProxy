package mariusz.ambroziak.kassistant.webclients.edamam.recipes;

import mariusz.ambroziak.kassistant.enums.ProductType;
import mariusz.ambroziak.kassistant.pojos.quantity.PreciseQuantity;
import mariusz.ambroziak.kassistant.webclients.spacy.ner.NerResults;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EdamanRecipeSearchService {

	private final RestTemplate restTemplate;

	@Autowired
	private ResourceLoader resourceLoader;
	private Resource inputFileResource;
	//private Resource outputFileResource;
	private Resource expectedOutputFileResource;

	private static final int defaultToValue=10;

	private final String baseUrl="https://api.edamam.com/api/search?q={q}&app_id=1d006ca9&app_key=d089c348b9338fc421bdc6695ff34e8c&from={from}&to={to}";


	private final String csvSeparator=";";



	@Autowired
	public EdamanRecipeSearchService(RestTemplateBuilder restTemplateBuilder, ResourceLoader resourceLoader) {
		this.restTemplate = restTemplateBuilder.build();
		
		this.resourceLoader = resourceLoader;
		
		this.inputFileResource=this.resourceLoader.getResource("classpath:/teachingResources/wordsInput");
		this.expectedOutputFileResource=this.resourceLoader.getResource("classpath:/teachingResources/wordsTestset1");
		RecipeHitOuter x;

	}



	public RecipeSearchResponse findInApi(String param,int from,int to) {
		Map<String,String> urlVariables=new HashMap<>();
		urlVariables.put("from",Integer.toString(from));
		urlVariables.put("to",Integer.toString(to));
		urlVariables.put("q",param);



		RecipeSearchResponse retValue=this.restTemplate.getForObject("https://api.edamam.com/search?q={q}&app_id=af08be14&app_key=2ac175efa4ddfeff85890bed42dff521&{from}=0&to={to}", RecipeSearchResponse.class,urlVariables);

		return retValue;
	}

	public RecipeSearchResponse findInApi(String param) {
		return findInApi(param,0,defaultToValue);
	}
	

	
}
