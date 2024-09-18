package br.com.erudio.controllers.v1;


import br.com.erudio.data.vo.v1.security.AccountCredentialsVO;
import br.com.erudio.service.v1.AuthServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication EndPoint")
public class AuthController {

    @Autowired
    AuthServices authServices;

    @SuppressWarnings("rawtypes")
    @Operation(summary = "Authenticates a user and return a token")
    @PostMapping(value = "/signin")
    public ResponseEntity signin(@RequestBody AccountCredentialsVO data){
        if(checkIfParamsIsNotNull(data)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        var token = authServices.signin(data);
        if(token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        return token;
    }

    @SuppressWarnings("rawtypes")
    @Operation(summary = "Refresh token for authenticate user and return a token")
    @PutMapping(value = "/refresh/{username}")
    public ResponseEntity refreshToken(@PathVariable("username") String username, @RequestHeader("Authorization") String refreshToken){
        if(checkIfParamsIsNotNull(refreshToken, username)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        var token = authServices.refreshToken(username, refreshToken);
        if(token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        return token;
    }
    private boolean checkIfParamsIsNotNull(String refreshToken, String username){
        return  refreshToken == null || refreshToken.isBlank() || username == null || username.isBlank();
    }
    private boolean checkIfParamsIsNotNull(AccountCredentialsVO data){
        return data == null || data.getUsername() == null || data.getUsername().isBlank()
                || data.getPassword() == null || data.getPassword().isBlank();
    }
}
