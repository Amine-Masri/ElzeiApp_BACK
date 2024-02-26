package com.example.operation.Operation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/test")
public class OperationController {
    @Autowired
    OpService opService;

    @ResponseBody
    @GetMapping("/operation")
    public List<Operation> getAll(@RequestParam(required = false) String status,
                                  @RequestParam(required = false) String type) {
       if (status != null) {
            // Filtrer  par le statut
            return opService.findByStatus(status);
        } else if (type != null) {
            // Filtrer par le type
            return opService.findByType(type);
        } else {
            // Aucun filtre, retourner toutes les opérations
            return opService.findAll();
        }
    }

    
    @ResponseBody
	@GetMapping("/operation/{id}")
	public Operation get(@PathVariable("id") Long id) {
		return opService.findById(id);

	}
    
    
    
    
    @ResponseBody
    @PutMapping("/operation/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @RequestBody Operation operationData) {
    	Operation existingOperation = opService.findById(id);
        
        if (existingOperation == null) {
            return new ResponseEntity<>("Operation not found", HttpStatus.NOT_FOUND);
        }
        
        existingOperation.setDescription(operationData.getDescription());
        existingOperation.setTauxTVA(operationData.getTauxTVA());
        existingOperation.setTVAdeductible(operationData.getTVAdeductible());
        opService.save(existingOperation);

        return new ResponseEntity<>("Operation updated successfully", HttpStatus.OK);
    }
}
