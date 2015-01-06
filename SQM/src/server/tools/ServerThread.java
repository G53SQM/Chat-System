package server.tools;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Set;

import server.db.UserChecker;
import server.view.ServerVisual;
import common.Message;
import common.MessageType;

public class ServerThread extends Thread
{
	private Socket socket;
	private String userID = "";
	private int numErrToQuit = 3;
	
	private ServerVisual display;
	
	public ServerThread(Socket s, String id, ServerVisual sv) { socket = s; userID = id; display = sv;}
	public ServerThread(Socket s, String id) { socket = s; userID = id;}
	
	public Socket getSocket() { return socket;}
	public void setSocket(Socket s) { socket = s;}
	
	private boolean flag = true;

	
	public void terminate() 
	{ 		
		try
		{
			display.disp(userID + " logout");
			Message msgReturn = new Message();
			msgReturn.setMsgTypye(MessageType.MSG_SERVER_OUT);
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			oos.writeObject(msgReturn);
			Thread.sleep(1000);
			socket.close();
			Resource.rmThread(userID);
			UserChecker.userLogout(userID);
		}
		catch (Exception e) {e.printStackTrace();}
		flag = false;
	}
	
	public void run()
	{
		int numErr = 0;
		while (flag)
		{				
			try
			{	
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());		
				Message msg = (Message) ois.readObject();
				// TODO check srcUser == userID ?
				String msgType = msg.getMsgType();
				if (msgType.equals(MessageType.MSG_PLAIN))
				{
					display.disp("Message received from " + msg.getSrcUser() + ", to " + msg.getDestUser());
					Iterable<String> userList = msg.getDestUser().equals("#All") ? Resource.getOnlineUsers()
											: Arrays.asList(new String[] {msg.getSrcUser(), msg.getDestUser()});
					userList.forEach((dest) ->
					{				
						try
						{
							msg.setDestUser(dest);
							ServerThread thread = Resource.getThread(dest);
							ObjectOutputStream oos = new ObjectOutputStream(thread.getSocket().getOutputStream());
							oos.writeObject(msg);	
						}
						catch (Exception e) { e.printStackTrace();}
					});
				}
				else if (msgType.equals(MessageType.MSG_GET_FND))
				{
					broadcastOnlineList();	
				}	
				else if (msgType.equals(MessageType.MSG_CLIENT_OUT))
				{
					terminate();					
					broadcastOnlineList();							
				}
				numErr = numErr == 0 ? 0 : numErr - 1;
			}	
			catch (Exception e) 
			{ 
				e.printStackTrace();
				numErr++;
				if (numErr >= numErrToQuit)
				{
					Resource.rmThread(userID);
					try 
					{						
						socket.close();
						Resource.rmThread(userID);
						UserChecker.userLogout(userID);
					} 
					catch (IOException e1) { e1.printStackTrace();}
					break;
				}
			}	
		}
	}
	
	private void broadcastOnlineList()
	{
		try
		{
			Set<String>	users = Resource.getOnlineUsers();
			for (String destUser : users)
			{
				String con = "";
				for (String str : users) con = con + str + " ";
				Message msgReturn = new Message();
				msgReturn.setMsgTypye(MessageType.MSG_LIST_FND);
				msgReturn.setDestUser(destUser);
				msgReturn.setContent(con);
				ObjectOutputStream oos = new ObjectOutputStream(Resource.getThread(destUser).getSocket().getOutputStream());
				oos.writeObject(msgReturn);
			}
		}	
		catch (Exception e) { e.printStackTrace();}
	}
}
