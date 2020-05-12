package mariusz.ambroziak.kassistant.hibernate.model;

import mariusz.ambroziak.kassistant.enums.AmountTypes;
import mariusz.ambroziak.kassistant.enums.ProductType;

import javax.persistence.*;

@Entity
@Table(name = "Ingredient_Phrase_Parsing_Result")
public class IngredientPhraseParsingResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ipprs_id;


    private String originalName;


    private double edamamAmount;

    @Enumerated(EnumType.STRING)
    private AmountTypes edamamAmountType;

    @Enumerated(EnumType.STRING)
    private ProductType edamamProductType;

    @Column(length = 500)
    private String minimalResultsCalculated;
    @Column(length = 500)
    private String extendedResultsCalculated;

    @Enumerated(EnumType.STRING)
    private ProductType typeCalculated;

    public IngredientPhraseParsingResult() {

    }


    public Long getIpprs_id() {
        return ipprs_id;
    }

    public void setIpprs_id(Long ipprs_id) {
        this.ipprs_id = ipprs_id;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public double getedamamAmount() {
        return edamamAmount;
    }

    public void setedamamAmount(double edamamAmount) {
        this.edamamAmount = edamamAmount;
    }

    public AmountTypes getedamamAmountType() {
        return edamamAmountType;
    }

    public void setedamamAmountType(AmountTypes edamamAmountType) {
        this.edamamAmountType = edamamAmountType;
    }

    public ProductType getedamamProductType() {
        return edamamProductType;
    }

    public void setedamamProductType(ProductType edamamProductType) {
        this.edamamProductType = edamamProductType;
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

    public IngredientPhraseParsingResult(String originalName, double edamamAmount, AmountTypes edamamAmountType, ProductType edamamProductType, String minimalResultsCalculated, String extendedResultsCalculated, ProductType typeCalculated) {
        this.originalName = originalName;
        this.edamamAmount = edamamAmount;
        this.edamamAmountType = edamamAmountType;
        this.edamamProductType = edamamProductType;
        this.minimalResultsCalculated = minimalResultsCalculated;
        this.extendedResultsCalculated = extendedResultsCalculated;
        this.typeCalculated = typeCalculated;
    }


}
