package mariusz.ambroziak.kassistant.products.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import mariusz.ambroziak.kassistant.products.model.ProductData;


public interface ProductDAO {
	public List<ProductData> list();
//	public List<ProductData> getProduktsById(String id);
	public void saveProduct(ProductData p);
//
//	

}
