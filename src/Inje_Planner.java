import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.util.*;
import javax.swing.border.EmptyBorder;



public class Inje_Planner extends JFrame{
	
	String[] option = {"메인 화면", "이번 주 계획", "다음 주 계획", "회원 정보 수정"};
	JButton [] optionButton = new JButton [option.length];
	
	JPanel [] panel = new JPanel [4];

	Font italicFont1 = new Font("Arial", Font.PLAIN, 30);
	Font italicFont2 = new Font("Arial", Font.PLAIN, 20);
	
	String [] labels = {"ID:", "이름:", "PW:", "PW확인:", "학과:", "이메일:", "전화번호"};
	JComponent[] components = new JComponent[15];
	
	JLabel [] lb = new JLabel[14];
	JLabel [] lb2 = new JLabel[14];
	JButton [] moveButton = new JButton[14];
	JButton [] addButton = new JButton[14];
	JButton [] deleteButton = new JButton[14];
	
	Map<String, DefaultListModel<String>> todoListsByDay;
    JList<String>[] topDayLists;
    JList<String>[] bottomDayLists;
    JScrollPane[] topScrollPanes;
    JScrollPane[] bottomScrollPanes;
   
	public Inje_Planner() {
		setTitle("InjePlanner");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1395, 745);
		setVisible(true);
		setLayout(new BorderLayout());
		
		//center 패널
		JPanel center = new JPanel();
		center.setLayout(null);
		
		for(int i = 0; i < 4; i++) {
			panel[i] = new JPanel();
			panel[i].setVisible(false);
			panel[i].setLayout(null);
			panel[i].setSize(getWidth(),getHeight());
		}
		panel[0].setVisible(true);
		
		//메인 패널
		JLabel lb1 = new JLabel("Today ToDo");
		lb1.setSize(170, 20);
		lb1.setLocation(20, 20);
		lb1.setFont(italicFont2);
		panel[0].add(lb1);
		
		
		//이번주 계획 패널		
		// 각 요일별 ToDo 리스트를 담을 맵 초기화
        todoListsByDay = new HashMap<>();
        // 요일별 ToDo 리스트 생성
        createDayLists();

        topDayLists = new JList[14]; // 요일별 ToDo 리스트를 담을 배열 (위 리스트)
        bottomDayLists = new JList[14]; // 요일별 ToDo 리스트를 담을 배열 (아래 리스트)
        topScrollPanes = new JScrollPane[14]; // 스크롤 패널 배열 (위 리스트)
        bottomScrollPanes = new JScrollPane[14]; // 스크롤 패널 배열 (아래 리스트)
        String[] days = {"월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일",
        		"다음주 월요일", "다음주 화요일", "다음주 수요일", "다음주 목요일", "다음주 금요일", "다음주 토요일", "다음주 일요일"};
        String[] week = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        for(int i=0; i<7; i++) {
        	topDayLists[i] = new JList<>(todoListsByDay.get(days[i]));
            topScrollPanes[i] = new JScrollPane(topDayLists[i]);
            bottomDayLists[i] = new JList<>(new DefaultListModel<>());
            bottomScrollPanes[i] = new JScrollPane(bottomDayLists[i]);
            //요일 ToDo List 라벨
            lb[i] = new JLabel(week[i], SwingConstants.CENTER);
            lb[i].setSize(180, 50);
            lb[i].setLocation(180 * i, 0);
            lb[i].setFont(italicFont1);
            panel[1].add(lb[i]);
            //요일 ToDo List
            topScrollPanes[i].setSize(180, 250);
            topScrollPanes[i].setLocation(180 * i, 50);
            panel[1].add(topScrollPanes[i]);
            //Done List로 옮기기 버튼
            moveButton[i] = new JButton("Clear"); // 아래로 이동하는 버튼
            moveButton[i].setSize(180, 50);
            moveButton[i].setLocation(180 * i, 300);
            moveButton[i].addActionListener(new MoveButtonAction(i));
            panel[1].add(moveButton[i]);
            //Done List 라벨
            lb2[i] = new JLabel("Done", SwingConstants.CENTER);
            lb2[i].setSize(180, 50);
            lb2[i].setLocation(180 * i, 350);
            lb2[i].setFont(italicFont2);
            panel[1].add(lb2[i]);
            //Done List
            bottomScrollPanes[i].setSize(180, 250);
            bottomScrollPanes[i].setLocation(180 * i, 400);
            panel[1].add(bottomScrollPanes[i]);
            //추가 삭제 버튼
            addButton[i] = new JButton("추가"); // 아래로 이동하는 버튼
            addButton[i].setSize(90, 50);
            addButton[i].setLocation(180 * i, 650);
            addButton[i].addActionListener(new AddButtonAction(i));
            panel[1].add(addButton[i]);
            deleteButton[i] = new JButton("삭제"); // 아래로 이동하는 버튼
            deleteButton[i].setSize(90, 50);
            deleteButton[i].setLocation(180 * i + 90, 650);
            deleteButton[i].addActionListener(new DeleteButtonAction(i));
            panel[1].add(deleteButton[i]);
        }
        lb[5].setForeground(Color.BLUE);
        lb[6].setForeground(Color.RED);
        
        
		//다음주 계획 패널	
        for(int i=7; i<14; i++) {
        	topDayLists[i] = new JList<>(todoListsByDay.get(days[i]));
            topScrollPanes[i] = new JScrollPane(topDayLists[i]);
            bottomDayLists[i] = new JList<>(new DefaultListModel<>());
            bottomScrollPanes[i] = new JScrollPane(bottomDayLists[i]);
            //요일 ToDo List 라벨
            lb[i] = new JLabel(week[i-7], SwingConstants.CENTER);
            lb[i].setSize(180, 50);
            lb[i].setLocation(180 * (i-7), 0);
            lb[i].setFont(italicFont1);
            panel[2].add(lb[i]);
            //요일 ToDo List
            topScrollPanes[i].setSize(180, 250);
            topScrollPanes[i].setLocation(180 * (i-7), 50);
            panel[2].add(topScrollPanes[i]);
            //Done List로 옮기기 버튼
            moveButton[i] = new JButton("Clear"); // 아래로 이동하는 버튼
            moveButton[i].setSize(180, 50);
            moveButton[i].setLocation(180 * (i-7), 300);
            moveButton[i].addActionListener(new MoveButtonAction(i));
            panel[2].add(moveButton[i]);
            //Done List 라벨
            lb2[i] = new JLabel("Done", SwingConstants.CENTER);
            lb2[i].setSize(180, 50);
            lb2[i].setLocation(180 * (i-7), 350);
            lb2[i].setFont(italicFont2);
            panel[2].add(lb2[i]);
            //Done List
            bottomScrollPanes[i].setSize(180, 250);
            bottomScrollPanes[i].setLocation(180 * (i-7), 400);
            panel[2].add(bottomScrollPanes[i]);
            //추가 삭제 버튼
            addButton[i] = new JButton("추가"); // 아래로 이동하는 버튼
            addButton[i].setSize(90, 50);
            addButton[i].setLocation(180 * (i-7), 650);
            addButton[i].addActionListener(new AddButtonAction(i));
            panel[2].add(addButton[i]);
            deleteButton[i] = new JButton("삭제"); // 아래로 이동하는 버튼
            deleteButton[i].setSize(90, 50);
            deleteButton[i].setLocation(180 * (i-7) + 90, 650);
            deleteButton[i].addActionListener(new DeleteButtonAction(i));
            panel[2].add(deleteButton[i]);
        }
        lb[12].setForeground(Color.BLUE);
        lb[13].setForeground(Color.RED);
     
		
		//회원 정보 수정 패널
		JPanel edit = new JPanel(new GridLayout(8, 2));
        edit.setBorder(new EmptyBorder(20, 20, 20, 20));

        for (int i = 0; i < 7; i++) {
            JLabel label = new JLabel(labels[i]);
            JTextField textField = new JTextField(20);
            components[i * 2] = label;
            components[i * 2 + 1] = textField;
        }

        for (int i = 0; i < 14; i++) {
            if (components[i] != null) {
                edit.add(components[i]);
            }
        }
        
        JButton editButton = new JButton("수정");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(editButton);
        edit.add(new JLabel());
        edit.add(buttonPanel);
        edit.setSize(1280, 800);
		edit.setLocation(0, 0);
        panel[3].add(edit);
        
		
        //center 패널에 각각의 기능을 구현한 패널들을 삽입(패널 전환을 위해 패널안에 패널을 넣은 구조)
		for(int i = 0; i < 4; i++) {
			center.add(panel[i]);
		}
		
		//left 패널
		JPanel left = new JPanel();
		left.setLayout(new GridLayout(5, 1));
		ImageIcon Diary = new ImageIcon("images/Diary.jpg");
		int width = Diary.getIconWidth();
		int height = Diary.getIconHeight();
		int newWidth = 80;
		int newHeight = 70;
		ImageIcon resizedIcon = new ImageIcon(Diary.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
		left.add(new JLabel(resizedIcon));
		for (int i = 0; i < 4; i++) {
			optionButton[i] = new JButton(option[i]);
			left.add(optionButton[i]);
		}
		
		//left 패널의 버튼 누를때 마다 해당 패널이 나오고 기존 패널은 보이지 않게 설정
		optionButton[0].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton btn0 = (JButton)e.getSource();
				panel[0].setVisible(true);
				panel[1].setVisible(false);
				panel[2].setVisible(false);
				panel[3].setVisible(false);
			}
		});
		optionButton[1].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton btn1 = (JButton)e.getSource();
				panel[0].setVisible(false);
				panel[1].setVisible(true);
				panel[2].setVisible(false);
				panel[3].setVisible(false);
			}
		});
		optionButton[2].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton btn2 = (JButton)e.getSource();
				panel[0].setVisible(false);
				panel[1].setVisible(false);
				panel[2].setVisible(true);
				panel[3].setVisible(false);
			}
		});
		optionButton[3].addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton btn3 = (JButton)e.getSource();
				panel[0].setVisible(false);
				panel[1].setVisible(false);
				panel[2].setVisible(false);
				panel[3].setVisible(true);
			}
		});
		
		
		//전체 프레임에 가장 큰 패널 두개를 배치
		add(center, BorderLayout.CENTER);
		add(left, BorderLayout.WEST);
	    }
		
	// 각 요일별 ToDo 리스트 생성
    private void createDayLists() {
        String[] days = {"월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일",
        		"다음주 월요일", "다음주 화요일", "다음주 수요일", "다음주 목요일", "다음주 금요일", "다음주 토요일", "다음주 일요일"};
        for (String day : days) {
            DefaultListModel<String> dayList = new DefaultListModel<>();
            loadToDoList(day, dayList);
            todoListsByDay.put(day, dayList);
        }
    }
    // 각 요일별 ToDo 리스트 파일에서 데이터를 로드하여 리스트에 추가
    private void loadToDoList(String day, DefaultListModel<String> dayList) {
        try (Scanner scanner = new Scanner(new File("todolist_" + day + ".txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                dayList.addElement(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println(day + "의 ToDo 리스트 파일을 찾을 수 없습니다.");
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
                DefaultListModel<String> dayList = (DefaultListModel<String>) bottomDayLists[index].getModel();
                dayList.remove(selectedBottomIndex); // 아래 리스트에서 선택된 항목 삭제
                saveToDoList(getDayName(index), dayList); // 변경된 리스트를 파일에 저장
                bottomDayLists[index].setModel(dayList); // 아래 리스트 갱신
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
                DefaultListModel<String> bottomDayList = (DefaultListModel<String>) bottomDayLists[index].getModel();
                String selectedItem = topDayList.get(selectedIndex);
                
                topDayList.remove(selectedIndex); // 위 리스트에서 삭제
                bottomDayList.addElement(selectedItem); // 아래 리스트에 추가
                
                saveToDoList(getDayName(index), topDayList);
                saveToDoList(getDayName(index), bottomDayList);
            }
        }
    }
    // ToDo 리스트를 파일에 저장
    private void saveToDoList(String day, DefaultListModel<String> dayList) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("todolist_" + day + ".txt"))) {
            for (int i = 0; i < dayList.size(); i++) {
                writer.println(dayList.getElementAt(i));
            }
        } catch (IOException e) {
            System.out.println(day + "의 ToDo 리스트 파일에 쓰기 실패");
        }
    }
    // 인덱스를 요일명으로 변환
    private String getDayName(int index) {
        String[] days = {"월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일",
        		"다음주 월요일", "다음주 화요일", "다음주 수요일", "다음주 목요일", "다음주 금요일", "다음주 토요일", "다음주 일요일"};
        return days[index];
    }
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Inje_Planner().setVisible(true);
		});
	}
}