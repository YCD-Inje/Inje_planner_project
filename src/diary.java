package model;

import controller.DBConnection;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class diary extends JFrame {

    private Connection con;
    private ResultSet rs;
	private String url = "jdbc:mysql://localhost:3306/inje?severTimezon=UTC";
    private String user = "root";
    private String password = "chlwnsgur1!";
    private PreparedStatement pstmt;
    private JFrame frame = new JFrame("수정창");
    private JPanel buttonPanel;
    private JPanel contentPanel;
    private JPanel homePanel;
    private String loggedInUserId;
    private DBConnection loginInstance;
    
    
    public diary(DBConnection loginInstance) {
        this.loginInstance = loginInstance;
        this.loggedInUserId = loginInstance.getLoggedInUserId();
        System.out.println("로그인 한 아이디: " + loggedInUserId);

        try {
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "데이터베이스 연결에 실패했습니다.");
            System.exit(1);
        }
        
        setTitle("Inje Weekly Planner");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLayout(new BorderLayout());

        buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.WEST);

        homePanel = createHomePanel();
        contentPanel = homePanel;
        add(contentPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public void showLoggedInUserId() {
        System.out.println("로그인한 아이디: " + loggedInUserId);
    }

    public JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));
        
        
        ImageIcon Diary = new ImageIcon("image/Diary.png");
        
        JButton homeButton = new JButton();
        ImageIcon mainIcon = new ImageIcon("image/_Home.png");
        homeButton.setIcon(mainIcon);
        homeButton.setContentAreaFilled(false);
        homeButton.setBorder(BorderFactory.createEmptyBorder());
        
        
        JButton thisButton = new JButton("");
        ImageIcon thisIcon = new ImageIcon("image/This_Week.png");
        thisButton.setIcon(thisIcon);
        thisButton.setContentAreaFilled(false);
        thisButton.setBorder(BorderFactory.createEmptyBorder());
        
        
        
        
        JButton nextButton = new JButton("");
        ImageIcon nextIcon = new ImageIcon("image/Next_Week.png");
        nextButton.setIcon(nextIcon);
        nextButton.setContentAreaFilled(false);
        nextButton.setBorder(BorderFactory.createEmptyBorder());
        
      
        JButton editButton = new JButton();
        ImageIcon editIcon = new ImageIcon("image/_Settings.png");
        editButton.setIcon(editIcon);
        editButton.setContentAreaFilled(false);
        editButton.setBorder(BorderFactory.createEmptyBorder());
        
       
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                contentPanel.add(createHomePanel(), BorderLayout.CENTER);
                contentPanel.revalidate();
                contentPanel.repaint();
                
                System.out.print(loginInstance.getLoggedInUserId() + "\n");
            }
        });

        thisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                contentPanel.add(createPlanPanel(), BorderLayout.CENTER);
                contentPanel.revalidate();
                contentPanel.repaint();
                
                System.out.print(loginInstance.getLoggedInUserId() + "\n");
            }
        });
        
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                contentPanel.add(createPlanPanel(), BorderLayout.CENTER);
                contentPanel.revalidate();
                contentPanel.repaint();
                
                System.out.print(loginInstance.getLoggedInUserId() + "\n");
            }
        });
        
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                contentPanel.add(createEditPanel(), BorderLayout.CENTER);
                contentPanel.revalidate();
                contentPanel.repaint();
                
                System.out.print(loginInstance.getLoggedInUserId() + "\n");
            }
        });
        int width = Diary.getIconWidth();
		int height = Diary.getIconHeight();
		int newWidth = 110;
		int newHeight = 100;
		
		
        ImageIcon resizedIcon = new ImageIcon(Diary.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
        
        panel.add(new JLabel(resizedIcon));
     
        panel.add(homeButton);
        panel.add(thisButton);
        panel.add(nextButton);
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
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        String[] columnNames = {"월", "화", "수", "목", "금", "토", "일"};
        String[][] data = new String[3][7];

        JTable table = new JTable(data, columnNames);

        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        return panel;
    }
    
    private JTextField idField, nameField;
    private JPasswordField currentPwField, newPwField, pwConfirmField;
    private JTextField departmentField, emailField, phoneField;
    
    public void setUserInfoFromDB(String userId) {
        try {
            String sql = "SELECT * FROM user WHERE ID = ?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, loginInstance.getLoggedInUserId());
            rs = pstmt.executeQuery();

            if (rs.next()) {
                idField.setText(rs.getString("ID"));
                nameField.setText(rs.getString("name"));
                currentPwField.setText("");
                newPwField.setText("");
                pwConfirmField.setText("");
                departmentField.setText(rs.getString("department"));
                emailField.setText(rs.getString("email"));
                phoneField.setText(rs.getString("phone"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

  
    public JPanel createEditPanel() {
        JPanel panel = new JPanel(new GridLayout(9, 2));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
     
        
        
        JLabel idLabel = new JLabel("ID:");
        idField = new JTextField(20);
        idField.setEditable(false);

        JLabel nameLabel = new JLabel("이름:");
        nameField = new JTextField(20);

        JLabel currentPwLabel = new JLabel("현재 PW:");
        currentPwField = new JPasswordField(20);
     
        JLabel newPwLabel = new JLabel("새로운 PW:");
        newPwField = new JPasswordField(20);
 
        JLabel pwConfirmLabel = new JLabel("PW 확인:");
        pwConfirmField = new JPasswordField(20);  
        
        JLabel departmentLabel = new JLabel("학과:");
        departmentField = new JTextField(20);

        JLabel emailLabel = new JLabel("이메일:");
        emailField = new JTextField(20);

        JLabel phoneLabel = new JLabel("전화번호:");
        phoneField = new JTextField(20);

        setUserInfoFromDB(loggedInUserId);
        
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
        
        
        
        
        
        
        
        
        
        JButton logoutButton = new JButton("로그아웃");
        panel.add(logoutButton);
        
        
        
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
                DBConnection dbConnection = new DBConnection();
                dbConnection.connect();
                dbConnection.setLoggedInUserId(null);
                

                // 수정된 부분: main 메서드를 클래스 이름을 통해 정적으로 호출
                DBConnection.main(new String[0]);
            }
        });


        
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 사용자로부터 입력받은 값을 가져옴
                String id = idField.getText();
                String name = nameField.getText();
                String currentPw = new String(currentPwField.getPassword());
                String newPw = new String(newPwField.getPassword());
                String pwConfirm = new String(pwConfirmField.getPassword());
                String department = departmentField.getText();
                String email = emailField.getText();
                String phone = phoneField.getText();

             // 현재 비밀번호와 DB 비밀번호 비교
                if (!currentPw.equals(loginInstance.getPassword())) {
                    JOptionPane.showMessageDialog(diary.this, "현재 비밀번호가 일치하지 않습니다.", "비밀번호 오류", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // 새로운 비밀번호와 확인 비밀번호 일치 여부 확인
                if (!newPw.equals(pwConfirm)) {
                    JOptionPane.showMessageDialog(diary.this, "새로운 비밀번호가 일치하지 않습니다.", "비밀번호 오류", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // 새로운 비밀번호와 현재 비밀번호가 동일한 경우
                if (currentPw.equals(newPw)) {
                    JOptionPane.showMessageDialog(diary.this, "현재 비밀번호와 새로운 비밀번호가 동일합니다. 다른 비밀번호를 입력하세요.", "비밀번호 오류", JOptionPane.ERROR_MESSAGE);
                    return;
                }

             // DB 업데이트
                try {
                    String sql = "UPDATE user SET name=?, pass=?, department=?, email=?, phone=? WHERE ID=?";
                    pstmt = con.prepareStatement(sql);
                    pstmt.setString(1, name);
                    pstmt.setString(2, newPw);
                    pstmt.setString(3, department);
                    pstmt.setString(4, email);
                    pstmt.setString(5, phone);
                    pstmt.setString(6, id);

                    // 실행
                    int rowsAffected = pstmt.executeUpdate();

                    if (rowsAffected > 0) {
                        // 성공 메시지 또는 다른 작업 수행
                        JOptionPane.showMessageDialog(diary.this, "정보가 성공적으로 수정되었습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
                        System.out.println("DB 업데이트 성공!");

                        // 입력값 초기화
                        currentPwField.setText("");
                        newPwField.setText("");
                        pwConfirmField.setText("");
                    } else {
                        // 실패 메시지 또는 예외 처리
                        JOptionPane.showMessageDialog(diary.this, "DB 업데이트 실패: 수정된 행이 없습니다.", "실패", JOptionPane.ERROR_MESSAGE);
                        System.out.println("DB 업데이트 실패: 수정된 행이 없습니다.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    // 실패 메시지 또는 예외 처리
                    JOptionPane.showMessageDialog(diary.this, "DB 업데이트 실패: " + ex.getMessage(), "실패", JOptionPane.ERROR_MESSAGE);
                    System.out.println("DB 업데이트 실패: " + ex.getMessage());
                }
            }
        });

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
        
        DBConnection loginInstance = new DBConnection();

        new diary(loginInstance);
    }
}
