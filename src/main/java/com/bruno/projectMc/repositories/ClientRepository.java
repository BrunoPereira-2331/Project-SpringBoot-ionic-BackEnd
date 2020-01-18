package com.bruno.projectMc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bruno.projectMc.domain.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer>{

}
