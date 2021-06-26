import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
		
		@Override
		public void caretUpdate(CaretEvent e) {
			// TODO Auto-generated method stub
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
			
			
		}
	};
	
	ItemListener itemListner=new ItemListener() {
		
		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			System.out.println(wordpad.fontLists.getSelectedItem());
			StyleConstants.setFontFamily(wordpad.textAttribute, wordpad.fontLists.getSelectedItem().toString());
			wordpad.textPane.setCharacterAttributes(wordpad.textAttribute, false);
			
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
			if(ae.getSource()==wordpad.newBar||ae.getSource()==wordpad.newFileMenu) {
//				textPane.setText(null);
//				frame.setTitle("WordPad");
				wordpad objnew =new wordpad();
				objnew.createMenuBar();
				objnew.createHorizontalToolbar();
				objnew.createVerticalToolbar();
				objnew.createTextPane();
				objnew.createStatusLabel();
				objnew.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
			if(ae.getSource()==wordpad.openBar|| ae.getSource()==wordpad.openFileMenu) {
				callOpen();
				objectOut out=new objectOut();
				try {
					wordpad.encryptAttribute= out.readObjectFile();
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(wordpad.encryptAttribute);
				
			}
			else if (ae.getSource()==wordpad.saveBar||ae.getSource()==wordpad.saveFileMenu) {
				objectOut out=new objectOut();
				try {
					out.writeObjectToFile(wordpad.encryptAttribute);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					callSave();
			}
			else if (ae.getSource()==wordpad.saveAsFileMenu) {
				callSaveAs();
			}
			else if (ae.getSource()==wordpad.bold||ae.getSource()==wordpad.b) {
				if(wordpad.textAttribute.containsAttribute(StyleConstants.Bold, true)) {
					wordpad.textAttribute.removeAttribute(StyleConstants.Bold);
				}
				else {
				StyleConstants.setBold(wordpad.textAttribute, true);
				}
				wordpad.textPane.setCharacterAttributes(wordpad.textAttribute, true);
				wordpad.textPane.requestFocus();
			}
			else if (ae.getSource()==wordpad.italic||ae.getSource()==wordpad.i) {
				if(wordpad.textAttribute.containsAttribute(StyleConstants.Italic, true)) {
					wordpad.textAttribute.removeAttribute(StyleConstants.Italic);
				}
				else
				StyleConstants.setItalic(wordpad.textAttribute, true);
				wordpad.textPane.setCharacterAttributes(wordpad.textAttribute, true);
				wordpad.textPane.requestFocus();
			}
			else if (ae.getSource()==wordpad.underline||ae.getSource()==wordpad.u) {
				if(wordpad.textAttribute.containsAttribute(StyleConstants.Underline, true)) {
					wordpad.textAttribute.removeAttribute(StyleConstants.Underline);
				}
				else
				StyleConstants.setUnderline(wordpad.textAttribute, true);
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
				callKeyScreen("encryption");
			}
			else if(ae.getSource()==wordpad.decryptBar||ae.getSource()==wordpad.decryptPop) {
				callKeyScreen("decryption");
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
				if(wordpad.textPane.getText()==""||fileOps.savedStatus==true) {
				System.exit(1);
				}
				else {
					callSaveScreen();
				}
			}
			else if (ae.getSource()==wordpad.undoEdit) {
				undoManager.undo();
			}
			else if (ae.getSource()==wordpad.redoEdit) {
				undoManager.redo();
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

	
}