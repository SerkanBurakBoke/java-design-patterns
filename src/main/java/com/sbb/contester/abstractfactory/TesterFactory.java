package com.sbb.contester.abstractfactory;

public class TesterFactory {
	public Tester getConnectionTester() {
		return ConnectionTester.getInstance();
	}
}
