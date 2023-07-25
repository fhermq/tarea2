package com.javaguides.employeeservice.service.impl;

import com.javaguides.employeeservice.exception.EmployeeAlreadyExistException;
import com.javaguides.employeeservice.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import com.javaguides.employeeservice.dto.EmployeeDto;
import com.javaguides.employeeservice.entity.Employee;
import com.javaguides.employeeservice.mapper.AutoEmployeeMapper;
import com.javaguides.employeeservice.repository.EmployeeRepository;
import com.javaguides.employeeservice.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {
        Employee existEmployee = employeeRepository.findEmployeeByEmail(employeeDto.getEmail());
        if (existEmployee != null){
            throw  new EmployeeAlreadyExistException(employeeDto.getEmail());
        }
        Employee employee = AutoEmployeeMapper.MAPPER.mapToEmployee(employeeDto);
        EmployeeDto savedEmployeeDto = AutoEmployeeMapper.MAPPER.mapToEmployeeDto( employeeRepository.save(employee) );
        return savedEmployeeDto;
    }

    @Override
    public EmployeeDto getEmployeeByID(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Employee", "id", id.toString())
        );
        EmployeeDto employeeDto = AutoEmployeeMapper.MAPPER.mapToEmployeeDto(employee);
        return employeeDto;
    }
}
