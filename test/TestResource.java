<<<<<<< HEAD
package client.tools.test;
=======
package server.tools.test;
>>>>>>> origin/Server

import static org.junit.Assert.*;

import java.net.Socket;
<<<<<<< HEAD

import org.junit.Test;

import client.tools.ClientThread;
import client.tools.Resource;
import client.view.ViewerProxy;
=======
import java.util.Set;

import org.junit.Test;

import server.tools.Resource;
import server.tools.ServerThread;


>>>>>>> origin/Server

public class TestResource 
{
	
	@Test
	public void testTread()
	{
		String id = "id";
<<<<<<< HEAD
		ClientThread thread = new ClientThread(new Socket(), id);
		Resource.setThread(id, thread);
		assertEquals(thread, Resource.getThread(id));
		
=======
		ServerThread thread = new ServerThread(new Socket(), id);
		Resource.setThread(id, thread);
		assertEquals(thread, Resource.getThread(id));
		
		Set<String> set = Resource.getOnlineUsers();
		assertEquals(set.size(), 1);
		
>>>>>>> origin/Server
		Resource.rmThread(id);
		assertNull(Resource.getThread(id));
	}
	
<<<<<<< HEAD
	@Test
	public void testProxy()
	{
		String id = "id";
		ViewerProxy proxy = new ViewerProxy();
		Resource.setProxy(id, proxy);
		assertEquals(proxy, Resource.getProxy(id));
		
		Resource.rmProxy(id);
		assertNull(Resource.getProxy(id));
		
	}
=======
	
>>>>>>> origin/Server
}
