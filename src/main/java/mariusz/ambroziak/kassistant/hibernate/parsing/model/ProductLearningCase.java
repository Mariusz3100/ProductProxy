package mariusz.ambroziak.kassistant.hibernate.parsing.model;

import mariusz.ambroziak.kassistant.enums.ProductType;

import javax.persistence.*;


@Entity
@Table(name = "Product_Learning_Case",schema = "parsing")
public class ProductLearningCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long plc_id;
    @Column(length = 500)
    private String name;

    @Column(length = 500)
    private String url;

    @Enumerated(EnumType.STRING)
    @Column(length = 100)
    private ProductType type_expected;

    @Column(length = 2000)
    private String minimal_words_expected;
    @Column(length = 2000)
    private String extended_words_expected;


    public Long getPlc_id() {
        return plc_id;
    }

    public void setPlc_id(Long plc_id) {
        this.plc_id = plc_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ProductType getType_expected() {
        return type_expected;
    }

    public void setType_expected(ProductType type_expected) {
        this.type_expected = type_expected;
    }

    public String getMinimal_words_expected() {
        return minimal_words_expected;
    }

    public void setMinimal_words_expected(String minimal_words_expected) {
        this.minimal_words_expected = minimal_words_expected;
    }

    public String getExtended_words_expected() {
        return extended_words_expected;
    }

    public void setExtended_words_expected(String extended_words_expected) {
        this.extended_words_expected = extended_words_expected;
    }

    public ProductLearningCase(String name, String url, ProductType type_expected, String minimal_words_expected, String extended_words_expected) {
        this.name = name;
        this.url = url;
        this.type_expected = type_expected;
        this.minimal_words_expected = minimal_words_expected;
        this.extended_words_expected = extended_words_expected;
    }


    public ProductLearningCase() {
    }
}
