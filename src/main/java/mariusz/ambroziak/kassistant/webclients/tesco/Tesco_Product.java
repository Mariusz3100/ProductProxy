package mariusz.ambroziak.kassistant.webclients.tesco;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import mariusz.ambroziak.kassistant.hibernate.model.ProductData;

import java.util.Objects;

@Entity
@DiscriminatorValue("tesco")
public class Tesco_Product extends ProductData{


	private String quantity_string;
	private String superdepartment;
	private String tbnp;
	private String ingredients;




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


	public Tesco_Product(String name, String detailsUrl, String description, String department,
						 String quantity_string, String superdepartment,
						 String tbnp, String ingredients) {
		super(name, detailsUrl, description, department);
		this.quantity_string = quantity_string;
		this.superdepartment = superdepartment;
		this.tbnp = tbnp;
		this.ingredients = ingredients;
	}



	public Tesco_Product() {
		super();
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
}
