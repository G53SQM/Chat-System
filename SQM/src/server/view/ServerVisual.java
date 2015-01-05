package server.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.InetAddress;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import server.tools.Server;

public class ServerVisual extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private String title = "Chat system server";
	private int width = 450, height = 450;
	private JTextArea display = new JTextArea();
	
	private Server server;
	
	public ServerVisual()
	{
		setTitle(title);
		setVisible(true);
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				setVisible(false);
				server.shutdown();	
				System.exit(0);
			}
		});
		setPreferredSize(new Dimension(width, height));		
		setResizable(false);
		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - width) / 2,
				    (Toolkit.getDefaultToolkit().getScreenSize().height - height) / 2);
		setLayout(new BorderLayout());
		
		JPanel buttons = new JPanel();
		buttons.setLayout(new FlowLayout());
		JButton start = new JButton("Start server");
		start.setPreferredSize(new Dimension(120, 30));
		JButton stop = new JButton("Stop server");
		stop.setPreferredSize(new Dimension(120, 30));
		stop.setEnabled(false);
		start.addActionListener((e) -> 
		{
			new Thread(() -> 
			{
				start.setEnabled(false);
				stop.setEnabled(true);		
				display.setText("");
				server = new Server(this);
				try
				{
					server.run();
				}
				catch (Exception ex) 
				{
					if (!start.isEnabled())
					{
						display.setText("Error: " + ex.getMessage());
						start.setEnabled(true); 
						stop.setEnabled(false);
					}
				}
			}).start();
		});
		stop.addActionListener((e) ->
		{
			new Thread(() -> 
			{
				start.setEnabled(true);
				stop.setEnabled(false);
				server.shutdown();			
				display.setText("Server closed");
			}).start();
		});		
		buttons.add(start);
		buttons.add(stop);
		
		JLabel label = new JLabel();
		label.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		try { label.setText("IP: " + InetAddress.getLocalHost().getHostAddress()); } 
		catch (Exception e) {}
		JPanel top = new JPanel();
		top.setLayout(new BorderLayout());
		top.add(label, BorderLayout.CENTER);
		buttons.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
		top.add(buttons, BorderLayout.SOUTH);
		add(top, BorderLayout.NORTH);
		
		display.setLineWrap(true);
		display.setEditable(false);
		display.setBorder(BorderFactory.createLoweredBevelBorder());
		JScrollPane scroll = new JScrollPane(display);
		scroll.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
		
		add(scroll, BorderLayout.CENTER);
		
		
		
		pack();
	}
	
	public void disp(String str)
	{
		display.setText(str + "\n" + display.getText());
	}
	
	public static void main(String[] args)
	{
		try 
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} 
		catch (Exception e) { e.printStackTrace();}	
		new ServerVisual();
	}
}
