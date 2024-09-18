package br.com.erudio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.erudio.service.MathService;

@RestController
public class MathController {

    @Autowired
    private MathService service;

    @RequestMapping(value = "/sum/{numberOne}/{numberTwo}", method=RequestMethod.GET)
    public ResponseEntity<Double> sum(@PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo) throws Exception {
        return ResponseEntity.status(200).body(service.sum(numberOne, numberTwo));   
    }

    @RequestMapping(value = "/sub/{numberOne}/{numberTwo}", method=RequestMethod.GET)
    public Double sub(@PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo) throws Exception {
        return service.divide(numberOne, numberTwo);
    }

    @RequestMapping(value = "/mult/{numberOne}/{numberTwo}", method=RequestMethod.GET)
    public Double mult(@PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo) throws Exception {
        return service.multiplication(numberOne, numberTwo);
    }

    @RequestMapping(value = "/div/{numberOne}/{numberTwo}", method=RequestMethod.GET)
    public Double div(@PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo) throws Exception {
        return service.divide(numberOne, numberTwo);
    }

    @RequestMapping(value = "/media/{numberOne}/{numberTwo}", method=RequestMethod.GET)
    public Double media(@PathVariable(value = "numberOne") String numberOne, @PathVariable(value = "numberTwo") String numberTwo) throws Exception {
        return service.mean(numberOne, numberTwo);
    }

    @RequestMapping(value = "/raiz/{numberOne}", method=RequestMethod.GET)
    public Double raiz(@PathVariable(value = "numberOne") String numberOne) throws Exception {
        return service.square(numberOne);
    }

    
}
