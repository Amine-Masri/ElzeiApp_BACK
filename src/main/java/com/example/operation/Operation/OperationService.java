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
	
	@Override
	public Operation update(Long id, Operation updatedOperation) {
	    Operation existingOperation = findById(id);
	    if (existingOperation != null) {
	        if (updatedOperation.getDescription() != null) {
	            existingOperation.setDescription(updatedOperation.getDescription());
	        }

	        if (updatedOperation.getTauxTVA() != 0.0) {
	            existingOperation.setTauxTVA(updatedOperation.getTauxTVA());
	        }

	        if (updatedOperation.getTVAdeductible() != null) {
	            existingOperation.setTVAdeductible(updatedOperation.getTVAdeductible());
	        }

	        // Ajoutez des conditions similaires pour les autres champs que vous souhaitez mettre à jour

	        return operationRepo.save(existingOperation);
	    }
	    return null;
	}


	@Override
	public Operation save(Operation operation) {
		operationRepo.save(operation);
        return operation;
	}
	
	
}




    

