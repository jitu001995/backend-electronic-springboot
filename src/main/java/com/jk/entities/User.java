package com.jk.entities;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class User {
	
	@Id
	//@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String userId;
	@Column(name="user_name")
	private String name;
	@Column(name="user_email")
	private String email;
	@Column(name="user_password")
	private String password;
	private String gender;
	@Column(length=1000)
	private String about;
	@Column(name="user_image_name")
	private String imageName;
	
	@ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	private Set<Role> roles=new HashSet<>();
	
//	@Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Set<SimpleGrantedAuthority> authorities = this.roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toSet());
//	
//		return authorities;
//    }
//
//    @Override
//    public String getUsername() {
//        return this.email;
//    }
//
//    @Override
//    public String getPassword() {
//        return this.password;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
	

}
