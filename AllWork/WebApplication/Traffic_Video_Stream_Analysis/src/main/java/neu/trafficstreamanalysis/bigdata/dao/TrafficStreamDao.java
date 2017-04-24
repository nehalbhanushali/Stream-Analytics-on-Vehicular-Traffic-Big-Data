package neu.trafficstreamanalysis.bigdata.dao;


import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;

public class TrafficStreamDao {

   // public static void main(String[] args) {
	
	public void viewAzureStreamData(){

        // Connect to database // check for connection string > JDBC
       // refer discussion for variables
        String url = String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;", hostName, dbName, user, password);
        
        Connection connection = null;
        
        try {   // class added so that the driver registers itself
        	    Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                connection = DriverManager.getConnection(url);
                String schema = connection.getSchema();
                System.out.println("Successful connection - Schema: " + schema);

                System.out.println("Query data example:");
                System.out.println("=========================================");

                // Create and execute a SELECT SQL statement.
                String selectSql = "SELECT * FROM licenseInputTable " ;
//                    + "FROM [SalesLT].[ProductCategory] pc "  
//                    + "JOIN [SalesLT].[Product] p ON pc.productcategoryid = p.productcategoryid";
                // project comppliance and jre changed to 1.7
                try (Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(selectSql)) {

                        // Print results from select statement
                        System.out.println("Top 20 categories:");
                        while (resultSet.next())
                        {
                            System.out.println(resultSet.getString(1) + " "
                                + resultSet.getString(2));
                        }
                }
        }
        catch (Exception e) {
                e.printStackTrace();
        }
    }
}

