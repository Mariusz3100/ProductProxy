package mariusz.ambroziak.kassistant.webclients.morrisons;

import mariusz.ambroziak.kassistant.hibernate.model.ProductData;

import javax.persistence.*;

@Entity
@DiscriminatorValue("tesco")
public class Morrisons_Response{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pf_id;


	@Column(length = 10000000)
	private String response;

	@Column(length = 200)
	private String url;

	public Long getPf_id() {
		return pf_id;
	}

	public void setPf_id(Long pf_id) {
		this.pf_id = pf_id;
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
