package com.currency.exchange;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.currency.exchange.controller.CurrencyExchangeRatesServiceClient;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
//@RunWith(MockitoJUnitRunner.class)
public class CurrencyExchangeRatesTest {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CurrencyExchangeRatesServiceClient client;
	
	private MockRestServiceServer mockServer;
	
	@BeforeEach
	void init() {
		mockServer = MockRestServiceServer.createServer(restTemplate);
	}
	
	@Test
	public void testCurrencyExchangeRates() throws URISyntaxException, IOException {
		
		Path p = Paths.get("src\\test\\resources\\sampleRates.txt");
		
		String strRates = Files.readString(p.toAbsolutePath());
		
		mockServer.expect(requestTo(new URI("https://api.currencyfreaks.com/latest?apikey=4cedff46aa1641adaef9c321bcc52c1f")))
		.andExpect(method(HttpMethod.GET))
		.andRespond(withStatus(HttpStatus.OK)
				.contentType(new MediaType("text", "xml"))
				.body(strRates));
				
		client.getExchangeRates();
		mockServer.verify();
	}
	
}
