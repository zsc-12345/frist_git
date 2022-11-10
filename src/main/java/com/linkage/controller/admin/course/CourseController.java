package com.linkage.controller.admin.course;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.linkage.base.BaseApiController;
import com.linkage.base.RestResponse;
import com.linkage.controller.admin.RoleController;
import com.linkage.domain.Role;
import com.linkage.domain.TaskExam;
import com.linkage.domain.course.Course;
import com.linkage.domain.course.CourseDoc;
import com.linkage.domain.course.form.CourseDocInfoForm;
import com.linkage.domain.course.form.CourseInfoForm;
import com.linkage.service.course.CourseDocService;
import com.linkage.service.course.CourseService;
import com.linkage.utility.DateTimeUtil;
import com.linkage.utility.PageInfoHelper;
import com.linkage.utility.course.UnPackeUtil;
import com.linkage.viewmodel.admin.task.TaskPageRequestVM;
import com.linkage.viewmodel.admin.task.TaskPageResponseVM;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "课程管理 Controller")
@RestController("CourseController")
@RequestMapping(value = "/api/admin/course")
public class CourseController  extends BaseApiController {
	
	private static final Logger logger = LoggerFactory.getLogger(CourseController.class);
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private CourseDocService courseDocService;
	
	
	@ApiOperation("添加课程")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public RestResponse<Course> addCourseInfo(@RequestBody CourseInfoForm courseInfoForm) {
		
		try {
			Course course = modelMapper.map(courseInfoForm, Course.class);
			courseService.insertCourse(course);
			
            logger.info("CourseController.addCourseInfo: Param: " + course.toString());
            return new RestResponse<>(1, "添加课程成功。");
            
        }catch (Exception e){
            logger.error("CourseController.addCourseInfo: 添加课程失败" + e.getMessage());
            return RestResponse.fail(2,"添加课程失败");
        }
		
	}
	
	@ApiOperation("删除课程")
	@DeleteMapping("deleteCourseById/{id}")
	public RestResponse<Course> deleteCourseById(@PathVariable String id) {
		int res = courseService.deleteById(Integer.valueOf(id));
		if (res > 0) {
			return new RestResponse<>(1, "删除课程成功。");
		} else {
			return RestResponse.fail(2,"删除课程失败");
		}
	}
	
	@ApiOperation("更新课程")
	@PostMapping("updateCourseInfo")
	public RestResponse<Course> updateCourseInfo(@RequestBody CourseInfoForm courseInfoForm) {
		Course course = modelMapper.map(courseInfoForm, Course.class);
		 courseService.updateCourse(course);
		 //return RestResponse.ok(page);
		 return RestResponse.ok(course);
	}
	
	@ApiOperation("获取课程")
	@GetMapping("getCourseInfo/{id}")
	public RestResponse<Course> getCourseInfo(@PathVariable String id) {
		Course course = courseService.selectByCourseId(Integer.valueOf(id));
		return RestResponse.ok(course);
	}
	
    @ApiOperation(value="获取课程列表")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public RestResponse<PageInfo<CourseInfoForm>> pageList(@RequestBody CourseInfoForm model) {
        PageInfo<CourseInfoForm> pageInfo = courseService.page(model);
        
        PageInfo<CourseInfoForm> page = PageInfoHelper.copyMap(pageInfo, m -> {
        	CourseInfoForm vm = modelMapper.map(m, CourseInfoForm.class);
            return vm;
        });
        return RestResponse.ok(page);
    }
    
    @ApiOperation(value="发布课程")
    @GetMapping("publishCourse/{courseId}")
	public RestResponse<Course> publishCourse(@PathVariable String courseId) {
		Course course = new Course();
		course.setId(Integer.valueOf(courseId));
		course.setStatus("Normal");
		courseService.updateCourse(course);
		
		return RestResponse.ok(course);
	}
    
    @ApiOperation("上传资源")
    @PostMapping("/upload")
	public RestResponse<CourseDoc> uploadFile(@RequestParam("uploadFile") MultipartFile uploadFile, HttpServletRequest req) {
		
		//TODO: log reocrd file type: uploadFile.getContentType()
		String fileType = uploadFile.getContentType();
		String serverPath = req.getScheme()+"://"+req.getServerName()+":"+req.getServerPort() + "/";
		
		String url = "";
		if (uploadFile != null) {
			CourseDocInfoForm courseDocInfoForm = new CourseDocInfoForm();
			if("application/x-zip-compressed".equalsIgnoreCase(fileType)) {
				url = serverPath  + UnPackeUtil.uploadZip(uploadFile);
				courseDocInfoForm.setType("1");
			}else if("application/pdf".equalsIgnoreCase(fileType)) {
				url = serverPath  + UnPackeUtil.uploadPdfDoc(uploadFile);
				courseDocInfoForm.setType("2");
			}else if("video/mp4".equalsIgnoreCase(fileType)) {
				url = serverPath  + UnPackeUtil.uploadPdfDoc(uploadFile);
				courseDocInfoForm.setType("3");
			}
			
			courseDocInfoForm.setUrl(url);
			courseDocInfoForm.setName(uploadFile.getOriginalFilename());
			
			CourseDoc record = modelMapper.map(courseDocInfoForm, CourseDoc.class);
			courseDocService.insertCourseDoc(record);
			//courseDocInfoForm.setId(record.getId());
			return RestResponse.ok(record);
		}
		
		return RestResponse.fail(2,"上传资源失败");
	}
	

}
