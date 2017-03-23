package com.sbb.contester.abstractfactory.tester;

public class TesterFactory {
	public Tester getConnectionTester() {
		return ConnectionTester.getInstance();
	}
}
