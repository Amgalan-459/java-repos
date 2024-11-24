package ru.itstep;

import java.util.List;

public interface UserDtoService {
	UserDto save(UserDto u);
	Iterable<UserDto>  findAll();
	List<UserDto>  findByFirstName(String firstName);
	List<UserDto> findByEmail(String email);
}
