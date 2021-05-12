package com.tridiots.cms.kanye;

public class DoubleObject <J,K> {
	private J object1;
	private K object2;
	
	public DoubleObject (J object1, K object2) {
		this.object1 = object1;
		this.object2 = object2;
	}
	
	public J getObject1() {
		return object1;
	}

	public void setObject1(J object1) {
		this.object1 = object1;
	}

	public K getObject2() {
		return object2;
	}

	public void setObject2(K object2) {
		this.object2 = object2;
	}

	
	
}
