package br.edu.atitus.apisample.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.atitus.apisample.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, UUID>{

	boolean existsByEmail(String email);
	
	boolean existsByEmailAndIdNot(String email, UUID id);
	
	Optional<UserEntity> findByEmail(String email);
}
