package net.learntechnology.genericcrud.services;

import net.learntechnology.genericcrud.domain.Employee;
import net.learntechnology.genericcrud.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService extends GenericCrudService<Employee, Long> {

    @Autowired
    public EmployeeService(EmployeeMapper mapper) {
        super(mapper);
    }
}
