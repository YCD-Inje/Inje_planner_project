import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;
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
		String enteredID = idField.getText();
		String enteredPW = new String(pwField.getPassword());
		 try {
	  

	         // SQL 쿼리
	         String sql = "SELECT * FROM inje WHERE id = ? AND pass = ?";
	         pstmt = con.prepareStatement(sql);
	         pstmt.setString(1, enteredID);
	         pstmt.setString(2, enteredPW);

	         // 쿼리 실행
	         rs = pstmt.executeQuery();

	         // 결과 확인
	         if (rs.next()) {
	             // 일치하는 사용자가 존재하면 로그인 성공
	             JOptionPane.showMessageDialog(frame, "로그인에 성공했습니다.");

	             frame.dispose();
	             home.main(new String[0]);
	         } else {
	             // 일치하는 사용자가 없으면 로그인 실패
	             JOptionPane.showMessageDialog(frame, "로그인에 실패했습니다. 올바른 ID와 PW를 입력하세요.");
	         }
	     } 
		 catch (SQLException ex) {
	         ex.printStackTrace();
	         JOptionPane.showMessageDialog(frame, "로그인 중 오류가 발생했습니다.");
	     } finally {
	         // 연결 및 리소스 해제
	         try {
	             if (rs != null) rs.close();
	             if (pstmt != null) pstmt.close();
	             if (con != null) con.close();
	         } catch (SQLException ex) {
	             ex.printStackTrace();
	         }
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
	    signupPanel.setLayout(new GridLayout(6, 2));
	    signupPanel.setVisible(isSignupFormVisible);

	    JLabel studentNoLabel = new JLabel("학번:");
	    JTextField studentNoField = new JTextField(15);

	    JLabel nameLabel = new JLabel("이름:");
	    JTextField nameField = new JTextField(15);

	    JLabel signupIdLabel = new JLabel("ID:");
	    JTextField signupIdField = new JTextField(15);

	    JLabel signupPwLabel = new JLabel("PW:");
	    JPasswordField signupPwField = new JPasswordField(15);

	    JLabel confirmPasswordLabel = new JLabel("비밀번호 확인:");
	    JPasswordField confirmPasswordField = new JPasswordField(15);
	    
	    JPanel signupConfirmPanel = new JPanel();
	    JButton signupConfirmButton = new JButton("가입");
	    signupConfirmPanel.add(new JLabel(""));
	    signupConfirmPanel.add(signupConfirmButton);
	    
	    signupPanel.add(studentNoLabel);
	    signupPanel.add(studentNoField);
	    signupPanel.add(nameLabel);
	    signupPanel.add(nameField);
	    signupPanel.add(signupIdLabel);
	    signupPanel.add(signupIdField);
	    signupPanel.add(signupPwLabel);
	    signupPanel.add(signupPwField);
	    signupPanel.add(confirmPasswordLabel);
	    signupPanel.add(confirmPasswordField);
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
