package com.sankha.springboot.firstrestapi.users;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

//@RepositoryRestResource(path = "u")
public interface UserDetailsRestRepository extends PagingAndSortingRepository<UserDetails, Long> {

	List<UserDetails> findByRole(String string);

}
