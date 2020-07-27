package mariusz.ambroziak.kassistant.webclients.wikipedia;

import javax.persistence.*;

@Entity
public class Wikipedia_Response {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long wr_id;


	@Column(length = 10000000)
	private String response;

	@Column(length = 200)
	private String url;

	public Long getWr_id() {
		return wr_id;
	}

	public void setWr_id(Long wr_id) {
		this.wr_id = wr_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}


	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
}
