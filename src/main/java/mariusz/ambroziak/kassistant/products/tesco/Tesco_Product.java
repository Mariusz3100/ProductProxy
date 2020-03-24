package mariusz.ambroziak.kassistant.products.tesco;

import org.json.JSONObject;


public class Tesco_Product extends ProductData{


	private String quantityString;
	private String superdepartment;

	public String getQuantityString() {
		return quantityString;
	}
	public void setQuantityString(String quantityString) {
		this.quantityString = quantityString;
	}

	public String getSuperdepartment() {
		return superdepartment;
	}
	public void setSuperdepartment(String superdepartment) {
		this.superdepartment = superdepartment;
	}
	
	
	public Tesco_Product(String name, String detailsUrl, String description, String quantityString, String department,
			String superdepartment) {
		super();
		this.name = name;
		this.detailsUrl = detailsUrl;
		this.description = description;
		this.quantityString = quantityString;
		this.department = department;
		this.superdepartment = superdepartment;
	}
	
	@Override
	public void updateMetadata() {
		JSONObject object=new JSONObject(this.metadata);
		object.put(MetadataConstants.quantityStringNameJsonName, quantityString);
		object.put(MetadataConstants.superdepartmentNameJsonName, superdepartment);
		
		this.metadata=object.toString();
	}
	@Override
	public void setMetadata(String metadata) {
		super.setMetadata(metadata);
		JSONObject object=new JSONObject(this.metadata);
		this.superdepartment=object.getString(MetadataConstants.superdepartmentNameJsonName);
		this.quantityString=object.getString(MetadataConstants.quantityStringNameJsonName);
		
		
		
	}
	
	
	
	

}
