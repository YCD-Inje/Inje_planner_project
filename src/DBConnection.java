package controller;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import model.aa;
import model.Planner;

public class DBConnection extends JFrame{
	private static int cnt =0;
	private Connection con;
	private Statement st;
	private ResultSet rs;
	private String url = "jdbc:mysql://localhost:3306/inje?severTimezon=UTC";
	private String user = "root";
	private String password = "chlwnsgur1!";
	private static boolean isLoginFormVisible = false;
	private static boolean isSignupFormVisible = false;
	private PreparedStatement pstmt;
	private String loggedInUserName;
	private String loggedInUserId;
	
	/**DB 연결*/
	
	public String getLoggedInUserId() {
		
		return loggedInUserId;
	}
	
	public void setLoggedInUserId(String userId) {
        this.loggedInUserId = userId;
    }


	
	public String getPassword() {
		try {
            String sql = "SELECT pass FROM user WHERE ID = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setString(1, loggedInUserId); // loggedInUserId는 클래스 필드로 가정합니다.
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("pass");
            } else {
                return null; // 사용자를 찾을 수 없는 경우
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null; // 오류 발생 시
        }
    }
    
	
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
		} catch(Exception e)
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
			 String sql = "INSERT INTO user (ID, pass, name, department, email, phone) VALUES (?, ?, ?, ?, ?, ?)";
			 String sql1 = "INSERT INTO plan (user_id) VALUES (?)";
			 try {
				 PreparedStatement pstmt = con.prepareStatement(sql);
			        pstmt.setString(1, username);
			        pstmt.setString(2, password);
			        pstmt.setString(3, name);
			        pstmt.setString(4, department);
			        pstmt.setString(5, email);
			        pstmt.setString(6, phone);
				 
			        int rowsAffected = pstmt.executeUpdate();
			        // 첫 번째 쿼리의 결과가 성공일 때만 두 번째 쿼리 실행하기 
			        if (rowsAffected > 0) {
			            PreparedStatement pstmt2 = con.prepareStatement(sql1);
			            pstmt2.setString(1, username);  

			            int rowsAffected2 = pstmt2.executeUpdate();

			            return rowsAffected2 > 0;
			        } else {
			            return false;
			        }
			    } catch (Exception e) {
			        e.printStackTrace();
			        return false;
			    }
			}
		public boolean updateMember(String username, String password, String name, String department, String email, String phone)
		{
			 String sql = "UPDATE user SET pass = ?, name = ?, department = ?, email = ?, phone = ? WHERE ID = ?";
			 
			 try {
				 PreparedStatement pstmt = con.prepareStatement(sql);
			        pstmt.setString(1, password);
			        pstmt.setString(2, name);
			        pstmt.setString(3, department);
			        pstmt.setString(4, email);
			        pstmt.setString(5, phone);
			        pstmt.setString(6, username);
				 
			        int rowsAffected = pstmt.executeUpdate();
			        return rowsAffected > 0;
			 }catch (Exception e) {
				 e.printStackTrace();
					return false;
		}
	}		
public class Login extends JFrame{
	
	Container c = getContentPane();
}
	
	public static void main(String[] args) {

		JFrame frame = new JFrame("로그인 화면");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setSize(1200, 1000);
	    frame.setLocationRelativeTo(null);
	    frame.setLayout(new GridBagLayout());
       
	    
	    GridBagConstraints constraints = new GridBagConstraints();
	    constraints.insets = new Insets(10, 10, 10, 10);

	    ImageIcon imageIcon = new ImageIcon("image/Diary.png");
	    JLabel imageLabel = new JLabel(imageIcon);

	    JPanel buttonPanel = new JPanel();
	    buttonPanel.setLayout(new FlowLayout());

	    JButton loginButton = new JButton("");
	    ImageIcon loginIcon = new ImageIcon("image/Login.png");
	    loginButton.setIcon(loginIcon);
	    loginButton.setContentAreaFilled(false);
	    loginButton.setBorder(BorderFactory.createEmptyBorder());
        
	    JButton signupButton = new JButton("");
	    ImageIcon signIcon = new ImageIcon("image/Sign up.png");
	    signupButton.setIcon(signIcon);
	    signupButton.setContentAreaFilled(false);
	    signupButton.setBorder(BorderFactory.createEmptyBorder());

	    buttonPanel.add(loginButton);
	    buttonPanel.add(signupButton);
	    
       
	    JPanel loginPanel = new JPanel();
	    
	    loginPanel.setLayout(new GridBagLayout());
	    loginPanel.setVisible(isLoginFormVisible);

	    GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets.set(5, 5, 5, 5); // Padding
	    
	    JLabel idLabel = new JLabel("ID:");
	    JTextField idField = new JTextField(15);
	    
	    gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(idLabel, gbc);

        gbc.gridx = 1;
        loginPanel.add(idField, gbc);


	    JLabel pwLabel = new JLabel("PW:");
	    JPasswordField pwField = new JPasswordField(15);
	 
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(pwLabel, gbc);

        
        gbc.gridx = 1;
        loginPanel.add(pwField, gbc);
	    
	    JButton loginConfirmButton = new JButton("");
	    ImageIcon login1Icon = new ImageIcon("image/Login1.png");
	    loginConfirmButton.setIcon(login1Icon);
	    loginConfirmButton.setContentAreaFilled(false);
	    loginConfirmButton.setBorder(BorderFactory.createEmptyBorder());
	    
	    
	 
	    
	    gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.SOUTH; // Align to the right
        loginPanel.add(loginConfirmButton, gbc);

	    
	    
	    
	    
	    JPanel signupPanel = new JPanel();
	    signupPanel.setLayout(new GridBagLayout());
	    signupPanel.setVisible(isSignupFormVisible);
	    
	    GridBagConstraints gbcs = new GridBagConstraints();
        gbcs.insets.set(5 ,5 , 5 , 5); // Padding
        
	   
	    
	    
	    JLabel signupIdLabel = new JLabel("ID:");
	    JTextField signupIdField = new JTextField(15);
	    
	    gbcs.gridx = 0;
	    gbcs.gridy = 0;
	    gbcs.anchor = GridBagConstraints.WEST;
        signupPanel.add(signupIdLabel, gbcs);

        gbcs.gridx = 1;
        signupPanel.add(signupIdField, gbcs);

	    
	    
	    JLabel signupPwLabel = new JLabel("PW:");
	    JPasswordField signupPwField = new JPasswordField(15);
	    
	    gbcs.gridx = 0;
	    gbcs.gridy = 1;
        signupPanel.add(signupPwLabel, gbcs);

        gbcs.gridx = 1;
        signupPanel.add(signupPwField, gbcs);
        
        JLabel confirmPasswordLabel = new JLabel("비밀번호 확인:");
	    JPasswordField confirmPasswordField = new JPasswordField(15);
	    
        gbcs.gridx = 0;
	    gbcs.gridy = 2;
	    gbcs.anchor = GridBagConstraints.WEST;
        signupPanel.add(confirmPasswordLabel, gbcs);

        gbcs.gridx = 1;
        signupPanel.add(confirmPasswordField, gbcs);
        
        
        
       
	    JLabel nameLabel = new JLabel("이름:");
	    JTextField nameField = new JTextField(15);
	    
	    gbcs.gridx = 0;
	    gbcs.gridy = 3;
        signupPanel.add(nameLabel, gbcs);

        gbcs.gridx = 1;
        signupPanel.add(nameField, gbcs);



	    JLabel numberNoLabel = new JLabel("번호:");
	    JTextField numberNoField = new JTextField(15);
	    
	    gbcs.gridx = 0;
	    gbcs.gridy = 4;
        signupPanel.add(numberNoLabel, gbcs);

        gbcs.gridx = 1;
        signupPanel.add(numberNoField, gbcs);


	    JLabel department = new JLabel("소속:");
	    JTextField departmentField = new JTextField(15);
	    
	    gbcs.gridx = 0;
	    gbcs.gridy = 5;
        signupPanel.add(department, gbcs);

        gbcs.gridx = 1;
        signupPanel.add(departmentField, gbcs);


	    JLabel email = new JLabel("이메일:");
	    JTextField emailField = new JTextField(15);
	    
	    gbcs.gridx = 0;
	    gbcs.gridy = 6;
        signupPanel.add(email, gbcs);

        gbcs.gridx = 1;
        signupPanel.add(emailField, gbcs);



	    
	    JPanel signupConfirmPanel = new JPanel();
	    JButton signupConfirmButton = new JButton("");
	    ImageIcon Signup1Icon = new ImageIcon("image/Sign up1.png");
	    signupConfirmButton.setIcon(Signup1Icon);
	    signupConfirmButton.setContentAreaFilled(false);
	    signupConfirmButton.setBorder(BorderFactory.createEmptyBorder());
	    
	    
	    gbcs.gridx = 1;
	    gbcs.gridy = 7;
	    gbcs.anchor = GridBagConstraints.SOUTH; // Align to the right
        signupPanel.add(signupConfirmButton, gbcs);

	    
	    
	    
	    signupConfirmPanel.add(new JLabel(""));
	    
	   
	   
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
	            	dbConnection.setLoggedInUserId(userId);
	            	frame.dispose();
	                //TEST_home.main(new String[0]);
	            	Planner testHome = new Planner(dbConnection);
                    //testHome.setVisible(true);
	              
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
	    	        String password1 = new String(confirmPasswordField.getPassword());
	    	        
	    	        // 데이터베이스에 연결
	    	        DBConnection dbConnection = new DBConnection();
	    	        dbConnection.connect();

	    	        // 사용자 정보를 데이터베이스에 추가
	    	        boolean isSuccess = dbConnection.addUser(username, password, name ,department ,email , phone );

	    	        if (isSuccess) {
	    	            // 회원가입 성공 메시지
	    	            JOptionPane.showMessageDialog(frame, "회원가입 성공!");
	    	        } 
	    	        else if(!password.equals(password1))
	    	        {
	    	        	 JOptionPane.showMessageDialog(frame, "비밀번호가 다릅니다. 다시 시도하세요.");
	    	        } 
	    	        else {
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
