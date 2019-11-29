package com.datacenter.taskschedular;

import java.sql.SQLException;

public class DataCenter {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Controller controler=new Controller();
		try {
			controler.getController();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
