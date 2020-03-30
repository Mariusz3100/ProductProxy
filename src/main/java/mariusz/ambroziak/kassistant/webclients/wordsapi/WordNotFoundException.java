package mariusz.ambroziak.kassistant.webclients.wordsapi;


public class WordNotFoundException extends Exception{
	private String phrase;

	
	public String getPhrase() {
		return phrase;
	}

	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}

	
	public WordNotFoundException(String phrase) {
		this.phrase=phrase;
	}

}
