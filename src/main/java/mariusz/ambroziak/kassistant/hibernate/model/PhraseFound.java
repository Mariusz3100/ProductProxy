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
    private WordType wordType;

    @Enumerated(EnumType.STRING)
    @Column(length = 100)
    private ProductType productType;


    @ManyToOne(cascade = CascadeType.ALL)
    private IngredientPhraseParsingResult relatedIngredientResult;

    @ManyToOne(cascade = CascadeType.ALL)

    private ProductParsingResult relatedProductResult;

    private String reasoning;

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

    public String getReasoning() {
        return reasoning;
    }

    public void setReasoning(String reasoning) {
        this.reasoning = reasoning;
    }


    public WordType getWordType() {
        return wordType;
    }

    public void setWordType(WordType wordType) {
        this.wordType = wordType;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public PhraseFound(String phrase, WordType type, String reasoning, IngredientPhraseParsingResult relatedIngredientResult, ProductParsingResult relatedProductResult) {
        this.phrase = phrase;
        this.wordType = type;
        this.relatedIngredientResult = relatedIngredientResult;
        this.relatedProductResult = relatedProductResult;
        this.reasoning = reasoning;
    }


    public PhraseFound(String phrase, WordType type, String reasoning) {
        this.phrase = phrase;
        this.wordType = type;
        this.reasoning = reasoning;
    }

    public PhraseFound() {
    }


}
