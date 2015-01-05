package server.tools;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import server.db.UserChecker;
import common.Message;
import common.MessageType;
import common.User;

public class Server 
{
	public static int port = 19999;
	
	
	public void run()
	{
		try
		{
			ServerSocket ssocket = new ServerSocket(port);
			System.out.println("Server launched " + InetAddress.getLocalHost().getHostAddress());
			while (true)
			{
				Socket socket = ssocket.accept();
				System.out.println("Connection established from " + socket.getRemoteSocketAddress());
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				User user = (User) ois.readObject();	
				System.out.println("Logging in using name = " + user.getID() + ", password = " + user.getPassword());
				Message msg = new Message();
				ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
				if (userValid(user))
				{
					UserChecker.userLogin(user.getID());
					msg.setMsgTypye(MessageType.MSG_LOGIN_SUCC);
					oos.writeObject(msg);
					ServerThread thread = new ServerThread(socket, user.getID());
					Resource.setThread(user.getID(), thread);
					thread.start();
				}
				else
				{
					msg.setMsgTypye(MessageType.MSG_LOGIN_FAIL);
					oos.writeObject(msg);
					socket.close();
				}
			}
		}
		catch (Exception e) { e.printStackTrace();}
	}
	
	private boolean userValid(User user)
	{
		return UserChecker.userValid(user);
	}
	
	public static void main(String[] args)
	{
		new Server().run();
	}
}
