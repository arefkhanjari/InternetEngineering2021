package com.example.demo.controller;

import com.example.demo.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StateController {

    private final StateService stateService;

    @Autowired
    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

    @GetMapping("state/get")
    public String get() {
        return stateService.getCurrentState().toString();
    }

    @GetMapping("state/next")
    public String next() {
        return stateService.goToNextState();
    }
}
