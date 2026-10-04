package com.swe307.pro1;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DataRowRepository extends MongoRepository<DataRow, String> {

    DataRow findByRow(String row);
}
