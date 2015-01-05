package client.tools.test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import common.Message;
import common.MessageType;
import client.tools.ClientThread;
import client.tools.Resource;
import client.view.ViewerProxy;

public class TestClientThread 
{
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	}

	@After
	public void cleanUpStreams() {
	    System.setOut(null);
	}
		
	
	int port = 19999;
	boolean flag = true;
	
	
	@Test
	public void testConnection()
	{
		try
		{
			String serverIP = InetAddress.getLocalHost().getHostAddress();	
			ServerSocket ssocket = new ServerSocket(port);
			ssocket.setSoTimeout(100);
			new ClientThread(new Socket(serverIP, port), "id");
			ssocket.accept();
			ssocket.close();
		}
		catch (Exception e) { e.printStackTrace();}
	}
	
	
	@Test
	public void testMsg()
	{
		try
		{
			String serverIP = InetAddress.getLocalHost().getHostAddress();	
			ServerSocket ssocket = new ServerSocket(port);
			ssocket.setSoTimeout(100);
			Socket socket = new Socket(serverIP, port);
			ClientThread thread = new ClientThread(socket, "id");
			Resource.setProxy("id", new ViewerProxy());
			Resource.setThread("id", thread);
			thread.start();
			Socket s = ssocket.accept();
			
			Message msg = new Message();
			msg.setMsgTypye(MessageType.MSG_PLAIN);
			msg.setSrcUser("idSrc");
			msg.setDestUser("id");
			msg.setContent("");
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(msg);
			

			msg = new Message();
			msg.setMsgTypye(MessageType.MSG_LIST_FND);
			msg.setSrcUser("id");
			msg.setDestUser("id");
			msg.setContent("");
			oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(msg);
			
			msg = new Message();
			msg.setMsgTypye(MessageType.MSG_SERVER_OUT);
			msg.setSrcUser("id");
			msg.setContent("");
			oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(msg);
						
			ssocket.close();
		}
		catch (Exception e) { e.printStackTrace();}
	}
	
	
	
	
	
	

}
