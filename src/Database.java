import java.io.File;
import java.sql.*;
import java.util.UUID;

// https://bitbucket.org/xerial/sqlite-jdbc/downloads
public class Database
{
	public Connection connection;
	public Connection transaction;
	public Statement statmt;
	public ResultSet resSet;
	
	public Database() throws Exception 
	{
		// remove old file
		File file=new File("test.s3db");
		if(file.exists()) file.delete();

		connection = null;
		Class.forName("org.sqlite.JDBC");
		connection = DriverManager.getConnection("jdbc:sqlite:test.s3db");
		if(connection==null) throw new Exception("can't create connection");
		
		//connection.setAutoCommit(false);
	}
	public void CreateDB() throws ClassNotFoundException, SQLException
	{
		statmt = connection.createStatement();
		statmt.execute("CREATE TABLE if not exists 'users' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' text, 'phone' BIGINT);");
	}
	public void WriteDB() throws SQLException
	{
		PreparedStatement statmt=connection.prepareStatement("INSERT INTO 'users' ('phone', 'name') VALUES (?, ?);");
		statmt.setLong(1,(long)(Math.random()*1000000));
		statmt.setString(2,UUID.randomUUID().toString());
		statmt.execute();
		//connection.commit(); // not need to begin transaction
		// TODO: place to the catch block connection.rollback();
	}
	public void ReadDB() throws ClassNotFoundException, SQLException
	{
		PreparedStatement statmt=connection.prepareStatement("SELECT * FROM users");
		resSet=statmt.executeQuery();
		while(resSet.next())
		{
			int id = resSet.getInt("id");
			String  name = resSet.getString("name");
			String  phone = resSet.getString("phone");
			//System.out.println( "ID = " + id +", name = " + name + " phone = " + phone );
		}	
		resSet.close();
	}
	public void CloseDB() throws ClassNotFoundException, SQLException
	{
		statmt.close();
		connection.close();
	}
}
