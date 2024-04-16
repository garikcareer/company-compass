package main.java.com.example.companycompass.controller;

import main.java.com.example.companycompass.model.Company;
import main.java.com.example.companycompass.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/company")
public class CompanyRestController {
    @Autowired
    private CompanyService companyService;

    @PostMapping(path = "/add")
    public ResponseEntity<Map<String, Object>> addCompany(@RequestBody Company company) {
        Company newCompany = companyService.saveCompany(company);
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", newCompany + " was added successfully");
        response.put("timestamp", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get")
    public ResponseEntity<Map<String, Object>> getCompany(@RequestParam Long id) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", companyService.getCompanyById(id));
        response.put("timestamp", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/get/all")
    public ResponseEntity<Map<String, Object>> getCompanies() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", companyService.getCompanies());
        response.put("timestamp", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<Map<String, Object>> updateCompany(@RequestBody Company company, @RequestParam Long id) {
        Company updatedCompany = companyService.updateCompany(company, id);
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Company with ID (" + id + ") updated successfully with values: " + updatedCompany);
        response.put("timestamp", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete")
    public ResponseEntity<Map<String, Object>> deleteCompanyById(@RequestParam Long id) {
        companyService.deleteCompanyById(id);
        Map<String, Object> response = new HashMap<>();
        response.put("status", HttpStatus.OK.value());
        response.put("message", "Company with ID (" + id + ") deleted successfully");
        response.put("timestamp", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
