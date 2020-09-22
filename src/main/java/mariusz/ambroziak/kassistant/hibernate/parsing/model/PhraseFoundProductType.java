package mariusz.ambroziak.kassistant.hibernate.parsing.model;


import mariusz.ambroziak.kassistant.enums.PhraseFoundDataSource;
import mariusz.ambroziak.kassistant.enums.PhraseFoundProductType_Scope;
import mariusz.ambroziak.kassistant.enums.ProductType;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "PhraseFoundProductType",schema = "parsing")
public class PhraseFoundProductType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pfpt_id;


    @Enumerated(EnumType.STRING)
    @Column(length = 100)
    private ProductType productType;


    @ManyToOne(cascade = CascadeType.ALL)
    private IngredientPhraseParsingResult relatedIngredientResult;

    @ManyToOne(cascade = CascadeType.ALL)
    private ProductParsingResult relatedProductResult;


    private String keywords;

    @Enumerated(EnumType.STRING)
    private PhraseFoundDataSource apiSource;

    @ManyToOne
    private PhraseFound basePhrase;

    private int count;


    private PhraseFoundProductType_Scope scope;

    public Long getPfpt_id() {
        return pfpt_id;
    }

    public void setPfpt_id(Long pfpt_id) {
        this.pfpt_id = pfpt_id;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public IngredientPhraseParsingResult getRelatedIngredientResult() {
        return relatedIngredientResult;
    }

    public void setRelatedIngredientResult(IngredientPhraseParsingResult relatedIngredientResult) {
        this.relatedIngredientResult = relatedIngredientResult;
    }

    public ProductParsingResult getRelatedProductResult() {
        return relatedProductResult;
    }

    public void setRelatedProductResult(ProductParsingResult relatedProductResult) {
        this.relatedProductResult = relatedProductResult;
    }

    public PhraseFound getBasePhrase() {
        return basePhrase;
    }

    public void setBasePhrase(PhraseFound basePhrase) {
        this.basePhrase = basePhrase;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void incrementCount() {
        this.count++;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {

        this.keywords = keywords;
        this.scope=PhraseFoundProductType_Scope.Phrase;

    }

    public PhraseFoundDataSource getApiSource() {
        return apiSource;
    }

    public void setApiSource(PhraseFoundDataSource apiSource) {
        this.apiSource = apiSource;
        this.scope=PhraseFoundProductType_Scope.Phrase;

    }

    public PhraseFoundProductType_Scope getScope() {
        return scope;
    }

    public void setScope(PhraseFoundProductType_Scope scope) {
        this.scope = scope;
    }

    public PhraseFoundProductType(ProductType productType, IngredientPhraseParsingResult relatedIngredientResult, ProductParsingResult relatedProductResult, PhraseFound basePhrase) {
        this.productType = productType;
        this.relatedIngredientResult = relatedIngredientResult;
        this.relatedProductResult = relatedProductResult;
        this.basePhrase = basePhrase;
        this.count = 1;
        this.scope=PhraseFoundProductType_Scope.Result;
    }

    public PhraseFoundProductType() {
       // System.out.println();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhraseFoundProductType)) return false;
        PhraseFoundProductType that = (PhraseFoundProductType) o;
        return getProductType() == that.getProductType() &&
                Objects.equals(getRelatedIngredientResult(), that.getRelatedIngredientResult()) &&
                Objects.equals(getRelatedProductResult(), that.getRelatedProductResult()) &&
                getBasePhrase().equals(that.getBasePhrase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductType(), getRelatedIngredientResult(), getRelatedProductResult(), getBasePhrase());
    }
}
