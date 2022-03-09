package JavaDB;
import java.util.*;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
class Details {
String uname;
String email;
String phone;
boolean success;
Details(String uname, String email, String phone, boolean success) {
this.uname = uname;
this.email = email;
this.phone = phone;
this.success = success;
}
}
class DBConnectivity extends JFrame {
Connection getConnections() throws ClassNotFoundException, SQLException {
final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
final String DB = "jdbc:mysql://localhost:3306/contact_book";
final String USER_NAME = "root";
final String PASSWORD = "dio2063@benedire";
Connection con = null;
Class.forName(JDBC_DRIVER);
con = DriverManager.getConnection(DB, USER_NAME, PASSWORD);
return con;
}
boolean insertData(String name) {
PreparedStatement pstmt = null;
Connection con = null;
boolean success = false;
DBConnectivity db = new DBConnectivity();
String sql = "INSERT INTO user VALUES(?,?,?,?)";
try {
con = db.getConnections();
pstmt = con.prepareStatement(sql);
pstmt.setInt(1, 1);
pstmt.setString(2, name);
pstmt.setString(3, "jojumon@gmail.com");
pstmt.setString(4, "9888674865");
pstmt.execute();
success = true;
pstmt.close();
con.close();
} catch (SQLException e1) {
success = false;
} catch (ClassNotFoundException e1) {
success = false;
}
return success;
}
Details searchData(String name) {
String email = "";
String uName = "";
String phone = "";
Statement stmt = null;
Connection con = null;
boolean success = false;
DBConnectivity db = new DBConnectivity();

boolean flag = Character.isLetter(name.charAt(1));
if(flag) {
            String sql = "SELECT email, name, phone_no FROM user WHERE name =
"+"'"+name+"'";
         }
         else {
            String sql = "SELECT email, name, phone_no FROM user WHERE phone_no =
"+"'"+name+"'";
         }

try {
con = db.getConnections();
stmt = con.createStatement();
ResultSet rs = stmt.executeQuery(sql);
if(rs.next()) {
email = rs.getString("email");
uName = rs.getString("name");
phone = rs.getString("phone_no");
}
success=true;
rs.close();
stmt.close();
con.close();
} catch (SQLException e1) {
success = false;
} catch (ClassNotFoundException e1) {
success = false;
}
return new Details(uName, email, phone, success);
}
}
class GUIActivity extends JFrame implements ActionListener {
Container container = getContentPane();
 JLabel userLabel = new JLabel("Enter Name: ");
 JTextField userField = new JTextField();
 JButton search = new JButton("Search");
 JTextField userName = new JTextField();
 JTextField userEmail = new JTextField();
 JTextField userPhone = new JTextField();

 GUIActivity() {
 setLocation();
addComponent();
setLayout(null);
setVisible(true);
setSize(500, 500);
userField.addActionListener(this);
search.addActionListener(this);
userName.addActionListener(this);
userEmail.addActionListener(this);
userPhone.addActionListener(this);
 }

 public void setLocation() {
userLabel.setBounds(80, 50, 100, 20);
userField.setBounds(80, 80, 200, 30);
search.setBounds(290, 80, 80, 30);
userName.setBounds(80, 200, 200, 30);
userEmail.setBounds(80, 230, 200, 30);
userPhone.setBounds(80, 260, 200, 30);
}
public void addComponent() {
container.add(userLabel);
container.add(userField);
container.add(search);
container.add(userName);
container.add(userEmail);
container.add(userPhone);
}
public void actionPerformed(ActionEvent e) {
if(e.getSource() == search) {
if(userField.getText().isEmpty()) {
JOptionPane.showMessageDialog(this,"Please Enter
Something!!");
}
else if(success == false) {
JOptionPane.showMessageDialog(this,"No Result Found!");
}
else {
DBConnectivity dbConnection = new DBConnectivity();
Details details =
dbConnection.searchData(userField.getText());
if(details.success) {
userName.setText(details.uname);
userEmail.setText(details.email);
userPhone.setText(details.phone);
}
}
}
}
}
public class DBDemo {
public static void main(String [] args) throws ClassNotFoundException,
SQLException {
 new GUIActivity();
 }
}