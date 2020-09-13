package mariusz.ambroziak.kassistant.hibernate.parsing.model;


import mariusz.ambroziak.kassistant.enums.PhraseFoundDataSource;
import mariusz.ambroziak.kassistant.enums.ProductType;
import mariusz.ambroziak.kassistant.enums.WordType;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "Phrase_Found",schema = "parsing",uniqueConstraints = @UniqueConstraint(columnNames = {"phrase","wordType"}))

public class PhraseFound {
    public  static float fullWeight=1000f;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pf_id;

    @Column(length = 2000)

    private String phrase;

    @Enumerated(EnumType.STRING)
    private PhraseFoundDataSource source;

    @Enumerated(EnumType.STRING)
    @Column(length = 100)
    private WordType wordType;

    @OneToMany(mappedBy = "basePhrase",cascade = CascadeType.ALL)
    private Set<PhraseFoundProductType> phraseFoundProductType;


    @ManyToOne(cascade = CascadeType.ALL)
    private IngredientPhraseParsingResult relatedIngredientResult;

    @ManyToOne(cascade = CascadeType.ALL)
    private ProductParsingResult relatedProductResult;

    @OneToOne
    private PhraseFound lemmatizationBase;


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

    public PhraseFoundDataSource getSource() {
        return source;
    }

    public void setSource(PhraseFoundDataSource source) {
        this.source = source;
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

    public Set<PhraseFoundProductType> getPhraseFoundProductType() {
        if(phraseFoundProductType==null)
            phraseFoundProductType=new HashSet<>();

        return phraseFoundProductType;
    }

    public Set<PhraseFoundProductType> getTypesFoundForPhraseAndBase() {
        Set<PhraseFoundProductType> phraseFoundProductType = this.getPhraseFoundProductType();

        if(phraseFoundProductType==null||phraseFoundProductType.isEmpty()&&getLemmatizationBase()!=null){
            Set<PhraseFoundProductType> baseTypes = getLemmatizationBase().getPhraseFoundProductType();
            phraseFoundProductType.addAll(baseTypes);
        }

        return phraseFoundProductType;

    }



    public void setPhraseFoundProductType(Set<PhraseFoundProductType> phraseFoundProductType) {
        this.phraseFoundProductType = phraseFoundProductType;
    }

    @Transient
    public void addPhraseFoundProductType(PhraseFoundProductType phraseFoundProductType) {
        this.getPhraseFoundProductType().add(phraseFoundProductType);


    }


    public PhraseFound getLemmatizationBase() {
        return lemmatizationBase;
    }

    public void setLemmatizationBase(PhraseFound lemmatizationBase) {
        this.lemmatizationBase = lemmatizationBase;
    }

    public ProductType getLeadingProductType(){
        Set<PhraseFoundProductType> phraseFoundProductType = getTypesFoundForPhraseAndBase();

        Map<ProductType, Long> occurenceMap = phraseFoundProductType
                .stream().filter(p->p.getProductType()!=ProductType.unknown)
                .collect(Collectors.groupingBy(p -> p.getProductType(), Collectors.counting()));

        Optional<Map.Entry<ProductType, Long>> max = occurenceMap.entrySet()
                .stream().max(Comparator.comparing(Map.Entry::getValue));

        if(max.isPresent()){
            return max.get().getKey();
        }else{
            return ProductType.unknown;
        }


    }


    public  ProductType getWeightedLeadingProductType() {
        ProductType weightedLeadingProductType = this.getWeightedLeadingProductType(getTypesFoundForPhraseAndBase());
        return weightedLeadingProductType;
    }
    public static ProductType getWeightedLeadingProductType(Set<PhraseFoundProductType> phraseFoundProductType){

        Map<ProductType,Float> occurenceMap= new HashMap<>();

        for(PhraseFoundProductType pfpt:phraseFoundProductType){
            if(occurenceMap.get(pfpt.getProductType())==null){
                occurenceMap.put(pfpt.getProductType(),0f);
            }

            String original="";
            long ingredientPhrasesCount = phraseFoundProductType.stream().filter(phraseFoundProductType1 -> phraseFoundProductType1.getRelatedIngredientResult() != null).count();
            long productPhrasesCount = phraseFoundProductType.stream().filter(phraseFoundProductType1 -> phraseFoundProductType1.getRelatedProductResult() != null).count();
            float phraseTypeCoeficient=0;
            if(pfpt.getRelatedIngredientResult()!=null){
                original= pfpt.getRelatedIngredientResult().getOriginalName();
                phraseTypeCoeficient=(float)ingredientPhrasesCount/(ingredientPhrasesCount+productPhrasesCount);
            }
            if(pfpt.getRelatedProductResult()!=null){
                original=pfpt.getRelatedProductResult().getOriginalName();
                phraseTypeCoeficient=(float)productPhrasesCount/(ingredientPhrasesCount+productPhrasesCount);
            }

            float calculatedWeight=1;
            if(!original.isEmpty()){
                int length = original.split(" ").length;

                calculatedWeight=fullWeight/length;
                calculatedWeight*=phraseTypeCoeficient;
            }else{
                calculatedWeight=1;
            }

            Float aFloat = occurenceMap.get(pfpt.getProductType());
            aFloat+=calculatedWeight;
            occurenceMap.put(pfpt.getProductType(),aFloat);


        }

        Optional<Map.Entry<ProductType, Float>> max = occurenceMap.entrySet()
                .stream().max(Comparator.comparing(Map.Entry::getValue));

        if(max.isPresent()){
            return max.get().getKey();
        }else{
            return ProductType.unknown;
        }


    }


    public PhraseFound(String phrase, WordType type, String reasoning, IngredientPhraseParsingResult relatedIngredientResult, ProductParsingResult relatedProductResult,PhraseFoundDataSource phraseFoundDataSource) {
        this.phrase = phrase;
        this.wordType = type;
        this.relatedIngredientResult = relatedIngredientResult;
        this.relatedProductResult = relatedProductResult;
        this.reasoning = reasoning;
        this.source=phraseFoundDataSource;

    }


    public PhraseFound(String phrase, WordType type, String reasoning,PhraseFoundDataSource phraseFoundDataSource) {
        this.phrase = phrase;
        this.wordType = type;
        this.reasoning = reasoning;
        this.source=phraseFoundDataSource;
    }

    public PhraseFound() {
    }


}
