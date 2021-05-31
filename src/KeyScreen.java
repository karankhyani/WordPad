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
		keyFrame.setSize(400, 200);
		keyFrame.setResizable(false);
		keyFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		keyFrame.setLocation(500, 300);
		Container cpContainer=keyFrame.getContentPane();
		JPanel panel=new JPanel();
		panel.setBackground(Color.white);
		panel.setLayout(new GridLayout(3,1,10,10));
		JLabel label=new JLabel("Enter the secret key for "+task);
		label.setFont(new Font("Times New Roman",Font.PLAIN,20));
		label.setBackground(Color.white);
		label.setLayout(null);
		tf=new JTextField();
		tf.setEditable(true);
		tf.setBounds(50, 100, 200, 50);
		tf.requestFocus();
		tf.setBackground(Color.white);
		JLabel loweLabel=new JLabel();
		loweLabel.setLayout(new FlowLayout());
		 submit=new JButton("Submit");
		 cancel=new JButton("Cancel");
		loweLabel.add(submit);
		loweLabel.add(cancel);
		loweLabel.setBackground(Color.white);
		panel.add(label);
		panel.add(tf);
		panel.add(loweLabel);
		panel.setVisible(true);
		label.setVisible(true);
		tf.setVisible(true);
		loweLabel.setVisible(true);
		cpContainer.add(panel);
		keyFrame.setAlwaysOnTop(true);
		keyFrame.setResizable(false);
		keyFrame.setVisible(true);
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
