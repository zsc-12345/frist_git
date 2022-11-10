package com.linkage.service.impl;

import com.linkage.domain.dingtalk.DepartmentDTO;
import com.linkage.repository.dingtalk.DepartmentMapper;
import com.linkage.service.dingtalk.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentMapper departmentMapper;
    @Autowired
    public DepartmentServiceImpl(DepartmentMapper departmentMapper){
        this.departmentMapper = departmentMapper;
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        return departmentMapper.getAllDepartments();
    }

    @Override
    public void updateById(DepartmentDTO departmentDTO) {
        departmentMapper.updateById(departmentDTO);
    }

    @Override
    public void insertDepartment(DepartmentDTO departmentDTO) {
        departmentMapper.insertDepartment(departmentDTO);
    }

    @Override
    public DepartmentDTO getDepartmentById(Long deptId) {
        return departmentMapper.getDepartmentById(deptId);
    }

    @Override
    public int deleteDepartmentById(Long deptId) {
        return departmentMapper.deleteDepartmentById(deptId);
    }
}
