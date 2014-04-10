import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

public class populate
{
    Connection mainConnection = null;
    Statement mainStatement = null;
    ResultSet mainResultSet = null;
    static String buildingsfile;
    static String studentsfile;
    static String aSysfile;
    
    /**
     * @throws SQLException 
     * @throws IOException ***************************/
    public static void main(String args[]) throws IOException, SQLException
    {
        System.out.println();
    	populate e = new populate(args);
    	//e.ConnectToDB();
    	//e.PublishData();
        System.out.println();
    }

    /*****************************/
    public populate(String args[]) throws IOException, SQLException
    {
    	int i;
        for (i=0;i<args.length;i++)
        {
            File ff=new File(args[i]);
            
            if (ff.getName().equals("building.xy"))
            {    
            	buildingsfile=args[i];
            }
            
            else  if (ff.getName().equals("students.xy"))
            { 
            	studentsfile=args[i];
            }

            else  if (ff.getName().equals("announcementSystems.xy"))
            {    
            	aSysfile=args[i];
            }
        }
    	
        ConnectToDB();			// connect to DB

    	PublishData();			// publish some data

		//SearchAllTuples();		// search for all tuples

    	//ShowMetaData();			// show meta data

    	//ShowAllTuples();		// show the result of the search
    }


    /*****************************/
    public void PublishData()
    {
        try
        {

				// delete previous data from the DB
            System.out.print( "\n ** Deleting previous tuples ..." );
            mainStatement.executeUpdate( "delete from buildings" );
            mainStatement.executeUpdate( "delete from students" );
            mainStatement.executeUpdate( "delete from aSys" );
            System.out.println( ", Deleted. **" );


			// publish new data
            System.out.print( " ** Inserting Data ..." );
            //insert buildings 
            
            FileReader reader = new FileReader("F:/study/正在学/Database Systems/homework2/HW2/buildings.xy");
            BufferedReader br = new BufferedReader(reader);
           
            String str = null;
            String coodinate = "" ;
            
            while((str = br.readLine()) != null) {
                  String a[] = str.split(",");
                  
                  coodinate = "";//reset
                  for(int i = 2; i < a.length; i++){
                	  if(i != a.length - 1)
                		  coodinate += a[i] + ",";
                	  else
                		  coodinate += a[i];
                  }
                  
                  System.out.println( "insert into buildings values "
                		  	+ "(" + a[0] + "," + a[1] + ",SDO_GEOMETRY(2003, NULL, NULL,"
                		  	+ "SDO_ELEM_INFO_ARRAY(1,1003,1),"
                		  	+ "SDO_ORDINATE_ARRAY(" + coodinate + ")))");
                  
                  mainStatement.executeUpdate( 
                		  "insert into buildings values "
                		  + "('" + a[0] + "','" + a[1] + "',SDO_GEOMETRY(2003, NULL, NULL,"
                		  		+ "SDO_ELEM_INFO_ARRAY(1,1003,1),"
                		  + "SDO_ORDINATE_ARRAY(" + coodinate + ")))" );
       
                  System.out.println(str);
            }
           
            br.close();
            reader.close();
            
            //insert students
            reader = new FileReader("F:/study/正在学/Database Systems/homework2/HW2/students.xy");
            br = new BufferedReader(reader);
           
            str = "";
            coodinate = "" ;
            
            while((str = br.readLine()) != null) {
                  String a[] = str.split(",");
                  
                  coodinate = "";//reset
                  for(int i = 1; i < a.length; i++){
                	  if(i != a.length - 1)
                		  coodinate += a[i] + ",";
                	  else
                		  coodinate += a[i];
                  }
                  
                  System.out.println( "insert into students values "
                		  	+ "('" + a[0] + "',SDO_GEOMETRY(2001, NULL,"
                		  	+ "SDO_POINT_TYPE(" + coodinate + ", NULL),"
                		  	+ "NULL, NULL))");
                  
                  mainStatement.executeUpdate( 
                		  "insert into students values "
                      		  	+ "('" + a[0] + "',SDO_GEOMETRY(2001, NULL,"
                      		  	+ "SDO_POINT_TYPE(" + coodinate + ", NULL),"
                      		  	+ "NULL, NULL))");
       
                  System.out.println(str);
            }
           
            br.close();
            reader.close();
            
            //insert announcementSystems
            reader = new FileReader("F:/study/正在学/Database Systems/homework2/HW2/announcementSystems.xy");
            br = new BufferedReader(reader);
           
            str = "";
            coodinate = "" ;

            int [][] Th;
            Th = new int[3][2];
            
            while((str = br.readLine()) != null) {
                  String a[] = str.split(", ");

                  Th[0][0] = Integer.parseInt(a[1]); 
                  Th[0][1] = Integer.parseInt(a[2]) + Integer.parseInt(a[3]); 
                  Th[1][0] = Integer.parseInt(a[1]) + Integer.parseInt(a[3]);
                  Th[1][1] = Integer.parseInt(a[2]); 
                  Th[2][0] = Integer.parseInt(a[1]); 
                  Th[2][1] = Integer.parseInt(a[2]) - Integer.parseInt(a[3]);
                 

                  
                  coodinate = String.valueOf(Th[0][0]) + ","
                		   		+ String.valueOf(Th[0][1]) + ","
                		   		+ String.valueOf(Th[1][0]) + ","
                		   		+ String.valueOf(Th[1][1]) + ","
                		   		+ String.valueOf(Th[2][0]) + ","
                		   		+ String.valueOf(Th[2][1]);
                  
                  System.out.println(  
                		  	"insert into aSys values "
                			+ "('" + a[0] + "',SDO_GEOMETRY(2003, NULL, NULL,"
          		  			+ "SDO_ELEM_INFO_ARRAY(1,1003,4),"
          		  			+ "SDO_ORDINATE_ARRAY(" + coodinate + "))," 
          		  			+ a[3] + ")" );
                  
                  mainStatement.executeUpdate( 
              		  	"insert into aSys values "
              		  		+ "('" + a[0] + "',SDO_GEOMETRY(2003, NULL, NULL,"
        		  			+ "SDO_ELEM_INFO_ARRAY(1,1003,4),"
        		  			+ "SDO_ORDINATE_ARRAY(" + coodinate + "))," 
        		  			+ a[3] + ")");
       
                  System.out.println(str);
            }
           
            br.close();
            reader.close();
            System.out.println( ", Done.\n **" );

        }
        catch( Exception e )
        { System.out.println( " Error 2: " + e.toString() ); }
    }

    /*****************************/
    public void SearchAllTuples()
    {
        try
        {
                                // searches for all tuples
            System.out.println(" ** Selecting all tuples in the table **" );
            mainResultSet = mainStatement.executeQuery( "select * from info " );
        }
        catch( Exception e )
        { System.out.println( " Error : " + e.toString() ); }
    }

    /*****************************/
    public void ShowMetaData()
    {
    	try
		{
				// shows meta data
    	    System.out.println("\n ** Obtaining Meta Data ** " );
	    	ResultSetMetaData meta = mainResultSet.getMetaData();
	    	for( int col=1; col<=meta.getColumnCount(); col++ )
    			System.out.println( "Column: " + meta.getColumnName(col) + "\t, Type: " + meta.getColumnTypeName(col) );
		}
    	catch( Exception e )
		{ System.out.println( " Error : " + e.toString() ); }
    }

    /*****************************/
    public void ShowAllTuples()
    {
    	try
		{
				// shows result of the query
    	    ResultSetMetaData meta = mainResultSet.getMetaData();

	    	System.out.println("\n ** Showing all Tuples ** " );
	    	int tupleCount=1;
     		while( mainResultSet.next() )
	    	{
				System.out.print( "Tuple " + tupleCount++ + " : " );
	        	for( int col=1; col<=meta.getColumnCount(); col++)
		   			System.out.print( "\""+mainResultSet.getString( col )+"\"," );
        		System.out.println();
    	    }
        }
		catch( Exception e )
	    { System.out.println(" Error : " + e.toString() ); }

		System.out.println();
    }


    /*****************************/
    public void ConnectToDB()
    {
		try
		{
			// loading Oracle Driver
    		System.out.print("Looking for Oracle's jdbc-odbc driver ... ");
	    	DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
	    	System.out.println(", Loaded.");

			String URL = "jdbc:oracle:thin:@127.0.0.1:1521:HOMEWORK1";
	    	String userName = "system";
	    	String password = "Lwb3838513";

	    	System.out.print("Connecting to DB...");
	    	mainConnection = DriverManager.getConnection(URL, userName, password);
	    	System.out.println(", Connected!");

    		mainStatement = mainConnection.createStatement();

   		}
   		catch (Exception e)
   		{
     		System.out.println( "Error while connecting to DB: "+ e.toString() );
     		e.printStackTrace();
     		System.exit(-1);
   		}
    }


}


