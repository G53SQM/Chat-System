package common.test;

import static org.junit.Assert.*;

import org.junit.Test;

import common.Message;
import common.MessageType;

public class TestMessage 
{
	
	@Test
	public void testAll()
	{
		Message message = new Message();
		message.setMsgTypye(MessageType.MSG_PLAIN);
		message.setSrcUser("src");
		message.setDestUser("dest");
		message.setContent("This is the content of the testing");
		message.setTime("10:30");
		
		assertEquals(MessageType.MSG_PLAIN, message.getMsgType());
		assertEquals("src", message.getSrcUser());
		assertEquals("dest", message.getDestUser());
		assertEquals("This is the content of the testing", message.getContent());
		assertEquals("10:30", message.getTime());
	}	
	
}
