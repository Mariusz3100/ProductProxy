package mariusz.ambroziak.kassistant.hibernate.model;

import javax.persistence.*;

@Entity
@Table(name = "Product_Parsing_Result")
public class ProductParsingResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ppr_id;

    @OneToOne(cascade = CascadeType.ALL)
    private ProductData product;
    @Column(length = 500)
    private String originalName;

    @Column(length = 500)
    private String minimalResultsCalculated;
    @Column(length = 500)
    private String extendedResultsCalculated;


    public Long getPpr_id() {
        return ppr_id;
    }

    public void setPpr_id(Long ppr_id) {
        this.ppr_id = ppr_id;
    }

    public ProductData getProduct() {
        return product;
    }

    public void setProduct(ProductData product) {
        this.product = product;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getMinimalResultsCalculated() {
        return minimalResultsCalculated;
    }

    public void setMinimalResultsCalculated(String minimalResultsCalculated) {
        this.minimalResultsCalculated = minimalResultsCalculated;
    }

    public String getExtendedResultsCalculated() {
        return extendedResultsCalculated;
    }

    public void setExtendedResultsCalculated(String extendedResultsCalculated) {
        this.extendedResultsCalculated = extendedResultsCalculated;
    }

    public ProductParsingResult(ProductData product, String originalName, String minimalResultsCalculated, String extendedResultsCalculated) {
        this.product = product;
        this.originalName = originalName;
        this.minimalResultsCalculated = minimalResultsCalculated;
        this.extendedResultsCalculated = extendedResultsCalculated;
    }
}
