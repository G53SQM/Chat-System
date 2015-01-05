package client.tools;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import client.view.Login;
import common.Message;
import common.MessageType;
import common.User;

public class ToServer
{
	//private static String serverIP = "10.154.135.59";
	private static int port = 19999;
	
	public void login(String serverIP, User user, Login frame) throws Exception
	{
		Socket socket = new Socket(serverIP, port);
		ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
		oos.writeObject(user);
		ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
		Message msg = (Message) ois.readObject();
		String msgType = msg.getMsgType();
		if (msgType.equals(MessageType.MSG_LOGIN_SUCC))
		{
			ClientThread thread = new ClientThread(socket, user.getID());
			Resource.setThread(user.getID(), thread);
			Resource.getProxy(user.getID()).handleLoginSucc(user.getID(), frame);
			thread.start();				
		}
		else if (msgType.equals(MessageType.MSG_LOGIN_FAIL))
		{
			Resource.getProxy(user.getID()).handleLoginFailed();
			//socket.close();
		}
	}
}
