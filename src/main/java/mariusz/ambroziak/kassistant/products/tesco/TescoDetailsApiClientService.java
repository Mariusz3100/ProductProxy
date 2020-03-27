package mariusz.ambroziak.kassistant.products.tesco;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import mariusz.ambroziak.kassistant.products.dao.ProductDAO;
import mariusz.ambroziak.kassistant.products.model.ProductData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class TescoDetailsApiClientService {
	private static final String DETAILS_BASE_URL = "https://dev.tescolabs.com/product/?tpnb=";
	private static final int  productsReturnedLimit=100;

	private static final String headerName="Ocp-Apim-Subscription-Key";
	private static final String headerValue="bb40509242724f799153796d8718c3f3";

	
	@Autowired
	private ResourceLoader resourceLoader;
	
	@Autowired
	private ProductDAO productDao;

	public Map<String,String> map;
	
	
	
	public TescoDetailsApiClientService(ResourceLoader resourceLoader) {
		super();
		this.resourceLoader = resourceLoader;
		map=new HashMap<>();
	}

	public String returnOrRetrieveProductResponseFor(String url){
		String resp=map.get(url);
		if(resp==null){
			resp=getResponse(url);
			map.put(url,resp);
		}
		return resp;
	}


	private String getResponse(String url) {
		ClientConfig cc = new DefaultClientConfig();
		cc.getProperties().put(ClientConfig.PROPERTY_FOLLOW_REDIRECTS, true);

		Client c = Client.create();
		WebResource client = c.resource(url);

		Builder clientWithParamsAndHeader = client.header(headerName, headerValue);

		String response1 ="";

		try{
			response1 = clientWithParamsAndHeader.accept("application/json").get(String.class);
			return response1;

		}catch( com.sun.jersey.api.client.UniformInterfaceException e){
			System.err.println("UniformInterfaceException for url: "+url+". Waiting and retrying");
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



}
