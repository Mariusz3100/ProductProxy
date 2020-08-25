package mariusz.ambroziak.kassistant.hibernate.parsing.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import mariusz.ambroziak.kassistant.pojos.QualifiedToken;
import mariusz.ambroziak.kassistant.webclients.spacy.tokenization.Token;
import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "PhraseConsidered",schema = "parsing")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type",discriminatorType = DiscriminatorType.STRING)
public abstract class PhraseConsidered {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pc_id;

    private boolean accepted=false;




    @OneToOne
    @JsonIgnore
    private ProductParsingResult productParsingResult;
    @OneToOne
    @JsonIgnore
    private IngredientPhraseParsingResult ingredientPhraseParsingResult;




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

    public int getPc_id() {
        return pc_id;
    }

    public void setPc_id(int pc_id) {
        this.pc_id = pc_id;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPc_id(), getProductParsingResult(), getIngredientPhraseParsingResult());
    }
}
