package common.test;

import static org.junit.Assert.*;

import org.junit.Test;

import common.User;

public class TestUser
{
	
	@Test
	public void testUser()
	{
		User user = new User("name", "pass");
		assertEquals("name", user.getID());
		assertEquals("pass", user.getPassword());
	}
	
	@Test
	public void testUserModify()
	{
		User user = new User("name", "pass");
		user.setID("name2");
		user.setPassword("pass2");
		assertEquals("name2", user.getID());
		assertEquals("pass2", user.getPassword());
	}
	
	@Test
	public void testUserModify2()
	{
		User user = new User("name", "pass");
		user.setPassword("pass2");
		assertEquals("name", user.getID());
		assertEquals("pass2", user.getPassword());
	}
	
	@Test
	public void testUserModify3()
	{
		User user = new User("name", "pass");
		user.setID("name2");
		assertEquals("name2", user.getID());
		assertEquals("pass", user.getPassword());
	}
	
	@Test
	public void testUserDefault()
	{
		User user = new User();
		assertEquals("", user.getID());
		assertEquals("", user.getPassword());
	}
}
