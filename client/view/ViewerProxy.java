package client.view;


import client.tools.Resource;
import common.Message;
import common.MessageType;

public class ViewerProxy
{
	
	public void handleLoginSucc(String id, Login f)
	{
		System.out.println("log in succ");
		
	}
	
	public void handleLoginFailed()
	{
		System.out.println("log in failed");
		
	}
	
	public void handleMsgIncome(Message msg)
	{
		System.out.println("Message From " + msg.getSrcUser());
		System.out.println(msg.getTime());
	
	}
	
	public void handleFndList(Message msg)
	{
		System.out.println("Received friend list: " + msg.getContent());

	}
	
	public void offline()
	{
		System.out.println("user offline");
	}
}
