package at.windesign.application.serie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public enum serieDataSource
{
	INSTANCE;

	private static final String url  = "jdbc:mysql://localhost:3306/multimedia";
	private static final String user = "multimedia";
	private static final String pwd  = "WeissIchNicht8";

	private Connection conn = null;

	static
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	private serieDataSource()
	{
		// drop the table if it exists
		try
		{
			Statement stmt = this.getStatement();
			stmt.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			this.close();
		}
	}

	public Statement getStatement() throws SQLException
	{
		Statement stmt = null;
		// get connection
		conn = DriverManager.getConnection(url, user, pwd);
		stmt = conn.createStatement();
		return stmt;
	}

	public Connection getConnection() throws SQLException
	{
		conn = DriverManager.getConnection(url, user, pwd);
		return conn;
	}

	public void close()
	{
		try
		{
			if(conn != null)
			{
				conn.close();
				conn = null;
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
}