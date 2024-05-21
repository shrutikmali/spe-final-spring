package com.example.demo;


import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.example.demo.models.Request;
import com.example.demo.services.Services;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private Services services;

	@Test
	public void testGetTextSuccess() throws IOException {
		Request mockRequest = Mockito.mock(Request.class);
		Mockito.when(mockRequest.getSaveImage()).thenReturn(true);
		String base64Image = "data:image/png;base64,R0lGODlhAQABAAAAACwAAAAAAQABAAA=";
		Mockito.when(mockRequest.getImg()).thenReturn(base64Image);
		
		// Expect webclient stack trace during testing, flask server will not be running
		services.getText(mockRequest);
	}

	@Test
	public void testSaveImageSuccess() {
		Request mockRequest = Mockito.mock(Request.class);
		String base64Image = "data:image/png;base64,R0lGODlhAQABAAAAACwAAAAAAQABAAA=";
		Mockito.when(mockRequest.getImg()).thenReturn(base64Image);

		boolean saved = services.saveImage(mockRequest);

		Assert.isTrue(saved, "Image not saved");
	}

	@Test
	public void testSaveImageFailIncorrectBaseString() {
		Request mockRequest = Mockito.mock(Request.class);
		String base64Image = "data:image/png;base64R0lGODlhAQABAAAAACwAAAAAAQABAAA=";
		Mockito.when(mockRequest.getImg()).thenReturn(base64Image);

		boolean saved = services.saveImage(mockRequest);

		Assert.isTrue(!saved, "Image not saved");
	}

}
