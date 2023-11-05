import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;

import model.home;
import model.home2;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class DBConnection extends JFrame{
	private static int cnt =0;
	private Connection con;
	private Statement st;
	private ResultSet rs;
	private String url = "jdbc:mysql://localhost:3306/inje?severTimezone=UTC";
	private String user = "root";
	private String password = "chlwnsgur1!";
	private static boolean isLoginFormVisible = false;
	private static boolean isSignupFormVisible = false;
	private PreparedStatement pstmt;
	/**DB 연결*/
	
		
	public void connect()
	{
		JTextField idField = new JTextField(15);
		JPasswordField pwField = new JPasswordField(15);
		JFrame frame = new JFrame();
		try 
		{
			 Class.forName("com.mysql.cj.jdbc.Driver");
			 con = DriverManager.getConnection(url,user,password);
			 st = con.createStatement();
			 System.out.println("MySQL 서버 연동 성공");
			 
			 
			 if(cnt == 0)
			 {
				System.out.println("[Connection successful!]");
				cnt++;
			 }
		}catch(Exception e)
		{
			System.out.println("MySQL 서버 연동 실패" + e.toString());
		}
	}
	public boolean isUser(String userId , String userPass)
	{
		String sql = "SELECT * FROM user WHERE id = ? AND pass = ?";
		
		try
		{
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, userId);
			pstmt.setString(2, userPass);
			
			ResultSet rs = pstmt.executeQuery();
			
			return rs.next();
		}catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
	   
		public boolean addUser(String username, String password, String name, String department, String email, String phone)
		{
			 String sql = "INSERT INTO user (id, pass, name, department, email, phone) VALUES (?, ?, ?, ?, ?, ?)";
			 
			 try {
				 PreparedStatement pstmt = con.prepareStatement(sql);
			        pstmt.setString(1, username);
			        pstmt.setString(2, password);
			        pstmt.setString(3, name);
			        pstmt.setString(4, department);
			        pstmt.setString(5, email);
			        pstmt.setString(6, phone);
				 
			        int rowsAffected = pstmt.executeUpdate();
			        return rowsAffected > 0;
			 }catch (Exception e) {
				 e.printStackTrace();
					return false;
		}
	}

		

	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("로그인 화면");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(1000, 800);
	    frame.setLocationRelativeTo(null);
	    frame.setLayout(new GridBagLayout());

	    GridBagConstraints constraints = new GridBagConstraints();
	    constraints.insets = new Insets(10, 10, 10, 10);

	    ImageIcon imageIcon = new ImageIcon("C:\\Users\\User\\Desktop\\Java\\팀플\\Diary\\src\\다이어리.jpg");
	    JLabel imageLabel = new JLabel(imageIcon);

	    JPanel buttonPanel = new JPanel();
	    buttonPanel.setLayout(new FlowLayout());

	    JButton loginButton = new JButton("로그인");
	    JButton signupButton = new JButton("회원가입");

	    buttonPanel.add(loginButton);
	    buttonPanel.add(signupButton);

	    JPanel loginPanel = new JPanel();
	    loginPanel.setLayout(new GridLayout(3, 2));
	    loginPanel.setVisible(isLoginFormVisible);

	    JLabel idLabel = new JLabel("ID:");
	    JTextField idField = new JTextField(15);

	    JLabel pwLabel = new JLabel("PW:");
	    JPasswordField pwField = new JPasswordField(15);

	    loginPanel.add(idLabel);
	    loginPanel.add(idField);
	    loginPanel.add(pwLabel);
	    loginPanel.add(pwField);
	    
	    JButton loginConfirmButton = new JButton("확인");
	    loginPanel.add(new JLabel(""));
	    loginPanel.add(loginConfirmButton);
	    
	   


	    JPanel signupPanel = new JPanel();
	    signupPanel.setLayout(new GridLayout(8, 2));
	    signupPanel.setVisible(isSignupFormVisible);
	    
	    JLabel signupIdLabel = new JLabel("ID:");
	    JTextField signupIdField = new JTextField(15);
	    
	    JLabel signupPwLabel = new JLabel("PW:");
	    JPasswordField signupPwField = new JPasswordField(15);
	    
	    JLabel nameLabel = new JLabel("이름:");
	    JTextField nameField = new JTextField(15);


	    JLabel numberNoLabel = new JLabel("번호:");
	    JTextField numberNoField = new JTextField(15);

	    JLabel department = new JLabel("소속:");
	    JTextField departmentField = new JTextField(15);

	    JLabel email = new JLabel("이메일:");
	    JTextField emailField = new JTextField(15);


	    JLabel confirmPasswordLabel = new JLabel("비밀번호 확인:");
	    JPasswordField confirmPasswordField = new JPasswordField(15);
	    
	    JPanel signupConfirmPanel = new JPanel();
	    JButton signupConfirmButton = new JButton("가입");
	    signupConfirmPanel.add(new JLabel(""));
	    signupConfirmPanel.add(signupConfirmButton);
	    
	    signupPanel.add(signupIdLabel);
	    signupPanel.add(signupIdField);
	    signupPanel.add(signupPwLabel);
	    signupPanel.add(signupPwField);
	    signupPanel.add(confirmPasswordLabel);
	    signupPanel.add(confirmPasswordField);
	    signupPanel.add(nameLabel);
	    signupPanel.add(nameField);
	    signupPanel.add(department);
	    signupPanel.add(departmentField);
	    signupPanel.add(email);
	    signupPanel.add(emailField);
	    signupPanel.add(numberNoLabel);
	    signupPanel.add(numberNoField);
	   
	    signupPanel.add(new JLabel(""));
	    signupPanel.add(signupConfirmPanel);

	    loginButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            isLoginFormVisible = !isLoginFormVisible;
	            isSignupFormVisible = false;
	            loginPanel.setVisible(isLoginFormVisible);
	            signupPanel.setVisible(false);
	            frame.revalidate();
	        }
	    });

	    signupButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            isSignupFormVisible = !isSignupFormVisible;
	            isLoginFormVisible = false;
	            signupPanel.setVisible(isSignupFormVisible);
	            loginPanel.setVisible(false);
	            frame.revalidate();
	        }
	    });
	    loginConfirmButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            String userId = idField.getText();
	            String userPass = new String(pwField.getPassword());

	            DBConnection dbConnection = new DBConnection();
	            dbConnection.connect();
	            
	            boolean isAuthenticated = dbConnection.isUser(userId, userPass);
	            
	            if (isAuthenticated) {
	                // 성공적인 로그인 시, 새 창을 열거나 다른 작업을 수행할 수 있습니다.
	                JOptionPane.showMessageDialog(frame, "로그인 성공!");
	                frame.dispose();
	                home2.main(new String[0]);
	            } else {
	                // 로그인 실패 시 오류 메시지를 표시합니다.
	                JOptionPane.showMessageDialog(frame, "로그인 실패.");
	            }
	        }
	      
	    });
	    signupConfirmButton.addActionListener(new ActionListener() {
	    	 @Override
	    	 public void actionPerformed(ActionEvent e)
	    	 {
	    		   // 사용자가 입력한 정보 가져오기
	    		  String username = signupIdField.getText();
	    	        String password = new String(signupPwField.getPassword());
	    	        String name = nameField.getText();
	    	        String department = departmentField.getText();
	    	        String email = emailField.getText();
	    	        String phone = numberNoField.getText();
	    	        // 데이터베이스에 연결
	    	        DBConnection dbConnection = new DBConnection();
	    	        dbConnection.connect();

	    	        // 사용자 정보를 데이터베이스에 추가
	    	        boolean isSuccess = dbConnection.addUser(username, password, name ,department ,email , phone );

	    	        if (isSuccess) {
	    	            // 회원가입 성공 메시지
	    	            JOptionPane.showMessageDialog(frame, "회원가입 성공!");
	    	        } else {
	    	            // 회원가입 실패 메시지
	    	            JOptionPane.showMessageDialog(frame, "회원가입 실패. 다시 시도하세요.");
	    	        }
	    	    }
	    	 
	    	
	    });
	    constraints.gridx = 0;
	    constraints.gridy = 0;
	    frame.add(imageLabel, constraints);

	    constraints.gridy = 1;
	    frame.add(buttonPanel, constraints);

	    constraints.gridy = 2;
	    frame.add(loginPanel, constraints);

	    constraints.gridy = 3;
	    frame.add(signupPanel, constraints);

	    frame.setVisible(true);
	    
		

	}

}
