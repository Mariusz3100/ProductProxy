package mariusz.ambroziak.kassistant.products.tesco;

import java.util.concurrent.atomic.AtomicLong;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TescoProxyController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@Autowired
	TescoApiClientService searchService;


	@Autowired
	TescoDetailsApiClientService detailsService;

	
	
	@GetMapping("/proxy/search")
	public String search(@RequestParam(value = "query") String name) {
		return searchService.returnOrRetrieveProductResponseFor(name);
	}

	@GetMapping("/proxy/product")
	public String product(@RequestParam(value = "url") String url) {
		return detailsService.returnOrRetrieveProductResponseFor(url);
	}
}
