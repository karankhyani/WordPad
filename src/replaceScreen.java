import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class replaceScreen {
	JDialog frame;
	JTextField tField;
	JButton submit;
	public replaceScreen(wordpad wp, String str) {
		// TODO Auto-generated constructor stub
		frame=new JDialog();
		frame.setTitle("Replace");
		frame.setSize(385, 185);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocation(500, 300);
		Container cp=frame.getContentPane();
		cp.setLayout(null);
		cp.setBackground(Color.white);
		
		
		JLabel label=new JLabel("Replace "+str+" with:");
		label.setFont(new Font("Times New Roman",Font.PLAIN,20));
		label.setBounds(10, 25, 350, 25);
		cp.add(label);
		
		tField=new JTextField();
		tField.requestFocus();
		tField.setBounds(10, 55, 350, 35);
		cp.add(tField);
		submit=new JButton("Submit");
		submit.setBackground(Color.white);
		submit.setBorder(BorderFactory.createEmptyBorder());
		submit.setBounds(140, 100, 100, 30);
		cp.add(submit);
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
		
		frame.setVisible(true);
		frame.setResizable(false);
		
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		new replaceScreen("karan khyani");
	}

}
