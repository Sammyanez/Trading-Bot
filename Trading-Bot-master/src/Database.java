import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Database {

	private static Connection connection;

	public Database() throws SQLException {
		String s1 = "jdbc:mysql://35.222.96.220:3306/trading_bot?useSSL=false";
		connection = DriverManager.getConnection(s1,"root","qwerty12345");
		
	}

	
	//Pulls all the Users information stored in the database locally
	public Members fetchMembers() throws SQLException {
		Statement statement = connection.createStatement();
		ArrayList<User> users = new ArrayList<User>();
		ResultSet rs = statement.executeQuery("select * from Users");
		
		while(rs.next()) {
			User u = new User(
					rs.getLong(1),
					rs.getString(2),
					rs.getString(3),
					rs.getString(4),
					rs.getString(5),
					rs.getDouble(6),
					rs.getDouble(7)
			);
			users.add(u);
		}
		Members m = new Members(users);
		return m;
		
	}
	
	//This function gets all the information in the database
	//makes a ListArray of the Stocks and initializes the Portfolio 
	//class with that ListArray
	public Portfolio fetchPortfolio(User u) throws SQLException {
		Statement statement = this.connection.createStatement();
		ArrayList<Stock> stocks = new ArrayList<Stock>();
		ResultSet rs = statement.executeQuery("select * from Stock where user_id="+u.getUserId()+";");
		while(rs.next()) {
			Stock s = new Stock(
					rs.getLong(6),
					rs.getString(2),
					rs.getDouble(3),
					Yahoo.getStockPrice(rs.getString(2)),
					rs.getInt(5));
			stocks.add(s);
		}
		Portfolio p = new Portfolio(stocks);
		return p;
		
	}
	
	
	public static void addStockDB(Stock s) throws SQLException {
		
		String statement = ("INSERT INTO Stock VALUES ("+
									Members.getCurrentUser().getUserId()+","+
								"'"+s.getTickerNumber()+"',"+
									s.getPriceOfPurchase()+","+
									s.getCurrentPrice()+","+
									s.getShares()+","+
									s.getID()+");");
		System.out.println(statement);
		PreparedStatement preparedStatement = connection.prepareStatement(statement);
		preparedStatement.executeUpdate();
	}

	public static void sellStock(Stock s) throws SQLException {

		String statement = ("UPDATE Stock SET shares="+s.getShares()+" WHERE stock_id="+s.getID()+";");
		PreparedStatement preparedStatement = connection.prepareStatement(statement);
		preparedStatement.executeUpdate();
	}
	public static void removeStockDB(long stock_id) throws SQLException {

		String statement = ("DELETE FROM Stock WHERE stock_id="+stock_id+";");
		PreparedStatement preparedStatement = connection.prepareStatement(statement);
		preparedStatement.executeUpdate();
	}
	
	public static void addUserDB(User u) throws SQLException {
			
			String statement = ("INSERT INTO Users VALUES ("+
										"'"+ u.getUserId()+"',"+
										"'"+u.getFirstName()+"',"+
										"'"+u.getLastName()+"',"+
										"'"+u.getPassword()+"',"+
										"'"+u.getEmail()+"');");
			PreparedStatement preparedStatement = connection.prepareStatement(statement);
			preparedStatement.executeUpdate();
		}

		public static void changeMoney(User u,Double money,Double balance) throws SQLException {

			String statement = ("UPDATE Users" + " SET deposit="+ money + ",balance=" +balance+" WHERE user_id="+u.getUserId()+";");
			PreparedStatement preparedStatement = connection.prepareStatement(statement);
			preparedStatement.executeUpdate();
		}


	//Will close connection to the Database
	public void closeConnection() throws SQLException {
		connection.close();
	}
}
