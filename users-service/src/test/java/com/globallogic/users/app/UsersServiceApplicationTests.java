package com.globallogic.users.app;


import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.Assert;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globallogic.users.bean.ErrorMessage;
import com.globallogic.users.bean.Phone;
import com.globallogic.users.bean.RegisterUserMessage;
import com.globallogic.users.bean.User;

import lombok.extern.slf4j.Slf4j;


@SpringBootTest
@AutoConfigureMockMvc
@EnableAutoConfiguration
@Slf4j
class UsersServiceApplicationTests {

	@Autowired
	private MockMvc mvc;

	/**
	 * Se prueba status HTTP 400 Bad Request por error de validación al pasar un campo 'email' de longitud 0.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test0001() throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();

		Phone phone = new Phone();
		phone.setCitycode("2");
		phone.setCountrycode("56");
		phone.setNumber("4343434");

		User user = new User();
		user.setEmail("");
		user.setName("Marco Mora");
		user.setPassword("Addfd12");
		user.setEmail(null);
		user.addPhone(phone);

		String response = mvc
				.perform(MockMvcRequestBuilders.post("/users/register").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(user)).header("Authorization", "Bearer token"))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn().getResponse()
				.getContentAsString();

		ErrorMessage errorMessage = objectMapper.readValue(response, ErrorMessage.class);

		Assert.isTrue(errorMessage.getMensaje().contains("El campo 'email' es obligatorio"), "test0001 NOK");
	}

	/**
	 * Se prueba status HTTP 400 Bad Request por error de validación al pasar un campo 'email' nulo.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test0002() throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();

		Phone phone = new Phone();
		phone.setCitycode("2");
		phone.setCountrycode("56");
		phone.setNumber("4343434");

		User user = new User();
		user.setName("Marco Mora");
		user.setPassword("Addfd12");
		user.setEmail(null);
		user.addPhone(phone);

		String response = mvc
				.perform(MockMvcRequestBuilders.post("/users/register").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(user)).header("Authorization", "Bearer token"))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn().getResponse()
				.getContentAsString();

		ErrorMessage errorMessage = objectMapper.readValue(response, ErrorMessage.class);

		Assert.isTrue(errorMessage.getMensaje().contains("El campo 'email' es obligatorio"), "test0002 NOK");
	}

	/**
	 * Se prueba status HTTP 400 Bad Request por error de validación al pasar un campo 'email' de largo mayor a 25.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test0003() throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();

		Phone phone = new Phone();
		phone.setCitycode("2");
		phone.setCountrycode("56");
		phone.setNumber("4343434");

		User user = new User();
		user.setName("Marco Mora");
		user.setPassword("Addfd12");
		user.setEmail("fsdfffffffffffffffffffffgfdgdfgdffffffffffffffffffffffffffffffffhgfhfghgf");
		user.addPhone(phone);

		String response = mvc
				.perform(MockMvcRequestBuilders.post("/users/register").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(user)).header("Authorization", "Bearer token"))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn().getResponse()
				.getContentAsString(Charset.forName("UTF-8"));

		ErrorMessage errorMessage = objectMapper.readValue(response, ErrorMessage.class);

		Assert.isTrue(errorMessage.getMensaje().contains("El largo máximo del campo 'email' es 25"), "test0003 NOK");
	}

	/**
	 * Se prueba status HTTP 400 Bad Request por error de validación al pasar un campo 'email' de formato inválido.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test0004() throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();

		Phone phone = new Phone();
		phone.setCitycode("2");
		phone.setCountrycode("56");
		phone.setNumber("4343434");

		User user = new User();
		user.setName("Marco Mora");
		user.setPassword("Addfd12");
		user.setEmail("efdsf");
		user.addPhone(phone);

		String response = mvc
				.perform(MockMvcRequestBuilders.post("/users/register").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(user)).header("Authorization", "Bearer token"))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn().getResponse()
				.getContentAsString(Charset.forName("UTF-8"));

		ErrorMessage errorMessage = objectMapper.readValue(response, ErrorMessage.class);

		Assert.isTrue(errorMessage.getMensaje().contains("El formato del campo 'email' es inválido"), "test0004 NOK");
	}

	/**
	 * Se prueba status HTTP 400 Bad Request por error de validación al pasar un campo 'password' de formato inválido.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test0005() throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();

		Phone phone = new Phone();
		phone.setCitycode("2");
		phone.setCountrycode("56");
		phone.setNumber("4343434");

		User user = new User();
		user.setName("Marco Mora");
		user.setPassword("Addfd124");
		user.setEmail("morilla777@gmail.com");
		user.addPhone(phone);

		String response = mvc
				.perform(MockMvcRequestBuilders.post("/users/register").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(user)).header("Authorization", "Bearer token"))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn().getResponse()
				.getContentAsString(Charset.forName("UTF-8"));

		ErrorMessage errorMessage = objectMapper.readValue(response, ErrorMessage.class);

		Assert.isTrue(errorMessage.getMensaje().contains("El formato del campo 'password' es inválido"),
				"test0005 NOK");
	}
	
	/**
	 * Se prueba status HTTP 400 Bad Request por error de validación al no pasar el campo 'password'.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test0006() throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();

		Phone phone = new Phone();
		phone.setCitycode("2");
		phone.setCountrycode("56");
		phone.setNumber("4343434");

		User user = new User();
		user.setName("Marco Mora");
		user.setEmail("morilla777@gmail.com");
		user.addPhone(phone);

		String response = mvc
				.perform(MockMvcRequestBuilders.post("/users/register").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(user)).header("Authorization", "Bearer token"))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn().getResponse()
				.getContentAsString(Charset.forName("UTF-8"));

		ErrorMessage errorMessage = objectMapper.readValue(response, ErrorMessage.class);

		Assert.isTrue(errorMessage.getMensaje().contains("El campo 'password' es obligatorio"),
				"test0006 NOK");
	}
	
	/**
	 * Se prueba status HTTP 400 Bad Request por error de validación al pasar un campo 'password' de largo mayor a 10.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test0007() throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();

		Phone phone = new Phone();
		phone.setCitycode("2");
		phone.setCountrycode("56");
		phone.setNumber("4343434");

		User user = new User();
		user.setName("Marco Mora");
		user.setPassword("fsddddddddddddddfsdfdsfdsdsf");
		user.setEmail("morilla777@gmail.com");
		user.addPhone(phone);

		String response = mvc
				.perform(MockMvcRequestBuilders.post("/users/register").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(user)).header("Authorization", "Bearer token"))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn().getResponse()
				.getContentAsString(Charset.forName("UTF-8"));

		ErrorMessage errorMessage = objectMapper.readValue(response, ErrorMessage.class);

		Assert.isTrue(errorMessage.getMensaje().contains("El largo máximo del campo 'password' es 10"),
				"test0007 NOK");
	}

	/**
	 * Se prueba status HTTP 400 Bad Request por error al pasar un campo 'email' ya registrado.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test0008() throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();

		Phone phone = new Phone();
		phone.setCitycode("2");
		phone.setCountrycode("56");
		phone.setNumber("4343434");

		User user = new User();
		user.setName("Marco Mora");
		user.setPassword("Addfd12");
		user.setEmail("morilla777@gmail.com");
		user.addPhone(phone);

		mvc.perform(MockMvcRequestBuilders.post("/users/register").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user)).header("Authorization", "Bearer token")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn().getResponse().getContentAsString(Charset.forName("UTF-8"));

		String response = mvc
				.perform(MockMvcRequestBuilders.post("/users/register").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(user)).header("Authorization", "Bearer token"))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn().getResponse()
				.getContentAsString(Charset.forName("UTF-8"));

		ErrorMessage errorMessage = objectMapper.readValue(response, ErrorMessage.class);

		Assert.isTrue(errorMessage.getMensaje().contains("El correo ya registrado"), "test0008 NOK");
	}
	
	
	/**
	 * Se prueba status HTTP 200 OK al registrar un usuario sin problemas de validación y se compara que el usuario
	 * obtenido desde la respuesta sea igual al ingresado.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test0009() throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();

		Phone phone = new Phone();
		phone.setCitycode("2");
		phone.setCountrycode("56");
		phone.setNumber("4343434");

		User user = new User();
		user.setName("Juan Pérez");
		user.setPassword("Bddfd21");
		user.setEmail("jperez@gmail.com");
		user.addPhone(phone);

		String response = mvc.perform(MockMvcRequestBuilders.post("/users/register").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user)).header("Authorization", "Bearer token")).andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn().getResponse().getContentAsString(Charset.forName("UTF-8"));

		RegisterUserMessage registerUserMessage = objectMapper.readValue(response, RegisterUserMessage.class);
		
		log.debug(user.toString());
		log.debug(registerUserMessage.getUser().toString());

		Assert.isTrue(user.equals(registerUserMessage.getUser()), "test0009 NOK");
	}
	
	/**
	 * Se prueba status HTTP 401 Unauthorized al pasar un token inválido dentro de la cabecera 'Authorization'.
	 * 
	 * @throws Exception
	 */
	@Test
	public void test0010() throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();

		Phone phone = new Phone();
		phone.setCitycode("2");
		phone.setCountrycode("56");
		phone.setNumber("4343434");

		User user = new User();
		user.setName("Juan Pérez");
		user.setPassword("Bddfd21");
		user.setEmail("jperez@gmail.com");
		user.addPhone(phone);

		String response = mvc.perform(MockMvcRequestBuilders.post("/users/register").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user)).header("Authorization", "bad token")).andExpect(MockMvcResultMatchers.status().isUnauthorized())
				.andReturn().getResponse().getContentAsString(Charset.forName("UTF-8"));

		ErrorMessage errorMessage = objectMapper.readValue(response, ErrorMessage.class);

		Assert.isTrue(errorMessage.getMensaje().contains("El token no se pudo obtener"), "test0010 NOK");
	}

}
