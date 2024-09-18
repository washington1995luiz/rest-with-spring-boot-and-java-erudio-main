package br.com.erudio.service;

import org.springframework.stereotype.Service;

@Service
public class MathOperations {

    public Double makeSum(Double first, Double second){
        return first + second;
    }

    public Double makeSubtraction(Double first, Double second){
        return first - second;
    }

    public Double makeMultiplication(Double first, Double second){
        return first * second;
    }

    public Double makeDivision(Double first, Double second){
        return first / second;
    }

    public Double makeMean(Double first, Double second){
        return (first + second) / 2;
    }

    public Double makeSquare(Double value){
        return Math.sqrt(value);
    }
}
