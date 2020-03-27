package mariusz.ambroziak.kassistant.products.model;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "Product_data")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="shop",discriminatorType = DiscriminatorType.STRING)
public abstract class ProductData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pd_id;

    protected String name;
    protected String details_url;
    protected String description;
    protected String department;
    protected String metadata;
    protected String in_shop_id;
    
    public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

//	public abstract void updateMetadata();
	
	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetailsUrl() {
        return details_url;
    }

    public void setDetailsUrl(String detailsUrl) {
        this.details_url = detailsUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

	public String getInShopId() {
		return in_shop_id;
	}

	public void setInShopId(String id) {
		this.in_shop_id = id;
	}

	public ProductData() {
		super();
	}

	public ProductData(Long pd_id, String name, String detailsUrl, String description, String department,
			String metadata, String inShopId) {
		super();
		this.pd_id = pd_id;
		this.name = name;
		this.details_url = detailsUrl;
		this.description = description;
		this.department = department;
		this.metadata = metadata;
		this.in_shop_id = inShopId;
	}
    
    
}
