package com.linkage.controller.admin;


import com.linkage.base.BaseApiController;
import com.linkage.base.RestResponse;
import com.linkage.configuration.property.SystemConfig;
import com.linkage.service.FileUpload;
import com.linkage.service.UserService;
import com.linkage.viewmodel.admin.file.UeditorConfigVM;
import com.linkage.viewmodel.admin.file.UploadResultVM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.UUID;

@Api(tags = "管理上传Controller")
@RequestMapping("/api/admin/upload")
@RestController("AdminUploadController")
public class UploadController extends BaseApiController {

    private final FileUpload fileUpload;
    private final SystemConfig systemConfig;
    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
    private static final String IMAGE_UPLOAD = "imgUpload";
    private static final String IMAGE_UPLOAD_FILE = "upFile";
    private final UserService userService;

    @Autowired
    public UploadController(FileUpload fileUpload, SystemConfig systemConfig, UserService userService) {
        this.fileUpload = fileUpload;
        this.systemConfig = systemConfig;
        this.userService = userService;
    }

    @ApiOperation(value = "配置上传任务")
    @ResponseBody
    @RequestMapping("/configAndUpload")
    public Object upload(HttpServletRequest request, HttpServletResponse response) {
        String action = request.getParameter("action");
        if (action.equals(IMAGE_UPLOAD)) {
            try {
                MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
                MultipartFile multipartFile = multipartHttpServletRequest.getFile(IMAGE_UPLOAD_FILE);
                long attachSize = multipartFile.getSize();
                String imgName = multipartFile.getOriginalFilename();
                String filePath;
                try (InputStream inputStream = multipartFile.getInputStream()) {
                    // 使用UUID保证文件名唯一
                    String uuidPath = UUID.randomUUID().toString();
                    // 创建上传文件的保存路径：外界无法直接访问
                    String uploadPath = "/opt/apps/upload/";
                    // 存储路径：uploadPath
                    String realPath = uploadPath + uuidPath+ '/' ; // 真实存在的路径
                    // 给每个文件创建一个文件夹
                    File realPathFile = new File(realPath);
                    makeDirIfNotExist(realPathFile);
                    String uploadFileName = realPath + multipartFile.getOriginalFilename();
                    filePath = "http://192.168.3.167:10000/upload/"+uuidPath+"/"+multipartFile.getOriginalFilename();
                    // 获得输出流
                    FileOutputStream fos = new FileOutputStream(uploadFileName);
                    // 创建缓冲区
                    byte[] buffer = new byte[1024];

                    int len;
                    while ((len = inputStream.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                    inputStream.close();
                    fos.close();

//                    filePath = fileUpload.uploadFile(inputStream, attachSize, imgName);
                }
                String imageType = imgName.substring(imgName.lastIndexOf("."));
                UploadResultVM uploadResultVM = new UploadResultVM();
                uploadResultVM.setOriginal(imgName);
                uploadResultVM.setName(imgName);
                uploadResultVM.setUrl(filePath);
                uploadResultVM.setSize(multipartFile.getSize());
                uploadResultVM.setType(imageType);
                uploadResultVM.setState("SUCCESS");
                return uploadResultVM;
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        } else {
            UeditorConfigVM ueditorConfigVM = new UeditorConfigVM();
            ueditorConfigVM.setImageActionName(IMAGE_UPLOAD);
            ueditorConfigVM.setImageFieldName(IMAGE_UPLOAD_FILE);
            ueditorConfigVM.setImageMaxSize(2048000L);
            ueditorConfigVM.setImageAllowFiles(Arrays.asList(".png", ".jpg", ".jpeg", ".gif", ".bmp"));
            ueditorConfigVM.setImageCompressEnable(true);
            ueditorConfigVM.setImageCompressBorder(1600);
            ueditorConfigVM.setImageInsertAlign("none");
            ueditorConfigVM.setImageUrlPrefix("");
            ueditorConfigVM.setImagePathFormat("");
            return ueditorConfigVM;
        }
        return null;
    }

    @ApiOperation(value = "图片任务")
    @RequestMapping("/image")
    @ResponseBody
    public RestResponse questionUploadAndReadExcel(HttpServletRequest request) {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartHttpServletRequest.getFile("file");
        long attachSize = multipartFile.getSize();
        String imgName = multipartFile.getOriginalFilename();
        try (InputStream inputStream = multipartFile.getInputStream()) {
            String filePath = fileUpload.uploadFile(inputStream, attachSize, imgName);
            userService.changePicture(getCurrentUser(), filePath);
            return RestResponse.ok(filePath);
        } catch (IOException e) {
            return RestResponse.fail(2, e.getMessage());
        }
    }

    /**
     * 如果文件目录不存在，为其创建目录
     *
     * @param file
     */
    private void makeDirIfNotExist(File file) {
        if (!file.exists()) {
            file.mkdir();
        }
    }
}
