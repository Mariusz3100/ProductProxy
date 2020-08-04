package mariusz.ambroziak.kassistant.hibernate.model;

import javax.persistence.*;

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
