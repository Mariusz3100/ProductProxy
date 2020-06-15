package mariusz.ambroziak.kassistant.webclients.spacy.tokenization;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TokenizationClientService {

	private final RestTemplate restTemplate;

	
	@Autowired
	public TokenizationClientService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	public TokenizationResults parse(String name) {
		if(name==null||name.isEmpty()) {
			 return TokenizationResults.createEmpty();
		}
		TokenizationResults retValue=this.restTemplate.getForObject("http://127.0.0.1:8000/tokenizer?param={param}", TokenizationResults.class,name);

		for(int i=0;i<retValue.getTokens().size();i++){
			if(retValue.getTokens().get(i).getText().equals("tomatoes")){
				retValue.getTokens().get(i).setLemma("tomato");
			}
		}

		return retValue;
	}

	
	public List<String> justTokenize(String name) {
		//	TokenizationResults retValue=this.restTemplate.getForObject("http://127.0.0.1:8000/tokenizer?param={param}", TokenizationResults.class,name);
		TokenizationResults retValue=new TokenizationResults();

		retValue.setPhrase("");
		if(name==null||name.isEmpty()) {
			return new ArrayList<String>();
		}
		else
		{
			String[] split = name.split(" ");
			List<String> tokens = Arrays.asList(split);
			return tokens;
		}

	}
	
	
	
}
