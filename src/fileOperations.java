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
		chooser.setFileFilter(new FileNameExtensionFilter("Only RTF files", "rtf"));
		chooser.setCurrentDirectory(new File("C:/"));
		int status=chooser.showOpenDialog(chooser);
		File file=chooser.getSelectedFile();
		if(file==null) {
			
		}
		else {
		wp.file=file;
		
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
		objectOut outObject=new objectOut(wp.file);
		try {
			wp.encryptAttribute= outObject.readObjectFile();
			//wp.textPane.setCharacterAttributes(wp.textAttribute, true);
//			System.out.println(outObject.readObjectFile()+"  read object file ");
//			System.out.println(wp.textAttribute+" file ops ->textatt");
//			System.out.println(wp.encryptAttribute+" fileops-> enc att");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		}
	}
	public void saveAsFile(wordpad wp) {
		JFileChooser chooser=new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter("Only RTF files", "rtf"));
		chooser.setCurrentDirectory(new File("C:/"));
		int status=chooser.showSaveDialog(chooser);
		File file=chooser.getSelectedFile();
		wp.file=file;
		objectOut outObject=new objectOut(wp.file);
		try {
			outObject.writeObjectToFile(wp.encryptAttribute);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			FileOutputStream out=new FileOutputStream(file);
			wp.kit.write(out, wp.doc, 0, wp.doc.getLength());
			savedStatus=true;
			wp.frame.setTitle(file.getName()+" -WordPad");
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
			objectOut outObject=new objectOut(f);
			try {
				outObject.writeObjectToFile(wp.encryptAttribute);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				FileOutputStream out=new FileOutputStream(f);
				wp.kit.write(out, wp.doc, 0, wp.doc.getLength());
				savedStatus=true;
				wp.changesSaved.setVisible(true);
//				wait(1000);
				wp.changesSaved.setVisible(false);
				
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
			}	
			
		}
		
	}
	
	public static void main(String args[]) {
	}
}

