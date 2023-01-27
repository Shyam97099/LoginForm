import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import com.formdev.flatlaf.FlatDarculaLaf;

public class proj extends JFrame
 {
    final private Font mainFont = new Font("Segoe print",Font.BOLD,18);
    JTextField tfEmail;
    JPasswordField pfJPassword;
  

    public void initialize() 
    {
        JLabel lbLoginForm = new JLabel("Login Form",SwingConstants.CENTER);
        lbLoginForm.setFont(mainFont);

    JLabel lbEmail = new JLabel("Email");
    lbEmail.setFont(mainFont);

        tfEmail = new JTextField();
        tfEmail.setFont(mainFont);

        JLabel lbpassword = new JLabel("password");
        lbpassword.setFont(mainFont);

        pfJPassword = new JPasswordField();
        pfJPassword.setFont(mainFont);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(0, 1, 10,10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
            formPanel.add(lbLoginForm);
            formPanel.add(lbEmail);
            formPanel.add(tfEmail);
            formPanel.add(lbpassword);
            formPanel.add(pfJPassword);
            // ************** Button pannel*********
            JButton btnLogin = new JButton("Login");
            btnLogin.setFont(mainFont);

            btnLogin.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // TODO Auto-generated method stub
                    String email = tfEmail.getText();
                    String password = String.valueOf(pfJPassword.getPassword());
                User user = getAuthenticated(email, password);
                
                if(user!=null) {
                    MainFrame mainFrame = new MainFrame();
                    mainFrame.initialine(user);
                    dispose();

                }else{
                    JOptionPane.showMessageDialog(proj.this,
                    "Email or Password Invalid",
                   "try again",
                   JOptionPane.ERROR_MESSAGE);
                }}});
                JButton btncancel = new JButton("cancel");
                btncancel.setFont(mainFont);
                btncancel.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // TODO Auto-generated method stub
                        dispose();
                    }});

     JPanel buttonPanel = new JPanel();
     buttonPanel.setLayout(new GridLayout(1, 2, 10,0));
     buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
    buttonPanel.add(btnLogin);
    buttonPanel.add(btncancel);
            // *************initialiase the frame********
    add(formPanel, BorderLayout.NORTH);
    add(buttonPanel, BorderLayout.SOUTH);

     setTitle("Login Form");
     setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
     setSize(400, 500);
     setMinimumSize(new Dimension(350, 450));
     setLocationRelativeTo(null);
     setVisible(true);
    }
    private User getAuthenticated(String email, String password)
    {
        User user = null;
        final String DB_URL = "jdbc:mysql://localhost:3306/my+store";
        final String USERNAME = "root";
        final String PASSWORD = "";
        try {
           Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            String sql ="SELECT * FROM users WHERE email=? AND password=?";
            PreparedStatement  preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next()) {
                user = new User();
                user.name = resultSet.getString("name");
                user.email = resultSet.getString("email");
                user.phone = resultSet.getString("phone");
                user.address = resultSet.getString("address");
                user.password = resultSet.getString("password");
            }
            preparedStatement.close();
            conn.close();
        }catch(Exception e) {
           System.out.println("Database connexion faild");
         }
           return user;
        }
public static void main(String[] args) {
    try {
        UIManager.setLookAndFeel( new FlatDarculaLaf() );
    } catch( Exception ex ) {
        System.err.println( "Failed to initialize LaF" );
    }
    proj project = new proj();
    project.initialize();
}
    }

    