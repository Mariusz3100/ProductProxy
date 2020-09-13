package mariusz.ambroziak.kassistant.webclients.morrisons;

import mariusz.ambroziak.kassistant.hibernate.parsing.model.ProductData;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("morrisons")
public class Morrisons_Product extends ProductData{



	@Column(length = 200)
	private String sku;
	@Column(length = 1200)
	private String ingredients;
	@Column(length = 403)
	private String packageType;

	@Column(length = 2111)
	private String prepAndUsage;



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


	public String getPackageType() {
		return packageType;
	}

	public void setPackageType(String packageType) {
		this.packageType = packageType;
	}

	public String getPrepAndUsage() {
		return prepAndUsage;
	}

	public void setPrepAndUsage(String prepAndUsage) {
		this.prepAndUsage = prepAndUsage;
	}

	public Morrisons_Product(String name, String detailsUrl, String description, String department, String departmentList, String sku, String ingredients) {
		super(name, detailsUrl, description, department);
		this.setDepartmentList(departmentList);
		this.sku = sku;
		this.ingredients = ingredients;
	}

	public Morrisons_Product() {

	}

	@Override
	public ProductData clone() {
		return new Morrisons_Product(""+this.getName(),""+this.getUrl(),
				""+this.getDescription(),""+this.getDepartment(),
				""+this.getDepartmentList(),""+this.getSku(),""+this.getIngredients());
	}
}
