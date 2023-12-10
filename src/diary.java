import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

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
	
	Map<String, DefaultListModel<String>> todoListsByDay;
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
    //이번주 계획
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

        todoListsByDay = new HashMap<>();
        createDayLists();
        
        topDayLists = new JList[14]; // 요일별 ToDo 리스트를 담을 배열 (위 리스트)
        bottomDayLists = new JList[14]; // 요일별 Done 리스트를 담을 배열 (아래 리스트)
        String[] days = {"월요일 ToDo", "화요일 ToDo", "수요일 ToDo", "목요일 ToDo", "금요일 ToDo", "토요일 ToDo", "일요일 ToDo",
        		"다음주 월요일 ToDo", "다음주 화요일 ToDo", "다음주 수요일 ToDo", "다음주 목요일 ToDo", "다음주 금요일 ToDo", "다음주 토요일 ToDo", "다음주 일요일 ToDo"};
        String[] Done_days = {"월요일 Done", "화요일 Done", "수요일 Done", "목요일 Done", "금요일 Done", "토요일 Done", "일요일 Done",
        		"다음주 월요일 Done", "다음주 화요일 Done", "다음주 수요일 Done", "다음주 목요일 Done", "다음주 금요일 Done", "다음주 토요일 Done", "다음주 일요일 Done"};
        String[] week = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        
        for(int i=0; i<7; i++) {
        	topDayLists[i] = new JList<>(todoListsByDay.get(days[i]));
            bottomDayLists[i] = new JList<>(todoListsByDay.get(Done_days[i]));
            //요일 ToDo List 라벨
            lb[i] = new JLabel(week[i], SwingConstants.CENTER);
            lb[i].setSize(185, 50);
            lb[i].setLocation(185 * i, 0);
            lb[i].setFont(italicFont1);
            panel.add(lb[i]);
            //요일 ToDo List
            topDayLists[i].setSize(183, 220);
            topDayLists[i].setLocation(185 * i + 1, 65);
            panel.add(topDayLists[i]);
            //Done List로 옮기기 버튼
            moveButton[i] = new JButton("Clear"); // 아래로 이동하는 버튼
            moveButton[i].setSize(182, 50);
            moveButton[i].setLocation(185 * i + 2, 299);
            moveButton[i].addActionListener(new MoveButtonAction(i));
            panel.add(moveButton[i]);
            //Done List 라벨
            lb2[i] = new JLabel("Done", SwingConstants.CENTER);
            lb2[i].setSize(185, 50);
            lb2[i].setLocation(185 * i, 350);
            lb2[i].setFont(italicFont2);
            panel.add(lb2[i]);
            //Done List
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
            deleteButton[i].addActionListener(new DeleteButtonAction(i));
            panel.add(deleteButton[i]);
        }
        lb[5].setForeground(Color.BLUE);
        lb[6].setForeground(Color.RED);


        panel.add(new JScrollPane(), BorderLayout.CENTER);

        return panel;
    }
    //다음주 계획
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
        
        todoListsByDay = new HashMap<>();
        createDayLists();
        
        topDayLists = new JList[14]; // 요일별 ToDo 리스트를 담을 배열 (위 리스트)
        bottomDayLists = new JList[14]; // 요일별 Done 리스트를 담을 배열 (아래 리스트)
        String[] days = {"월요일 ToDo", "화요일 ToDo", "수요일 ToDo", "목요일 ToDo", "금요일 ToDo", "토요일 ToDo", "일요일 ToDo",
        		"다음주 월요일 ToDo", "다음주 화요일 ToDo", "다음주 수요일 ToDo", "다음주 목요일 ToDo", "다음주 금요일 ToDo", "다음주 토요일 ToDo", "다음주 일요일 ToDo"};
        String[] Done_days = {"월요일 Done", "화요일 Done", "수요일 Done", "목요일 Done", "금요일 Done", "토요일 Done", "일요일 Done",
        		"다음주 월요일 Done", "다음주 화요일 Done", "다음주 수요일 Done", "다음주 목요일 Done", "다음주 금요일 Done", "다음주 토요일 Done", "다음주 일요일 Done"};
        String[] week = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        
        for(int i=7; i<14; i++) {
        	topDayLists[i] = new JList<>(todoListsByDay.get(days[i]));
            bottomDayLists[i] = new JList<>(todoListsByDay.get(Done_days[i]));
            //요일 ToDo List 라벨
            lb[i] = new JLabel(week[i-7], SwingConstants.CENTER);
            lb[i].setSize(185, 50);
            lb[i].setLocation(185 * (i-7), 0);
            lb[i].setFont(italicFont1);
            panel.add(lb[i]);
            //요일 ToDo List
            topDayLists[i].setSize(183, 220);
            topDayLists[i].setLocation(185 * (i-7) + 1, 65);
            panel.add(topDayLists[i]);
            //Done List로 옮기기 버튼
            moveButton[i] = new JButton("Clear"); // 아래로 이동하는 버튼
            moveButton[i].setSize(182, 50);
            moveButton[i].setLocation(185 * (i-7) + 2, 299);
            moveButton[i].addActionListener(new MoveButtonAction(i));
            panel.add(moveButton[i]);
            //Done List 라벨
            lb2[i] = new JLabel("Done", SwingConstants.CENTER);
            lb2[i].setSize(185, 50);
            lb2[i].setLocation(185 * (i-7), 350);
            lb2[i].setFont(italicFont2);
            panel.add(lb2[i]);
            //Done List
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
            deleteButton[i].addActionListener(new DeleteButtonAction(i));
            panel.add(deleteButton[i]);
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
    
    private void createDayLists() {
    	String[] days = {"월요일 ToDo", "화요일 ToDo", "수요일 ToDo", "목요일 ToDo", "금요일 ToDo", "토요일 ToDo", "일요일 ToDo",
        		"다음주 월요일 ToDo", "다음주 화요일 ToDo", "다음주 수요일 ToDo", "다음주 목요일 ToDo", "다음주 금요일 ToDo", "다음주 토요일 ToDo", "다음주 일요일 ToDo"};
        String[] Done_days = {"월요일 Done", "화요일 Done", "수요일 Done", "목요일 Done", "금요일 Done", "토요일 Done", "일요일 Done",
        		"다음주 월요일 Done", "다음주 화요일 Done", "다음주 수요일 Done", "다음주 목요일 Done", "다음주 금요일 Done", "다음주 토요일 Done", "다음주 일요일 Done"};
        for (String day : days) {
            DefaultListModel<String> dayList = new DefaultListModel<>();
            loadToDoList(day, dayList);
            todoListsByDay.put(day, dayList);
        }
        for (String Done_day : Done_days) {
            DefaultListModel<String> Done_dayList = new DefaultListModel<>();
            loadToDoList(Done_day, Done_dayList);
            todoListsByDay.put(Done_day, Done_dayList);
        }
    }
    // 각 요일별 ToDo 리스트 파일에서 데이터를 로드하여 리스트에 추가
    private void loadToDoList(String day, DefaultListModel<String> dayList) {
        try (Scanner scanner = new Scanner(new File(day + "_List.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                dayList.addElement(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println(day + " List 파일을 찾을 수 없습니다.");
        }
    }
    // 추가 버튼 동작을 위한 ActionListener 클래스
    private class AddButtonAction implements ActionListener {
        private int index;

        public AddButtonAction(int index) {
            this.index = index;
        }
        public void actionPerformed(ActionEvent e) {
            String todoItem = JOptionPane.showInputDialog("할 일을 입력하세요:");
            if (todoItem != null && !todoItem.trim().isEmpty()) {
                DefaultListModel<String> dayList = todoListsByDay.get(getDayName(index));
                dayList.addElement(todoItem);
                saveToDoList(getDayName(index), dayList);
            }
        }
    }
    // 삭제 버튼 동작을 위한 ActionListener 클래스
    private class DeleteButtonAction implements ActionListener {
        private int index;

        public DeleteButtonAction(int index) {
            this.index = index;
        }
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = topDayLists[index].getSelectedIndex();
            int selectedBottomIndex = bottomDayLists[index].getSelectedIndex();
            //위 리스트 항목 삭제
            if (selectedIndex != -1) {
                DefaultListModel<String> dayList = todoListsByDay.get(getDayName(index));
                dayList.remove(selectedIndex);
                saveToDoList(getDayName(index), dayList);
            }
            //아래 리스트 삭제
            if (selectedBottomIndex != -1) {
                DefaultListModel<String> Done_dayList = todoListsByDay.get(getDoneDayName(index));
                Done_dayList.remove(selectedBottomIndex); // 아래 리스트에서 선택된 항목 삭제
                saveToDoList(getDoneDayName(index), Done_dayList); // 변경된 리스트를 파일에 저장
                bottomDayLists[index].setModel(Done_dayList); // 아래 리스트 갱신
            }
        }
    }
    // 위 리스트에서 아래 리스트로 항목을 이동시키는 ActionListener 클래스
    private class MoveButtonAction implements ActionListener {
        private int index;

        public MoveButtonAction(int index) {
            this.index = index;
        }
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = topDayLists[index].getSelectedIndex();
            if (selectedIndex != -1) {
                DefaultListModel<String> topDayList = todoListsByDay.get(getDayName(index));
                DefaultListModel<String> bottomDayList = todoListsByDay.get(getDoneDayName(index));
                String selectedItem = topDayList.get(selectedIndex);
                topDayList.remove(selectedIndex); // 위 리스트에서 삭제
                bottomDayList.addElement(selectedItem); // 아래 리스트에 추가
                
                saveToDoList(getDayName(index), topDayList);
                saveToDoList(getDoneDayName(index), bottomDayList);
            }
        }
    }
    // ToDo 리스트를 파일에 저장
    private void saveToDoList(String day, DefaultListModel<String> dayList) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(day + "_List.txt"))) {
            for (int i = 0; i < dayList.size(); i++) {
                writer.println(dayList.getElementAt(i));
            }
        } catch (IOException e) {
            System.out.println(day + " List 파일에 쓰기 실패");
        }
    }
    // 인덱스를 요일명으로 변환
    private String getDayName(int index) {
    	String[] days = {"월요일 ToDo", "화요일 ToDo", "수요일 ToDo", "목요일 ToDo", "금요일 ToDo", "토요일 ToDo", "일요일 ToDo",
        		"다음주 월요일 ToDo", "다음주 화요일 ToDo", "다음주 수요일 ToDo", "다음주 목요일 ToDo", "다음주 금요일 ToDo", "다음주 토요일 ToDo", "다음주 일요일 ToDo"};
        return days[index];
    }
    private String getDoneDayName(int index) {
        String[] Done_days = {"월요일 Done", "화요일 Done", "수요일 Done", "목요일 Done", "금요일 Done", "토요일 Done", "일요일 Done",
        		"다음주 월요일 Done", "다음주 화요일 Done", "다음주 수요일 Done", "다음주 목요일 Done", "다음주 금요일 Done", "다음주 토요일 Done", "다음주 일요일 Done"};
        return Done_days[index];
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