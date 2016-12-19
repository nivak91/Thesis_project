import java.sql.*;
public class JavaMySqlQueries {

	/**
	 * @param args
	 */
	// MySQL queries
	static final String NP = "SELECT COUNT(*) FROM asqatasun.WEB_RESOURCE_STATISTICS WHERE Id_Audit=59 and Http_Status_Code=200" ;
	static final String min_page_ID = "SELECT MIN(Id_Web_Resource_Statistics) FROM asqatasun.WEB_RESOURCE_STATISTICS WHERE Id_Audit = 59 and Http_Status_Code = 200";
	static final String max_page_ID = "SELECT MAX(Id_Web_Resource_Statistics) FROM asqatasun.WEB_RESOURCE_STATISTICS WHERE Id_Audit = 59 and Http_Status_Code = 200";
	static final String NT = "SELECT SUM(Nb_Failed+Nb_Passed+Nb_Nmi) FROM asqatasun.CRITERION_STATISTICS WHERE Id_Web_Resource_Statistics = ?";
	static final String NTx = "SELECT SUM(Nb_Failed+Nb_Passed+Nb_Nmi) FROM asqatasun.CRITERION_STATISTICS WHERE Id_Web_Resource_Statistics = ? AND Theme_Id_Theme = ?";
	static final String NTxy = "SELECT SUM(Nb_Failed+Nb_Passed+Nb_Nmi) FROM asqatasun.CRITERION_STATISTICS WHERE Id_Web_Resource_Statistics = ? AND Theme_Id_Theme = ? AND Criterion_Result = ?";
	static final String Bxyz1 = "SELECT SUM(Nb_Nmi) FROM asqatasun.CRITERION_STATISTICS WHERE Id_Web_Resource_Statistics = ? AND Theme_Id_Theme = ? AND Criterion_Result = ? AND Level_Id_Level = ?";
	static final String Bxyz2 = "SELECT SUM(Nb_Failed) FROM asqatasun.CRITERION_STATISTICS WHERE Id_Web_Resource_Statistics = ? AND Theme_Id_Theme = ? AND Criterion_Result = ? AND Level_Id_Level = ?";
	static final String Pxyz = "SELECT SUM(Nb_Failed+Nb_Passed+Nb_Nmi) FROM asqatasun.CRITERION_STATISTICS WHERE Id_Web_Resource_Statistics = ? AND Theme_Id_Theme = ? AND Criterion_Result = ? AND Level_Id_Level = ?";
	static final double[] W = {0.80, 0.16, 0.04}; 
	public static Connection getConnection() throws Exception {
		// create our mysql database connection
	      String myDriver = "org.gjt.mm.mysql.Driver";
	      String myUrl = "jdbc:mysql://localhost:3306/asqatasun";
	      Class.forName(myDriver);
	      Connection conn = DriverManager.getConnection(myUrl, "asqatasun", "asqaP4sswd");
	      return conn;
	  }
	public static double calculate_Score(double B, double P){
		double a = 0.3;
		double b = 20;
		double dou = B/P;
		double A;
		if(B/P<(a-100)/(a/P-100/b)){
			A=B*(-100/b)+100;
		}
		else{
			A=(-a*B/P)+a;
		}
		return A;
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection conn = null;
		try
	    {
		conn = getConnection();
		PreparedStatement stmt = conn.prepareStatement(NP);
		PreparedStatement stmt1 = conn.prepareStatement(min_page_ID);
		PreparedStatement stmt2 = conn.prepareStatement(max_page_ID);
		ResultSet rs = stmt.executeQuery();
		ResultSet rs1 = stmt1.executeQuery();
		ResultSet rs2 = stmt2.executeQuery();
	    rs.next();
	    rs1.next();
	    rs2.next();
	    int pages = rs.getInt(1);
	    int min = rs1.getInt(1);
	    int max = rs2.getInt(1); 
        double NT_xy,N_T,NT_x,B,P;
        String Criterion_result;
        double Score=0,Score1=0,Score2=0,Score3=0,Score4=0;
	    for(int i=min; i <= max; i++){
	    	Score3=0;
	    	PreparedStatement stmt3 = conn.prepareStatement(NT);
	    	stmt3.setInt(1,i);
	    	ResultSet rs3 = stmt3.executeQuery();
	    	rs3.next();
	    	N_T=rs3.getDouble(1);
	    	if (N_T==0) continue;
	    	for(int x=44; x<57; x++){
	    		Score2=0;
	    		PreparedStatement stmt4 = conn.prepareStatement(NTx);
		    	stmt4.setInt(1,i);
		    	stmt4.setInt(2,x);
	    		ResultSet rs4 = stmt4.executeQuery();
		    	rs4.next();
		    	NT_x=rs4.getDouble(1);
		    	if (NT_x==0) continue;
	    		for(int y=0; y<=1; y++){
	    			Score1=0;
	    			if (y==0){
    					Criterion_result="NEED_MORE_INFO";
    					PreparedStatement stmt5 = conn.prepareStatement(NTxy);
    					stmt5.setInt(1,i);
    			    	stmt5.setInt(2,x);
    			    	stmt5.setString(3,Criterion_result);
    					ResultSet rs5 = stmt5.executeQuery();
    					rs5.next();
    					NT_xy=rs5.getDouble(1);
	    			}
	    			else{
	    				Criterion_result="FAILED";
	    				PreparedStatement stmt5 = conn.prepareStatement(NTxy);
	    				stmt5.setInt(1,i);
    			    	stmt5.setInt(2,x);
    			    	stmt5.setString(3,Criterion_result);
	    				ResultSet rs5 = stmt5.executeQuery();
	    				rs5.next();
	    				NT_xy=rs5.getDouble(1);
	    			}
	    			if(NT_xy==0) continue;
	    			for(int z=0; z<=2; z++){
	    				if(y==0){ 
	    					PreparedStatement stmt6 = conn.prepareStatement(Bxyz1);
	    					stmt6.setInt(1,i);
		    				stmt6.setInt(2,x);
		    				stmt6.setString(3,Criterion_result);
		    				stmt6.setInt(4,z+1);
		    				ResultSet rs6 = stmt6.executeQuery();
		    				rs6.next();
		    				B = rs6.getDouble(1);
	    				}
	    				
	    				else{
	    					PreparedStatement stmt6 = conn.prepareStatement(Bxyz2);
	    					stmt6.setInt(1,i);
		    				stmt6.setInt(2,x);
		    				stmt6.setString(3,Criterion_result);
		    				stmt6.setInt(4,z+1);
		    				ResultSet rs6 = stmt6.executeQuery();
		    				rs6.next();
		    				B = rs6.getDouble(1);
	    				}
	    				
	    				
	    				
	    				PreparedStatement stmt7 = conn.prepareStatement(Pxyz);
	    				stmt7.setInt(1,i);
	    				stmt7.setInt(2,x);
	    				stmt7.setString(3,Criterion_result);
	    				stmt7.setInt(4,z+1);
	    				
	    				ResultSet rs7 = stmt7.executeQuery();
	    				
	    			    rs7.next();
	    			    
	    			    P = rs7.getDouble(1);
	    			    if(P!=0){
	    			    double A=calculate_Score(B,P);
	    			    Score1+= W[z]*A;
	    				
	    			    }
	    				
	    			}
	    			Score2=Score2+((NT_xy/NT_x)*Score1);
	    		}
	    		Score3=Score3+(NT_x/N_T*Score2);
	    	}
	    	Score4+=Score3;
	    	
	    }  
	    Score=Score4/pages;
    	System.out.printf("%f",Score);

	      	    }
	    catch (Exception e)
	    {
	      System.err.println("Got an exception! ");
	      System.err.println(e.getMessage());
	      e.printStackTrace();
	    }
	  }
	

	}
