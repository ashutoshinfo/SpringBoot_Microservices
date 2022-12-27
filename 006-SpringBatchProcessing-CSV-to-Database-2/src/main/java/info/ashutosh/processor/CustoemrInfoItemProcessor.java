package info.ashutosh.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import info.ashutosh.entity.Customer;

@Component
public class CustoemrInfoItemProcessor implements ItemProcessor<Customer, Customer> {

	@Override
	public Customer process(Customer customer) throws Exception {
		System.out.println("CustoemrInfoItemProcessor.process()");
		return customer;
	}

}
