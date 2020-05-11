package mariusz.ambroziak.kassistant.webclients.edamam.recipes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class RecipeSearchResponse {
    private String q;
    private int from;
    private int to;
    private List<RecipeHitOuter> hits;


    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public List<RecipeHitOuter> getHits() {
        return hits;
    }

    public void setHits(List<RecipeHitOuter> hits) {
        this.hits = hits;
    }

    public String toJsonString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return e.getLocalizedMessage();
        }

    }
}


