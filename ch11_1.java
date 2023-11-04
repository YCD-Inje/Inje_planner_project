import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ch11_1 extends JFrame{
	private JButton button = new JButton("test button");
	private JCheckBox[] checkBox = new JCheckBox[2];

	public ch11_1()
	
	{
		setTitle("checkbox practice");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container c = getContentPane();
		c.setLayout(new FlowLayout());
		
		checkBox[1] = new JCheckBox("버튼 비활성화");
		checkBox[2] = new JCheckBox("버튼 감추기");
		
		c.add(button);
		
		setSize(300,200);
		setVisible(true);
		
		checkBox[0].addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent e)
					{
						if(e.getStateChange() == ItemEvent.SELECTED)
							button.setEnabled(false);
						else
							button.setEnabled(true);
					}
					
				});
		checkBox[1].addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED)
					button.setVisible(false);
				else
					button.setVisible(true);
			}
		});
	}
	public static void main(String[] args) {
		
		new ch11_1();
		
	}

}
