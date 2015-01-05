package server.tools;

import java.util.HashMap;
import java.util.Set;

public class Resource 
{

	private static HashMap<String, ServerThread> threadPool = new HashMap<String, ServerThread>();
	
	public static ServerThread getThread(String id) { return threadPool.get(id);}
	public static void setThread(String id, ServerThread thread) { threadPool.put(id, thread);}
	public static void rmThread(String id) { threadPool.remove(id);}
	public static Set<String> getOnlineUsers() { return threadPool.keySet();}
	
}
