package client.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

import client.tools.Resource;
import common.Message;
import common.MessageType;

public class ChatFrame extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private String title = "Chat System";
	private int width = 550;
	private int height = 550;
	
	private JTextArea input = new JTextArea();
	private JScrollPane inputPane = new JScrollPane();
	private JTextArea display = new JTextArea();
	private JScrollPane screen = new JScrollPane();
	
	private String userID = "";
	private DefaultListModel<String> fndList;
	private ArrayList<String> fndData = null;
	
	public ChatFrame(String id)
	{
		userID = id;
		
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				Message msg = new Message();
				msg.setMsgTypye(MessageType.MSG_CLIENT_OUT);
				msg.setSrcUser(userID);
				try
				{
					ObjectOutputStream oos = new ObjectOutputStream(Resource.getThread(userID).getSocket().getOutputStream());
					oos.writeObject(msg);
				}
				catch (Exception ex) { ex.printStackTrace();}
			}
		});
		
		setTitle(title + " @" + userID);
		setVisible(true);
		this.toFront();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(width, height));		
		setResizable(false);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2,
				    (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2);
		setLayout(new BorderLayout());
		JPanel main = new JPanel();
		main.setLayout(new BorderLayout(5, 5));
		main.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		display.setEditable(false);
		display.setLineWrap(true);
		screen.setViewportView(display);		
		main.add(screen, BorderLayout.CENTER);
		JPanel bottom = new JPanel();
		bottom.setLayout(new BorderLayout(5, 5));		
		bottom.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		input.setLineWrap(true);
		input.addKeyListener(new KeyListener()
		{
			public void keyPressed(KeyEvent e) { if (e.getKeyCode() == KeyEvent.VK_ENTER) sendMsg();}			
			public void keyReleased(KeyEvent e) {}		
			public void keyTyped(KeyEvent e) {}
		});
		inputPane.setViewportView(input);
		inputPane.setPreferredSize(new Dimension(width * 3 / 4, height / 6));
		bottom.add(inputPane, BorderLayout.CENTER);
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BorderLayout());
		buttonPane.add(new JLabel(""), BorderLayout.CENTER);
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		JButton clear = new JButton("Clear");
		clear.addActionListener((e) -> {input.setText("");});
		clear.setPreferredSize(new Dimension(80, 30));
		buttons.add(clear);
		JButton send = new JButton("Send");
		send.addActionListener((e) -> {sendMsg();});
		send.setPreferredSize(new Dimension(80, 30));
		buttons.add(send);
		buttonPane.add(buttons, BorderLayout.EAST);
		bottom.add(buttonPane, BorderLayout.SOUTH);
		main.add(bottom, BorderLayout.SOUTH);
		JPanel fndPane = new JPanel();
		fndPane.setLayout(new BorderLayout());
		fndList = new DefaultListModel<String>();
		JList<String> list = new JList<String>(fndList);
		list.addListSelectionListener((e) -> 
		{
			if (list.getSelectedIndex() >= 0)
				input.setText("[To " + list.getSelectedValue() + "]: ");
		});
		list.addFocusListener(new FocusListener()
		{
			public void focusGained(FocusEvent e) {}
			public void focusLost(FocusEvent e) {list.clearSelection();}
		});
		JScrollPane fnd = new JScrollPane(list);
		fndPane.add(fnd, BorderLayout.CENTER);
		JLabel label = new JLabel("Members online");
		label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		label.setFont(new Font("Consolas", 0, 20));
		fndPane.add(label, BorderLayout.NORTH);
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, main, fndPane);
		split.setDividerSize(3);
		split.setDividerLocation(width * 2 / 3);
		add(split, BorderLayout.CENTER);
		pack();
	}
	
	public void addMsg(Message msg)
	{
		String msgContent = msg.getContent();
		String disContent = msgContent.startsWith("[To " + userID + "]: ") ? 
							"[From " + msg.getSrcUser() + "]: " + 
							msgContent.substring(msgContent.indexOf("]") + 3) : 
							msgContent;
		display.append("[" + msg.getSrcUser() + "] " + msg.getTime() + "\n" + disContent + "\n\n");
		display.setCaretPosition(display.getDocument().getLength());
		input.setEditable(true);
		input.setText("");			
	}
	
	private void sendMsg()
	{
		if (input.getText().equals("")) return;
		input.setEditable(false);
		String content = input.getText();
		String destUser = "#All";
		if (content.startsWith("[To ") && content.contains("]: "))
		{
			String user = content.substring(4, content.indexOf("]"));
			if (fndData.contains(user))
				destUser = user;
		}
		Message msg = new Message();
		msg.setMsgTypye(MessageType.MSG_PLAIN);
		msg.setSrcUser(userID);
		msg.setDestUser(destUser);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		msg.setTime(sdf.format(date));
		msg.setContent(content);
		try
		{
			ObjectOutputStream oos = new ObjectOutputStream(Resource.getThread(userID).getSocket().getOutputStream());
			oos.writeObject(msg);
		}
		catch (Exception ex) { ex.printStackTrace();}
	}
	
	public void setFndList(String[] list) 
	{
		fndData = new ArrayList<String>(Arrays.asList(list));
		fndData.remove(userID);
		fndData.sort((a, b) -> a.compareTo(b));
		fndList.removeAllElements();
		fndData.forEach((str) -> fndList.addElement(str));
		repaint();
	}
	
	public void offline()
	{
		JOptionPane.showMessageDialog(null, "Server disconnected", "Chat System",
				JOptionPane.WARNING_MESSAGE, null);
	}
	
}
