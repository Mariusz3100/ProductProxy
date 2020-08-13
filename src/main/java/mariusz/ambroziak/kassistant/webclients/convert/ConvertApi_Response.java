package mariusz.ambroziak.kassistant.webclients.convert;

import javax.persistence.*;

@Entity
@Table(name = "ConvertApi_Response",schema = "cache")
public class ConvertApi_Response {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long car_id;


	@Column(length = 10000000)
	private String response;

	@Column(length = 500)
	private String inputType;


	@Column(length = 500)
	private String outputType;


	public Long getCar_id() {
		return car_id;
	}

	public void setCar_id(Long car_id) {
		this.car_id = car_id;
	}


	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public String getOutputType() {
		return outputType;
	}

	public void setOutputType(String outputType) {
		this.outputType = outputType;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
}
