package com.currency.exchange.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.currency.exchange.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
	
	public default void currencyExchange() {
		
	}
	
}

