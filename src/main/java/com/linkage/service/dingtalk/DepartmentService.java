package com.linkage.service.dingtalk;

import com.linkage.domain.dingtalk.DepartmentDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DepartmentService {

    List<DepartmentDTO> getAllDepartments();

    void updateById(DepartmentDTO departmentDTO);

    void insertDepartment(DepartmentDTO departmentDTO);

    DepartmentDTO getDepartmentById(Long deptId);

    int deleteDepartmentById(Long deptId);
}
