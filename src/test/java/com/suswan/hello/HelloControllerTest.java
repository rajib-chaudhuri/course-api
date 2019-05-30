package com.suswan.hello;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@WebMvcTest(value = HelloController.class, secure = false)
public class HelloControllerTest {

	@Test
	public void getAllTopicsTest() throws Exception {

		String expected = "All topics";
		String ss = "All topics";

		JSONAssert.assertEquals(expected, ss, false);
	}
}
