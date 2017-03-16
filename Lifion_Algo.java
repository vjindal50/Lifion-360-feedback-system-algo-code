package trials;

import java.sql.*;

public class Lifion_Algo {
	
	// JDBC driver name and database URL  
	static final String DB_URL = "***********************";

  	//  Database credentials
  	static final String USER = "root";
  	static final String PASS = "137115";
  	
  	public static void GetAvgScore(){
		 Connection conn = null;
		 Statement stmt = null; 
		 try{
			  //Open a connection
		      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);

		      stmt = conn.createStatement();
		      String sql;
		      sql = "SELECT DISTINCT Survey_ID FROM SURVEY_CREATE";
		      ResultSet rs = stmt.executeQuery(sql);
		      
		      //Extract data from result set
		      while(rs.next()){  //for each survey
		    	  int counter_submitter = 0;
		    	  int Survey_ID  = rs.getInt("Survey_ID");	 
		    	  
		    	  // getting score for each survey for each submitter
		    	  sql = "SELECT Answer_Value FROM SURVEY_ANS WHERE Survey_ID = "+ Survey_ID; // + "AND Employee_ID = " + Employee_ID;
				  ResultSet rs2 = stmt.executeQuery(sql);
				      
				      //taking total of answers from all submitters for a given survey
				      int sum = 0;
				      while(rs2.next()){
				    	  int Answer_Value  = rs2.getInt("Answer_Value");
				    	  sum = sum + Answer_Value;
				    	  counter_submitter++;
				      }
				      
				      // calculating average
				      double avg_score = sum / counter_submitter; 
			          System.out.print("average score across Survey_ID: " + Survey_ID + " for all submitter is = " + avg_score);
			          System.out.println();
			          
			          //Clean-up environment
			          rs2.close();
		      }
		      //Clean-up environment
		      rs.close();
		      stmt.close();
		      conn.close();
		   }catch(SQLException se){
		      se.printStackTrace();
		   }catch(Exception e){
		      e.printStackTrace();
		   }finally{
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		      }
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }
		   }
		   System.out.println("Goodbye!");
  	}
  	
  	
  	public static void Recipient(int Employee_ID){
  		
  	   Connection conn = null;
  	   Statement stmt = null;
  	   try{
  	      //Open a connection
  	      System.out.println("Connecting to database...");
  	      conn = DriverManager.getConnection(DB_URL,USER,PASS);

  	      //Execute query
  	      System.out.println("Creating statement...");
  	      stmt = conn.createStatement();
  	      String sql;
  	      // find all the survey the recipient created
  	      sql = "SELECT Survey_ID FROM SURVEY_CREATE WHERE Survey_Create.Employee_ID = " + Employee_ID;
  	      ResultSet rs = stmt.executeQuery(sql);
  	      int Counter = 0;
  	      int Sum = 0;
  	      double AVG_SCORE_OVERALL = 0;
  	      while(rs.next()){
  	         int Survey_ID  = rs.getInt("Survey_ID");
  	         System.out.print("Survey_ID: " + Survey_ID);
  	         
  	         sql = "SELECT Question, Answer_Value FROM QUESTIONS, SURVEY_ANS WHERE Survey_Ans.Survey_ID = " + Survey_ID + "AND Submission_date NOT Null";
  	         ResultSet rs2 = stmt.executeQuery(sql);
  	         while(rs2.next()){
  	        	 String Question = rs2.getString("Question");
  	        	 int Answer_Value = rs2.getInt("Answer_Value");
  	        	 
  	        	 // to get an overall average score
  	        	 Sum = Sum +  Answer_Value;
  	        	 Counter++;
  	        	 
  	        	 // print out all the feedback he or she received
  	        	 System.out.print("Question: " + Question + " ");
  	        	 System.out.print("Answer_Value: " + Answer_Value + " ");
  	        	 System.out.println();
  	        	 
  	         }
  	         System.out.println();
  	         
  	         //Clean-up environment
  	         rs2.close();
  	       
  	      }
  	      
  	      //over all average score calculation
  	      AVG_SCORE_OVERALL = Sum / Counter;
  	      System.out.print("Average acore across all submitter and all feedback is = " + AVG_SCORE_OVERALL);
  	      
  	      //Clean-up environment and handle error
  	      rs.close();
  	      stmt.close();
  	      conn.close();
  	   }catch(SQLException se){
  	      se.printStackTrace();
  	   }catch(Exception e){
  	      e.printStackTrace();
  	   }finally{
  	      try{
  	         if(stmt!=null)
  	            stmt.close();
  	      }catch(SQLException se2){
  	      }
  	      try{
  	         if(conn!=null)
  	            conn.close();
  	      }catch(SQLException se){
  	         se.printStackTrace();
  	      }
  	   }
  	   System.out.println("Goodbye!");
  		
  	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GetAvgScore();
		Recipient(0000);

	}

}