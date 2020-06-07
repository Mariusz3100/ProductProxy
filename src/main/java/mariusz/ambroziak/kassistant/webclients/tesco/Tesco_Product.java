package mariusz.ambroziak.kassistant.webclients.tesco;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import mariusz.ambroziak.kassistant.hibernate.model.ProductData;

import java.util.Objects;

@Entity
@DiscriminatorValue("tesco")
public class Tesco_Product extends ProductData{

	@Column(length = 200)
	private String quantity_string;
	@Column(length = 500)
	private String superdepartment;
	@Column(length = 200)
	private String tbnp;
	@Column(length = 500)
	private String ingredients;
	@Column(length = 500)
	private String searchApiName;
	@Column(length = 200)
	private String imageUrl;


    public String getQuantityString() {
		return quantity_string;
	}
	public void setQuantityString(String quantityString) {
		this.quantity_string = quantityString;
	}

	public String getSuperdepartment() {
		return superdepartment;
	}
	public void setSuperdepartment(String superdepartment) {
		this.superdepartment = superdepartment;
	}


	//	public Tesco_Product(String name, String detailsUrl, String description, String quantityString, String department,
	//			String superdepartment, String tbnp) {
	//		super();
	//		this.name = name;
	//		this.detailsUrl = detailsUrl;
	//		this.description = description;
	//		this.quantityString = quantityString;
	//		this.department = department;
	//		this.superdepartment = superdepartment;
	//		this.tbnp=tbnp;
	//		this.setInShopId(tbnp);
	//		this.metadata="{}";
	//		updateMetadata();
	//	}

//	public Tesco_Product(String name, String detailsUrl, String description, String department,
//			String metadata, String quantityString, String superdepartment, String tbnp) {
//		super(name, detailsUrl, description, department, metadata);
//		this.quantity_string = quantityString;
//		this.superdepartment = superdepartment;
//		this.tbnp = tbnp;
//	}




	@Override
	public Tesco_Product clone() {
		Tesco_Product clone=new Tesco_Product(name,super.getUrl(),super.description,this.getDepartment(),quantity_string,superdepartment,tbnp,ingredients,brand,imageUrl);

		return clone;
	}

	public Tesco_Product() {
		super();
	}

	public String getIngredients() {
		return ingredients;
	}

	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}

	@Override
	public String getInShopId() {
		return getTbnp();
	}

	@Override
	public void setInShopId(String id) {
		setTbnp(id);
	}

	public String getTbnp() {
		return tbnp;
	}
	public void setTbnp(String tbnp) {
		this.tbnp = tbnp;
	}

	public String getSearchApiName() {
		return searchApiName;
	}

	public void setSearchApiName(String searchApiName) {
		this.searchApiName = searchApiName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Tesco_Product)) return false;
		Tesco_Product that = (Tesco_Product) o;
		return getTbnp().equals(that.getTbnp());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getTbnp());
	}

	public Tesco_Product(String name, String detailsUrl, String description, String department,
						 String quantity_string, String superdepartment,
						 String tbnp, String ingredients,String brand,String imageUrl) {
		super(name, detailsUrl, description, department);
		this.quantity_string = quantity_string;
		this.superdepartment = superdepartment;
		this.tbnp = tbnp;
		this.ingredients = ingredients;
		this.brand=brand;
		this.imageUrl=imageUrl;
	}
}
