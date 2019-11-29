package com.datacenter.taskschedular;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
public class Controller {
	
	
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private PreparedStatement preparedStmt = null;
	private ResultSet resultSet = null;
	List<String> idle_job_ids = null;
	String[][] pending_task = null;
	Properties prop = null;
	
	private static Logger logger = Logger.getLogger(Controller.class);

	public void getController() throws SQLException {
     
		logger.info("Controller_start:");
		try (InputStream input = new FileInputStream("resources/config.properties")) {
			prop = new Properties();  
			// load a properties file
			prop.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
			logger.error("Controller:read config file"+ex.getMessage().toString());
		}
		
		/* Connection Class */
		MysqlConnect mysqlConnect = new MysqlConnect();
		try {
			statement = getConnection(mysqlConnect.connect());
			idle_job_ids = contentAnalyzer(statement);  //fetch all idle jobs from central server
			pending_task = taskClassifier(statement);    //identifying tasks in queues of each tasks
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Controller:connection class"+e.getMessage().toString());
		}
		mysqlConnect.disconnect();
		schedular(idle_job_ids, pending_task);    //Reroutings to new jobs
	}
	
	@SuppressWarnings("null")
	private void schedular(List<String> idle_job_ids, String[][] pending_task) {
		// TODO Auto-generated method stub
		String SQL = prop.getProperty("sql_update_task_table");
		String sql = prop.getProperty("sql_update_jobs_incomming_table");
		MysqlConnect mysqlConnect = new MysqlConnect();
		connect = mysqlConnect.connect();
		try {

			preparedStatement = connect.prepareStatement(SQL);
			preparedStmt = connect.prepareStatement(sql);

			for (int i = 0; i < idle_job_ids.size(); i++) {
				preparedStatement.setString(1, idle_job_ids.get(i));
				preparedStatement.setInt(2, 0);
				preparedStatement.setString(3, pending_task[i][1]);
				preparedStatement.addBatch();
				preparedStmt.setString(1, "Busy");
				preparedStmt.setString(2, idle_job_ids.get(i));
				preparedStmt.addBatch();

			}
			preparedStatement.executeBatch();
			preparedStmt.executeBatch();

		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("Controller:schedular"+e.getMessage().toString());
			
		} finally {
			mysqlConnect.disconnect();
		}

	}

	private String[][] taskClassifier(Statement statement) {
		// TODO Auto-generated method stub
		String[][] names = null;
		int rowCount;
		String sql_get_tasks_in_queue = prop.getProperty("sql_get_tasks_in_queue");
		try {
			resultSet = statement.executeQuery(sql_get_tasks_in_queue);
			resultSet.last();
			rowCount = resultSet.getRow();

			resultSet = statement.executeQuery(sql_get_tasks_in_queue);
            logger.info("rowCount" + rowCount);
			names = new String[rowCount][4];
			int i = 0;
			while (resultSet.next()) {

				// System.out.println("task_job id: "+resultSet.getString("task_job_id")+"
				// pending task count: "+resultSet.getString("count"));

				names[i][0] = resultSet.getString(prop.getProperty("task_job_id"));
				names[i][1] = resultSet.getString(prop.getProperty("task_id"));
				names[i][2] = resultSet.getString(prop.getProperty("task_status"));
				names[i][3] = resultSet.getString(prop.getProperty("task_type"));
				i++;

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Controller:taskClassifier"+e.getMessage().toString());
		}

		for (int ii = 0; ii < names.length; ii++) {
            logger.info("Task inforamtion:");
			for (int jj = 0; jj < 4; jj++) {
				logger.info( names[ii][jj]);
			}
			System.out.println("\n");
		}
		return names;

	}

	private List<String> contentAnalyzer(Statement statement) {
		// TODO Auto-generated method stub
		String sql_get_jobstatus = prop.getProperty("sql_get_jobstatus");
		List<String> zoom = new ArrayList<String>();
		try {
			resultSet = statement.executeQuery(sql_get_jobstatus);
			while (resultSet.next()) {

				zoom.add(resultSet.getString(prop.getProperty("job_id")));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("Controller:contentAnalyzer"+e.getMessage().toString());
		}
		for (String z : zoom) {
			logger.info("Idle jobs--->" + z);
		}

		return zoom;

	}

	private Statement getConnection(Connection connect2) {
		// TODO Auto-generated method stub
		try {
			return connect2.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("getConnection"+e.getMessage().toString());
		}
		return statement;
	}

}
