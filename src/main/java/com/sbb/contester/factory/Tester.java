package com.sbb.contester.factory;

import java.io.IOException;

public interface Tester {

	public void testIt();
	public void checkService(String wsURL, String xmlInput) throws IOException;
}
