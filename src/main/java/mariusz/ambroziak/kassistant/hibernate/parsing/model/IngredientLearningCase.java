package mariusz.ambroziak.kassistant.hibernate.parsing.model;


import mariusz.ambroziak.kassistant.enums.AmountTypes;
import mariusz.ambroziak.kassistant.enums.ProductType;
import mariusz.ambroziak.kassistant.pojos.quantity.PreciseQuantity;
import mariusz.ambroziak.kassistant.webclients.edamam.nlp.EdamanApiQuantityExtractor;

import javax.persistence.*;
@Entity
@Table(name = "ingredient_learning_case",schema = "parsing")

public class IngredientLearningCase {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ilc_id;


	private String originalPhrase;

	private String source;

	private String foodMatch;
	private float amount;

	@Enumerated(EnumType.STRING)
	private AmountTypes amountType;

	@Enumerated(EnumType.STRING)
	private ProductType foodTypeCategory;

	public ProductType getFoodTypeCategory() {
		return foodTypeCategory;
	}

	public void setFoodTypeCategory(ProductType foodTypeCategory) {
		this.foodTypeCategory = foodTypeCategory;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public AmountTypes getAmountType() {
		return amountType;
	}

	public void setAmountType(AmountTypes amountType) {
		this.amountType = amountType;
	}

//	public ExpectedResult(float quantity, String measure, String foodMatch, AmountTypes amountType) {
//		super(quantity, measure, foodMatch);
//		PreciseQuantity resultingQuantity = EdamanApiQuantityExtractor.getResultingQuantity(quantity, measure);
//		
//		this.amount = quantity*resultingQuantity.getAmount();
//		this.amountType = resultingQuantity.getType();
//	}

	public IngredientLearningCase(String originalPhrase, float quantity, String measure, String foodMatch, ProductType productType) {
		PreciseQuantity resultingQuantity = EdamanApiQuantityExtractor.getResultingQuantity(quantity, measure);

		this.originalPhrase = originalPhrase;
		this.amount = resultingQuantity.getAmount();
		this.amountType = resultingQuantity.getType();
		this.foodTypeCategory=productType;
		this.foodMatch=foodMatch;

	}

	public IngredientLearningCase(String originalPhrase, float amount, AmountTypes amountType) {
		this.originalPhrase = originalPhrase;
		this.foodMatch=originalPhrase;
		this.foodTypeCategory=ProductType.unknown;
		this.amount = amount;
		this.amountType = amountType;
	}

	public IngredientLearningCase() {
	}

	public String getOriginalPhrase() {
		return originalPhrase;
	}

	public void setOriginalPhrase(String originalPhrase) {
		this.originalPhrase = originalPhrase;
	}


	public String getFoodMatch() {
		return foodMatch;
	}

	public void setFoodMatch(String foodMatch) {
		this.foodMatch = foodMatch;
	}

	public Long getIlc_id() {
		return ilc_id;
	}

	public void setIlc_id(Long ilc_id) {
		this.ilc_id = ilc_id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}
