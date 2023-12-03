import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Inje_Planner extends JFrame{
	
	String[] option = {"메인 화면", "이번 주 계획", "다음 주 계획", "회원 정보 수정"};
	JButton [] optionButton = new JButton [option.length];
	
	JPanel [] panel = new JPanel [4];
	
	public Inje_Planner() {
		setTitle("InjePlanner");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200, 700);
		setVisible(true);
		setLayout(new BorderLayout());
		
		for(int i = 0; i < 4; i++) {
			panel[i] = new JPanel();
			panel[i].add(new JLabel(Integer.toString(i)));
			panel[i].setVisible(false);
		}
		panel[0].setVisible(true);
		
		
		//메인 패널
		
		//이번주 계획 패널		
				
		//다음주 계획 패널	
				
		//회원 정보 수정 패널
		//최+당 만드는 중	
				
				
			
		//center 패널
		JPanel center = new JPanel();
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
		//왼쪽 버튼 누를때 마다 해당 패널이 나오고 기존 패널은 보이지 않게 설정
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
		
		        
		        
		add(center, BorderLayout.CENTER);
		add(left, BorderLayout.WEST);
	    }
			
		public static void main(String[] args) {
			SwingUtilities.invokeLater(() -> {
			    new Inje_Planner().setVisible(true);
			});
		}
}

