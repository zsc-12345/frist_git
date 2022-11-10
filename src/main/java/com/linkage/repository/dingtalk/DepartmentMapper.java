package com.linkage.repository.dingtalk;

import com.linkage.domain.dingtalk.DepartmentDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DepartmentMapper {
    /**
     * 获取所有部门信息
     * @return DepartmentDTO
     */
    List<DepartmentDTO> getAllDepartments();

    /**
     *
     * @param departmentDTO
     */
    void updateById(DepartmentDTO departmentDTO);

    void insertDepartment(DepartmentDTO departmentDTO);

    DepartmentDTO getDepartmentById(@Param("id") Long deptId);

    int deleteDepartmentById(@Param("id") Long deptId);
}
