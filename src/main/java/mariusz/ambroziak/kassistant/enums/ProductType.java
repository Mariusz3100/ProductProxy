package mariusz.ambroziak.kassistant.enums;

import java.util.Arrays;
import java.util.Optional;

public enum ProductType{
	fresh("fresh",0),
	processed("processed",10),
	puree("puree",20),
	juice("juice",30),
	flavoured("flavoured",90),
	unknown("unknown",-1),
	meal("meal",30),
	dried("dried",30),
	notFood("notFood",100);

	private String name;
	private int priority;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}
	ProductType(String name, int priority) {
		this.name = name;
		this.priority = priority;
	}

	public static ProductType parseType(String phrase){
		if(phrase==null||phrase.isEmpty())
			return ProductType.unknown;

		Optional<ProductType> first = Arrays.stream(values()).filter(pt -> pt.getName().equals(phrase)).findFirst();
		if(first.isPresent())
			return first.get();
		else
			return ProductType.unknown;
	}

	@Override
	public String toString() {
		return name;
	}
}
