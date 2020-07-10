package mariusz.ambroziak.kassistant.webclients.usda;

import javax.persistence.*;

@Entity
public class Usda_Response {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ur_id;


	@Column(length = 10000000)
	private String response;

	@Column(length = 500)
	private String query;




	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}


	public Long getUr_id() {
		return ur_id;
	}

	public void setUr_id(Long ur_id) {
		this.ur_id = ur_id;
	}
}
