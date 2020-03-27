package mariusz.ambroziak.kassistant.products.tesco;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.json.JSONObject;

import mariusz.ambroziak.kassistant.products.model.ProductData;

@Entity
@DiscriminatorValue("tesco")
public class Tesco_Product extends ProductData{


	private String quantity_string;
	private String superdepartment;
	private String tbnp;


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

	public Tesco_Product(Long pd_id, String name, String detailsUrl, String description, String department,
			String metadata, String inShopId, String quantityString, String superdepartment, String tbnp) {
		super(pd_id, name, detailsUrl, description, department, metadata, inShopId);
		this.quantity_string = quantityString;
		this.superdepartment = superdepartment;
		this.tbnp = tbnp;
	}



	public Tesco_Product() {
		super();
	}
	
	
	//@Override
//	public void updateMetadata() {
//		JSONObject object=new JSONObject(this.metadata);
//		object.put(MetadataConstants.quantityStringNameJsonName, quantityString);
//		object.put(MetadataConstants.superdepartmentNameJsonName, superdepartment);
//
//		this.metadata=object.toString();
//	}
	@Override
	public void setMetadata(String metadata) {
		super.setMetadata(metadata);
		JSONObject object=new JSONObject(this.metadata);
		this.superdepartment=object.getString(MetadataConstants.superdepartmentNameJsonName);
		this.quantity_string=object.getString(MetadataConstants.quantityStringNameJsonName);



	}
	public String getTbnp() {
		return tbnp;
	}
	public void setTbnp(String tbnp) {
		this.tbnp = tbnp;
	}





}
