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
	public void testThread()
	{
		String id = "id";
		ServerThread thread = new ServerThread(new Socket(), id);
		Resource.setThread(id, thread);
		assertEquals(thread, Resource.getThread(id));
	}
	
	@Test
	public void testOnline()
	{
		String id = "id";
		ServerThread thread = new ServerThread(new Socket(), id);
		Resource.setThread(id, thread);
		Set<String> set = Resource.getOnlineUsers();
		assertEquals(set.size(), 1);
	}
	
	@Test
	public void testThreadRm()
	{
		String id = "id";
		ServerThread thread = new ServerThread(new Socket(), id);
		Resource.setThread(id, thread);		
		Resource.rmThread(id);
		assertNull(Resource.getThread(id));
	}
}
