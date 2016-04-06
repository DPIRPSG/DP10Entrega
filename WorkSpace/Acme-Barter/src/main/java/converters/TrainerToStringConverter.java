package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Auditor;

@Component
@Transactional
public class TrainerToStringConverter implements Converter<Auditor, String> {
	
	@Override
	public String convert(Auditor trainer) {
		String result;

		if (trainer == null)
			result = null;
		else
			result = String.valueOf(trainer.getId());

		return result;
	}

}