import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class diary extends JFrame {

    private Connection con;
    private ResultSet rs;
	private String url = "jdbc:mysql://localhost:3306/inje?severTimezon=UTC";
    private String user = "root";
    private String password = "5641";
    private PreparedStatement pstmt;
    private JPanel buttonPanel;
    private JPanel contentPanel;
    private JPanel homePanel;
    private String loggedInUserId;
    private DBConnection loginInstance;
    
    JLabel [] lb = new JLabel[14];
	JLabel [] lb2 = new JLabel[14];
	JButton [] moveButton = new JButton[14];
	JButton [] addButton = new JButton[14];
	JButton [] deleteButton = new JButton[14];
	
    JList<String>[] topDayLists;
    JList<String>[] bottomDayLists;
    
    Font italicFont1 = new Font("Arial", Font.PLAIN, 30);
	Font italicFont2 = new Font("Arial", Font.PLAIN, 20);
    
	
	
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
        setSize(1595, 765);
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
        
        
        ImageIcon Diary = new ImageIcon("images/Diary.png");
        
        JButton homeButton = new JButton();
        ImageIcon mainIcon = new ImageIcon("images/_Home.png");
        homeButton.setIcon(mainIcon);
        homeButton.setContentAreaFilled(false);
        homeButton.setBorder(BorderFactory.createEmptyBorder());
        
        
        JButton thisButton = new JButton("");
        ImageIcon thisIcon = new ImageIcon("images/This_Week.png");
        thisButton.setIcon(thisIcon);
        thisButton.setContentAreaFilled(false);
        thisButton.setBorder(BorderFactory.createEmptyBorder());
        
        
        JButton nextButton = new JButton("");
        ImageIcon nextIcon = new ImageIcon("images/Next_Week.png");
        nextButton.setIcon(nextIcon);
        nextButton.setContentAreaFilled(false);
        nextButton.setBorder(BorderFactory.createEmptyBorder());
        
      
        JButton editButton = new JButton();
        ImageIcon editIcon = new ImageIcon("images/_Settings.png");
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
                
                deletePreviousWeekData();
                
                System.out.print(loginInstance.getLoggedInUserId() + "\n");
            }
        });
        
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                contentPanel.add(createNextPlanPanel(), BorderLayout.CENTER);
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
        JPanel panel = new JPanel() {
        	@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                //가로줄
                g.drawLine(0, 0, 1295, 0);
                g.drawLine(0, 50, 1295, 50);
                g.drawLine(0, 350, 1295, 350);
                g.drawLine(0, 400, 1295, 400);
                g.drawLine(0, 701, 1295, 701);
                //세로줄
                g.drawLine(0, 0, 0, 701);
                g.drawLine(185, 0, 185, 701);
                g.drawLine(370, 0, 370, 701);
                g.drawLine(555, 0, 555, 701);
                g.drawLine(740, 0, 740, 701);
                g.drawLine(925, 0, 925, 701);
                g.drawLine(1110, 0, 1110, 701);
                g.drawLine(1295, 0, 1295, 701);
            }
        };
        panel.setLayout(null);

        
        topDayLists = new JList[14]; // 요일별 ToDo 리스트를 담을 배열 (위 리스트)
        bottomDayLists = new JList[14]; // 요일별 Done 리스트를 담을 배열 (아래 리스트)
        lb = new JLabel[14];
        lb2 = new JLabel[14];
        
        String[] week = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        
        for(int i=0; i<7; i++) {
        	//요일 ToDo List 라벨
            lb[i] = new JLabel(week[i], SwingConstants.CENTER);
            lb[i].setSize(185, 50);
            lb[i].setLocation(185 * i, 0);
            lb[i].setFont(italicFont1);
            panel.add(lb[i]);
            //요일 ToDo List
            topDayLists[i] = new JList<>();
            topDayLists[i].setSize(183, 220);
            topDayLists[i].setLocation(185 * i + 1, 65);
            panel.add(topDayLists[i]);
            //Done List로 옮기기 버튼
            moveButton[i] = new JButton("Clear"); // 아래로 이동하는 버튼
            moveButton[i].setSize(182, 50);
            moveButton[i].setLocation(185 * i + 2, 299);
            moveButton[i].addActionListener(new MoveButtonAction(topDayLists[i], bottomDayLists[i]));
            panel.add(moveButton[i]);
            //Done List 라벨
            lb2[i] = new JLabel("Done", SwingConstants.CENTER);
            lb2[i].setSize(185, 50);
            lb2[i].setLocation(185 * i, 350);
            lb2[i].setFont(italicFont2);
            panel.add(lb2[i]);
            //Done List
            bottomDayLists[i] = new JList<>();
            bottomDayLists[i].setSize(183, 220);
            bottomDayLists[i].setLocation(185 * i + 1, 415);
            panel.add(bottomDayLists[i]);
            //추가 삭제 버튼
            addButton[i] = new JButton("추가"); // 아래로 이동하는 버튼
            addButton[i].setSize(90, 50);
            addButton[i].setLocation(185 * i + 2, 650);
            addButton[i].addActionListener(new AddButtonAction(i));
            panel.add(addButton[i]);
            deleteButton[i] = new JButton("삭제"); // 아래로 이동하는 버튼
            deleteButton[i].setSize(90, 50);
            deleteButton[i].setLocation(185 * i + 94, 650);
            deleteButton[i].addActionListener(new DeleteButtonAction(topDayLists[i]));
            deleteButton[i].addActionListener(new DeleteButtonAction(bottomDayLists[i]));
            panel.add(deleteButton[i]);
            // 각 요일의 JList에 대한 모델 설정
            DefaultListModel<String> topModel = new DefaultListModel<>();
            DefaultListModel<String> bottomModel = new DefaultListModel<>();
            topDayLists[i].setModel(topModel);
            bottomDayLists[i].setModel(bottomModel);
            // 해당 요일의 일정 표시
            populateJLists(i);
        }
        lb[5].setForeground(Color.BLUE);
        lb[6].setForeground(Color.RED);


        panel.add(new JScrollPane(), BorderLayout.CENTER);

        return panel;
    }
    
    public JPanel createNextPlanPanel() {
        JPanel panel = new JPanel() {
        	@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                //가로줄
                g.drawLine(0, 0, 1295, 0);
                g.drawLine(0, 50, 1295, 50);
                g.drawLine(0, 350, 1295, 350);
                g.drawLine(0, 400, 1295, 400);
                g.drawLine(0, 701, 1295, 701);
                //세로줄
                g.drawLine(0, 0, 0, 701);
                g.drawLine(185, 0, 185, 701);
                g.drawLine(370, 0, 370, 701);
                g.drawLine(555, 0, 555, 701);
                g.drawLine(740, 0, 740, 701);
                g.drawLine(925, 0, 925, 701);
                g.drawLine(1110, 0, 1110, 701);
                g.drawLine(1295, 0, 1295, 701);
            }
        };
        panel.setLayout(null);
        
         
        topDayLists = new JList[14]; // 요일별 ToDo 리스트를 담을 배열 (위 리스트)
        bottomDayLists = new JList[14]; // 요일별 Done 리스트를 담을 배열 (아래 리스트)
        lb = new JLabel[14];
        lb2 = new JLabel[14];
        String[] week = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        
        for(int i=7; i<14; i++) {
        	//요일 ToDo List 라벨
            lb[i] = new JLabel(week[i-7], SwingConstants.CENTER);
            lb[i].setSize(185, 50);
            lb[i].setLocation(185 * (i-7), 0);
            lb[i].setFont(italicFont1);
            panel.add(lb[i]);
            //요일 ToDo List
            topDayLists[i] = new JList<>();
            topDayLists[i].setSize(183, 220);
            topDayLists[i].setLocation(185 * (i-7) + 1, 65);
            panel.add(topDayLists[i]);
            //Done List로 옮기기 버튼
            moveButton[i] = new JButton("Clear"); // 아래로 이동하는 버튼
            moveButton[i].setSize(182, 50);
            moveButton[i].setLocation(185 * (i-7) + 2, 299);
            moveButton[i].addActionListener(new MoveButtonAction(topDayLists[i], bottomDayLists[i]));
            panel.add(moveButton[i]);
            //Done List 라벨
            lb2[i] = new JLabel("Done", SwingConstants.CENTER);
            lb2[i].setSize(185, 50);
            lb2[i].setLocation(185 * (i-7), 350);
            lb2[i].setFont(italicFont2);
            panel.add(lb2[i]);
            //Done List
            bottomDayLists[i] = new JList<>();
            bottomDayLists[i].setSize(183, 220);
            bottomDayLists[i].setLocation(185 * (i-7) + 1, 415);
            panel.add(bottomDayLists[i]);
            //추가 삭제 버튼
            addButton[i] = new JButton("추가"); // 아래로 이동하는 버튼
            addButton[i].setSize(90, 50);
            addButton[i].setLocation(185 * (i-7) + 2, 650);
            addButton[i].addActionListener(new AddButtonAction(i));
            panel.add(addButton[i]);
            deleteButton[i] = new JButton("삭제"); // 아래로 이동하는 버튼
            deleteButton[i].setSize(90, 50);
            deleteButton[i].setLocation(185 * (i-7) + 94, 650);
            deleteButton[i].addActionListener(new DeleteButtonAction(topDayLists[i]));
            deleteButton[i].addActionListener(new DeleteButtonAction(bottomDayLists[i]));
            panel.add(deleteButton[i]);
            // 각 요일의 JList에 대한 모델 설정
            DefaultListModel<String> topModel = new DefaultListModel<>();
            DefaultListModel<String> bottomModel = new DefaultListModel<>();
            topDayLists[i].setModel(topModel);
            bottomDayLists[i].setModel(bottomModel);
            // 해당 요일의 일정 표시
            populateJLists(i);
        }
        lb[12].setForeground(Color.BLUE);
        lb[13].setForeground(Color.RED);

        panel.add(new JScrollPane(), BorderLayout.CENTER);

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
        
        
        JButton logoutButton = new JButton("로그아웃");
        buttonPanel.add(logoutButton);
        
        panel.add(buttonPanel);
        
        
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
                DBConnection dbConnection = new DBConnection();
                dbConnection.connect();
                dbConnection.setLoggedInUserId(null);
                
                dispose();

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
    
 // 특정 날짜와 bool 값에 따라 해당 요일의 계획을 가져오는 메서드
    private ArrayList<String> getPlansForDay(LocalDateTime dateTime, boolean boolValue) {
        ArrayList<String> plans = new ArrayList<>();
        
        try {
            String query = "SELECT list FROM plan WHERE DATE(data_time) = ? AND bool = ? AND user_id = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setDate(1, Date.valueOf(dateTime.toLocalDate()));
            pstmt.setBoolean(2, boolValue);
            pstmt.setString(3, loggedInUserId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String plan = rs.getString("list");
                plans.add(plan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "데이터베이스에서 계획을 가져오는 중 오류가 발생했습니다.");
        }

        return plans;
    }

    private void populateJLists(int dayIndex) {
        LocalDateTime now = LocalDateTime.now();

        // 해당 요일의 LocalDateTime 가져오기
        int currentDayOfWeek = now.getDayOfWeek().getValue();
        int daysUntilTargetDay = dayIndex - currentDayOfWeek + 1;
        LocalDateTime targetDay = now.plusDays(daysUntilTargetDay);
        targetDay = targetDay.withHour(0).withMinute(0).withSecond(0).withNano(0);

        // 해당 요일의 위와 아래 JList에 대한 계획 가져오기
        ArrayList<String> topPlans = getPlansForDay(targetDay, false); // bool 값이 false인 계획
        ArrayList<String> bottomPlans = getPlansForDay(targetDay, true); // bool 값이 true인 계획

        // 가져온 계획들을 JList에 설정
        DefaultListModel<String> topModel = new DefaultListModel<>();
        DefaultListModel<String> bottomModel = new DefaultListModel<>();

        for (String plan : topPlans) {
            topModel.addElement(plan);
        }

        for (String plan : bottomPlans) {
            bottomModel.addElement(plan);
        }

        // JList 갱신
        topDayLists[dayIndex].setModel(topModel);
        bottomDayLists[dayIndex].setModel(bottomModel);
    }
    
    public void deletePreviousWeekData() {
        try {
            // 현재 날짜를 가져옴
            LocalDate today = LocalDate.now();
            
            // 현재 날짜에서 일주일 전의 일요일 날짜 계산
            LocalDate previousSunday = today.minusWeeks(1).with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
            
            // 이전 주 일요일 이전의 데이터 삭제하는 쿼리 작성
            String deleteQuery = "DELETE FROM plan WHERE data_time < ?";

            PreparedStatement pstmt = con.prepareStatement(deleteQuery);
            pstmt.setObject(1, previousSunday.atStartOfDay());
            
            // 실행
            int deletedRows = pstmt.executeUpdate();
            
            if (deletedRows > 0) {
                System.out.println("이전 주의 데이터 삭제 완료");
            } else {
                System.out.println("삭제할 데이터가 없습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "이전 주의 데이터 삭제 중 오류가 발생했습니다.");
        }
    }
    
 // '추가' 버튼 액션 리스너 클래스
    class AddButtonAction implements ActionListener {
        private int dayIndex; // 요일 인덱스 (0부터 13까지)

        public AddButtonAction(int dayIndex) {
            this.dayIndex = dayIndex;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String userID = loggedInUserId; // 로그인한 유저 아이디


            // 추가하고자 하는 항목 (다이얼로그를 통해 사용자로부터 입력 받음)
            String newItem = JOptionPane.showInputDialog("일정 내용을 입력하세요:");

            // 요일별로 datetime 값 계산
            LocalDateTime dateTime = calculateDayDate(dayIndex); // dayIndex는 해당 요일의 인덱스(0부터 6까지)

            
            // 입력이 취소되거나 아무것도 입력하지 않았을 때의 처리
            if (newItem == null || newItem.trim().isEmpty()) {
                JOptionPane.showMessageDialog(diary.this, "내용을 입력하세요.", "경고", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            // DB에 데이터 삽입
            try {
                String sql = "INSERT INTO plan (user_id, data_time, list, bool) VALUES (?, ?, ?, ?)";
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setString(1, userID);
                pstmt.setObject(2, dateTime);
                pstmt.setString(3, newItem);
                pstmt.setBoolean(4, false);

                // 실행
                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    // 성공 메시지 또는 다른 작업 수행
                    JOptionPane.showMessageDialog(diary.this, "일정이 추가되었습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    // 실패 메시지 또는 예외 처리
                    JOptionPane.showMessageDialog(diary.this, "일정 추가 실패", "실패", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                // 실패 메시지 또는 예외 처리
                JOptionPane.showMessageDialog(diary.this, "일정 추가 실패: " + ex.getMessage(), "실패", JOptionPane.ERROR_MESSAGE);
            }
        }

     // 해당 요일의 날짜를 계산하는 메서드
        private LocalDateTime calculateDayDate(int dayIndex) {
            LocalDateTime now = LocalDateTime.now(); // 현재 날짜와 시간
            DayOfWeek today = now.getDayOfWeek(); // 현재 요일

            // 오늘이 해당 요일이라면 현재 날짜를 반환
            if (today.getValue() == dayIndex + 1) { // dayIndex는 0부터 시작하기 때문에 요일 값에 +1 해줍니다.
                return now;
            }

            // 현재 날짜에서 해당 요일로 이동하기 위해 추가로 필요한 일 수 계산
            int daysToAdd = dayIndex + 1 - today.getValue();
            if (daysToAdd < 0) {
                daysToAdd += 7; // 해당 요일이 오늘보다 이전이라면 다음 주의 해당 요일로 계산
            }

            // 계산된 일 수를 더하여 해당 요일의 날짜를 계산
            return now.plusDays(daysToAdd).toLocalDate().atStartOfDay();
        }
    }
 // '삭제' 버튼 액션 리스너 클래스
    class DeleteButtonAction implements ActionListener {
        private JList<String> list; // 해당 요일의 리스트

        public DeleteButtonAction(JList<String> list) {
            this.list = list;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedValue = list.getSelectedValue(); // 선택된 항목

            if (selectedValue != null) {
                // DB에서 해당 항목 삭제
                try {
                    String sql = "DELETE FROM plan WHERE list = ?";
                    PreparedStatement pstmt = con.prepareStatement(sql);
                    pstmt.setString(1, selectedValue);

                    // 실행
                    int rowsAffected = pstmt.executeUpdate();

                    if (rowsAffected > 0) {
                        // 성공 메시지 또는 다른 작업 수행
                        JOptionPane.showMessageDialog(diary.this, "선택한 항목이 삭제되었습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        // 실패 메시지 또는 예외 처리
                        JOptionPane.showMessageDialog(diary.this, "항목 삭제 실패", "실패", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    // 실패 메시지 또는 예외 처리
                    JOptionPane.showMessageDialog(diary.this, "항목 삭제 실패: " + ex.getMessage(), "실패", JOptionPane.ERROR_MESSAGE);
                }
            } else {
            	
            }
        }
    }
 // 'Clear' 버튼 액션 리스너 클래스
    class MoveButtonAction implements ActionListener {
        private JList<String> topList; // 상단 리스트
        private JList<String> bottomList; // 하단 리스트

        public MoveButtonAction(JList<String> topList, JList<String> bottomList) {
            this.topList = topList;
            this.bottomList = bottomList;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedValue = topList.getSelectedValue(); // 선택된 항목

            if (selectedValue != null) {
                // DB에서 해당 항목의 bool 값을 업데이트 (false에서 true로)
                try {
                    String sql = "UPDATE plan SET bool = ? WHERE list = ?";
                    PreparedStatement pstmt = con.prepareStatement(sql);
                    pstmt.setBoolean(1, true); // true로 업데이트
                    pstmt.setString(2, selectedValue);

                    // 실행
                    int rowsAffected = pstmt.executeUpdate();

                    if (rowsAffected > 0) {
                        // 성공 메시지 또는 다른 작업 수행
                        JOptionPane.showMessageDialog(diary.this, "선택한 항목이 완료로 표시되었습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        // 실패 메시지 또는 예외 처리
                        JOptionPane.showMessageDialog(diary.this, "완료로 표시 실패", "실패", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    // 실패 메시지 또는 예외 처리
                    JOptionPane.showMessageDialog(diary.this, "완료로 표시 실패: " + ex.getMessage(), "실패", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(diary.this, "완료로 표시할 항목을 선택해주세요.", "경고", JOptionPane.WARNING_MESSAGE);
            }
        }
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
