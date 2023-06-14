package test;

import model.TuTaoKey;

public class TestKeyGenerator {

	public static void main(String[] args) {
		String tiento = "HD";
		String key = TuTaoKey.createKey(tiento);
		System.out.println("Generated key: " + key);
	}
}
