import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Inje_Planner extends JFrame{
	String[] option = {"메인 화면", "이번 주 계획", "다음 주 계획", "회원 정보 수정"};
	JButton [] optionButton = new JButton [option.length];
	
	public Inje_Planner() {
		setTitle("InjePlanner");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200, 700);
		setVisible(true);
		setLayout(new BorderLayout());
		
		//메인 패널
		JPanel main = new JPanel();
		
		
		
		//이번주 계획 패널
		JPanel thisWeek = new JPanel();		
				
				
		//다음주 계획 패널
		JPanel nextWeek = new JPanel();	
				
				
		//회원 정보 수정 패널
		//최+당 만드는 중
				
				
				
				
		//왼쪽 버튼 패널
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
		        
		        
		//add(main, BorderLayout.CENTER);
		add(left, BorderLayout.WEST);
	    }
			
		public static void main(String[] args) {
			SwingUtilities.invokeLater(() -> {
			    new Inje_Planner().setVisible(true);
			});
		}
}

