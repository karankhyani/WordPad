import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class replaceScreen {
	JFrame frame;
	JTextField tField;
	JButton submit;
	public replaceScreen(wordpad wp, String str) {
		// TODO Auto-generated constructor stub
		frame=new JFrame("Replace with:");
		frame.setSize(400, 200);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocation(500, 350);
		Container cp=frame.getContentPane();
		
		JPanel panel=new JPanel();
		panel.setLayout(new GridLayout(3,1,0,20));
		panel.setBackground(Color.white);
		
		JLabel label=new JLabel("Replace "+str+" with:");
		label.setFont(new Font("Times New Roman",Font.PLAIN,20));
		panel.add(label);
		
		tField=new JTextField();
		panel.add(tField);
		tField.requestFocus();
		
		submit=new JButton("Submit");
		submit.setFont(new Font(submit.getFont().getFamily(),Font.PLAIN,18));
		submit.setBackground(Color.white);
		submit.setBorder(BorderFactory.createEmptyBorder());
		submit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String str=tField.getText();
				System.out.println(str);
				wp.setReplacedText(str);
				frame.dispose();
			}
		});
		panel.add(submit);
		cp.add(panel);
		frame.setVisible(true);
		frame.setResizable(false);
		
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		new replaceScreen("karan khyani");
	}

}
