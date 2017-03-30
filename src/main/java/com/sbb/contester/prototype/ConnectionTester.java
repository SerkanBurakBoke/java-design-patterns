package com.sbb.contester.prototype;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.sbb.contester.util.TesterConstants;

public class ConnectionTester {
	Logger logger = Logger.getLogger(this.getClass());
	private static PropsType props = null;
	private static ConnectionTester INSTANCE = null;

	public static ConnectionTester getInstance() {
		if (INSTANCE == null)
			INSTANCE = new ConnectionTester();
		return INSTANCE;
	}

	public PropsType getProps() {
		if (props == null)
			props = new PropsType();
		return props;
	}

	private static void setProps(PropsType props) {
		ConnectionTester.props = props;
	}

	public void testIt(PropsType props) {
		try {

			setProps(props);

			for (String url : getUrls()) {
				getInstance().checkService(url, getXml(TesterConstants.XML_FILE_NAME));
			}

		} catch (MalformedURLException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}
	}

	public void testIt() {
		PropsType props = getProps();
		try {
			PropsType propsNoProxy = props.clone();
			propsNoProxy.remove(TesterConstants.PROXY_HOST_LABEL);
			propsNoProxy.remove(TesterConstants.PROXY_PORT_LABEL);

			setProps(propsNoProxy);
			testIt(propsNoProxy);
				
		} catch (CloneNotSupportedException e) {
			logger.error(e);
		}
		
		testIt(props);

	}

	public static void main(String[] args) {
		new ConnectionTester().testIt();
	}

	public void checkService(String wsURL, String xmlInput) throws IOException {
		HttpURLConnection httpConn = (HttpURLConnection) generateConnection(wsURL);

		httpConn.setRequestProperty("Content-Length", String.valueOf(xmlInput.getBytes().length));
		httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");

		httpConn.setRequestMethod(TesterConstants.REQUEST_METHOD);
		httpConn.setDoOutput(true);
		httpConn.setDoInput(true);
		OutputStream out = httpConn.getOutputStream();

		out.write(xmlInput.getBytes());
		out.close();

		BufferedReader in = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));

		StringBuilder outputString = new StringBuilder();

		String responseString;
		while ((responseString = in.readLine()) != null)
			outputString.append(responseString);

		logger.info("Response is");
		logger.info(outputString.toString());

	}

	private URLConnection generateConnection(String wsURL) throws IOException {
		URL url = new URL(wsURL);
		URLConnection connection;
		if (getProxyHost() == null || getProxyHost().isEmpty() || getProxyPort() == 0)
			connection = url.openConnection();
		else {
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(getProxyHost(), getProxyPort()));
			connection = url.openConnection(proxy);
		}
		return connection;
	}

	private String getXml(String fileName) {

		StringBuilder result = new StringBuilder("");
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(fileName).getFile());

		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				result.append(line).append("\n");
			}
			scanner.close();

		} catch (IOException e) {
			logger.error(e);
		}

		return result.toString();

	}

	private String[] getUrls() {
		String urlStr = getProps().get(TesterConstants.URLS_LABEL).toString();
		return urlStr.split(",");
	}

	private String getProxyHost() {
		try {
			return getProps().get(TesterConstants.PROXY_HOST_LABEL).toString();

		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	private int getProxyPort() {
		try {
			return Integer.parseInt(getProps().get(TesterConstants.PROXY_PORT_LABEL).toString());
		} catch (Exception e) {
			logger.error(e);
		}
		return 0;
	}

}
