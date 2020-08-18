package mariusz.ambroziak.kassistant.hibernate.parsing.model;

import mariusz.ambroziak.kassistant.enums.ProductType;
import mariusz.ambroziak.kassistant.hibernate.statistical.model.ProductWordOccurence;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Product_Parsing_Result",schema = "parsing")
public class ProductParsingResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ppr_id;

    @OneToOne(cascade = CascadeType.ALL)
    private ProductData product;
    @Column(length = 500)
    private String originalName;

    @OneToOne(cascade = CascadeType.ALL)
    private ParsingBatch parsingBatch;
    @Column(length = 500)
    private String minimalResultsCalculated;
    @Column(length = 500)
    private String extendedResultsCalculated;

    @Enumerated(EnumType.STRING)
    private ProductType typeCalculated;

    @OneToMany(mappedBy = "productParsingResult")
    private List<ProductWordOccurence> wordsOccuring;

    public ProductParsingResult() {

    }

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

    public ProductType getTypeCalculated() {
        return typeCalculated;
    }

    public void setTypeCalculated(ProductType typeCalculated) {
        this.typeCalculated = typeCalculated;
    }

    public ParsingBatch getParsingBatch() {
        return parsingBatch;
    }

    public void setParsingBatch(ParsingBatch parsingBatch) {
        this.parsingBatch = parsingBatch;
    }

    public ProductParsingResult(ProductData product, String originalName, String minimalResultsCalculated, String extendedResultsCalculated, ProductType typeCalculated) {
        this.product = product;
        this.originalName = originalName;
        this.minimalResultsCalculated = minimalResultsCalculated;
        this.extendedResultsCalculated = extendedResultsCalculated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductParsingResult)) return false;
        ProductParsingResult that = (ProductParsingResult) o;
        return Objects.equals(getProduct(), that.getProduct()) &&
                Objects.equals(getOriginalName(), that.getOriginalName()) &&
                Objects.equals(getMinimalResultsCalculated(), that.getMinimalResultsCalculated()) &&
                Objects.equals(getExtendedResultsCalculated(), that.getExtendedResultsCalculated()) &&
                getTypeCalculated() == that.getTypeCalculated();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProduct(), getOriginalName(), getMinimalResultsCalculated(), getExtendedResultsCalculated(), getTypeCalculated());
    }


    public List<ProductWordOccurence> getWordsOccuring() {
        return wordsOccuring;
    }

    public void setWordsOccuring(List<ProductWordOccurence> wordsOccuring) {
        this.wordsOccuring = wordsOccuring;
    }
}
