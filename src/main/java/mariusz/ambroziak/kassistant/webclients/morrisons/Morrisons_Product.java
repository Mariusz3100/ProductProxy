package mariusz.ambroziak.kassistant.webclients.morrisons;

import mariusz.ambroziak.kassistant.hibernate.model.ProductData;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@DiscriminatorValue("morrisons")
public class Morrisons_Product extends ProductData{


	@Column(length = 501)
	private String departmentList;
	@Column(length = 200)
	private String sku;
	@Column(length = 1000)
	private String ingredients;


	public String getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(String departmentList) {
		this.departmentList = departmentList;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getIngredients() {
		return ingredients;
	}

	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}

	@Override
	public String getInShopId() {
		return getSku();
	}

	@Override
	public void setInShopId(String id) {
		setSku(id);
	}

	public Morrisons_Product(String name, String detailsUrl, String description, String department, String departmentList, String sku, String ingredients) {
		super(name, detailsUrl, description, department);
		this.departmentList = departmentList;
		this.sku = sku;
		this.ingredients = ingredients;
	}

	public Morrisons_Product() {

	}
}