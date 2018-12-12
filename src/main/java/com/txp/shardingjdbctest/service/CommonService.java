package com.txp.shardingjdbctest.service;

public interface CommonService {
    
    void initEnvironment();
    
    void cleanEnvironment();
    
    void processSuccess();
    
    void processFailure();
    
    void printData();
}
