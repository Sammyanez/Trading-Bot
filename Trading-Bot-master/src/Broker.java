import java.sql.SQLException;

public class Broker {

    private Portfolio p;
    private User u;

    public Broker(User u, Portfolio p ){
        this.p = p;
        this.u = u;
    }

    public void depositMoney(double money) throws SQLException {
        this.u.changeMoney(money);
        this.u.changeBalance(money);
        Database.changeMoney(this.u,this.u.getMoney(),this.u.getBalance());
    }

    public boolean buyStock(String ticker,int shares) throws SQLException {
        Double stockPrice = Yahoo.getStockPrice(ticker);
        if(stockPrice*shares > this.u.getBalance()){
            System.out.println("User CANNOT buy stock! ");
            return false;
        }else{

            Stock s = new Stock(
                    System.currentTimeMillis(),
                    ticker,
                    stockPrice,
                    stockPrice,
                    shares
            );
            System.out.println("User can buy stock! ");
            this.u.changeBalance(-1*stockPrice*shares);
            Database.changeMoney(this.u,this.u.getMoney(),this.u.getBalance());
            this.p.addStock(s);
            return true;
        }

        //TODO: Add Stock to portfolio
        //TODO: Withdraw money from User's balance
        //TODO: If user doesn't have enough money return false
    }
    public void sellStock(long stock_id, int shares) throws SQLException {
        Stock s = this.p.getStock(stock_id);
        if(s != null){
            double profit = s.sellShares(shares);
            if(s.getShares() == 0){
                this.p.removeStock(s.getID());
                Database.removeStockDB(s.getID());
            }else{
                //TODO:Database.sellStock
                Database.sellStock(s);
            }
            User curr_user = Members.getCurrentUser();
            curr_user.changeBalance(profit);
            Database.changeMoney(curr_user,curr_user.getMoney(),curr_user.getBalance());
        }
        //TODO: Check if stock is in User's portfolio
        //TODO: Calculate how much money they are going to get and add that do
        //TODO: User's 'bank'
        //TODO: Remove Stock amount from Portfolio
    }

    public Portfolio getPortfolio(){
        return  this.p;
    }
    public User getUser(){
        return this.u;
    }


}
