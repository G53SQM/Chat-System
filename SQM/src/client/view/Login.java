package client.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;


import common.User;
import client.tools.Resource;
import client.tools.ToServer;

public class Login extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private String title = "Log in";
	private int width = 280, height = 280;
	private JTextField tfName = new JTextField();
	private JPasswordField tfPass = new JPasswordField();
	private JTextField tfIP = new JTextField();
	private JLabel info = new JLabel();
	
	public Login()
	{		
		try 
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} 
		catch (Exception e) { e.printStackTrace();}		
		
		setTitle(title);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(width, height));		
		setResizable(false);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2,
				    (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2);
		setLayout(new BorderLayout());
		JPanel middle = new JPanel();
		middle.setLayout(new GridLayout(4, 1, 5, 5));
		info.setText("Type in user inormation: ");
		middle.add(info);
		JPanel name = new JPanel();
		name.setLayout(new BorderLayout());
		name.add(new JLabel("User name:  "), BorderLayout.WEST);
		name.add(tfName, BorderLayout.CENTER);
		tfName.setText("Andrew");
		middle.add(name);
		tfPass.addActionListener(listener);
		JPanel pass = new JPanel();
		pass.setLayout(new BorderLayout());
		pass.add(new JLabel("Password:   "), BorderLayout.WEST);
		pass.add(tfPass, BorderLayout.CENTER);
		tfPass.setText("andrew");
		middle.add(pass);
		JPanel ip = new JPanel();
		ip.setLayout(new BorderLayout());
		ip.add(new JLabel("Server:     "), BorderLayout.WEST);
		ip.add(tfIP, BorderLayout.CENTER);
		try { tfIP.setText(InetAddress.getLocalHost().getHostAddress()); }
		catch (UnknownHostException e1) {}
		middle.add(ip);
		JPanel bottom = new JPanel();
		bottom.setLayout(new BorderLayout(10, 10));
		bottom.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		bottom.add(middle, BorderLayout.CENTER);
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		JButton logIn = new JButton("Log in");
		logIn.setPreferredSize(new Dimension(80, 30));
		logIn.addActionListener(listener);
		JButton cancel = new JButton("Cancel");
		cancel.setPreferredSize(new Dimension(80, 30));
		cancel.addActionListener((e) -> { System.exit(0);});
		buttons.add(logIn);
		buttons.add(cancel);
		bottom.add(buttons, BorderLayout.SOUTH);
		add(bottom, BorderLayout.SOUTH);
		JLabel logo = new JLabel("Chat System");
		logo.setFont(new Font("Trebuchet MS", 3, 35));
		logo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		add(logo, BorderLayout.CENTER);
		pack();
	}

	private ActionListener listener = (e) -> 
	{
		info.setText("Logging in");
		tfName.setEditable(false);
		tfPass.setEditable(false);
		tfIP.setEditable(false);
		new Thread(() -> 
		{
			String name = tfName.getText();
			String pass = new String(tfPass.getPassword());
			String ip = tfIP.getText();
			User user = new User(name, pass);			
			Resource.setProxy(name, new ViewerProxy());
			try
			{
				new ToServer().login(ip, user, this);
			}
			catch (Exception ex)
			{
				info.setText("Log in failed");
			}
			tfName.setText("");
			tfPass.setText("");
			tfName.setEditable(true);
			tfPass.setEditable(true);
			tfIP.setEditable(true);
		}).start();
	};
	
	public static void main(String[] args)
	{
		new Login();
	}

}
