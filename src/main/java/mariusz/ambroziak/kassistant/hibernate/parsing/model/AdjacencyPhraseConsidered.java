package mariusz.ambroziak.kassistant.hibernate.parsing.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@DiscriminatorValue("adjacency")
public class AdjacencyPhraseConsidered extends  PhraseConsidered{

    private String phrase;


    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }



}
