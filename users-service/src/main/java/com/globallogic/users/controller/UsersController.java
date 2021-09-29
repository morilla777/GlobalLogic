package com.globallogic.users.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.globallogic.users.bean.ErrorMessage;
import com.globallogic.users.bean.RegisterUserMessage;
import com.globallogic.users.bean.User;
import com.globallogic.users.common.MessagesEnum;
import com.globallogic.users.exception.RegisteredEmailException;
import com.globallogic.users.exception.TokenNotFoundException;
import com.globallogic.users.service.UserService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/users")
@OpenAPIDefinition(info = @Info(title = "users-service", description = "Servicios REST de Usuarios"))
@Slf4j
public class UsersController {

	private static final String SPACE = " ";
	private static final String BEARER = "Bearer";

	@Autowired
	private UserService userService;

	@GetMapping(value = "/health", produces = "application/json")
	@Operation(summary = "Chequea la salud del servicio")
	public @ResponseBody ResponseEntity<Object> health() {
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}

	@PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
	@Operation(summary = "Registra un Usuario")
	public @ResponseBody ResponseEntity<Object> register(@RequestHeader("Authorization") String authorizationHeader,@Valid @RequestBody User user)
			throws TokenNotFoundException, RegisteredEmailException {

		final String token = getToken(authorizationHeader);
		RegisterUserMessage registerUserMessage = userService.register(user, token);
		return new ResponseEntity<>(registerUserMessage, HttpStatus.OK);

	}

	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(TokenNotFoundException.class)
	public ErrorMessage handleException(TokenNotFoundException ex) {
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setMensaje(ex.getMessage());
		log.error(errorMessage.getMensaje());
		return errorMessage;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(RegisteredEmailException.class)
	public ErrorMessage handleException(RegisteredEmailException ex) {
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setMensaje(ex.getMessage());
		log.error(errorMessage.getMensaje());
		return errorMessage;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ErrorMessage handleException(MethodArgumentNotValidException ex) {
		List<FieldError> list = ex.getFieldErrors();
		String msgs = "";
		for(FieldError fieldError:list) {
			msgs += fieldError.getDefaultMessage() + ". ";
		}
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setMensaje(msgs);
		log.error(errorMessage.getMensaje());
		return errorMessage;
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MissingRequestHeaderException.class)
	public ErrorMessage handleException(MissingRequestHeaderException ex) {
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setMensaje(ex.getMessage());
		log.error(errorMessage.getMensaje());
		return errorMessage;
	}

	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	@ExceptionHandler(Exception.class)
	public ErrorMessage handleException(Exception ex) {
		ErrorMessage errorMessage = new ErrorMessage();
		errorMessage.setMensaje(ex.getMessage());
		log.error(errorMessage.getMensaje());
		return errorMessage;
	}

	private String getToken(String authorizationHeader) throws TokenNotFoundException {

		try {
			String[] arrayAuthorizationHeader = authorizationHeader.split(SPACE);
			
			if(arrayAuthorizationHeader.length!=2) {
				throw new TokenNotFoundException();
			}

			if (!BEARER.equals(arrayAuthorizationHeader[0])) {
				throw new TokenNotFoundException();
			}

			return arrayAuthorizationHeader[1];
		} catch (Exception ex) {
			throw new TokenNotFoundException(MessagesEnum.MSG_TOKEN_NOT_FOUND.getMessage());
		}
	}

}
