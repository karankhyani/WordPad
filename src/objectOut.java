import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.text.SimpleAttributeSet;

public class objectOut {
	File file=new File("./kk");
	
	public objectOut() {
		// TODO Auto-generated constructor stub
	}
	public void writeObjectToFile(SimpleAttributeSet obj) throws IOException {
		
		FileOutputStream fileOutputStream=new FileOutputStream(file);
		ObjectOutputStream objectOutputStream=new ObjectOutputStream(fileOutputStream);
		objectOutputStream.writeObject(obj);
	}
	
	public SimpleAttributeSet readObjectFile() throws IOException, ClassNotFoundException {
		
		FileInputStream fileInputStream=new FileInputStream(file);
		ObjectInputStream objectInputStream=new ObjectInputStream(fileInputStream);
		SimpleAttributeSet attributeSet= (SimpleAttributeSet) objectInputStream.readObject();
		return attributeSet;
	}

	public static void main(String[] args) throws ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
//		objectOut objectOut=new objectOut();
//		objectOut.readObjectFile();
	}

}
