package com.example.in28min.microservices.currency_exchange_service;

import java.lang.System.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import org.slf4j.LoggerFactory;

@RestController
public class CurrencyExchangeController {

@Autowired
public CurrencyExchangeRepository currencyRepository;
	
@Autowired
public Environment environment;
	private org.slf4j.Logger logger=LoggerFactory.getLogger(CurrencyExchangeController.class);
	
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange retrieveCurrency(@PathVariable String from,@PathVariable String to)
	{
		
		logger.info("value of from and to",from,to);
		
		//CurrencyExchange currencyExchange = new CurrencyExchange(1000L,from,to,BigDecimal.valueOf(50));
		CurrencyExchange currencyExchange = currencyRepository.findByFromAndTo(from, to);
		if(currencyExchange==null)
		{
			throw new RuntimeException("Unable to get data from "+from+" to "+to);
		}
		String port = environment.getProperty("server.port");
		currencyExchange.setEnvironment(port);
		
		return currencyExchange;
}
}
