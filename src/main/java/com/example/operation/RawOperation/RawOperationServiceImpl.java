package com.example.operation.RawOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RawOperationServiceImpl implements RawOperationService {

    @Autowired
     RawOperationRepository rawOperationRepository;
    

    @Override
    public List<RawOperation> findAll() {
        return rawOperationRepository.findAll();
    }

    @Override
    public RawOperation findById(int id) {
        return rawOperationRepository.findById(id).orElse(null);
    }

    @Override
    public RawOperation save(RawOperation rawOperation) {
        return rawOperationRepository.save(rawOperation);
    }

    @Override
    public void delete(int id) {
        rawOperationRepository.deleteById(id);
    }
}
