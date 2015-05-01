package com.airsupply.olap.vo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

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
			
			
			//test delete catalog
			System.out.println("---------------test delete catalog-----------------");
			DataSourcesConfig config1=config;
			config1.removeCatalogs("SteelWheels");
			System.out.println(xStream.toXML(config1));
			OutputStream out=new FileOutputStream("d:/test.xml");
			xStream.toXML(config1, out);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
