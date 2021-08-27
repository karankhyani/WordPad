import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.print.PrinterException;
import java.io.IOException;
import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleConstants.ParagraphConstants;
import javax.swing.undo.UndoManager;



public class working{
	
	
	fileOperations fileOps=new fileOperations();
	wordpad wordpad;
	UndoManager undoManager=new UndoManager();
	public working(wordpad wp) {
		// TODO Auto-generated constructor stub
		this.wordpad=wp;
	}
	
	 UndoableEditListener undolistener=new UndoableEditListener() {
		
		@Override
		public void undoableEditHappened(UndoableEditEvent e) {
			// TODO Auto-generated method stub
			undoManager.addEdit(e.getEdit());
			
		}
	};
	
	CaretListener caretListener=new CaretListener() {
		int i=1;
		@Override
		public void caretUpdate(CaretEvent e) {
			
			
			
//			System.out.println(wordpad.textAttribute);
			MutableAttributeSet sample =(wordpad.textPane.getInputAttributes());
//			
			System.out.println(sample+" caret mutable attribute\n"+i++);
			if(!(wordpad.fontLists.getSelectedItem().equals(wordpad.textPane.getInputAttributes().getAttribute(StyleConstants.FontFamily)))) {
				wordpad.fontLists.setSelectedItem(wordpad.textPane.getInputAttributes().getAttribute(StyleConstants.FontFamily));
			}
			if((wordpad.fontSizeSpinner.getValue()!=sample.getAttribute(StyleConstants.FontSize))) {
				wordpad.fontSizeSpinner.setValue(sample.getAttribute(StyleConstants.FontSize));
			}
			if( sample.getAttribute(StyleConstants.Bold)!=null) {
				if((boolean)sample.getAttribute(StyleConstants.Bold)) {
				wordpad.bold.setBorder(BorderFactory.createLineBorder(Color.black));
				wordpad.b.setBorder(BorderFactory.createLineBorder(Color.black));
				StyleConstants.setBold(wordpad.textAttribute, true);
				}
				else 
				{
					wordpad.textAttribute.removeAttribute(StyleConstants.Bold);
					wordpad.bold.setBorder(BorderFactory.createEmptyBorder());
					wordpad.b.setBorder(BorderFactory.createEmptyBorder());
					
				}
			}
			
			else 
				{
					wordpad.textAttribute.removeAttribute(StyleConstants.Bold);
					wordpad.bold.setBorder(BorderFactory.createEmptyBorder());
					wordpad.b.setBorder(BorderFactory.createEmptyBorder());
					
				}
			
			if( sample.getAttribute(StyleConstants.Italic)!=null) {
				if((boolean)sample.getAttribute(StyleConstants.Italic)) {
				wordpad.italic.setBorder(BorderFactory.createLineBorder(Color.black));
				wordpad.i.setBorder(BorderFactory.createLineBorder(Color.black));
				StyleConstants.setItalic(wordpad.textAttribute, true);
				}
			}
			else 
				{
				wordpad.textAttribute.removeAttribute(StyleConstants.Italic);
					wordpad.italic.setBorder(BorderFactory.createEmptyBorder());
					wordpad.i.setBorder(BorderFactory.createEmptyBorder());
				}
			
			if( sample.getAttribute(StyleConstants.Underline)!=null) {
				if((boolean)sample.getAttribute(StyleConstants.Underline)) {
				wordpad.underline.setBorder(BorderFactory.createLineBorder(Color.black));
				wordpad.u.setBorder(BorderFactory.createLineBorder(Color.black));
				StyleConstants.setUnderline(wordpad.textAttribute, true);
				}
			}
			else 
				{
				wordpad.textAttribute.removeAttribute(StyleConstants.Underline);
					wordpad.underline.setBorder(BorderFactory.createEmptyBorder());
					wordpad.u.setBorder(BorderFactory.createEmptyBorder());
				}
			
			int lineNumber=0, column=0, pos=0;

			try
			{
			pos=wordpad.textPane.getCaretPosition();
			lineNumber=caretPos.getLineOfOffset(wordpad.textPane, pos);
			column=pos- caretPos.getLineStartOffset(wordpad.textPane, lineNumber);
			}catch(Exception excp){}
			if(wordpad.doc.getLength()==0) {
				lineNumber=0;column=0;
			}
			wordpad. statusLabel.setText("Line:"+lineNumber+" Column:"+column+"        ");
			//-----------------------------
			String completeString;
			int len=wordpad.textPane.getDocument().getLength();
			try {
				completeString=(wordpad.textPane.getDocument().getText(0, len));
				String array[]=completeString.split("[ \n]");
				wordpad.wc.setText("Word Count: "+array.length);
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		};
	
	
	ItemListener itemListner=new ItemListener() {
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==wordpad.fontLists) {
			//System.out.println(wordpad.fontLists.getSelectedItem());
			StyleConstants.setFontFamily(wordpad.textAttribute, wordpad.fontLists.getSelectedItem().toString());
			wordpad.textPane.setCharacterAttributes(wordpad.textAttribute, false);
			}
			else if(e.getSource()==wordpad.viewstatus) {
				System.out.println(e.getStateChange());
				if(e.getStateChange()==1) {
					wordpad.panel.setVisible(true);
					
				}
				else if(e.getStateChange()==2)
				{
					wordpad.panel.setVisible(false);
				}
			}
			
		}
	};
	
	
	ChangeListener changeListener=new ChangeListener() {
		
		@Override
		public void stateChanged(ChangeEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==wordpad.fontSizeSpinner) {
				StyleConstants.setFontSize(wordpad.textAttribute,(int)wordpad.fontSizeSpinner.getValue());
				wordpad.textPane.setCharacterAttributes(wordpad.textAttribute, true);
				System.out.println((int)wordpad.fontSizeSpinner.getValue());
			}
			
		}
	};
	
	DocumentListener documentListener=new DocumentListener() {
		
		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			
			fileOps.savedStatus=false;
		}
		
		@Override
		public void insertUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			
			fileOps.savedStatus=false;
		}
		
		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			
			fileOps.savedStatus=false;
		}
	};
	
	
	ActionListener al = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent ae) {
			// TODO Auto-generated method stub
//			if(ae.getSource()==wordpad.newBar||ae.getSource()==wordpad.newFileMenu) {
////				textPane.setText(null);
////				frame.setTitle("WordPad");
//				wordpad objnew =new wordpad();
//				objnew.createMenuBar();
//				objnew.createHorizontalToolbar();
//				objnew.createVerticalToolbar();
//				objnew.createTextPane();
//				objnew.createStatusLabel();
//				objnew.createPopup();
//				StyleConstants.setFontFamily(objnew.textAttribute, objnew.fontLists.getSelectedItem().toString());
//				objnew.textPane.setCharacterAttributes(objnew.textAttribute, true);
//				StyleConstants.setFontSize(objnew.textAttribute,(int)objnew.fontSizeSpinner.getValue());
//				objnew.textPane.setCharacterAttributes(objnew.textAttribute, true);
//				try {
//					UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
//						| UnsupportedLookAndFeelException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				objnew.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//			}
			if(ae.getSource()==wordpad.openBar|| ae.getSource()==wordpad.openFileMenu) {
				callOpen();
			}
			else if (ae.getSource()==wordpad.saveBar||ae.getSource()==wordpad.saveFileMenu) {
					callSave();
			}
			else if (ae.getSource()==wordpad.saveAsFileMenu) {
				callSaveAs();
			}
			else if (ae.getSource()==wordpad.bold||ae.getSource()==wordpad.b) {
				if(wordpad.textAttribute.containsAttribute(StyleConstants.Bold, true)) {
					wordpad.textAttribute.removeAttribute(StyleConstants.Bold);
					wordpad.bold.setBorder(null);
					wordpad.b.setBorder(null);
				}
				else {
				StyleConstants.setBold(wordpad.textAttribute, true);
				wordpad.bold.setBorder(BorderFactory.createLineBorder(Color.black));
				wordpad.b.setBorder(BorderFactory.createLineBorder(Color.black));
				}
				wordpad.textPane.setCharacterAttributes(wordpad.textAttribute, true);
				wordpad.textPane.requestFocus();
			}
			else if (ae.getSource()==wordpad.italic||ae.getSource()==wordpad.i) {
				if(wordpad.textAttribute.containsAttribute(StyleConstants.Italic, true)) {
					wordpad.textAttribute.removeAttribute(StyleConstants.Italic);
					wordpad.italic.setBorder(null);
					wordpad.i.setBorder(null);
				}
				else {
				StyleConstants.setItalic(wordpad.textAttribute, true);
				wordpad.italic.setBorder(BorderFactory.createLineBorder(Color.black));
				wordpad.i.setBorder(BorderFactory.createLineBorder(Color.black));
				}
				wordpad.textPane.setCharacterAttributes(wordpad.textAttribute, true);
				wordpad.textPane.requestFocus();
			}
			else if (ae.getSource()==wordpad.underline||ae.getSource()==wordpad.u) {
				if(wordpad.textAttribute.containsAttribute(StyleConstants.Underline, true)) {
					wordpad.textAttribute.removeAttribute(StyleConstants.Underline);
					wordpad.underline.setBorder(null);
					wordpad.u.setBorder(null);
				}
				else {
				StyleConstants.setUnderline(wordpad.textAttribute, true);
				
				wordpad.underline.setBorder(BorderFactory.createLineBorder(Color.black));
				wordpad.u.setBorder(BorderFactory.createLineBorder(Color.black));
				}
				wordpad.textPane.setCharacterAttributes(wordpad.textAttribute, true);
				wordpad.textPane.requestFocus();
			}
			else if (ae.getSource()==wordpad.alignLeft) {
				StyleConstants.setAlignment(wordpad.textAttribute, StyleConstants.ALIGN_LEFT);
				wordpad.textPane.setParagraphAttributes(wordpad.textAttribute, true);
				wordpad.textPane.requestFocus();
			}
			else if (ae.getSource()==wordpad.alignCenter) {
				StyleConstants.setAlignment(wordpad.textAttribute, StyleConstants.ALIGN_CENTER);
				wordpad.textPane.setParagraphAttributes(wordpad.textAttribute, true);
				wordpad.textPane.requestFocus();
			}
			else if (ae.getSource()==wordpad.alignRight) {
				ParagraphConstants.setAlignment(wordpad.textAttribute, ParagraphConstants.ALIGN_RIGHT);
				wordpad.textPane.setParagraphAttributes(wordpad.textAttribute, true);
				wordpad.textPane.requestFocus();
			}
			else if (ae.getSource()==wordpad.encryptBar||ae.getSource()==wordpad.encryptPop) {
				if (!(wordpad.textPane.getSelectedText()==null)) {
					callKeyScreen("encryption");
				}
				else {
					System.out.print("mistake in enc");
				}
				
			}
			else if(ae.getSource()==wordpad.decryptBar||ae.getSource()==wordpad.decryptPop) {
				if (!(wordpad.textPane.getSelectedText()==null)) {
					callKeyScreen("decryption");
				}
				else {
					System.out.print("mistake in dec");
				}
			}
			else if (ae.getSource()==wordpad.cutBar||ae.getSource()==wordpad.cutEdit||ae.getSource()==wordpad.cutPop) {
				wordpad.textPane.cut();
			}
			else if (ae.getSource()==wordpad.copyBar||ae.getSource()==wordpad.copyEdit||ae.getSource()==wordpad.copyPop) {
				wordpad.textPane.copy();
			}
			else if(ae.getSource()==wordpad.pasteBar||ae.getSource()==wordpad.pasteEdit||ae.getSource()==wordpad.pastePop) {
				wordpad.textPane.paste();
			}
			else if(ae.getSource()==wordpad.findEdit) {
				findScreen objFindScreen=new findScreen(wordpad);
				
			}
			else if(ae.getSource()==wordpad.replacePop||ae.getSource()==wordpad.repalceEdit) {
				callReplaceScreen(wordpad.textPane.getSelectedText());
			}
			else if(ae.getSource()==wordpad.selectAllEdit) {
				wordpad.textPane.selectAll();
			}
			else if(ae.getSource()==wordpad.printFileMenu) {
				try {
					wordpad.textPane.print();
				} catch (PrinterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if (ae.getSource()==wordpad.exit) {
				if(wordpad.doc.getLength()==0||fileOps.savedStatus==true) {
				System.exit(1);
				}
				else {
					callSaveScreen();
				}
			}
			else if(ae.getSource()==wordpad.newFileMenu|| ae.getSource()==wordpad.newBar) {
				if (fileOps.savedStatus==true) {
					
					wordpad.textPane.setText("");
					wordpad.frame.setTitle("Wordpad");
					wordpad.file=null;
				}
				else {
					JDialog frame;
					frame=new JDialog();
					frame.setTitle("File not saved");
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frame.setSize(400, 150);
					frame.setLocation(500, 350);
					frame.setResizable(false);
					
					Container cp=frame.getContentPane();
					cp.setBackground(Color.white);
					cp.setLayout(new GridLayout(2,1,10,10));
					JLabel label=new JLabel("The changes made to this file will be lost. Continue?");
					label.setFont(new Font( "Times New Roman",Font.PLAIN,16));
					JButton cont=new JButton("Continue");
					
					JButton cancel=new JButton("Cancel");
					cp.add(label);
					JPanel panel=new JPanel();
					panel.setBackground(Color.white);
					panel.setLayout(new FlowLayout());
					panel.add(cont);
					panel.add(cancel);
					cp.add(panel);
					frame.setVisible(true);
					cont.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							wordpad.textPane.setText("");
							wordpad.file=null;
							wordpad.frame.setTitle("Wordpad");
							frame.dispose();
						}
					});
					cancel.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							frame.dispose();
						}
					});
				}
				
			}
			else if (ae.getSource()==wordpad.undoEdit) {
				undoManager.undo();
			}
			else if (ae.getSource()==wordpad.redoEdit) {
				undoManager.redo();
			}
			else if(ae.getSource()==wordpad.viewstatus) {
				
				wordpad.panel.setVisible(false);
			}
		}//actionPerformed method
	};//ActionListener inner class
	
	
	
	MouseListener ml=new MouseListener() {
		
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.isPopupTrigger()) {
				wordpad. popup.show(e.getComponent(), e.getX(), e.getY());
			}
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	};
	
	public void callKeyScreen(String task) {
		KeyScreen keyScreen=new KeyScreen(wordpad,task);
	}
	public void callReplaceScreen(String str) {
		replaceScreen obj=new replaceScreen(wordpad, str);
	}
	public void callOpen() {
		fileOps.openFile(wordpad);
	}
	public void callSaveAs() {
		fileOps.saveAsFile(wordpad);
	}
	public void callSave() {
		fileOps.saveFile(wordpad, wordpad.file);
	}
	public void callSaveScreen() {
		saveScreen sv=new saveScreen(wordpad);
	}
	WindowListener windowListener=new WindowAdapter() {
		public void windowClosing(WindowEvent we) {
			if(wordpad.doc.getLength()==0 ||fileOps.savedStatus==true) {
				System.exit(1);
				}
				else {
					callSaveScreen();
				}
		}
	};
	

}

