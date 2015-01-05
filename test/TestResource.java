package server.tools.test;

import static org.junit.Assert.*;

import java.net.Socket;
import java.util.Set;

import org.junit.Test;

import server.tools.Resource;
import server.tools.ServerThread;



public class TestResource 
{
	
	@Test
	public void testTread()
	{
		String id = "id";
		ServerThread thread = new ServerThread(new Socket(), id);
		Resource.setThread(id, thread);
		assertEquals(thread, Resource.getThread(id));
		
		Set<String> set = Resource.getOnlineUsers();
		assertEquals(set.size(), 1);
		
		Resource.rmThread(id);
		assertNull(Resource.getThread(id));
	}
	
	
}
