package mariusz.ambroziak.kassistant.hibernate.statistical.model;

import mariusz.ambroziak.kassistant.hibernate.parsing.model.IngredientPhraseParsingResult;
import mariusz.ambroziak.kassistant.hibernate.parsing.model.ProductParsingResult;

import javax.persistence.*;

@Entity
@Table(name = "IgnoredProductWordOccurence",schema = "statistics")
public class IgnoredProductWordOccurence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ipwo_id;

    @ManyToOne
    private Word word;


    @OneToOne(cascade = CascadeType.ALL)
    ProductParsingResult productParsingResult;
    @OneToOne(cascade = CascadeType.ALL)
    IngredientPhraseParsingResult ingredientPhraseParsingResult;

    public Long getIpwo_id() {
        return ipwo_id;
    }

    public void setIpwo_id(Long ipwo_id) {
        this.ipwo_id = ipwo_id;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public ProductParsingResult getProductParsingResult() {
        return productParsingResult;
    }

    public void setProductParsingResult(ProductParsingResult productParsingResult) {
        this.productParsingResult = productParsingResult;
    }

    public IngredientPhraseParsingResult getIngredientPhraseParsingResult() {
        return ingredientPhraseParsingResult;
    }

    public void setIngredientPhraseParsingResult(IngredientPhraseParsingResult ingredientPhraseParsingResult) {
        this.ingredientPhraseParsingResult = ingredientPhraseParsingResult;
    }

    public IgnoredProductWordOccurence(Word word, ProductParsingResult productParsingResult) {
        this.word = word;
        this.productParsingResult = productParsingResult;
    }

    public IgnoredProductWordOccurence() {
    }
}
