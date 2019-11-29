package com.datacenter.taskschedular;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class Controller {
	 private Connection connect = null;
	  private Statement statement = null;
	  private PreparedStatement preparedStatement = null;
	  private PreparedStatement preparedStmt = null;
	  private ResultSet resultSet = null;
	  private ResultSet rst=null;
	  List<String> idle_job_ids=null;
	  String[][] pending_task=null;

	  
	  
public void getController() throws SQLException {
	
	
	MysqlConnect mysqlConnect = new MysqlConnect();
	
	
	    try {
	    	statement =getConnection(mysqlConnect.connect());
	    	idle_job_ids= contentAnalyzer(statement);
	    	pending_task=taskClassifier(statement);
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	    mysqlConnect.disconnect();
	    
	    
	    schedular(idle_job_ids,pending_task);
	    
	
	

}


@SuppressWarnings("null")
private void schedular(List<String> idle_job_ids, String[][] pending_task)  {
	// TODO Auto-generated method stub
	String SQL = "update tasks set task_job_id=?,task_status=? where task_id=?"; 
	String sql="update jobs_incomming set job_status=? where job_id=?";
	MysqlConnect mysqlConnect = new MysqlConnect();
	connect = mysqlConnect.connect();
	
	
	
    // '?' is the placeholder for the parameter
	
	try {
		
		preparedStatement=connect.prepareStatement(SQL);
		preparedStmt=connect.prepareStatement(sql);
		
		for(int i=0;i<idle_job_ids.size();i++) {
		preparedStatement.setString(1,idle_job_ids.get(i));
		preparedStatement.setNull(2, Types.INTEGER);
		preparedStatement.setString(3, pending_task[i][1]);
		preparedStatement.addBatch();
		preparedStmt.setString(1, "Busy");
		preparedStmt.setString(2, idle_job_ids.get(i));
		preparedStmt.addBatch();
		
		
			
		}
		preparedStatement.executeBatch();
		preparedStmt.executeBatch();
		
		
		
	}catch(SQLException e){
		e.printStackTrace();
	}
	finally {
		mysqlConnect.disconnect();
	}
		
    }
	


private  String[][] taskClassifier(Statement statement) {
	// TODO Auto-generated method stub
	String[][] names=null;
	int rowCount;

	String sql_get_tasks_in_queue = "select task_job_id,task_id,task_status,task_type from tasks where task_status is NOT NULL and task_status !='0' and task_type='single' group by task_job_id DESC";
	try {
		resultSet=statement.executeQuery(sql_get_tasks_in_queue);
		
		
		
		
		resultSet.last();
		 rowCount=resultSet.getRow();
		
		resultSet=statement.executeQuery(sql_get_tasks_in_queue);
		
		System.out.println("rowCount"+rowCount);
		names=new String[rowCount][4];
		int i=0;
		while(resultSet.next()) {
			
			//System.out.println("task_job id: "+resultSet.getString("task_job_id")+" pending task count: "+resultSet.getString("count"));
			
			
			
				
				names[i][0]=resultSet.getString("task_job_id");
				names[i][1]=resultSet.getString("task_id");
				names[i][2]=resultSet.getString("task_status");
				names[i][3]=resultSet.getString("task_type");
				i++;
				
			
				
			               
			
			
		}
		
		
        
		
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	for (int ii = 0; ii < names.length; ii++) {
		
		for(int jj=0;jj<4;jj++)
		{
       System.out.println("task_job_id---:"+names[ii][jj]); 
	}
		System.out.println("\n");
	}
	return names;
	
	
	
	
	
}


private  List<String> contentAnalyzer(Statement statement) {
	// TODO Auto-generated method stub
	String sql_get_jobstatus = "SELECT * FROM jobs_incomming where job_status='Idle'";
	List<String> zoom = new ArrayList<String>();
	try {
		resultSet=statement.executeQuery(sql_get_jobstatus);
		while(resultSet.next()) {
			
			zoom.add(resultSet.getString("job_id"));
			//System.out.println("Idle job id: "+resultSet.getString("job_id"));
			
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	for (String z : zoom) {
	    System.out.println("Idle jobs--->"+z);
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
	}
	return statement;
}
	
} 
	
	
	
	
	


