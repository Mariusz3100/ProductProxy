package mariusz.ambroziak.kassistant.webclients.usda;

import javax.persistence.*;

@Entity
@Table(name = "Usda_Response",schema = "cache")
public class Usda_Response {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ur_id;


	@Column(length = 10000000)
	private String response;

	@Column(length = 500)
	private String queryJson;




	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getQueryJson() {
		return queryJson;
	}

	public void setQueryJson(String queryJson) {
		this.queryJson = queryJson;
	}

	public Long getUr_id() {
		return ur_id;
	}

	public void setUr_id(Long ur_id) {
		this.ur_id = ur_id;
	}
}
