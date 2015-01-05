package client.tools;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import common.Message;
import common.MessageType;

public class ClientThread extends Thread
{
	private Socket socket;
	private String userID = "";
	private int numErrToQuit = 3;
	
	public ClientThread(Socket s, String id) { socket = s; userID = id;}
	
	public Socket getSocket() { return socket;}
	public void setSocket(Socket s) { socket = s;}
	
	public void run()
	{
		int numErr = 0;
		while (true)
		{
			try
			{
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				Message msg = (Message) ois.readObject();
				// TODO check destUser == userID ?
				String msgType = msg.getMsgType();				
				if (msgType.equals(MessageType.MSG_PLAIN))
				{
					Resource.getProxy(msg.getDestUser()).handleMsgIncome(msg);
				}
				else if (msgType.equals(MessageType.MSG_LIST_FND))
				{
					Resource.getProxy(msg.getDestUser()).handleFndList(msg);
				}
				else if (msgType.equals(MessageType.MSG_SERVER_OUT))
				{
					new Thread(() -> 
					{
						Resource.getProxy(userID).offline();
						Resource.rmThread(userID);
						Resource.rmProxy(userID);						
					}).start();
					socket.close();
					break;
				}
				numErr = numErr == 0 ? 0 : numErr - 1;
			}
			catch (Exception e) 
			{ 
				e.printStackTrace();
				numErr++;
				if (numErr >= numErrToQuit)
				{
					Resource.getProxy(userID).offline();
					Resource.rmThread(userID);
					Resource.rmProxy(userID);
					try 
					{
						socket.close();
					} 
					catch (IOException e1) { e1.printStackTrace();}
					break;
				}
			}
		}
	}
}
