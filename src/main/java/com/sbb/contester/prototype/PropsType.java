package com.sbb.contester.prototype;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.sbb.contester.util.TesterConstants;

public class PropsType implements Cloneable {
	Logger logger = Logger.getLogger(this.getClass());
	Properties props = null;

	public PropsType() {
		setProps(readProps());
	}

	public PropsType(Properties props) {
		setProps(props);
	}

	@Override
	public PropsType clone() throws CloneNotSupportedException {
		return new PropsType((Properties) getProps().clone());
	}

	private Properties getProps() {
		return props;
	}

	private void setProps(Properties props) {
		this.props = props;
	}

	public synchronized Object get(Object paramObject) {
		return getProps().get(paramObject);
	}

	public synchronized Object setProperty(String paramString1, String paramString2) {
		return getProps().setProperty(paramString1, paramString2);
	}
	
	public synchronized Object remove(Object paramObject){
		return getProps().remove(paramObject);
	}

	private synchronized Properties readProps() {
		String resourceName = TesterConstants.RESOURCE_FILE_NAME;
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Properties props = new Properties();
		try {
			InputStream resourceStream = loader.getResourceAsStream(resourceName);
			props.load(resourceStream);
		} catch (Exception e) {
			logger.error(e);
		}
		return props;
	}

}
