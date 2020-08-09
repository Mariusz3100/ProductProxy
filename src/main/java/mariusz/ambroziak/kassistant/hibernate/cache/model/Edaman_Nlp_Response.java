package mariusz.ambroziak.kassistant.hibernate.cache.model;

import javax.persistence.*;

@Entity
public class Edaman_Nlp_Response {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long enr_id;


	@Column(length = 10000000)
	private String response;

	@Column(length = 200)
	private String ingredientLine;

	public Long getEnr_id() {
		return enr_id;
	}

	public void setEnr_id(Long enr_id) {
		this.enr_id = enr_id;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getIngredientLine() {
		return ingredientLine;
	}

	public void setIngredientLine(String ingredientLine) {
		this.ingredientLine = ingredientLine;
	}

	public Edaman_Nlp_Response(String response, String ingredientLine) {
		this.response = response;
		this.ingredientLine = ingredientLine;
	}

	public Edaman_Nlp_Response() {
	}
}
