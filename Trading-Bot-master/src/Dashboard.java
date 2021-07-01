import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class Dashboard extends JFrame
{
    private static String[] columnNames = {"Index","Ticker","Current Price","Price of Purchase","Earnings","Shares","Suggested Move"} ;
    private Object[][] data={{"","","","","","",""}};
    private static JTable table;
    private static DefaultTableModel dm;
    private JScrollPane scroll;


    private static JTextField TickerNumber1,ShareNumber1,IndexNumber1,ShareNumber2,TickerNumber2,DepositAmount;
    private static JLabel messageLabel1, messageLabel2, messageLabel3;
    private JButton BUY_button;
    private JButton SELL_button;
    private JButton DEPOSIT_button;
    private JButton LOGOUT_button;

    private static Broker broker;

    public Dashboard(Broker broker)
    {
        super("Portfolio");
        this.broker = broker;
        setSize(900,600);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLayout(null);


        table = new JTable();
        dm = new DefaultTableModel(data, columnNames);
        table.setModel(dm);
        scroll = new JScrollPane(table);
        scroll.setSize(800, 270);
        scroll.setLocation(50, 50);
        add(scroll);

        
        
        //Earnings *add how to calculate earnings or function that does so
        messageLabel1 = new JLabel("Earnings: ");
        messageLabel1.setSize(330, 30);
        messageLabel1.setLocation(50, 520);
        add(messageLabel1);
        //Earnings

        //Money Invested *add how to calculate earnings or function that does so
        messageLabel2 = new JLabel("Money Invested: ");
        messageLabel2.setSize(330, 30);
        messageLabel2.setLocation(200, 520);
        add(messageLabel2);
        //Money Invested

        //Balance *add how to calculate earnings or function that does so*
        messageLabel3 = new JLabel("Balance: ");
        messageLabel3.setSize(330, 30);
        messageLabel3.setLocation(630, 400);
        add(messageLabel3);
        //Balance

        //Dealing with Buying
        BUY_button = new JButton("BUY");
        BUY_button.setSize(100, 30);
        BUY_button.setLocation(290, 350);
        BUY_button.addActionListener(new BUY_buttonClicked());
        add(BUY_button);
        setVisible(true);

        TickerNumber1 = new JTextField(10);
        TickerNumber1.setHorizontalAlignment(JTextField.CENTER);
        TickerNumber1.setText("Ticker #");
        TickerNumber1.setSize(100, 30);
        TickerNumber1.setLocation(50, 350);
        add(TickerNumber1);

        ShareNumber1 = new JTextField(10);
        ShareNumber1.setHorizontalAlignment(JTextField.CENTER);
        ShareNumber1.setText("# of Shares");
        ShareNumber1.setSize(100, 30);
        ShareNumber1.setLocation(170, 350);
        add(ShareNumber1);
        //^^^Dealing with Buying^^^


        //Dealing With Selling
        SELL_button = new JButton("SELL");
        SELL_button.setSize(100, 30);
        SELL_button.setLocation(290, 400);
        SELL_button.addActionListener(new SELL_buttonClicked());
        add(SELL_button);
        setVisible(true);

        IndexNumber1 = new JTextField(10);
        IndexNumber1.setHorizontalAlignment(JTextField.CENTER);
        IndexNumber1.setText("Index #");
        IndexNumber1.setSize(100, 30);
        IndexNumber1.setLocation(50, 400);
        add(IndexNumber1);

        ShareNumber2 = new JTextField(10);
        ShareNumber2.setHorizontalAlignment(JTextField.CENTER);
        ShareNumber2.setText("# of Shares");
        ShareNumber2.setSize(100, 30);
        ShareNumber2.setLocation(170, 400);
        add(ShareNumber2);
        //^^Dealing with Selling^^




        // Deposit
        DEPOSIT_button = new JButton("DEPOSIT");
        DEPOSIT_button.setSize(100, 30);
        DEPOSIT_button.setLocation(750, 350);
        DEPOSIT_button.addActionListener(new DEPOSIT_buttonClicked(this.broker,this.DepositAmount));
        add(DEPOSIT_button);
        setVisible(true);


        DepositAmount = new JTextField(10);
        DepositAmount.setHorizontalAlignment(JTextField.CENTER);
        DepositAmount.setText("Amount of Money");
        DepositAmount.setSize(100, 30);
        DepositAmount.setLocation(630, 350);
        add(DepositAmount);
        // ^^^ Deposit ^^^


        // Logout
        LOGOUT_button = new JButton("LOGOUT");
        LOGOUT_button.setSize(100, 30);
        LOGOUT_button.setLocation(750, 520);
        LOGOUT_button.addActionListener(new LOGOUT_buttonClicked());
        add(LOGOUT_button);
        setVisible(true);
        // ^^^ Logout ^^^



        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);


        updateTable();
    }

    public static void updateTable() {
        Object[][] data = broker.getPortfolio().getData();
        dm = new DefaultTableModel(data, columnNames);
        messageLabel1.setText("Earnings: " + broker.getPortfolio().getEarnings());
        messageLabel2.setText("Money Invested: " + broker.getUser().getMoney());
        messageLabel3.setText("Balance: " + broker.getUser().getBalance());
        table.setModel(dm);
    }
    private class BUY_buttonClicked implements ActionListener
    {


        public void actionPerformed(ActionEvent e)
        {
            System.out.println("BUY BUTTON WORKS");
            String ticker = Dashboard.TickerNumber1.getText();
            int shares = Integer.valueOf(Dashboard.ShareNumber1.getText());
            try {
                if(Dashboard.broker.buyStock(ticker,shares)){
                    Dashboard.updateTable();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

    private class SELL_buttonClicked implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            long stock_id = Long.parseLong(Dashboard.IndexNumber1.getText());
            int shares = Integer.parseInt(Dashboard.ShareNumber2.getText());
            try {
                Dashboard.broker.sellStock(stock_id,shares);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            Dashboard.updateTable();
            System.out.println("SELL BUTTON WORKS");
        }
    }


    private class DEPOSIT_buttonClicked implements ActionListener
    {
        private Broker b;
        private JTextField tf;
        DEPOSIT_buttonClicked(Broker b, JTextField tf){
            this.b = b;
            this.tf = tf;
        }
        public void actionPerformed(ActionEvent e)
        {
            System.out.println("DEPOSIT BUTTON WORKS");
            System.out.println(Dashboard.DepositAmount.getText());
            try {
                double money = Double.parseDouble(Dashboard.DepositAmount.getText());
                this.b.depositMoney(money);
                Dashboard.updateTable();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    private class LOGOUT_buttonClicked implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            System.out.println("LOGOUT BUTTON WORKS");
            JOptionPane.showMessageDialog(null, "You have been logged out!");
            dispose();

            //logging out creates new login panel;
            try {
                new Login();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

}


