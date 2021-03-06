package br.com.caelum.vraptor.simplevalidator;

import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.DEFAULT_BIGGER_THAN_KEY;
import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.DEFAULT_LESS_THAN_KEY;
import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.and;
import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.biggerThan;
import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.lessThan;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AndValidationStrategyTest extends SimpleValidatorTestBase{

	private SimpleValidationStrategy<Long> lessThan2;
	private SimpleValidationStrategy<Long> biggerThan0;

	@Before
	public void setup() {
		lessThan2 = lessThan(2l);
		biggerThan0 = biggerThan(0l);
	}
	
	@Test
	public void should_not_add_errors_if_everyting_goes_fine() {
		validator.validate(1l, and(lessThan2, biggerThan0));
		verify(validationStrategyHelper, never()).addError(anyString());
	}
	
	@Test
	public void should_not_add_two_errors_to_and_rule() {
		SimpleValidationStrategy<Long> biggerThan2 = biggerThan(2l);
		SimpleValidationStrategy<Long> lessThan0 = lessThan(0l);
		long number = 1l;
		validator.validate(number, and(lessThan0, biggerThan2));
		verify(validationStrategyHelper, times(1)).addError(DEFAULT_LESS_THAN_KEY, 0l);
		verify(validationStrategyHelper, times(1)).addError(DEFAULT_BIGGER_THAN_KEY, 2l);
	}

}
