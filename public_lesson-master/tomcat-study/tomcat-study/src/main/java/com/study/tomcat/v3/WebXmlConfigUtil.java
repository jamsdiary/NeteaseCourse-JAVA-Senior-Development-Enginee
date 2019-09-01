package com.study.tomcat.v3;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.study.tomcat.v3.ProjectUtil.WebXml;

/**
 * 工具类，解析xml文件，转为java对象
 * 
 */
public class WebXmlConfigUtil extends DefaultHandler {

	/** Servlet 集合 */
	Map<String, Object> servlets = new HashMap<>();

	/** Servlet 参数 */
	// Map<String, Map<String, String>> servletParam = new HashMap<>();

	/** Servlet 映射 */
	Map<String, String> servletMapping = new HashMap<>();

	/** Servlet 实例集合 */
	Map<String, Servlet> servletInstances = new HashMap<>();

	/** xml文件地址 */
	private String xmlPath;

	/**
	 * 读取web.xml文件中的信息， 比如(context_param,listener,filter,servlet)
	 * <p>
	 * 这个示例中仅读取servlet信息
	 * </p>
	 * 
	 * @param xmlPath xml文件路径 @throws Exception @throws
	 */
	public WebXml loadXml(String xmlPath) throws Exception {
		this.xmlPath = xmlPath;
		// 创建一个解析XML的工厂对象
		SAXParserFactory parserFactory = SAXParserFactory.newInstance();
		// 创建一个解析XML的对象
		SAXParser parser = parserFactory.newSAXParser();
		// 创建一个解析助手类
		parser.parse(this.xmlPath, this);
		
		WebXml webXml = new ProjectUtil().new WebXml();
		webXml.servletInstances = servletInstances;
		webXml.servlets = this.servlets;
		webXml.servletMapping = this.servletMapping;
		return webXml;
	}

	String currentServlet = null;
	String currentServletMapping = null;
	String currentParam = null;
	String qName = null;

	// 开始解析文档，即开始解析XML根元素时调用该方法
	@Override
	public void startDocument() throws SAXException {
		System.out.println("--开始解析: " + this.xmlPath);
	}

	// 开始解析每个元素时都会调用该方法
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		// 判断正在解析的元素是不是开始解析的元素
		this.qName = qName;
	}

	// 解析到每个元素的内容时会调用此方法
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String currentValue = new String(ch, start, length);
		// 如果内容不为空和空格，也不是换行符则将该元素名和值和存入map中
		if (currentValue == null || currentValue.trim().equals("") || currentValue.trim().equals("\n")) {
			return;
		}
		if ("servlet-name".equals(qName)) {
			currentServlet = currentValue;
			currentServletMapping = currentValue;
		} else if (qName.equals("servlet-class")) {
			// servlet信息
			String servletClass = currentValue;
			servlets.put(currentServlet, servletClass);
		} else if (qName.equals("param-name")) {
			currentParam = currentValue;
		} else if (qName.equals("param-value")) {
			String paramValue = currentValue;
			// servlet param 参数
			HashMap<String, String> params = new HashMap<>();
			params.put(currentParam, paramValue);
			// servletParam.put(currentServlet, params);
		} else if ("servlet-name".equals(qName)) {
			currentServletMapping = currentValue;
		} else if (qName.equals("url-pattern")) {
			String urlPattern = currentValue;
			// url映射
			servletMapping.put(urlPattern, currentServletMapping);
		}
	}

	// 每个元素结束的时候都会调用该方法
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
	}

	// 结束解析文档，即解析根元素结束标签时调用该方法
	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
	}

	public Map<String, Object> getServlets() {
		return servlets;
	}

	public void setServlets(Map<String, Object> servlets) {
		this.servlets = servlets;
	}

//	public Map<String, Map<String, String>> getServletParam() {
//		return servletParam;
//	}
//
//	public void setServletParam(Map<String, Map<String, String>> servletParam) {
//		this.servletParam = servletParam;
//	}

	public Map<String, String> getServletMapping() {
		return servletMapping;
	}

	public void setServletMapping(Map<String, String> servletMapping) {
		this.servletMapping = servletMapping;
	}

	public Map<String, Servlet> getServletInstances() {
		return servletInstances;
	}

	public void setServletInstances(Map<String, Servlet> servletInstances) {
		this.servletInstances = servletInstances;
	}

}