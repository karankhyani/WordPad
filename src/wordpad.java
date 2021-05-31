import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;

import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.nio.CharBuffer;
import java.nio.file.Files;
import java.util.Stack;
import java.io.*;
import javax.swing.text.*;
import javax.swing.text.StyleConstants.FontConstants;
import javax.swing.text.StyleConstants.ParagraphConstants;
import javax.swing.text.rtf.RTFEditorKit;
public class wordpad   {
	JFrame frame;
	Container contentPane;
	JButton bold,italic,underline,alignLeft,alignRight,alignCenter,newBar,openBar,saveBar,clearBr,cutBar,copyBar,pasteBar,encryptBar,decryptBar;JButton b,i,u;
	JMenuBar menuBar;
	JMenu fileMenu,editMenu,search,help;
	JMenuItem newFileMenu,openFileMenu,saveFileMenu,saveAsFileMenu,printFileMenu,exit,cutEdit,copyEdit,pasteEdit,undoEdit,findEdit,repalceEdit,selectAllEdit,cutPop,copyPop,pastePop,replacePop,encryptPop,decryptPop;
	JTextPane textPane;
	JLabel statusLabel;JLabel changesSaved;
	JToolBar verticalToolBar,horizontalToolBar;
	JComboBox<String> fontLists,fontSizes;
	JSpinner fontSizeSpinner;
	JToggleButton theme;
	JPopupMenu popup;
	JFileChooser fileChooser;
	RTFEditorKit kit;
	StyledDocument doc;
	SimpleAttributeSet textAttribute,encryptAttribute;
	File file;
	String fontNames[]=GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
	int fontSize[]=new int[30];
	String key=null,replacedText=null;
	int height,width,lineNumber=0,colNum=0;
	fileOperations fileOps=new fileOperations();
	CharBuffer letters=CharBuffer.allocate(1000);
	
	//-------------------------
		wordpad() {
			Toolkit kit;
			kit=Toolkit.getDefaultToolkit();
			Dimension screenSize=kit.getScreenSize();
			height=screenSize.height;
			width=screenSize.width;
			frame=new JFrame("WordPad");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(700, 700);
			frame.setMinimumSize(new Dimension(200,200));
//			frame.setVisible(true);
			contentPane=frame.getContentPane();
			contentPane.setBackground(new Color(133, 193, 233));
			ImageIcon mainIcon=new ImageIcon("./icons/notepad.png");
			frame.setIconImage(mainIcon.getImage());
			frame.setBackground(new Color(214, 234, 248));
			
			}
		public void createMenuBar() {
			menuBar=new JMenuBar();
			menuBar.setBackground(new Color(190, 221, 244));
			fileMenu=new JMenu("File");
			fileMenu.setVisible(true);
			newFileMenu= createMenuItem(fileMenu, "New");
			openFileMenu= createMenuItem(fileMenu, "Open");
			saveFileMenu= createMenuItem(fileMenu, "Save");
			saveAsFileMenu= createMenuItem(fileMenu, "SaveAs");
			printFileMenu= createMenuItem(fileMenu, "Print");
			exit= createMenuItem(fileMenu, "Close");
			editMenu= new JMenu("Edit");
			cutEdit= createMenuItem(editMenu, "Cut");
			copyEdit= createMenuItem(editMenu, "Copy");
			pasteEdit= createMenuItem(editMenu, "Paste");
			undoEdit= createMenuItem(editMenu, "Undo");
			findEdit= createMenuItem(editMenu, "Find");
			repalceEdit= createMenuItem(editMenu, "Replace");
			selectAllEdit= createMenuItem(editMenu, "SelectAll");
			search=new JMenu("Search");
			help=new JMenu("Help");
			menuBar.add(fileMenu);
			menuBar.add(editMenu);
			menuBar.add(search);
			menuBar.add(help);
			menuBar.setVisible(true);
			menuBar.setSize(width, 20);
			frame.setJMenuBar(menuBar);
		}
		public JMenuItem createMenuItem(JMenu m,String name) {
			JMenuItem tempItem=new JMenuItem(name);
			tempItem.setBackground(new Color(190, 221, 244));
			tempItem.addActionListener(al);
			tempItem.setVisible(true);
			m.add(tempItem);
			return tempItem;
		}
		public void createHorizontalToolbar() {
			horizontalToolBar=new JToolBar(0);
			horizontalToolBar.setBackground(new Color(190, 221, 244));
			horizontalToolBar.setLayout(new FlowLayout(1,8,1));
			horizontalToolBar.setFloatable(false);
			horizontalToolBar.setLocation(66,5);
			contentPane.add(horizontalToolBar,BorderLayout.NORTH);
			bold=createButton(new ImageIcon("./icons/icons8-bold-52.png"), horizontalToolBar,20,"Bold");
			italic=createButton(new ImageIcon("./icons/icons8-italic-52.png"), horizontalToolBar,20,"Italic");
			underline=createButton(new ImageIcon("./icons/icons8-underline-52.png"), horizontalToolBar,20,"UnderLine");
			fontLists=new JComboBox<String>(fontNames);
			fontLists.setSelectedItem("Dialog");
			fontLists.addItemListener(itemListner);
			horizontalToolBar.add(fontLists);
			fontSizeSpinner=new JSpinner();
			fontSizeSpinner.setValue(20);
			fontSizeSpinner.addChangeListener(changeListener);
			horizontalToolBar.add(fontSizeSpinner);
			alignLeft=createButton(new ImageIcon("./icons/icons8-align-left-96 (1).png"), horizontalToolBar,20,"Align Left");
			alignCenter=createButton(new ImageIcon("./icons/icons8-align-center-96.png"), horizontalToolBar,20,"Align Center");
			alignRight=createButton(new ImageIcon("./icons/icons8-align-right-96.png"), horizontalToolBar,20,"Align Right");			
		}
		
		public JButton createButton(ImageIcon img,JToolBar tb,int size,String tip) {
			Image image=img.getImage().getScaledInstance(size, size,Image.SCALE_SMOOTH);
			ImageIcon newImageIcon=new ImageIcon(image);
			JButton temp =new JButton(newImageIcon);
			temp.setToolTipText(tip);
			temp.addActionListener(al);
			temp.setBorder(BorderFactory.createEmptyBorder());
			temp.setBackground(new Color(190, 221, 244));
			temp.setEnabled(true);
			tb.add(temp);
			temp.setVisible(true);
			tb.addSeparator();
			return temp;
		}
		public JButton createButton(String name,int val,JComponent jc) {
			JButton temp=new JButton(name);
			temp.setFont(new Font("Times New Roman", val, 17));
			temp.setSize(50, 50);
			temp.setBackground(new Color(190, 221, 244));
			temp.setBorder(BorderFactory.createEmptyBorder(7, 7, 7, 7));
			temp.addActionListener(al);
			jc.add(temp);
			
			return temp;
		}
		public void createVerticalToolbar() {
			verticalToolBar=new JToolBar(1);
			verticalToolBar.setBackground(new Color(190, 221, 244));
			verticalToolBar.setFloatable(false);
//			verticalToolBar.setLayout(new GridLayout(20,1,1,1));
//			verticalToolBar.setBackground(Color.LIGHT_GRAY);
			contentPane.add(verticalToolBar,BorderLayout.WEST);
			newBar=createButton(new ImageIcon("./icons/icons8-create-100 (1).png"),verticalToolBar,30,"New");
			openBar=createButton(new ImageIcon("./icons/icons8-open-document-100.png"),verticalToolBar,30,"Open");
			saveBar=createButton(new ImageIcon("./icons/icons8-save-100.png"),verticalToolBar,30,"Save");
			cutBar=createButton(new ImageIcon("./icons/cutting.png"),verticalToolBar,30,"Cut");
			copyBar=createButton(new ImageIcon("./icons/icons8-copy-100.png"),verticalToolBar,30,"Copy");
			pasteBar=createButton(new ImageIcon("./icons/icons8-paste-100 (2).png"),verticalToolBar,30,"Paste");
			encryptBar=createButton(new ImageIcon("./icons/encryption.png"),verticalToolBar,30,"Encrypt");
			decryptBar=createButton(new ImageIcon("./icons/unlocked.png"),verticalToolBar,30,"Decrypt");
		}
		public void createTextPane() {
			textPane=new JTextPane();
			textPane.setBackground(new Color(214, 234, 248));
			textPane.setEditable(true);
			textPane.setContentType("txt/docx");
			textPane.setFont(new Font(textPane.getFont().getFontName(),Font.PLAIN,20));
			JScrollPane sPane=new JScrollPane(textPane);
			contentPane.add(sPane,BorderLayout.CENTER);
			kit=new RTFEditorKit();
			textPane.setEditorKit(kit);	
			doc=textPane.getStyledDocument();
			doc.addDocumentListener(documentListener);
			textAttribute=new SimpleAttributeSet();
			encryptAttribute=new SimpleAttributeSet();
			
			FontConstants.setFontFamily(textAttribute, textPane.getFont().getFamily());
			FontConstants.setFontSize(textAttribute, textPane.getFont().getSize());
			textPane.setParagraphAttributes(textAttribute, true);
			textPane.addMouseListener(ml);
			textPane.addCaretListener(caretListener);
			
			textPane.addKeyListener(new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent e) {
					// TODO Auto-generated method stub
					if(e.getKeyChar()==(char)8) {
						System.out.println(letters.get());
					}
					else {
						letters.append(e.getKeyChar());
						System.out.println(letters.length());
						
					}
				}
				
				@Override
				public void keyReleased(KeyEvent e) {
					// TODO Auto-generated method stub	
				}
				
				@Override
				public void keyPressed(KeyEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
		}
		
		UndoableEditListener undolistener=new UndoableEditListener() {
			
			@Override
			public void undoableEditHappened(UndoableEditEvent e) {
				// TODO Auto-generated method stub
				
			}
		};
		
		
		CaretListener caretListener=new CaretListener() {
			
			@Override
			public void caretUpdate(CaretEvent e) {
				// TODO Auto-generated method stub
				int lineNumber=0, column=0, pos=0;

				try
				{
				pos=textPane.getCaretPosition();
				lineNumber=caretPos.getLineOfOffset(textPane, pos);
				column=pos- caretPos.getLineStartOffset(textPane, lineNumber);
				}catch(Exception excp){}
				if(doc.getLength()==0) {
					lineNumber=0;column=0;
				}
				statusLabel.setText("Line:"+lineNumber+" Column:"+column+"        ");
				
				
			}
		};
		
		ItemListener itemListner=new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				System.out.println(fontLists.getSelectedItem());
				StyleConstants.setFontFamily(textAttribute, fontLists.getSelectedItem().toString());
				textPane.setCharacterAttributes(textAttribute, false);
				
			}
		};
		
		ChangeListener changeListener=new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource()==fontSizeSpinner) {
					StyleConstants.setFontSize(textAttribute,(int)fontSizeSpinner.getValue());
					textPane.setCharacterAttributes(textAttribute, true);
					System.out.println((int)fontSizeSpinner.getValue());
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
		public void createStatusLabel() {
			JPanel panel=new JPanel();
			panel.setBackground(new Color(190, 221, 244));
			panel.setLayout(new FlowLayout());
			statusLabel=new JLabel("Line:"+lineNumber+" Column:"+colNum+"        ",JLabel.RIGHT);
			statusLabel.setBackground(new Color(133, 193, 233));
//			contentPane.add(statusLabel,BorderLayout.PAGE_END);
			panel.add(statusLabel,BorderLayout.PAGE_START);
			
			JLabel wc=new JLabel("Word Count:");
			wc.setBackground(new Color(133, 193, 233));
			panel.add(wc,BorderLayout.PAGE_END);
			 changesSaved=new JLabel("     Changes Saved");
			 changesSaved.setBackground(new Color(133, 193, 233));
			 changesSaved.setEnabled(true);
			changesSaved.setVisible(false);
			panel.add(changesSaved);
			contentPane.add(panel,BorderLayout.PAGE_END);
			frame.setVisible(true);
		}
		
		public void createThemeButton() {
			
		}
		
		public void setFile(File f) {
			file=f;
		}
		public void setKey(String str,String task) {
			key=str;
			if(task.equals("encryption")) {
				int start,end;
				start=textPane.getSelectionStart();
				end=textPane.getSelectionEnd();
				String encryptedText=AES.encrypt(textPane.getSelectedText(), key);
				encryptAttribute.addAttribute(encryptedText, key);
				StyleConstants.setForeground(encryptAttribute, Color.red);
				textPane.setCharacterAttributes(encryptAttribute, true);
				System.out.println(encryptAttribute.getAttribute(encryptedText));
				try {
					doc.insertString(start, encryptedText, encryptAttribute);
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(e);
				}
				textPane.replaceSelection("");
				textPane.setCharacterAttributes(textAttribute, true);
				
			}
			else {
				int start=textPane.getSelectionStart();
				if(encryptAttribute.containsAttribute(textPane.getSelectedText(), key)) {
				String decryptedText= AES.decrypt(textPane.getSelectedText(), key);
				textPane.setCharacterAttributes(textAttribute, true);
				try {
					doc.insertString(start, decryptedText, textAttribute);
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println(e);
				}
				textPane.replaceSelection("");
				textPane.setCharacterAttributes(textAttribute, true);
			}	
			}	
		}
		public void setReplacedText(String str) {
			replacedText=str;
			textPane.replaceSelection(replacedText);
		}
//		public void callFileHandler(String str) {
//			fileHandler fHandler=new fileHandler(str, this);
//		}
		public void callKeyScreen(String task) {
			KeyScreen keyScreen=new KeyScreen(this,task);
		}
		public void callReplaceScreen(String str) {
			replaceScreen obj=new replaceScreen(this, str);
		}
		public void callOpen() {
			fileOps.openFile(this);
		}
		public void callSaveAs() {
			fileOps.saveAsFile(this);
		}
		public void callSave() {
			fileOps.saveFile(this, file);
		}
		public void callSaveScreen() {
			saveScreen sv=new saveScreen(this);
		}
		public void createPopup() {
			
			JPanel panel=new JPanel();
			panel.setBackground(Color.WHITE);
			panel.setBackground(new Color(190, 221, 244));
			panel.setLayout(new FlowLayout(1));
			b=createButton("B", Font.BOLD, panel);
			i=createButton("I", Font.ITALIC, panel);
			u=createButton("U", Font.PLAIN, panel);
			popup=new JPopupMenu();
			popup.setBackground(Color.white);
			popup.setPopupSize(120, 160);
			popup.add(panel);
			popup.setBorder(BorderFactory.createLineBorder(Color.gray, 1, true));
			cutPop=createMenuItem(popup, "Cut");
			copyPop=createMenuItem(popup, "Copy");
			pastePop=createMenuItem(popup, "Paste");
			replacePop=createMenuItem(popup, "Replace");
			encryptPop=createMenuItem(popup, "Encrypt");
			decryptPop=createMenuItem(popup, "Decrypt");
			
		}
		
		public JMenuItem createMenuItem(JPopupMenu mb,String str) {
			JMenuItem temp=new JMenuItem(str);
			temp.setBackground(new Color(190, 221, 244));
			mb.add(temp);
			temp.addActionListener(al);
			temp.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.lightGray));
			return temp;
		}
		MouseListener ml=new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				if(e.isPopupTrigger()) {
					popup.show(e.getComponent(), e.getX(), e.getY());
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
			ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				// TODO Auto-generated method stub
				if(ae.getSource()==newBar||ae.getSource()==newFileMenu) {
//					textPane.setText(null);
//					frame.setTitle("WordPad");
					wordpad objnew =new wordpad();
					objnew.createMenuBar();
					objnew.createHorizontalToolbar();
					objnew.createVerticalToolbar();
					objnew.createTextPane();
					objnew.createStatusLabel();
					objnew.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				}
				if(ae.getSource()==openBar|| ae.getSource()==openFileMenu) {
					callOpen();
					System.out.println(encryptAttribute);
					System.out.println(textAttribute);
					System.out.println("open");
					
				}
				else if (ae.getSource()==saveBar||ae.getSource()==saveFileMenu) {
						callSave();
				}
				else if (ae.getSource()==saveAsFileMenu) {
					callSaveAs();
				}
				else if (ae.getSource()==bold||ae.getSource()==b) {
					if(textAttribute.containsAttribute(StyleConstants.Bold, true)) {
						textAttribute.removeAttribute(StyleConstants.Bold);
					}
					else {
					StyleConstants.setBold(textAttribute, true);
					}
					textPane.setCharacterAttributes(textAttribute, true);
					textPane.requestFocus();
				}
				else if (ae.getSource()==italic||ae.getSource()==i) {
					if(textAttribute.containsAttribute(StyleConstants.Italic, true)) {
						textAttribute.removeAttribute(StyleConstants.Italic);
					}
					else
					StyleConstants.setItalic(textAttribute, true);
					textPane.setCharacterAttributes(textAttribute, true);
					textPane.requestFocus();
				}
				else if (ae.getSource()==underline||ae.getSource()==u) {
					if(textAttribute.containsAttribute(StyleConstants.Underline, true)) {
						textAttribute.removeAttribute(StyleConstants.Underline);
					}
					else
					StyleConstants.setUnderline(textAttribute, true);
					textPane.setCharacterAttributes(textAttribute, true);
					textPane.requestFocus();
				}
				else if (ae.getSource()==alignLeft) {
					StyleConstants.setAlignment(textAttribute, StyleConstants.ALIGN_LEFT);
					textPane.setParagraphAttributes(textAttribute, true);
					textPane.requestFocus();
				}
				else if (ae.getSource()==alignCenter) {
					StyleConstants.setAlignment(textAttribute, StyleConstants.ALIGN_CENTER);
					textPane.setParagraphAttributes(textAttribute, true);
					textPane.requestFocus();
				}
				else if (ae.getSource()==alignRight) {
					ParagraphConstants.setAlignment(textAttribute, ParagraphConstants.ALIGN_RIGHT);
					textPane.setParagraphAttributes(textAttribute, true);
					textPane.requestFocus();
				}
				else if (ae.getSource()==encryptBar||ae.getSource()==encryptPop) {
					callKeyScreen("encryption");
				}
				else if(ae.getSource()==decryptBar||ae.getSource()==decryptPop) {
					callKeyScreen("decryption");
				}
				else if (ae.getSource()==cutBar||ae.getSource()==cutEdit||ae.getSource()==cutPop) {
					textPane.cut();
				}
				else if (ae.getSource()==copyBar||ae.getSource()==copyEdit||ae.getSource()==copyPop) {
					textPane.copy();
				}
				else if(ae.getSource()==pasteBar||ae.getSource()==pasteEdit||ae.getSource()==pastePop) {
					textPane.paste();
				}
				else if(ae.getSource()==replacePop||ae.getSource()==repalceEdit) {
					callReplaceScreen(textPane.getSelectedText());
				}
				else if(ae.getSource()==selectAllEdit) {
					textPane.selectAll();
				}
				else if(ae.getSource()==printFileMenu) {
					try {
						textPane.print();
					} catch (PrinterException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if (ae.getSource()==exit) {
					if(textPane.getText()==""||fileOps.savedStatus==true) {
					System.exit(1);
					}
					else {
						callSaveScreen();
					}
				}
				else if (ae.getSource()==search) {
					
					
				}
			}
		};
		
		
		
		

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		wordpad  obj =new wordpad();
		obj.createMenuBar();
		obj.createHorizontalToolbar();
		obj.createThemeButton();
		obj.createVerticalToolbar();
		obj.createTextPane();
		obj.createStatusLabel();
		obj.createPopup();
	}

}



