package br.com.projeto.aprendizado;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.aprendizado.converters.NumberConverter;
import br.com.projeto.aprendizado.exceptions.UnsupportedMathOperationException;
import br.com.projeto.aprendizado.math.SimpleMath;

@RestController
public class MathController {

	private final AtomicLong counter = new AtomicLong();

	private SimpleMath math = new SimpleMath();

	@RequestMapping(value = "/{operation}/{numberOne}/{numberTwo}", method = RequestMethod.GET)
	public Double calculate(@PathVariable(value = "operation") String operation,
							@PathVariable(value = "numberOne") String numberOne,
							@PathVariable(value = "numberTwo") String numberTwo) throws Exception {

		switch (operation.toLowerCase()) {
		case "sum": {
			if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) {
				throw new UnsupportedMathOperationException("Please set a numeric value!");
			}
			return math.sum(NumberConverter.convertToDouble(numberOne), NumberConverter.convertToDouble(numberTwo));
		}
		case "subtract": {
			if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) {
				throw new UnsupportedMathOperationException("Please set a numeric value!");
			}
			return math.subtraction(NumberConverter.convertToDouble(numberOne),
					NumberConverter.convertToDouble(numberTwo));
		}
		case "multiply": {
			if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) {
				throw new UnsupportedMathOperationException("Please set a numeric value!");
			}
			return math.multiplication(NumberConverter.convertToDouble(numberOne),
					NumberConverter.convertToDouble(numberTwo));
		}
		case "divide": {
			if (!NumberConverter.isNumeric(numberOne) || !NumberConverter.isNumeric(numberTwo)) {
				throw new UnsupportedMathOperationException("Please set a numeric value!");
			}
			if (Double.valueOf(numberOne) == 0 || Double.valueOf(numberTwo) == 0) {
				throw new UnsupportedMathOperationException("Division by zero is not allowed!");
			}
			return math.division(NumberConverter.convertToDouble(numberOne),
					NumberConverter.convertToDouble(numberTwo));
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + operation.toLowerCase());
		}
	}

	// Rota separada para a raiz quadrada
	@RequestMapping(value = "/sqrt/{number}", method = RequestMethod.GET)
	public Double squareRoot(@PathVariable(value = "number") String number) throws Exception {
		if (!NumberConverter.isNumeric(number)) {
			throw new UnsupportedMathOperationException("Please set a numeric value!");
		}
		if (Double.valueOf(number) < 0) {
			throw new UnsupportedMathOperationException("Cannot calculate the square root of a negative number!");
		}
		return math.squareRoot(NumberConverter.convertToDouble(number));
	}

}
