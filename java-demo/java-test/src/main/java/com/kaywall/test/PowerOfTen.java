package com.kaywall.test;

/**
 *  E
 * @author aikaiqiang
 * @date 2019年07月23日 15:29
 */
public enum PowerOfTen {

	ONE(1), TEN(10),
	HUNDRED(100){
		@Override
		public String toString() {
			return Integer.toString(val);
		}
	};

	protected final int val;

	PowerOfTen(int val) {
		this.val = val;
	}


	@Override
	public String toString() {
		return name().toLowerCase();
	}

	public static void main(String[] args) {
		System.out.println(ONE + " " + TEN + " " +  HUNDRED);
	}
}
