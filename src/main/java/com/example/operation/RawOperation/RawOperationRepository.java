package com.example.operation.RawOperation;

import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RawOperationRepository extends JpaRepository<RawOperation, Integer> {
    // You can add custom query methods here if needed
	 List<RawOperation> findByDate(String date);
	    
	    // You can add more query methods based on your needs
	    
	    // Example: Find operations by type
	    List<RawOperation> findByType(String type);

	    // Example: Find operations by libelle containing a certain string
	    List<RawOperation> findByLibelleContaining(String keyword);
}
