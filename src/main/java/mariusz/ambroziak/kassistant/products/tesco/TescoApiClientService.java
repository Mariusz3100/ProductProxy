package mariusz.ambroziak.kassistant.products.tesco;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


@Component
public class TescoApiClientService {
	private static final String DETAILS_BASE_URL = "https://dev.tescolabs.com/product/?tpnb=";
	private static final String baseUrl= "https://dev.tescolabs.com/grocery/products/";
	private static final int  productsReturnedLimit=100;

	private static final String headerName="Ocp-Apim-Subscription-Key";
	private static final String headerValue="bb40509242724f799153796d8718c3f3";

	
	@Autowired
	private ResourceLoader resourceLoader;
	private Resource inputFileResource;

	public Map<String,String> map;

	
	
	
	
	public TescoApiClientService(ResourceLoader resourceLoader) {
		super();
		this.resourceLoader = resourceLoader;
		this.inputFileResource = this.resourceLoader.getResource("classpath:/teachingResources/tomatoProducts");;
		map=new HashMap<>();

	}


	private String getResponse(String phrase) {
		ClientConfig cc = new DefaultClientConfig();
		cc.getProperties().put(ClientConfig.PROPERTY_FOLLOW_REDIRECTS, true);

		Client c = Client.create();
		WebResource client = c.resource(baseUrl);

		MultivaluedMap<String, String> queryParams = new MultivaluedMapImpl();
		queryParams.add("query", phrase);
		queryParams.add("offset", "0");
		queryParams.add("limit",Integer.toString(productsReturnedLimit));
		WebResource clientWithParams = client.queryParams(queryParams);
		Builder clientWithParamsAndHeader = clientWithParams.header(headerName, headerValue);

		String response1 ="";

		try{
			response1 = clientWithParamsAndHeader.accept("application/json").get(String.class);
			return response1;

		}catch( com.sun.jersey.api.client.UniformInterfaceException e){
			System.err.println("UniformInterfaceException for term: "+phrase+". Waiting and retrying");
			try{
				response1 = clientWithParamsAndHeader.accept("application/json").get(String.class);
				return response1;

			}catch( com.sun.jersey.api.client.UniformInterfaceException ex){
				System.err.println("Double: "+ex);
				ex.printStackTrace();

			}
		}


		return response1;
	}


	public String returnOrRetrieveProductResponseFor(String query) {
		String resp=map.get(query);
		if(resp==null){
			resp=getResponse(query);
			map.put(query,resp);
		}
		return resp;
	}
}
