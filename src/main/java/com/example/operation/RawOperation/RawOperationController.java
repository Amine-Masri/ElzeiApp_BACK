package com.example.operation.RawOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/raw-operations")
public class RawOperationController {

    @Autowired
    private RawOperationService rawOperationService;
    @ResponseBody
    @GetMapping
    public List<RawOperation> getAll() {
        return rawOperationService.findAll();
    }
    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity<RawOperation> getById(@PathVariable int id) {
        RawOperation rawOperation = rawOperationService.findById(id);
        return ResponseEntity.ok(rawOperation);
    }
    @ResponseBody
    @PostMapping
    public ResponseEntity<RawOperation> create(@RequestBody RawOperation rawOperation) {
        RawOperation savedRawOperation = rawOperationService.save(rawOperation);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedRawOperation);
    }
    @ResponseBody
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        rawOperationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
