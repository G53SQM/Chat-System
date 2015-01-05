package server.tools;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import server.db.UserChecker;
import server.view.ServerVisual;
import common.Message;
import common.MessageType;
import common.User;

public class Server 
{
	public static int port = 12360;
	
	private ServerVisual display;
	private boolean flag = true;
	private ServerSocket ssocket;
	
	public Server(ServerVisual sv) {display = sv;}
	
	public void run() throws Exception
	{
		ssocket = new ServerSocket(port);
		display.disp("Server launched " + InetAddress.getLocalHost().getHostAddress());
		while (flag)
		{
			Socket socket = ssocket.accept();
			display.disp("Connection established from " + socket.getRemoteSocketAddress());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			User user = (User) ois.readObject();	
			display.disp("Logging in using name = " + user.getID() + ", password = " + user.getPassword());
			Message msg = new Message();
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			if (userValid(user))
			{
				UserChecker.userLogin(user.getID());
				msg.setMsgTypye(MessageType.MSG_LOGIN_SUCC);
				oos.writeObject(msg);
				ServerThread thread = new ServerThread(socket, user.getID(), display);
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
	
	private boolean userValid(User user)
	{
		return UserChecker.userValid(user);
	}
	
	public void shutdown() 
	{
		Object[] users = Resource.getConnections().toArray();
		if (users != null)
			for (Object thread : users)
				((ServerThread) thread).terminate();
		flag = false;
		try
		{
			ssocket.close();	
		}
		catch (Exception e) { e.printStackTrace();}
	}
	
	
	
}
