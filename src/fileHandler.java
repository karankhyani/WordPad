import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
public class fileHandler {
	JFileChooser chooser;
	
		fileHandler(String str,wordpad wp){
			int i;
			chooser=new JFileChooser(str);
			chooser.setFileFilter(new FileNameExtensionFilter("Only doc/docx files", "docx"));
			chooser.setCurrentDirectory(new File("C:/"));
			if(str.equalsIgnoreCase("open"))
			 i=chooser.showOpenDialog(wp.frame);
			 else {
				i=chooser.showSaveDialog(wp.frame);
			}
			File file=chooser.getSelectedFile();
			if(i==JFileChooser.APPROVE_OPTION) {
				wp.setFile(file);
			}
			else {
				System.out.println("problem");
			}
		}
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		new fileHandler("Open File");
	}

}
