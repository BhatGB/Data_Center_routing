package com.datacenter.taskschedular;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.Logger;

public class DataCenter {
	private static Logger logger = Logger.getLogger(Controller.class);

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("DataCentre Tasks Re-routing process initiated.......");

		logger.info("Application Starting.....");

		/* Calling Business method to do all the operation */
		Controller controler = new Controller();
		try {
			controler.getController();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("DataCenter.main:" + e.getMessage().toString());

		}
		System.out.println("DataCentre Tasks Re-routing process finished.......");
	}

}
