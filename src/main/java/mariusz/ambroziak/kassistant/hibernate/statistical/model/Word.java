package mariusz.ambroziak.kassistant.hibernate.statistical.model;

import mariusz.ambroziak.kassistant.webclients.spacy.tokenization.Token;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Word",schema = "statistics")
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long w_id;

    @Column(unique=true)
    private String text;

    @Column
    private String lemma;

    private String tag;

    private String pos;

    @OneToMany(mappedBy = "word",cascade = CascadeType.ALL)
    List<ProductWordOccurence> productWordOccurences;


    @OneToMany(mappedBy = "word",cascade = CascadeType.ALL)
    List<IngredientWordOccurence> ingredientWordOccurenceList;

    public Word(Token token) {
        this.text=token.getText();
        this.lemma=token.getLemma();
        this.pos=token.getPos();
        this.tag=token.getTag();


    }


    public List<ProductWordOccurence> getProductWordOccurences() {
        if(productWordOccurences==null)
            return new ArrayList<>();
        else
            return productWordOccurences;
    }

    public void setProductWordOccurences(List<ProductWordOccurence> productWordOccurences) {
        this.productWordOccurences = productWordOccurences;
    }

    public List<IngredientWordOccurence> getIngredientWordOccurenceList() {
        if(ingredientWordOccurenceList==null)
            return new ArrayList<>();
        else
            return ingredientWordOccurenceList;
    }

    public void setIngredientWordOccurenceList(List<IngredientWordOccurence> ingredientWordOccurenceList) {
        this.ingredientWordOccurenceList = ingredientWordOccurenceList;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getLemma() {
        return lemma;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public Word(String text, String lemma) {
        this.text = text;
        this.lemma = lemma;
    }

    public Long getW_id() {
        return w_id;
    }

    public void setW_id(Long w_id) {
        this.w_id = w_id;
    }

    public Word() {
    }
}
