package com.example.operation.Operation;

import java.util.List;


public interface OpService {
    List<Operation> findAll();
    List<Operation> findByStatus(String status);
    List<Operation> findByType(String type);
    Operation findById(Long id);
	Operation update(Long id, Operation updatedOperation);
	Operation save(Operation operation);


 	
}