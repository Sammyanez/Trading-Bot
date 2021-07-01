
public class User {

	private long user_id;
	private String first_name;
	private String last_name;
	private String password;
	private String email;
	private double money;
	private double balance;
	
	public User(long user_id, String first_name, String last_name, String password , String email,double money,double balance) {
		this.user_id = user_id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.password = password;
		this.email = email;
		this.money = money;
		this.balance = balance;
	}
	
	public boolean checkCredentials(String email, String password) {
		return (this.email.equals(email) && this.password.equals(password));
	}
	
	public String getFirstName() {
		return this.first_name;
	}
	public String getLastName() {
		return this.last_name;
	}
	public String getEmail() {
		return this.email;
	}
	public long getUserId() {
		return this.user_id;
	}

	public String getPassword() {
		return this.password;
	}
	public double getBalance(){return this.balance;};
	public double getMoney(){return this.money;};
	public void changeMoney(Double m){
		this.money = this.money + m;
	}
	public void changeBalance(Double m){
		this.balance = this.balance + m;
	}

}
