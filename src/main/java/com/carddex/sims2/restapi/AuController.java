package com.carddex.sims2.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.carddex.sims2.security.UserSecurityDetailsService;
import com.carddex.sims2.security.UserAccount;
import com.carddex.sims2.security.UserLoginDetails;
import com.carddex.sims2.service.IUserService;

@RestController
public class AuController {

	@Autowired
	UserSecurityDetailsService userDetailsService;


	/*-
	 * Войти в систему POST /core/api/login 
	 * Параметры:
	 * { 
	 * 		username: [string], // логин
	 * 		password: [string] // пароль 
	 * }
	 * Возвращаемое значение: 
	 * 	В случае успеха: 
	 * { 
	 * 		status: 'success', // статус операции 
	 * 		description: 'Login Success', // описание статуса
	 * 		response: {//информация о залогинном пользователе
	 * 			id: [number], // ид пользователя
	 * 			username: [string], // имя пользователя 
	 * 			blocked: [number], // аккаунт заблокирован
	 * 			registerDate: [number], // дата регистрации аккаунта
	 * 			roles: [[...number] // ид ролей пользователя ],
	 * 			permissions: [[...number] // ид разрешений ]}}
	 */

	@RequestMapping(value = "/core/api/login", method = RequestMethod.POST)
	@ResponseBody
	public UserLoginDetails login(@RequestBody UserAccount account) {
		String username = account.getUsername();
		UserLoginDetails userDetails = userDetailsService.restLoadUserByUsername(username);

		return userDetails;
	}
}