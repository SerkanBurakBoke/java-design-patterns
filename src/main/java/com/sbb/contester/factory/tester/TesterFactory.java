package com.sbb.contester.factory.tester;

public class TesterFactory {
	public Tester getConnectionTester() {
		return ConnectionTester.getInstance();
	}
}
