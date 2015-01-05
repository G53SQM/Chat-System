package common;

public class Message implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
<<<<<<< HEAD
   	
=======
	
>>>>>>> origin/Server
	
	private String msgType, srcUser, destUser, content, time;
	
	public String getMsgType() { return msgType;}
	public void setMsgTypye(String str) { msgType = str;}
	
	public String getSrcUser() { return srcUser;}
	public void setSrcUser(String str) { srcUser = str;}
	
	public String getDestUser() { return destUser;}
	public void setDestUser(String str) { destUser = str;}
	
	public String getContent() { return content;}
	public void setContent(String str) { content = str;}
	
	public String getTime() { return time;}
	public void setTime(String str) { time = str;}
}
