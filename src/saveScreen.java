import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class saveScreen  {
	JFrame frame;
	JButton save,cancel,dontSaveButton;
	
	public saveScreen(wordpad wp) {
		// TODO Auto-generated constructor stub
		frame=new JFrame("File not Saved");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(400, 150);
		frame.setLocation(500, 350);
		
		Container cp=frame.getContentPane();
		cp.setLayout(new GridLayout(2,1,10,10));
		JLabel label=new JLabel("The changes made to this file will be lost. Continue?");
		label.setFont(new Font( "Times New Roman",Font.PLAIN,16));
		save=new JButton("Save");
		dontSaveButton=new JButton("Dont save");
		cancel=new JButton("Cancel");
		cp.add(label);
		JPanel panel=new JPanel();
		panel.setLayout(new FlowLayout());
		panel.add(save);
		panel.add(dontSaveButton);
		panel.add(cancel);
		cp.add(panel);
		frame.setVisible(true);
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource()==save) {
					wp.work.fileOps.saveFile(wp, wp.file);
				}
				
			}
		});
		dontSaveButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource()==dontSaveButton) {
					System.exit(1);
				}
			}
		});
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource()==cancel) {
					frame.dispose();
				}
			}
		});
	}
}


