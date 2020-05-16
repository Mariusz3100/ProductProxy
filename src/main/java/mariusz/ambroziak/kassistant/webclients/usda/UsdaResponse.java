package mariusz.ambroziak.kassistant.webclients.usda;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class UsdaResponse {
    private List<SingleResult> foods;
    private int currentPage;


    public List<SingleResult> getFoods() {
        return foods;
    }

    public void setFoods(List<SingleResult> foods) {
        this.foods = foods;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }



    public String toJsonString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return e.getLocalizedMessage();
        }

    }
}
