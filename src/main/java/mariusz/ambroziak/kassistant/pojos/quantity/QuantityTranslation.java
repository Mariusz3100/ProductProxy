package mariusz.ambroziak.kassistant.pojos.quantity;

import mariusz.ambroziak.kassistant.enums.AmountTypes;

public class QuantityTranslation{
	private AmountTypes targetAmountType=null;
	private float multiplier=0;
	
	
	public QuantityTranslation(AmountTypes targetAmountType, float multiplier) {
		super();
		this.targetAmountType = targetAmountType;
		this.multiplier = multiplier;
	}
	public AmountTypes getTargetAmountType() {
		return targetAmountType;
	}
	public void setTargetAmountType(AmountTypes targetAmountType) {
		this.targetAmountType = targetAmountType;
	}
	public float getMultiplier() {
		return multiplier;
	}
	public void setMultiplier(float multiplier) {
		this.multiplier = multiplier;
	}
	

}