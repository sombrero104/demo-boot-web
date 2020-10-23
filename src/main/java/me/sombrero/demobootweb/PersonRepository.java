package me.sombrero.demobootweb;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> { // Long은 키값인 ID의 타입.
}
