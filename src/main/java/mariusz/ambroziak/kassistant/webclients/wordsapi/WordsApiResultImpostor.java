package mariusz.ambroziak.kassistant.webclients.wordsapi;


import mariusz.ambroziak.kassistant.pojos.quantity.QuantityTranslation;

public class WordsApiResultImpostor extends WordsApiResult{
	private QuantityTranslation quanTranslation;

	public WordsApiResultImpostor(QuantityTranslation quanTranslation) {
		super();
		this.quanTranslation = quanTranslation;
	}
	
	
	
}
