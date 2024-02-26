package com.example.operation.RawOperation;

import java.util.List;

public interface RawOperationService {
    List<RawOperation> findAll();
    RawOperation findById(int id);
    RawOperation save(RawOperation rawOperation);
    void delete(int id);
}
