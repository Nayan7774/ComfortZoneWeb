
package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.HeroForm;

public interface HeroFormRepository extends JpaRepository<HeroForm , Long> {

}
