package com.resumeforge.controller;

import com.resumeforge.common.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

/*    @GetMapping("/api/health")
    public String HealthCheck(){
        return "ResumeForge AI is running!";
    }*/
    @GetMapping("/api/health")
    public ApiResponse healthCheck(){
        return new ApiResponse(true,"ResumeForge AI is running!",null);
    }
}
