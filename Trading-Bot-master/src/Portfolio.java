import java.sql.SQLException;
import java.util.ArrayList;

public class Portfolio {

	private ArrayList<Stock> stocks = new ArrayList<Stock>();

	
	public Portfolio(ArrayList<Stock> stocks) {
		this.stocks = stocks;
	}
	
	public Object[][] getData(){
		Object[][] data = new Object[this.stocks.size()][7];
		for(int i = 0 ; i <this.stocks.size(); i++){
			Stock s = this.stocks.get(i);
			data[i][0] = String.valueOf(s.getID());
			data[i][1] = String.valueOf(s.getTickerNumber());
			data[i][2] = String.valueOf(s.getCurrentPrice());
			data[i][3] = String.valueOf(s.getPriceOfPurchase());
			data[i][4] = String.valueOf(s.getEarnings());
			data[i][5] = String.valueOf(s.getShares());
			data[i][6] = s.getSuggestedMove();
		}
		return data;
	}
	public void showPortofolio() {
		System.out.println(" ");
		System.out.printf("%-17s %-11s %-9s %-9s %-9s %-9s\n",
				"ID","Ticker","Shares","Bought","Price","Earnings");
		for(Stock s : stocks) {
			if(s == null) {
				break;
			}
			System.out.printf("%-17d %-11s %-9d %-9.2f %-9.2f %-9.2f",
					s.getID(),
					s.getTickerNumber(),
					s.getShares(),
					s.getPriceOfPurchase(),
					s.getCurrentPrice(),
					s.getEarnings()
					);
			System.out.println(" ");
		}
		
		System.out.println(" ");
	}
	
	//Iterates through all the stocks available
	//Total value is equal to the current price of the stock times it's number of shares
	public double getPortfolioValue() {
		double total_value = 0.0;
		for(Stock s : this.stocks) {
			 total_value = s.getCurrentPrice() * s.getShares();
		}
		return total_value;
	}
	
	//Adding a Stock
	public void addStock(Stock stock) throws SQLException {

		this.stocks.add(stock);
		Database.addStockDB(stock);
	}
	public double getEarnings(){
		double earnings = 0.0;
		for(Stock s : this.stocks)	{
			earnings += s.getEarnings();
		}
		return Math.floor(earnings* 100) / 100;
	}
	public Stock getStock(long id){
	    Stock stock = null;
	    for(Stock s : this.stocks){
	    	if(s.getID() == id){
	    		return s;
			}
		}
	    return stock;
	}
	//Removing a Stock
	public void removeStock(long id) throws SQLException {
		Stock stockRemove = null;
		for(Stock s : stocks) {
			if(s.getID() == id) {
				stockRemove = s;
				
			}
		}
		stocks.remove(stockRemove);
		Database.removeStockDB(id);
		
	}
}
