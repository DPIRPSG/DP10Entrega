package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.User;

@Component
@Transactional
public class CustomerToStringConverter implements Converter<User, String> {
	
	@Override
	public String convert(User customer) {
		String result;

		if (customer == null)
			result = null;
		else
			result = String.valueOf(customer.getId());

		return result;
	}

}