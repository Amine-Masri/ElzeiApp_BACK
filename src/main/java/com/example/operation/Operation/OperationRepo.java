package com.example.operation.Operation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


	@Repository
	public interface OperationRepo extends JpaRepository<Operation, Long> {
	    List<Operation> findByStatus(String status);
	    List<Operation> findByType(String type);

	}
