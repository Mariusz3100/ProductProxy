package mariusz.ambroziak.kassistant.hibernate.model;


import mariusz.ambroziak.kassistant.enums.ProductType;
import mariusz.ambroziak.kassistant.enums.WordType;

import javax.persistence.*;

@Entity
@Table(name = "Phrase_Found")
public class PhraseFound {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pf_id;

    @Column(length = 2000)

    private String phrase;

    @Enumerated(EnumType.STRING)
    @Column(length = 100)
    private WordType type;

    @ManyToOne(cascade = CascadeType.ALL)
    private IngredientPhraseParsingResult relatedIngredientResult;

    @ManyToOne(cascade = CascadeType.ALL)

    private ProductParsingResult relatedProductResult;


    public Long getPf_id() {
        return pf_id;
    }

    public void setPf_id(Long pf_id) {
        this.pf_id = pf_id;
    }

    public String getPhrase() {
        return phrase;
    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public WordType getType() {
        return type;
    }

    public void setType(WordType type) {
        this.type = type;
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


    public PhraseFound(String phrase, WordType type, IngredientPhraseParsingResult relatedIngredientResult, ProductParsingResult relatedProductResult) {
        this.phrase = phrase;
        this.type = type;
        this.relatedIngredientResult = relatedIngredientResult;
        this.relatedProductResult = relatedProductResult;
    }
}
