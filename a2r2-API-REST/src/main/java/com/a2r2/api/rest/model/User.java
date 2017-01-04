package com.a2r2.api.rest.model;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.userdetails.UserDetails;

import com.a2r2.api.rest.model.serializer.UserAuthorityArrayJsonDeserializer;
import com.a2r2.api.rest.model.serializer.UserAuthorityArrayJsonSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Table(name="users")
@Entity
public class User implements UserDetails {

	private static final long serialVersionUID = 1L;

	@JsonProperty
	@Id
	private String username;

	@Column(nullable = false)
	private String password;

	@JsonProperty
	@NotNull
	@Column(nullable = false)
	private String name;

	@JsonProperty
	@NotNull
	@Column(nullable = false)
	private String surname;

	@Column(nullable = true)
	private String avatarUrl;

	@Transient
	private long expires;

	@JsonIgnore
	@NotNull
	private boolean accountExpired;

	@JsonIgnore
	@NotNull
	private boolean accountLocked;

	@JsonIgnore
	@NotNull
	private boolean credentialsExpired;

	@NotNull
	private boolean enabled;

	@Transient
	private String newPassword;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<UserAuthority> authorities;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	@JsonIgnore
	public String getNewPassword() {
		return newPassword;
	}

	@JsonProperty
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@JsonSerialize(using = UserAuthorityArrayJsonSerializer.class)
	@Override
	public Set<UserAuthority> getAuthorities() {
		return authorities;
	}

	@JsonDeserialize(using = UserAuthorityArrayJsonDeserializer.class)
	public void setAuthorities(Set<UserAuthority> authorities) {
		this.authorities = authorities;
		for (UserAuthority authority : authorities) {
			authority.setUser(this);
		}
	}

	// Use Roles as external API
	@JsonIgnore
	public Set<UserRole> getRoles() {
		Set<UserRole> roles = EnumSet.noneOf(UserRole.class);
		if (authorities != null) {
			for (UserAuthority authority : authorities) {
				roles.add(UserRole.valueOf(authority));
			}
		}
		return roles;
	}

//	 @JsonDeserialize(using = UserRolesArrayJsonDeserializer.class)
	@JsonIgnore
	public void setRoles(Set<UserRole> roles) {
		for (UserRole role : roles) {
			grantRole(role);
		}
	}

	@JsonIgnore
	public void grantRole(UserRole role) {
		if (authorities == null) {
			authorities = new HashSet<UserAuthority>();
		}
		authorities.add(role.asAuthorityFor(this));
	}

	@JsonIgnore
	public void revokeRole(UserRole role) {
		if (authorities != null) {
			authorities.remove(role.asAuthorityFor(this));
		}
	}

	@JsonIgnore
	public boolean hasRole(UserRole role) {
		return authorities.contains(role.asAuthorityFor(this));
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return !accountExpired;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return !accountLocked;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return !credentialsExpired;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return enabled;
	}

	public long getExpires() {
		return expires;
	}

	public void setExpires(long expires) {
		this.expires = expires;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
