package mariusz.ambroziak.kassistant.webclients.convert;

import javax.ws.rs.core.MultivaluedMap;

import mariusz.ambroziak.kassistant.enums.AmountTypes;
import mariusz.ambroziak.kassistant.hibernate.cache.repositories.ConvertApi_ResponseRepository;
import mariusz.ambroziak.kassistant.hibernate.cache.repositories.WikipediaResponseRepository;
import mariusz.ambroziak.kassistant.pojos.quantity.QuantityTranslation;
import mariusz.ambroziak.kassistant.utils.ProblemLogger;
import mariusz.ambroziak.kassistant.webclients.rapidapi.RapidApiClient;
import mariusz.ambroziak.kassistant.webclients.wikipedia.Wikipedia_Response;
import mariusz.ambroziak.kassistant.webclients.wordsapi.WordNotFoundException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import java.util.List;


@Component
public class ConvertApiClient extends RapidApiClient {

	public static final String baseUrl="https://community-neutrino-currency-conversion.p.rapidapi.com/convert";
	private static final String header1Value="community-neutrino-currency-conversion.p.rapidapi.com";

	@Value("${apis.used.convert}")
	private String useConvertApi;

	@Autowired
	ConvertApi_ResponseRepository convertApi_responseRepository;


	private static String getResponse(String phrase, AmountTypes targetType)  {
		if(phrase==null||targetType==null)
			return "";


		MultivaluedMap<String,String> formData = new MultivaluedMapImpl();
		formData.add("from-type", phrase);
		formData.add("to-type", targetType.toString());
		formData.add("from-value", "1");



		String response1 ="";

		ClientConfig cc = new DefaultClientConfig();
		cc.getProperties().put(ClientConfig.PROPERTY_FOLLOW_REDIRECTS, true);
		Client c = Client.create();
		WebResource client = c.resource(baseUrl);
		Builder clientWithParamsAndHeader=client.header(header1Name, header1Value).header(header2Name, header2Value);


		try{
			response1 = clientWithParamsAndHeader.accept("application/json").post(String.class,formData);
			return response1;

		}catch( com.sun.jersey.api.client.UniformInterfaceException e){
			e.printStackTrace();
		}


		return response1;
	}

	private boolean doWeUseConvertApi(){
		if(this.useConvertApi!=null&&this.useConvertApi.equalsIgnoreCase("true")){
			return true;
		}else{
			return false;
		}
	}

	public QuantityTranslation checkForTranslation(String phrase) throws WordNotFoundException {
		if(doWeUseConvertApi()) {

			QuantityTranslation fromLocalCache = checkLocalCache(phrase);
			if (fromLocalCache != null)
				return fromLocalCache;

			String response = getProxiedResponse(phrase, AmountTypes.mg);

			//	System.out.println(response);
			if (response != null && !response.isEmpty()) {
				JSONObject json = new JSONObject(response);
				String double1 = json.getString("result");
				if (!double1.isEmpty()) {
					float f = Float.parseFloat(double1);
					return new QuantityTranslation(AmountTypes.mg, f);
				}
			}

			response = getProxiedResponse(phrase, AmountTypes.ml);

			//	System.out.println(response);
			if (response != null && !response.isEmpty()) {
				JSONObject json = new JSONObject(response);
				String double1 = json.getString("result");
				if (!double1.isEmpty()) {

					float f = Float.parseFloat(double1);
					return new QuantityTranslation(AmountTypes.ml, f);
				}
			}
		}
		return null;

	}

	private String getProxiedResponse(String phrase, AmountTypes targetType) {
		List<ConvertApi_Response> fromDb = this.convertApi_responseRepository.findByInputTypeAndOutputType(phrase,targetType.toString());

		if(fromDb==null||fromDb.isEmpty()){
			ConvertApi_Response convertApi_response=new ConvertApi_Response();

			convertApi_response.setInputType(phrase);
			convertApi_response.setOutputType(targetType.toString());

			String response=getResponse(phrase,targetType);
			convertApi_response.setResponse(response);


			this.convertApi_responseRepository.save(convertApi_response);

			return response;
		}else{
			return fromDb.get(0).getResponse();
		}
	}

	private static QuantityTranslation checkLocalCache(String phrase) {
		if("tsp".equals(phrase))
			return new QuantityTranslation(AmountTypes.ml,(float)4.92892159375);

		if("oz".equals(phrase))
			return new QuantityTranslation(AmountTypes.ml,(float)28.3);

		if("tbsp".equals(phrase))
			return new QuantityTranslation(AmountTypes.ml,(float)15);

		return null;
	}

}
