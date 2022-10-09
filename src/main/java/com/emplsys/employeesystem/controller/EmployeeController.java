package com.emplsys.employeesystem.controller;

import com.emplsys.employeesystem.exception.ResourceNotFoundException;
import com.emplsys.employeesystem.model.Employee;
import com.emplsys.employeesystem.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/v1/employees")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable(name = "id") Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee with id " + id + " not found"));
        return ResponseEntity.ok(employee);
    }

    @PutMapping(path = "{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable(name = "id") Long id,
                                                   @RequestBody Employee updatedEmployee) {
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee with id " + id + " not found"));

        employee.setFirstName(updatedEmployee.getFirstName());
        employee.setLastName(updatedEmployee.getLastName());
        employee.setEmailId(updatedEmployee.getEmailId());
        employeeRepository.save(employee);

        return ResponseEntity.ok(employee);
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable(name = "id") Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Employee with id " + id + " not found")
        );

        employeeRepository.delete(employee);
        return ResponseEntity.ok("Deleted");
    }
}
