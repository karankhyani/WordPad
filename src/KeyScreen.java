import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class KeyScreen {
	JButton submit,cancel;
	String key;
	JTextField tf;
	KeyScreen(wordpad wp,String task){
		JDialog keyFrame=new JDialog();
		keyFrame.setTitle(task.toUpperCase());
		keyFrame.setSize(400, 185);
		keyFrame.setResizable(false);
		keyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		keyFrame.setLocation(500, 300);
		Container cpContainer=keyFrame.getContentPane();
		cpContainer.setBackground(Color.white);
		cpContainer.setLayout(null);
		JLabel label=new JLabel("Enter the secret key for "+task);
		label.setFont(new Font("Times New Roman",Font.PLAIN,20));
		label.setBackground(Color.white);
		label.setBounds(10, 25, 350, 25);
		cpContainer.add(label);
		tf=new JTextField();
		tf.setEditable(true);
		tf.setBounds(10, 55, 350, 35);
		tf.requestFocus();
		tf.setBackground(Color.white);
		cpContainer.add(tf);
		submit=new JButton("Submit");
		submit.setBounds(90, 100, 100, 30);
		cancel=new JButton("Cancel");
		cancel.setBounds(200, 100, 100, 30);
		cpContainer.add(submit);
		cpContainer.add(cancel);
		
		
		
		keyFrame.setAlwaysOnTop(true);
		keyFrame.setResizable(false);
		keyFrame.setVisible(true);
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				keyFrame.dispose();
			}
		});
		submit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				key=tf.getText();
				if(key.equals("")) {}
				else {
				wp.setKey(key,task);
				keyFrame.dispose();
				}
			}
		});
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
