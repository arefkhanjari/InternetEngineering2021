package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@Lazy
public class StateService {

    private final DatabaseManager databaseManager;

    @Autowired
    public StateService(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public Integer getCurrentState() {
        Integer currentState = databaseManager.getState();
        if (currentState == -1) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot get state from database!");
        }
        return currentState;
    }

    public String goToNextState() {
        Integer currentState = getCurrentState();

        Integer newState = currentState + 1;
        newState = newState % 3;

        boolean isSuccessful = databaseManager.setState(newState);
        if (!isSuccessful) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Cannot set state in database!");
        }

        return newState.toString();
    }
}
