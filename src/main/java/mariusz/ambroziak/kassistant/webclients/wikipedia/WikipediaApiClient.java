package mariusz.ambroziak.kassistant.webclients.wikipedia;

import mariusz.ambroziak.kassistant.hibernate.cache.repositories.WikipediaResponseRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import java.util.List;

@Component
public class WikipediaApiClient {
	public final static String baseUrl="https://en.wikipedia.org/api/rest_v1/page/title/";
	public final static String baseUrlForSummary="https://en.wikipedia.org/api/rest_v1/page/summary/";

	@Autowired
	WikipediaResponseRepository wikipediaResponseRepository;

	public String getRedirectIfAny(String original) {
		if(original.equals("")) {
			return "";
		}
		if(original.endsWith(".")) {
			original=original.substring(0,original.length()-1);
		}
		String url=baseUrl+original;
		try {
		String response=getProxiedResponse(url);
		if(response==null||response.isEmpty()){
			return null;
		}
		JSONObject json=new JSONObject(response);
		
		
		if(json.has("items")) {
			JSONArray jsonArray = json.getJSONArray("items");
			if(jsonArray.length()>0) {
				JSONObject jsonObject = jsonArray.getJSONObject(0);
				if(jsonObject.has("comment")) {
					String comment=jsonObject.getString("comment");
					
					if(comment.startsWith("redirect to [[")) {
						String result=comment.substring(14,comment.length()-2);
						return result;
					}else {
						boolean redirect=jsonObject.getBoolean("redirect");

						if(redirect) {
							String urlForSummary=baseUrlForSummary+original;
							
							String summaryResponse=getResponse(urlForSummary);
							JSONObject summaryJson=new JSONObject(summaryResponse);
							
							String title=summaryJson.getString("title");
							return title;
							
						}
					}
					
				}
			}
			
		}
			
		}catch(UniformInterfaceException e) {
			e.printStackTrace();
			if(e.getMessage().endsWith("wikipedia returned a response status of 404 Not Found")) {
				return null;
			}
		}catch(JSONException e) {
			System.err.println("returned inconstent wikipedia response for:"+original);
		}
			
		
		
		
		return null;
	}

	private String getProxiedResponse(String url) {
		List<Wikipedia_Response> byUrl = this.wikipediaResponseRepository.findByUrl(url);

		if(byUrl==null||byUrl.isEmpty()){
			String response=getResponse(url);
			Wikipedia_Response mr=new Wikipedia_Response();
			mr.setUrl(url);
			mr.setResponse(response);

			this.wikipediaResponseRepository.save(mr);

			return response;
		}else{
			return byUrl.get(0).getResponse();
		}
	}

	private String getResponse(String url) {
		ClientConfig cc = new DefaultClientConfig();

		Client c = Client.create();
		WebResource resource = c.resource(url);
		try {
			String response1 = resource.accept("application/json").get(String.class);
			return response1;

		}catch (UniformInterfaceException e){
			System.err.println(e.getMessage());
		}

		return "";
	}


	
	
	public static void main(String[] arg) {

//		String x=getRedirectIfAny("tbsp");
//		String y=getRedirectIfAny("tablespoon");
//		String z=getRedirectIfAny("tbsp.");
		
	//	String z=getRedirectIfAny("onions");
		
		
//		String response=getResponse("https://en.wikipedia.org/api/rest_v1/page/summary/Onions");
//		System.out.println(response);
////		System.out.println(getResponse("https://en.wikipedia.org/api/rest_v1/page/summary/Onions"));
		
		
	}
}
