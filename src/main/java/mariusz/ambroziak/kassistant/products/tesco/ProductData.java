package mariusz.ambroziak.kassistant.products.tesco;

public abstract class ProductData {
    protected String name;
    protected String detailsUrl;
    protected String description;
    protected String department;
    protected String metadata;

    
    public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

	public abstract void updateMetadata();
	
	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetailsUrl() {
        return detailsUrl;
    }

    public void setDetailsUrl(String detailsUrl) {
        this.detailsUrl = detailsUrl;
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
    
    
}
