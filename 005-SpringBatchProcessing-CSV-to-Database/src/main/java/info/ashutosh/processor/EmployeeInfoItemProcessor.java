package info.ashutosh.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import info.ashutosh.model.Employee;

@Component
public class EmployeeInfoItemProcessor implements ItemProcessor<Employee, Employee> {

	@Override
	public Employee process(Employee employee) throws Exception {
		System.out.println("EmployeeInfoItemProcessor.process() - Starts");
		Float e_slary = employee.getE_slary();
		if (e_slary <= 70_000f) {
			System.out.println("EmployeeInfoItemProcessor.process()");
			Float increment_salary = 1_00_000.0F - e_slary;
			e_slary = +e_slary + increment_salary;
			employee.setE_slary(e_slary);

		}

		System.out.println("EmployeeInfoItemProcessor.process() - Ends");
		return employee;
	}

}
