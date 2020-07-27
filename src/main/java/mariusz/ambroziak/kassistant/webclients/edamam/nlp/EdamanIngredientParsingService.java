package mariusz.ambroziak.kassistant.webclients.edamam.nlp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


import mariusz.ambroziak.kassistant.enums.ProductType;
import mariusz.ambroziak.kassistant.hibernate.model.IngredientLearningCase;
import mariusz.ambroziak.kassistant.hibernate.model.ProductLearningCase;
import mariusz.ambroziak.kassistant.hibernate.repository.EdamanResponseRepository;
import mariusz.ambroziak.kassistant.hibernate.repository.IngredientPhraseLearningCaseRepository;
import mariusz.ambroziak.kassistant.pojos.quantity.PreciseQuantity;
import mariusz.ambroziak.kassistant.webclients.morrisons.Morrisons_Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.UnknownHttpStatusCodeException;

@Service
public class EdamanIngredientParsingService {

	private final RestTemplate restTemplate;

	@Autowired
	private ResourceLoader resourceLoader;
	private Resource inputFileResource;
	//private Resource outputFileResource;
	private Resource expectedOutputFileResource;
	@Autowired
	private IngredientPhraseLearningCaseRepository ingredientPhraseLearningCaseRepository;

	@Autowired
	private EdamanResponseRepository edamanResponseRepository;

	private final String baseUrl="https://api.edamam.com/api/nutrition-details?app_id=1d006ca9&app_key=d089c348b9338fc421bdc6695ff34e8c";
	public static final String csvSeparator=";";



	@Autowired
	public EdamanIngredientParsingService(RestTemplateBuilder restTemplateBuilder, ResourceLoader resourceLoader) {
		this.restTemplate = restTemplateBuilder.build();

		this.resourceLoader = resourceLoader;

		this.inputFileResource=this.resourceLoader.getResource("classpath:/teachingResources/wordsInput");
		this.expectedOutputFileResource=this.resourceLoader.getResource("classpath:/teachingResources/tomatoIngredients");


	}

	public List<IngredientLearningCase> retrieveDataFromFile() throws IOException {
		InputStream inputStream = expectedOutputFileResource.getInputStream();
		BufferedReader br=new BufferedReader(new InputStreamReader(inputStream));


		String line=br.readLine();
		List<IngredientLearningCase> listOfExpectedResults=new ArrayList<IngredientLearningCase>();

		while(line!=null) {
			if(!line.startsWith("#")) {
				String[] elements = line.split(";", 5);

				String phrase = elements[0];
				String foodFound = elements[1];
				String multiplierString = elements[2];
				String containerPhraseString = elements[3];
				ProductType pt = elements.length <= 4 ? ProductType.unknown : ProductType.parseType(elements[4]);

				float multiplier = Float.parseFloat(multiplierString);
				PreciseQuantity pq = EdamanApiQuantityExtractor.getResultingQuantity(multiplier, containerPhraseString);
				IngredientLearningCase er = new IngredientLearningCase(phrase, pq.getAmount(), containerPhraseString, foodFound, pt);

				listOfExpectedResults.add(er);
			}
			line = br.readLine();

		}

		return listOfExpectedResults;
	}


	public EdamamNlpResponseData findInApi(List<String> ingredientLines) {
		if(ingredientLines.isEmpty()) {
			return EdamamNlpResponseData.createEmpty();
		}
		JSONObject bodyJson=new JSONObject();
		JSONArray jArr=new JSONArray();
		for(String ingLine:ingredientLines) {
			jArr.put(ingLine);
		}

		bodyJson.put("ingr", jArr);

		final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        final HttpEntity<String> entity = new HttpEntity<String>(bodyJson.toString(),headers);

		ResponseEntity<EdamamNlpResponseData> retValue=this.restTemplate.exchange(baseUrl, HttpMethod.POST,entity, EdamamNlpResponseData.class);

		return retValue.getBody();
	}

	public EdamamNlpResponseData find(String param) {
		if(param==null||param.isEmpty()) {
			return EdamamNlpResponseData.createEmpty();
		}

		List<Edaman_Nlp_Response> byingredientLine = edamanResponseRepository.findByingredientLine(param);

		if(byingredientLine!=null&&!byingredientLine.isEmpty()){
			String response = byingredientLine.get(0).getResponse();
			EdamamNlpResponseData edamamNlpResponseData = EdamamNlpResponseData.fromJsonString(response);
			return  edamamNlpResponseData;
		}else {
			List<String> paramList=new ArrayList<String>();
			paramList.add(param);
			EdamamNlpResponseData inApi = findInApi(paramList);
			Edaman_Nlp_Response toSave=new Edaman_Nlp_Response();
			toSave.setResponse(inApi.toJsonString());
			toSave.setIngredientLine(param);
			Edaman_Nlp_Response saved = edamanResponseRepository.save(toSave);
			return inApi;
		}


	}


//	public void retrieveAndSaveEdamanParsingDataFromFile() throws IOException {
//		List<String> lines = readAllIngredientLines();
//
//		EdamamNlpResponseData found = this.findInApi(lines);
//		StringBuilder sb=new StringBuilder();
//		for(EdamamNlpIngredientOuter outer:found.getIngredients()) {
//			String original=outer.getText();
//			for(EdamamNlpSingleIngredientInner inner:outer.getParsed()) {
//				String line=original+csvSeparator+inner.getFoodMatch()+csvSeparator
//						+inner.getQuantity()+csvSeparator+inner.getMeasure();
//				System.out.println(line);
//			}
//		}
//
//
//
//
//	}


	public void retrieveEdamanParsingDataFromFileSequentially() throws IOException {


		InputStream inputStream = inputFileResource.getInputStream();
		BufferedReader br=new BufferedReader(new InputStreamReader(inputStream));


		String line=br.readLine();
		List<String> lines=new ArrayList<String>();

		while(line!=null) {
			if(!line.startsWith("#")) {
				createAndSaveIngredientLearningCase(line);
			}
			line=br.readLine();
		}

//		String name= elements[0];
//
//		String type = elements[2];
//		ProductType foundType = ProductType.parseType(type);
//		String url=elements[1];
//		String minimalExpected = elements[3].toLowerCase();
//		String extendedExpected = elements[4].toLowerCase();
//		ProductLearningCase learningCase=new ProductLearningCase();
//		learningCase.setExtended_words_expected(extendedExpected);
//		learningCase.setMinimal_words_expected(minimalExpected);
//		learningCase.setName(name);
//		learningCase.setType_expected(foundType);
//		learningCase.setUrl(url);




	}

	public void createAndSaveIngredientLearningCase(String line) {
		try {
			EdamamNlpResponseData found = this.find(line);

			for (EdamamNlpIngredientOuter outer : found.getIngredients()) {
				String original = outer.getText();
				for (EdamamNlpSingleIngredientInner inner : outer.getParsed()) {
					String lineOut = original + csvSeparator + inner.getFoodMatch() + csvSeparator
							+ inner.getQuantity() + csvSeparator + inner.getMeasure();
//						System.out.println(lineOut);
//						ProductType.parseType(type)

					original = correctErrors(original);
					String foodMatch = inner.getFoodMatch();
					foodMatch = correctErrors(foodMatch);
					IngredientLearningCase ilc = new IngredientLearningCase(original, inner.getQuantity(), inner.getMeasure(), foodMatch, ProductType.unknown);
					this.ingredientPhraseLearningCaseRepository.save(ilc);
				}
			}
		} catch (UnknownHttpStatusCodeException e) {
			System.out.println(line + ";" + e.getLocalizedMessage());
		}
	}

	private String correctErrors(String phrase) {
		phrase=phrase.replaceFirst("½", "1/2");
		phrase=phrase.replaceFirst("¼", "1/4");
		phrase=phrase.replaceAll("é", "e");
		return phrase;
	}

	private List<String> readAllIngredientLines() throws IOException {
		InputStream inputStream = inputFileResource.getInputStream();
		BufferedReader br=new BufferedReader(new InputStreamReader(inputStream));


		String line=br.readLine();
		List<String> lines=new ArrayList<String>();

		while(line!=null) {
			lines.add(line);
			line=br.readLine();
		}
		return lines;
	}


}
