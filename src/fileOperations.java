import java.io.*;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.StyledDocument;
import javax.swing.text.rtf.RTFEditorKit;

public class fileOperations {
	boolean savedStatus;
	File file;
	String fileName;
	public fileOperations() {
		// TODO Auto-generated constructor stub
		savedStatus=false;
		fileName="Untitled";
	}
	public void openFile(wordpad wp) {
		JFileChooser chooser=new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter("Only doc/docx files", "docx"));
		chooser.setCurrentDirectory(new File("C:/"));
		int status=chooser.showOpenDialog(chooser);
		File file=chooser.getSelectedFile();
		
		try {
		FileInputStream in=new FileInputStream(file);
		wp.kit.read(in, wp.doc, 0);
		wp.frame.setTitle(file.getName()+" -WordPad");
		savedStatus=true;
		in.close();
		}
		catch (Exception e) {
			System.out.println(e);
			
		}
	}
	public void saveAsFile(wordpad wp) {
		JFileChooser chooser=new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter("Only doc/docx files", "docx"));
		chooser.setCurrentDirectory(new File("C:/"));
		int status=chooser.showSaveDialog(chooser);
		File file=chooser.getSelectedFile();
		try {
			FileOutputStream out=new FileOutputStream(file);
			wp.kit.write(out, wp.doc, 0, wp.doc.getLength());
			savedStatus=true;
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}		
	}
	public void saveFile(wordpad wp, File f) {
		if(f==null) {
			saveAsFile(wp);
		}
		else {
			try {
				FileOutputStream out=new FileOutputStream(file);
				wp.kit.write(out, wp.doc, 0, wp.doc.getLength());
				savedStatus=true;
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}	
		}
		
	}
	
	public static void main(String args[]) {
	}
}

