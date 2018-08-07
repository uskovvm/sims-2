package com.carddex.sims2.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.carddex.sims2.security.UserSecurityDetailsService;
import com.carddex.sims2.security.UserLoginDetails;
import com.carddex.sims2.service.IUserService;

@RestController
public class AuController {

	@Autowired
	UserSecurityDetailsService userDetailsService;

	@Autowired
	IUserService userService;

	/**
	 * Войти в систему 
	 * POST /core/api/login 
	 * Параметры:{ username: [string], // логин
	 * password: [string] // пароль }
	 * 
	 * Возвращаемое значение: 
	 * В случае успеха: 
	 * { 
	 * status: 'success', // статус операции 
	 * description: 'Login Success', // описание статуса 
	 * response: { //информация о залогинном пользователе 
	 *		id: [number], // ид пользователя
	 *  	username: [string], // имя пользователя 
	 *  	blocked: [number], // аккаунт заблокирован 
	 *  	registerDate: [number], // дата регистрации аккаунта 
	 *  	roles: [
	 *    		[...number] // ид ролей пользователя 
	 *  	],
	 *  	permissions: [ // права пользователя
	 *    		[...number] // ид разрешений 
	 *  	]}}
	 */

	@RequestMapping("/core/api/login")
	public UserLoginDetails login(@RequestParam(value = "name", defaultValue = "World") String name) {
		UserLoginDetails userDetails = userDetailsService.restLoadUserByUsername("test@test.com");
		//UserLoginDetails userDetails = userDetailsService.restLoadUserByUsername("t@test.com");
		
		return userDetails;
	}
}