package client.tools;

import java.util.HashMap;

import client.view.ViewerProxy;

public class Resource
{
	
	private static HashMap<String, ClientThread> threadPool = new HashMap<String, ClientThread>();
	private static HashMap<String, ViewerProxy> proxyList = new HashMap<String, ViewerProxy>();
	
	public static ClientThread getThread(String id) { return threadPool.get(id);}
	public static void setThread(String id, ClientThread thread) { threadPool.put(id, thread);}
	public static void rmThread(String id) { threadPool.remove(id);}
	
	public static ViewerProxy getProxy(String id) { return proxyList.get(id);}
	public static void setProxy(String id, ViewerProxy proxy) { proxyList.put(id, proxy);}
	public static void rmProxy(String id) { proxyList.remove(id);}
	
}
