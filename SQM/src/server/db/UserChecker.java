package server.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;

import common.User;

public class UserChecker 
{
	
	private static HashSet<String> online = new HashSet<String>();
	private static HashMap<String, String> users = new HashMap<String, String>();
	
	static
	{
		init();
	}
	
	public static void init()
	{
		try 
		{
			BufferedReader br = new BufferedReader(new FileReader(new File("src/server/db/user.csv")));
			String str;
			while ((str = br.readLine()) != null) 
			{
				String[] token = str.split(",");
				users.put(token[0], token[1]);
			}
			br.close();
		} 
		catch (Exception e) { e.printStackTrace();}
	}	
	
	public static boolean userValid(User user)
	{
		String pass = users.get(user.getID());	
		if (pass != null)
			return pass.equals(user.getPassword()) && !userOnline(user.getID()); 
		return false;
	}
	
	public static void userLogin(String id) { online.add(id);}
	public static void userLogout(String id) { online.remove(id);}
	public static boolean userOnline(String id) { return online.contains(id);}
}
