package mariusz.ambroziak.kassistant.webclients.webknox;

import java.util.ArrayList;
import java.util.List;

public class RecipeSearchResult {
	private String title;
	private List<String> ingredient;


	public List<String> getIngredient() {
		return ingredient;
	}

	public void setIngredient(List<String> ingredient) {
		this.ingredient = ingredient;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
