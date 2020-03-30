package mariusz.ambroziak.kassistant.hibernate.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import mariusz.ambroziak.kassistant.hibernate.model.ProductData;
import mariusz.ambroziak.kassistant.webclients.tesco.Tesco_Product;


@Component
public class ProductDAOImpl implements ProductDAO {
	
	
    @Autowired
    private EntityManager entityManager;

    private Session getSession() {
        return entityManager.unwrap(Session.class);
    }
	
	

	@Override
	public List<ProductData> list() {
		@SuppressWarnings("unchecked")
		List<ProductData> listProdukt = (List<ProductData>) getSession()
				.createCriteria(ProductData.class)
				.list();

		return listProdukt;
	}
//
//	@Override
//	public List<ProductData> getProduktsById(String id) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
	@Override
	public void saveProduct(ProductData produkt) {
		if(produkt!=null){
			if(produkt instanceof Tesco_Product) {
				
			}
			
			this.getSession().save(produkt);
		}else{
			System.err.println("Problem przy zapisywaniu danych produktu");
		}
		
	}

}
