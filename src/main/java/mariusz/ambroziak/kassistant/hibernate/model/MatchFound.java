package mariusz.ambroziak.kassistant.hibernate.model;

import javax.persistence.*;

@Entity
@Table(name = "Match_Found",schema = "parsing")
public class MatchFound {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long mf_id;

    @ManyToOne(cascade = CascadeType.ALL)
    private IngredientPhraseParsingResult ingredient;

    @ManyToOne(cascade = CascadeType.ALL)
    private ProductParsingResult product;

    @ManyToOne(cascade = CascadeType.ALL)
    private ParsingBatch batch;

    private boolean verdict;

    public ParsingBatch getBatch() {
        return batch;
    }

    public void setBatch(ParsingBatch batch) {
        this.batch = batch;
    }

    public long getMf_id() {
        return mf_id;
    }

    public void setMf_id(long mf_id) {
        this.mf_id = mf_id;
    }

    public IngredientPhraseParsingResult getIngredient() {
        return ingredient;
    }

    public void setIngredient(IngredientPhraseParsingResult ingredient) {
        this.ingredient = ingredient;
    }

    public ProductParsingResult getProduct() {
        return product;
    }

    public void setProduct(ProductParsingResult product) {
        this.product = product;
    }


    public boolean isVerdict() {
        return verdict;
    }

    public void setVerdict(boolean verdict) {
        this.verdict = verdict;
    }

    public MatchFound() {

    }
}
