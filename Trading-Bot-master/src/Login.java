import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Login extends JFrame {
    private JLabel messageLabel1;
    private JLabel messageLabel2;
    private JTextField textField1;
    private JTextField textField2;
    private JButton button1;

    public Login() throws SQLException {



        super("Turnip Market");
        setSize(330, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);


        messageLabel1 = new JLabel("Email: ");
        messageLabel1.setSize(100, 30);
        messageLabel1.setLocation(70, 50);
        add(messageLabel1);

        textField1 = new JTextField(10);
        textField1.setSize(100, 30);
        textField1.setLocation(150, 50);
        add(textField1);

        messageLabel2 = new JLabel("Password: ");
        messageLabel2.setSize(100, 30);
        messageLabel2.setLocation(70, 100);
        add(messageLabel2);

        textField2 = new JTextField(10);
        textField2.setSize(100, 30);
        textField2.setLocation(150, 100);
        add(textField2);

        button1 = new JButton("Login");
        button1.setSize(100, 30);
        button1.setLocation(100, 150);
        button1.addActionListener(new Button1Clicked());
        add(button1);
        setVisible(true);
    }

    //LOGGING IN CREATES A NEW DASHBOARD PERTAINING TO THE USER
    //DASHBOARD MAY NEED USER PARAMETER TO IMPLEMENT USER INFO
    private class Button1Clicked implements ActionListener
    {

        /////////////////// NEEDS TO ACCESSES THE DATABASE FIRST TO COMPARE TO TEXTFIELDS /////////
        Database current = new Database();
        Members currentMembers = current.fetchMembers();
        private Button1Clicked() throws SQLException {
        }

        public void actionPerformed(ActionEvent e)
        {
            if (currentMembers.logInUser(textField1.getText(),textField2.getText()))
            {
                try {
                    Portfolio p = current.fetchPortfolio(currentMembers.getCurrentUser());
                    dispose();
                    Broker broker = new Broker(currentMembers.getCurrentUser(),p);
                    new Dashboard(broker);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

            else
            {
                JOptionPane.showMessageDialog(null, "Incorrect Login Information");
            }
        }
    }
}
