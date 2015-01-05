package common;

public class User implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String id, password;
	
	public User(String name, String pass) { id = name; password = pass;}
<<<<<<< HEAD
=======
	public User() { id = ""; password = "";}
>>>>>>> origin/Server
	
	public String getID() { return id;}
	public void setID(String str) { id = str;}
	
	public String getPassword() { return password;}
	public void setPassword(String str) { password = str;}
	
}
