package mariusz.ambroziak.kassistant.hibernate.parsing.model;

import javax.persistence.*;

@Entity
@Table(name = "Match_Expected",schema = "parsing")
public class MatchExpected {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long me_id;

    private String ingredient;

    private String product;


    private boolean expectedVerdict;


    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public boolean isExpectedVerdict() {
        return expectedVerdict;
    }

    public void setExpectedVerdict(boolean expectedVerdict) {
        this.expectedVerdict = expectedVerdict;
    }

    public MatchExpected() {

    }
}
