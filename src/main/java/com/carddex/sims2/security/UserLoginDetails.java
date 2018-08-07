package com.carddex.sims2.security;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonInclude;




public class UserLoginDetails {

	private String status; // статус операции
	private String description; // описание статуса
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private UserLoginResponse response;

	public UserLoginDetails(String status, String description, Long userId, String username, String password,
			boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<Long> roles, Collection<Long> authorities) {
		this.status = status;
		this.description = description;
		this.response = new UserLoginResponse(userId, username, enabled ? 0 : 1, 1, roles, authorities);
	}

	public UserLoginDetails(String status, String description) {
		this.status = status;
		this.description = description;
	}

	public UserLoginResponse getResponse() {
		return response;
	}

	public void setResponse(UserLoginResponse response) {
		this.response = response;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	class UserLoginResponse {
		private Long id; // идпользователя
		private String username; // имя пользователя
		private Integer blocked; // аккаунт заблокирован
		private Integer registerDate; // дата регистрации аккаунта
		private Collection<Long> roles; // ид ролей пользователя
		private Collection<Long> permissions; // ид разрешений

		public UserLoginResponse(Long id, String username, int blocked, int registerDate, Collection<Long> roles,
				Collection<Long> authorities) {
			this.id = id;
			this.username = username;
			this.blocked = blocked;
			this.registerDate = registerDate;
			this.roles = roles;
			this.permissions = authorities;

		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public Integer getBlocked() {
			return blocked;
		}

		public void setBlocked(Integer blocked) {
			this.blocked = blocked;
		}

		public Integer getRegisterDate() {
			return registerDate;
		}

		public void setRegisterDate(Integer registerDate) {
			this.registerDate = registerDate;
		}

		public Collection<Long> getRoles() {
			return roles;
		}

		public void setRoles(Collection<Long> roles) {
			this.roles = roles;
		}

		public Collection<Long> getPermissions() {
			return permissions;
		}

		public void setPermissions(Collection<Long> permissions) {
			this.permissions = permissions;
		}
	}
}
