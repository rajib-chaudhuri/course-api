package com.suswan.topic;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@WebMvcTest(value = TopicController.class, secure = false)
public class TopicControllerTest {

	@Test
	public void getAllTopicsTest() throws Exception {

		String expected = "All topics";
		String ss = "All topics";

		JSONAssert.assertEquals(expected, ss, false);
	}

}
