package br.com.erudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.erudio.exceptions.UnsupportedMathOperationException;

@Service
public class MathService {

    @Autowired
    private MathValidateService service;

    @Autowired
    private MathOperations operations;

    public Double sum(String numberOne, String numberTwo){
        if(!service.isNumeric(numberOne) || !service.isNumeric(numberTwo)){
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        }
        return operations.makeSum(service.convertToDouble(numberOne),service.convertToDouble(numberTwo));
    }

    public Double subtraction(String numberOne, String numberTwo){
        if(!service.isNumeric(numberOne) || !service.isNumeric(numberTwo)){
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        }
        return operations.makeSubtraction(service.convertToDouble(numberOne),service.convertToDouble(numberTwo));
    }

    public Double multiplication(String numberOne, String numberTwo){
        if(!service.isNumeric(numberOne) || !service.isNumeric(numberTwo)){
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        }
        return operations.makeMultiplication(service.convertToDouble(numberOne),service.convertToDouble(numberTwo));
    }

    public Double divide(String numberOne, String numberTwo){
        if(!service.isNumeric(numberOne) || !service.isNumeric(numberTwo)){
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        }
        return operations.makeDivision(service.convertToDouble(numberOne),service.convertToDouble(numberTwo));
    }

    public Double mean(String numberOne, String numberTwo){
        if(!service.isNumeric(numberOne) || !service.isNumeric(numberTwo)){
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        }
        return operations.makeMean(service.convertToDouble(numberOne),service.convertToDouble(numberTwo));
    }
    
    public Double square(String number){
        if(!service.isNumeric(number)){
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        }
        return operations.makeSquare(service.convertToDouble(number));
    }
}
