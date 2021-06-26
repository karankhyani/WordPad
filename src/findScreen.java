import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.*;
import javax.swing.text.BadLocationException;

public class findScreen {
	wordpad wp;
	String allText;
	JDialog jd;
	JLabel errorLabel;
	public findScreen(wordpad wordpad) {
		this.wp=wordpad;
		jd=new JDialog();
		jd.setTitle("Find");
		jd.setBackground(Color.white);
		jd.getContentPane().setBackground(Color.white);
		jd.setIconImage(null);
		jd.setLayout(null);
		jd.setSize(350, 160);
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
		find.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String fText=tf.getText();
				test(fText);
				
			}
		});
		errorLabel =new JLabel("No such word found! Please try again");
		errorLabel.setForeground(Color.red);
		errorLabel.setBounds(55, 100, 220, 15);
		errorLabel.setVisible(false);
		
		
		jd.add(findLabel);
		jd.add(tf);
		jd.add(find);
		jd.add(errorLabel);
		jd.setVisible(true);
		
	}
	
	public void test(String findText) {
		boolean flag=false;
		int len=wp.textPane.getDocument().getLength();
		int line=0,col=0,pos=0;
		try {
			allText=(wp.textPane.getDocument().getText(0, len));
			allText=allText+" ";
			String w="";
			for(int i=0;i<allText.length();i++) {
				pos++;
				char ch=allText.charAt(i);
				if(Character.isLetterOrDigit(ch)) {
					w=w+ch;
				}
				else {
					if(w.equalsIgnoreCase(findText)) {
						flag=true;
						errorLabel.setVisible(false);
						wp.textPane.setSelectionColor(Color.yellow);
						wp.textPane.setSelectionStart(pos-findText.length()-1);
						wp.textPane.setSelectionEnd(pos-1);
						break;
					}
					else {
						w="";
					}
				}
			}
			if(flag==false) {
				System.out.println("could not find");
				errorLabel.setVisible(true);
				wp.textPane.setSelectionStart(pos);
				wp.textPane.setSelectionEnd(pos);
			}
			
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
