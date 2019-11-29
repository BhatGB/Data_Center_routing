package com.datacenter.taskschedular;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class MysqlConnect {

	Properties prop = null;

	// init connection object
	private Connection connection;
	// init properties object
	private Properties properties;

	private static Logger logger = Logger.getLogger(Controller.class);
	// create properties
	private Properties getProperties(Properties prop2) {

		if (properties == null) {
			properties = new Properties();
			properties.setProperty("user", prop2.getProperty("USERNAME"));
			properties.setProperty("password", prop2.getProperty("PASSWORD"));
			properties.setProperty("MaxPooledStatements", prop2.getProperty("MAX_POOL"));
		}
		return properties;
	}

	// connect database
	public Connection connect() {
		Properties prop = null;

		try (InputStream input = new FileInputStream("resources/config.properties")) {
			prop = new Properties();
			// load a properties file
			prop.load(input);

		} catch (IOException ex) {
			ex.printStackTrace();
			logger.error("MYSQLConnect.stream_load"+ex.getMessage().toString());
		}

		if (connection == null) {
			try {
				Class.forName(prop.getProperty("DATABASE_DRIVER"));
				connection = DriverManager.getConnection(prop.getProperty("DATABASE_URL"), getProperties(prop));
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				logger.error("MYSQLConnect.connection:"+e.getMessage().toString());
			}
		}
		return connection;
	}

	// disconnect database
	public void disconnect() {
		if (connection != null) {
			try {
				connection.close();
				connection = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
