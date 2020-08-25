package mariusz.ambroziak.kassistant.enums;

import java.util.ArrayList;


public enum PhraseEqualityTypes {
	notEqual("notEqual",-1),
	equal("equal",10),
	lemmasEqual("lemmasEqual",9),
	dependencyEquals("dependencyEquals",8),
	orderReversed("orderReversed",7),
	differentTypes("differentTypes",6);

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

	PhraseEqualityTypes(String name, int priority) {
		this.name = name;
		this.priority = priority;
	}

	private String name;
	private int priority;
	
}
