package com.sbb.contester.abstractfactory.tester;

public class Main {
	public static void main(String[] args) {
		new TesterFactory().getConnectionTester().testIt();
	}
}
