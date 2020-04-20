package mariusz.ambroziak.kassistant.webclients.spacy.tokenization;

public class WordParsed {

    private String word;
    private boolean match;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public boolean isMatch() {
        return match;
    }

    public void setMatch(boolean match) {
        this.match = match;
    }

    public WordParsed(String word, boolean match) {
        this.word = word;
        this.match = match;
    }


    @Override
    public String toString() {
        return  word+" ("+match+")";
    }
}
