package mariusz.ambroziak.kassistant.hibernate.statistical.model;

import mariusz.ambroziak.kassistant.hibernate.parsing.model.ProductParsingResult;

import javax.persistence.*;

@Entity
@Table(name = "ProductWordOccurence",schema = "statistics")
public class ProductWordOccurence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pwo_id;

    @ManyToOne
    private Word word;


    @OneToOne
    ProductParsingResult productParsingResult;


    public Long getPwo_id() {
        return pwo_id;
    }

    public void setPwo_id(Long pwo_id) {
        this.pwo_id = pwo_id;
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

    public ProductWordOccurence(Word word, ProductParsingResult productParsingResult) {
        this.word = word;
        this.productParsingResult = productParsingResult;
    }

    public ProductWordOccurence() {
    }
}
