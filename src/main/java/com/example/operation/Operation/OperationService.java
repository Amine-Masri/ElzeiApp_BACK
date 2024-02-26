package com.example.operation.Operation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class OperationService implements OpService {
    @Autowired
    private OperationRepo operationRepo;

    @Override
    public List<Operation> findAll() {
        return operationRepo.findAll();
    }

    @Override
    public List<Operation> findByStatus(String status) {
        return operationRepo.findByStatus(status);
    }

	@Override
	public List<Operation> findByType(String type) {
        return operationRepo.findByType(type);

	}

	@Override
	public Operation findById(Long id) {
		 if (operationRepo.findById(id).isPresent()) {
	            return operationRepo.findById(id).get();
	        }
	        return null;
	}
	 public Operation update(Long id, Operation updatedOperation) {
	        Operation existingoperation = findById(id);
	        if (existingoperation != null) {
	        	existingoperation.setDescription(updatedOperation.getDescription());
	        	existingoperation.setTauxTVA(updatedOperation.getTauxTVA());
	        	existingoperation.setTVAdeductible(updatedOperation.getTVAdeductible());
	            return operationRepo.save(existingoperation);
	        }
	        return null;
	    }

	@Override
	public Operation save(Operation operation) {
		operationRepo.save(operation);
        return operation;
	}
	
	
}




    

