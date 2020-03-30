package mariusz.ambroziak.kassistant.hibernate.model;


import mariusz.ambroziak.kassistant.pojos.quantity.PreciseQuantity;

import javax.persistence.*;

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

    @Transient
    protected PreciseQuantity quantity;
    @Transient
    protected PreciseQuantity totalQuantity;


    public PreciseQuantity getQuantity() {
        return quantity;
    }

    public void setQuantity(PreciseQuantity quantity) {
        this.quantity = quantity;
    }

    public PreciseQuantity getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(PreciseQuantity totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public Long getPd_id() {
        return pd_id;
    }

    public void setPd_id(Long pd_id) {
        this.pd_id = pd_id;
    }


	
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

	public abstract String getInShopId();

	public abstract void setInShopId(String id);

	public ProductData() {
		super();
	}

	public ProductData(String name, String detailsUrl, String description, String department) {
		super();
		this.name = name;
		this.details_url = detailsUrl;
		this.description = description;
		this.department = department;
	}
    
    
}
