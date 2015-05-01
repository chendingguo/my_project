package com.airsupply.olap.vo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * datasources.xml 映射类
 * 
 * @author arisupply
 *
 */
@XStreamAlias("DataSources")
public class DataSourcesConfig {

	@XStreamImplicit(itemFieldName = "DataSource")
	List<DataSource> dataSources;

	public void removeCatalogs(String name) {

		List<Catalog> catlogs = this.dataSources.get(0).getCatalogs()
				.getCatalogs();

		for (Catalog catalog : catlogs) {
			if (name.equals(catalog.getName().trim())) {
				catlogs.remove(catalog);
			}
		}

	}

	// [NOTE]only have one datasource node
	// mondrian didn't work well with multiply datasource
	// we definine the datasource info in catalog element to archieve the
	// multiply datasource purpose
	class DataSource {

		@XStreamAlias("DataSourceName")
		String dataSourceName;

		@XStreamAlias("DataSourceDescription")
		String dataSourceDescription;

		@XStreamAlias("URL")
		String url;

		@XStreamAlias("DataSourceInfo")
		String dataSourceInfo;

		@XStreamAlias("ProviderName")
		String providerName;

		@XStreamAlias("ProviderType")
		String providerType;

		@XStreamAlias("AuthenticationMode")
		String authenticationMode;

		@XStreamAlias("Catalogs")
		Catalogs catalogs;

		public String getDataSourceName() {
			return dataSourceName;
		}

		public void setDataSourceName(String dataSourceName) {
			this.dataSourceName = dataSourceName;
		}

		public String getDataSourceDescription() {
			return dataSourceDescription;
		}

		public void setDataSourceDescription(String dataSourceDescription) {
			this.dataSourceDescription = dataSourceDescription;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getDataSourceInfo() {
			return dataSourceInfo;
		}

		public void setDataSourceInfo(String dataSourceInfo) {
			this.dataSourceInfo = dataSourceInfo;
		}

		public String getProviderName() {
			return providerName;
		}

		public void setProviderName(String providerName) {
			this.providerName = providerName;
		}

		public String getProviderType() {
			return providerType;
		}

		public void setProviderType(String providerType) {
			this.providerType = providerType;
		}

		public String getAuthenticationMode() {
			return authenticationMode;
		}

		public void setAuthenticationMode(String authenticationMode) {
			this.authenticationMode = authenticationMode;
		}

		public Catalogs getCatalogs() {
			return catalogs;
		}

		public void setCatalogs(Catalogs catalogs) {
			this.catalogs = catalogs;
		}

	}

	/**
	 * 将文档输出到文件保存，可指定格式化输出,可指定字符编码。
	 * 
	 * @param document
	 * @param outputFile
	 */
	public static void saveDocument(Document doc, String outputPath) {
		// 输出文件
		File outputFile = new File(outputPath);
		try {
			// 美化格式
			OutputFormat format = OutputFormat.createPrettyPrint();
			// 指定XML编码,不指定的话，默认为UTF-8
			format.setEncoding("UTF-8");
			XMLWriter output = new XMLWriter(new FileWriter(outputFile), format);
			output.write(doc);
			output.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void main(String[] args) {
		InputStream input;
		try {
			input = new FileInputStream(
					"E:/github/mondrian/mondrian-engine/engine_config/datasources.xml");
			XStream xStream = new XStream(new DomDriver("utf-8"));
			xStream.processAnnotations(DataSourcesConfig.class);

			DataSourcesConfig dc = new DataSourcesConfig();

			DataSourcesConfig config = (DataSourcesConfig) xStream
					.fromXML(input);
			System.out.println(xStream.toXML(config));

			// test delete catalog
			
			DataSourcesConfig config1 = config;
			config1.removeCatalogs("SteelWheels");

			//operate xml use dom4j
			OutputFormat format = OutputFormat.createPrettyPrint();
			format.setEncoding("utf-8");
			StringWriter sw = new StringWriter();
			try {
				Document doc = DocumentHelper.parseText(xStream.toXML(config1));
				saveDocument(doc, "d:/test.xml");
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
