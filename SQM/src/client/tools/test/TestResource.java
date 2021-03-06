package client.tools.test;

import static org.junit.Assert.*;

import java.net.Socket;

import org.junit.Test;

import client.tools.ClientThread;
import client.tools.Resource;
import client.view.ViewerProxy;

public class TestResource 
{
	
	@Test
	public void testThread()
	{
		String id = "id";
		ClientThread thread = new ClientThread(new Socket(), id);
		Resource.setThread(id, thread);
		assertEquals(thread, Resource.getThread(id));		
	}
	
	@Test
	public void testThreadRm()
	{
		String id = "id";
		ClientThread thread = new ClientThread(new Socket(), id);
		Resource.setThread(id, thread);		
		Resource.rmThread(id);
		assertNull(Resource.getThread(id));
	}
	
	@Test
	public void testProxy()
	{
		String id = "id";
		ViewerProxy proxy = new ViewerProxy();
		Resource.setProxy(id, proxy);
		assertEquals(proxy, Resource.getProxy(id));
		
	}
	
	@Test
	public void testProxyRm()
	{
		String id = "id";
		ViewerProxy proxy = new ViewerProxy();
		Resource.setProxy(id, proxy);
		Resource.rmProxy(id);
		assertNull(Resource.getProxy(id));
	}
}
