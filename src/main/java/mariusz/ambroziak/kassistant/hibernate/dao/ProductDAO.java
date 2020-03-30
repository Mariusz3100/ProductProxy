package mariusz.ambroziak.kassistant.hibernate.dao;

import java.util.List;

import mariusz.ambroziak.kassistant.hibernate.model.ProductData;


public interface ProductDAO {
	public List<ProductData> list();
//	public List<ProductData> getProduktsById(String id);
	public void saveProduct(ProductData p);
//
//	

}
