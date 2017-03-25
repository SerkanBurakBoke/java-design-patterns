package com.sbb.contester.factory;

public class TesterFactory {
	public Tester getConnectionTester() {
		return ConnectionTester.getInstance();
	}
}
