package client.view;

import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

import client.tools.Resource;
import common.Message;
import common.MessageType;

public class ViewerProxy
{
	private ChatFrame frame;
	
	private static Login login = null;
	
	
	public void handleLoginSucc(String id, Login f)
	{
		System.out.println("log in succ");
		
		f.setVisible(false);
		if (login == null) login = f;
		Message msg = new Message();
		msg.setMsgTypye(MessageType.MSG_GET_FND);
		msg.setSrcUser(id);
		try
		{
			ObjectOutputStream oos = new ObjectOutputStream(Resource.getThread(id).getSocket().getOutputStream());
			oos.writeObject(msg);
		}
		catch (Exception ex) { ex.printStackTrace();}
		frame = new ChatFrame(id);
	}
	
	public void handleLoginFailed()
	{
		System.out.println("log in failed");
		
		JOptionPane.showMessageDialog(null, "Log in failed", "Log in",
				JOptionPane.WARNING_MESSAGE, null);
	}
	
	public void handleMsgIncome(Message msg)
	{
		System.out.println("Message From " + msg.getSrcUser());
		System.out.println(msg.getTime());
		frame.addMsg(msg);
	}
	
	public void handleFndList(Message msg)
	{
		String[] token = msg.getContent().split(" ");
		frame.setFndList(token);
	}
	
	public void offline()
	{
		frame.offline();
	}
}
