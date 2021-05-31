import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.*;

public class findScreen {
	
	public findScreen() {
		JDialog jd=new JDialog();
		jd.setTitle("Find");
		jd.setBackground(Color.white);
		jd.getContentPane().setBackground(Color.white);
		jd.setIconImage(null);
		jd.setLayout(null);
		jd.setSize(350, 150);
		jd.setLocation(500, 350);
		jd.setResizable(false);
		jd.setFocusable(true);
		jd.setAlwaysOnTop(true);
		JLabel findLabel=new JLabel("Find:");
		findLabel.setBounds(20, 5, 100, 75);
		findLabel.setBackground(Color.blue);
		JTextField tf=new JTextField();
		
		tf.setBounds(75, 23, 220, 30);
		
		
		JButton find=new JButton("Find");
		find.setBounds(125, 75, 100, 25);
		
		jd.add(findLabel);
		jd.add(tf);
		jd.add(find);
		jd.setVisible(true);
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new findScreen();
	}

}
