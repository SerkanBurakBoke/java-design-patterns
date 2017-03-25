package com.sbb.contester.abstractfactory;

import java.io.IOException;

public abstract class Tester {

	public abstract void testIt();
	public abstract void checkService(String wsURL, String xmlInput) throws IOException;
}
