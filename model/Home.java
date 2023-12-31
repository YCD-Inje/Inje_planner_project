package model;


import model.*;
import controller.*;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;


public class Home {
	
	private static int cnt =0;
	private Connection con;
	private Statement st;
	private ResultSet rs;
	private String url = "jdbc:mysql://localhost:3306/inje?useUnicode=true&characterEncoding=UTF-8";
	private String user = "root";
	private String password = "chlwnsgur1!";
	private static boolean isLoginFormVisible = false;
	private static boolean isSignupFormVisible = false;
	private PreparedStatement pstmt;
	private JTextField idField, nameField, departmentField, emailField, phoneField ;
	private JPasswordField  currentPwField;
    private JFrame frame;
    private JPanel buttonPanel;
    private JPanel contentPanel;
    private JPanel homePanel;

    public Home() {
 
    	this.currentPwField = new JPasswordField();
    	this.nameField = new JTextField(10);
    	this.departmentField = new JTextField(10);
    	this.emailField = new JTextField(10);
    	this.phoneField = new JTextField(10);
    	this.idField = new JTextField(10);
    	
    	

        frame = new JFrame("간단한 화면 전환 예제");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 800);
        frame.setLayout(new BorderLayout());

        buttonPanel = createButtonPanel();
        frame.add(buttonPanel, BorderLayout.WEST);

        homePanel = createHomePanel();
        contentPanel = homePanel;
        frame.add(contentPanel, BorderLayout.CENTER);

        frame.setVisible(true);
       
      
    }
   
    public JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JButton homeButton = new JButton("HOME");
        JButton planButton = new JButton("PLAN");
        JButton editButton = new JButton("EDIT");

        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                contentPanel.add(createHomePanel(), BorderLayout.CENTER);
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });

        planButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                contentPanel.add(createPlanPanel(), BorderLayout.CENTER);
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                contentPanel.add(createEditPanel(), BorderLayout.CENTER);
                contentPanel.revalidate();
                contentPanel.repaint();
                
            }
        });

        panel.add(homeButton);
        panel.add(planButton);
        panel.add(editButton);

        return panel;
    }

    public JPanel createHomePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        String[] columnNames = {"이번주 총 일정", "진행 전 일정", "완료한 일정"};
        String[][] data = new String[2][3];

        JTable table = new JTable(data, columnNames);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        return panel;
    }

    public JPanel createPlanPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));  // 여백 추가

        String[] columnNames = {"월", "화", "수", "목", "금", "토", "일"};
        String[][] data = new String[3][7];

        JTable table = new JTable(data, columnNames);

        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        return panel;
    }
    
   
    public JPanel createEditPanel() {
    
    	
        JPanel panel = new JPanel(new GridLayout(9, 2));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField(20);

        JLabel nameLabel = new JLabel("이름:");
        JTextField nameField = new JTextField(20);

        JLabel currentPwLabel = new JLabel("현재 PW:");
        JPasswordField currentPwField = new JPasswordField(20);
     
        JLabel newPwLabel = new JLabel("새로운 PW:");
        JPasswordField newPwField = new JPasswordField(20);
 
        JLabel pwConfirmLabel = new JLabel("PW 확인:");
        JPasswordField pwConfirmField = new JPasswordField(20);
  
        
        JLabel departmentLabel = new JLabel("학과:");
        JTextField departmentField = new JTextField(20);

        JLabel emailLabel = new JLabel("이메일:");
        JTextField emailField = new JTextField(20);

        JLabel phoneLabel = new JLabel("전화번호:");
        JTextField phoneField = new JTextField(20);

        panel.add(idLabel);
        panel.add(idField);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(currentPwLabel);
        panel.add(currentPwField);
        panel.add(newPwLabel);
        panel.add(newPwField);
        panel.add(pwConfirmLabel);
        panel.add(pwConfirmField);
        panel.add(departmentLabel);
        panel.add(departmentField);
        panel.add(emailLabel);
        panel.add(emailField);
        panel.add(phoneLabel);
        panel.add(phoneField);

        JButton editButton = new JButton("수정");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(editButton);
        panel.add(new JLabel());
        panel.add(buttonPanel);
        
        
        
        
        
        try {
            con = DriverManager.getConnection(url, user, password);
            if (con != null) {
                // prepareStatement 등의 작업 수행
                String sql = "SELECT * FROM user WHERE id = ?";
                pstmt = con.prepareStatement(sql);

                pstmt.setString(1, );
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    String idFieldValue = rs.getString("idField");

                    // 데이터베이스에서 읽어온 값을 idField에 설정
                    idField.setText(idFieldValue);
                    
              
                }
            } else {
                System.out.println("Connection is null. Cannot execute query.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return panel;
    }

   
    
    public static void main(String[] args) {
    	
    	try {
              Class.forName("com.mysql.cj.jdbc.Driver");
          } catch (ClassNotFoundException e) {
              e.printStackTrace();
              JOptionPane.showMessageDialog(null, "JDBC 드라이버를 찾을 수 없습니다.");
              System.exit(1);
          }
        new Home();
    }
}