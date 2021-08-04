import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.nio.CharBuffer;
import javax.swing.text.*;
import javax.swing.text.StyleConstants.FontConstants;
import javax.swing.text.rtf.RTFEditorKit;
public class wordpad   {
	JFrame frame;
	Container contentPane;
	JButton bold,italic,underline,alignLeft,alignRight,alignCenter,newBar,openBar,saveBar,clearBr,cutBar,copyBar,pasteBar,encryptBar,decryptBar;JButton b,i,u;
	JMenuBar menuBar;
	JMenu fileMenu,editMenu,help,tools;
	JMenuItem newFileMenu,openFileMenu,saveFileMenu,saveAsFileMenu,printFileMenu,exit,cutEdit,copyEdit,pasteEdit,undoEdit,redoEdit,findEdit,repalceEdit,selectAllEdit,cutPop,copyPop,pastePop,replacePop,encryptPop,decryptPop;
	JTextPane textPane;
	JLabel statusLabel;JLabel changesSaved;JLabel wc;
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
	CharBuffer letters=CharBuffer.allocate(1000);
	working work=new working(this);
	JPanel panel;
	JCheckBox viewstatus;
	//-------------------------
		wordpad() {
			Toolkit kit;
			kit=Toolkit.getDefaultToolkit();
			Dimension screenSize=kit.getScreenSize();
			height=screenSize.height;
			width=screenSize.width;
			frame=new JFrame("WordPad");
			frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			frame.setSize(700, 700);
			frame.setMinimumSize(new Dimension(200,200));
			contentPane=frame.getContentPane();
			contentPane.setBackground(new Color(133, 193, 233));
			ImageIcon mainIcon=new ImageIcon("./icons/notepad.png");
			frame.setIconImage(mainIcon.getImage());
			frame.setBackground(new Color(214, 234, 248));
			frame.setIconImage(Toolkit.getDefaultToolkit().getImage("./icons/notepad.png"));
			
			frame.addWindowListener(work.windowListener);
			}
		public void createMenuBar() {
			menuBar=new JMenuBar();
			menuBar.setBackground(new Color(190, 221, 244));
			fileMenu=new JMenu("File");
			fileMenu.setVisible(true);
			newFileMenu= createMenuItem(fileMenu, "New",new ImageIcon("./icons/small-icons/icons8-compose-16.png"));
			openFileMenu= createMenuItem(fileMenu, "Open",new ImageIcon("./icons/small-icons/icons8-open-document-16.png"));
			saveFileMenu= createMenuItem(fileMenu, "Save",new ImageIcon("./icons/small-icons/icons8-save-16.png"));
			saveAsFileMenu= createMenuItem(fileMenu, "SaveAs",new ImageIcon("./icons/small-icons/icons8-save-16.png"));
			printFileMenu= createMenuItem(fileMenu, "Print",new ImageIcon("./icons/small-icons/icons8-print-16.png"));
			exit= createMenuItem(fileMenu, "Close",new ImageIcon("./icons/small-icons/icons8-macos-close-16.png"));
			editMenu= new JMenu("Edit");
			cutEdit= createMenuItem(editMenu, "Cut",new ImageIcon("./icons/small-icons/icons8-cut-16.png"));
			copyEdit= createMenuItem(editMenu, "Copy",new ImageIcon("./icons/small-icons/icons8-copy-16.png"));
			pasteEdit= createMenuItem(editMenu, "Paste",new ImageIcon("./icons/small-icons/icons8-clipboard-16.png"));
			undoEdit= createMenuItem(editMenu, "Undo",new ImageIcon("./icons/small-icons/icons8-undo-16 (1).png"));
			redoEdit= createMenuItem(editMenu, "Redo",new ImageIcon("./icons/small-icons/icons8-redo-16 (1).png"));
			findEdit= createMenuItem(editMenu, "Find",new ImageIcon("./icons/small-icons/icons8-search-16.png"));
			repalceEdit= createMenuItem(editMenu, "Replace",new ImageIcon("./icons/small-icons/icons8-copy-to-clipboard-16.png"));
			selectAllEdit= createMenuItem(editMenu, "SelectAll",new ImageIcon("./icons/small-icons/icons8-select-all-16.png"));
			tools=new JMenu("Tools");
			help=new JMenu("Help");
			menuBar.add(fileMenu);
			menuBar.add(editMenu);
			viewstatus=new JCheckBox("View StatusBar",true);
			viewstatus.setBackground(new Color(190, 221, 244));
			viewstatus.addItemListener(work.itemListner);
			tools.add(viewstatus);
			menuBar.add(tools);
//			menuBar.add(help);
			menuBar.setVisible(true);
			menuBar.setSize(width, 20);
			frame.setJMenuBar(menuBar);
		}
		public JMenuItem createMenuItem(JMenu m,String name,Icon icon) {
			JMenuItem tempItem=new JMenuItem(name);
			tempItem.setBackground(new Color(190, 221, 244));
			tempItem.addActionListener(work.al);
			tempItem.setVisible(true);
			tempItem.setIcon(icon);
			tempItem.setIconTextGap(5);
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
			
			fontLists.addItemListener(work.itemListner);
			horizontalToolBar.add(fontLists);
			fontSizeSpinner=new JSpinner();
			fontSizeSpinner.setValue(20);
			
			fontSizeSpinner.addChangeListener(work.changeListener);
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
			temp.addActionListener(work.al);
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
			temp.addActionListener(work.al);
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
			doc.addDocumentListener(work. documentListener);
			textAttribute=new SimpleAttributeSet();
			encryptAttribute=new SimpleAttributeSet();
			FontConstants.setFontFamily(textAttribute, textPane.getFont().getFamily());
			FontConstants.setFontSize(textAttribute, textPane.getFont().getSize());
			textPane.setCharacterAttributes(textAttribute, true);
			textPane.setParagraphAttributes(textAttribute, true);
			textPane.addMouseListener(work.ml);
			textPane.addCaretListener(work.caretListener);
			doc.addUndoableEditListener(work.undolistener);
		}
		public void createStatusLabel() {
			panel=new JPanel();
			panel.setBackground(new Color(190, 221, 244));
			panel.setLayout(new FlowLayout());
			statusLabel=new JLabel("Line:"+lineNumber+" Column:"+colNum+"        ",JLabel.RIGHT);
			statusLabel.setBackground(new Color(133, 193, 233));
			panel.add(statusLabel,BorderLayout.PAGE_START);
			wc=new JLabel("Word Count:0");
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
//				StyleConstants.setForeground(encryptAttribute, Color.red);
//				textPane.setCharacterAttributes(encryptAttribute, true);
				try {
					doc.insertString(start, encryptedText, textAttribute);
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
			cutPop=createMenuItem(popup, "Cut",new ImageIcon("./icons/small-icons/icons8-cut-16.png"));
			copyPop=createMenuItem(popup, "Copy",new ImageIcon("./icons/small-icons/icons8-copy-16.png"));
			pastePop=createMenuItem(popup, "Paste",new ImageIcon("./icons/small-icons/icons8-clipboard-16.png"));
			replacePop=createMenuItem(popup, "Replace",new ImageIcon("./icons/small-icons/icons8-copy-to-clipboard-16.png"));
			encryptPop=createMenuItem(popup, "Encrypt",new ImageIcon("./icons/small-icons/icons8-data-encryption-16.png"));
			decryptPop=createMenuItem(popup, "Decrypt",new ImageIcon("./icons/small-icons/icons8-unlock-16.png"));	
		}
		
		public JMenuItem createPop(JPopupMenu pop,String str) {
			Icon icon=new ImageIcon("./icons/small-icons/icons8-undo-16.png");
			JMenuItem menuItem=new JMenuItem(str);
			menuItem.setIcon(icon);
			menuItem.setIconTextGap(5);
			pop.add(menuItem);
			return menuItem;
		}
		
		public JMenuItem createMenuItem(JPopupMenu mb,String str,Icon icon) {
			JMenuItem temp=new JMenuItem(str);
			temp.setBackground(new Color(190, 221, 244));
			temp.setIcon(icon);
			temp.setIconTextGap(5);
			mb.add(temp);
			temp.addActionListener(work.al);
			temp.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.lightGray));
			return temp;	
		}
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
		StyleConstants.setFontFamily(obj.textAttribute, obj.fontLists.getSelectedItem().toString());
		obj.textPane.setCharacterAttributes(obj.textAttribute, true);
		StyleConstants.setFontSize(obj.textAttribute,(int)obj.fontSizeSpinner.getValue());
		obj.textPane.setCharacterAttributes(obj.textAttribute, true);
		UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		
		
	}
}