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
    @Column(length = 500)
    protected String url;
    @Column(length = 500)
    protected String name;
    @Column(length = 2000)
    protected String description;
    @Column(length = 500)
    protected String department;
    @Column(length = 500)
    protected String brand;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
		this.url = detailsUrl;
		this.description = description;
		this.department = department;
	}


    @Override
    public abstract ProductData clone();

}
