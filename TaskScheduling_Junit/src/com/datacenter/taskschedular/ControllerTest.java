package com.datacenter.taskschedular;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class ControllerTest extends DataCenter{
	
	


	@Test
	/*
	void testidlejobs() throws SQLException {
		List<String> idlejobsactual= new ArrayList<String>();
		
		Controller control = new Controller();
		control.getController();
		idlejobsactual=control.idle_job_ids;
		List<String> idlejobs = new ArrayList<String>();
		idlejobs.add("j001");
		idlejobs.add("j002");
		assertEquals(idlejobs.get(0), idlejobsactual.get(0));
		
	}*/
	
	void testpending_task() throws SQLException
	{
		String[][] pending_task ={{"j003","t004","2","single"}};
		String[][] pending_task_actual;
		Controller control = new Controller();
		control.getController();
		pending_task_actual=control.pending_task;
		
		
			assertEquals(pending_task[0][0],pending_task_actual[1][0]);
			
		
		
	
		
	}
	

	

}
