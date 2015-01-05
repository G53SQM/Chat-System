package server.tools.test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.Test;

import server.tools.Resource;
import server.tools.ServerThread;
import common.Message;
import common.MessageType;


public class TestServerThread 
{
	
	int port = 19999;
	boolean flag = true;
	
	@Test
	public void testMsg()
	{
		try
		{
			String serverIP = InetAddress.getLocalHost().getHostAddress();	
			ServerSocket ssocket = new ServerSocket(port);
			ssocket.setSoTimeout(100);
			Socket socket = new Socket(serverIP, port);
			ServerThread thread = new ServerThread(socket, "id");
			Resource.setThread("id", thread);
			thread.start();
			Socket s = ssocket.accept();
			
			Message msg = new Message();
			msg.setMsgTypye(MessageType.MSG_PLAIN);
			msg.setSrcUser("id");
			msg.setContent("");
			ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(msg);

			msg = new Message();
			msg.setMsgTypye(MessageType.MSG_GET_FND);
			msg.setSrcUser("id");
			msg.setContent("");
			oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(msg);
			
			msg = new Message();
			msg.setMsgTypye(MessageType.MSG_CLIENT_OUT);
			msg.setSrcUser("id");
			msg.setContent("");
			oos = new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(msg);
						
			ssocket.close();
		}
		catch (Exception e) { e.printStackTrace();}
	}
	
	
	
	
	

}
