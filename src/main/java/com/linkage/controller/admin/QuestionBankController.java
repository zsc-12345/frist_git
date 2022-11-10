package com.linkage.controller.admin;

import com.github.pagehelper.PageInfo;
import com.linkage.base.BaseApiController;
import com.linkage.base.RestResponse;
import com.linkage.base.SystemCode;
import com.linkage.domain.QuestionBank;
import com.linkage.domain.TextContent;
import com.linkage.domain.enums.QuestionBankTypeEnum;
import com.linkage.domain.questionbank.QuestionBankObject;
import com.linkage.service.QuestionBankService;
import com.linkage.service.TextContentService;
import com.linkage.utility.*;
import com.linkage.viewmodel.admin.questionbank.QuestionBankEditRequestVM;
import com.linkage.viewmodel.admin.questionbank.QuestionBankPageRequestVM;
import com.linkage.viewmodel.admin.questionbank.QuestionBankResponseVM;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "题库管理Controller")
@RestController("AdminQuesBankController")
@RequestMapping(value = "/api/admin/questionBank")
public class QuestionBankController extends BaseApiController {

    private final QuestionBankService questionBankService;
    private final TextContentService textContentService;

    @Autowired
    public QuestionBankController(QuestionBankService questionBankService, TextContentService textContentService) {
        this.questionBankService = questionBankService;
        this.textContentService = textContentService;
    }

    @ApiOperation(value = "问题列表")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public RestResponse<PageInfo<QuestionBankResponseVM>> pageList(@RequestBody QuestionBankPageRequestVM model) {
        PageInfo<QuestionBank> pageInfo = questionBankService.page(model);
        PageInfo<QuestionBankResponseVM> page = PageInfoHelper.copyMap(pageInfo, q -> {
            QuestionBankResponseVM vm = modelMapper.map(q, QuestionBankResponseVM.class);
            vm.setCreateTime(DateTimeUtil.dateFormat(q.getCreateTime()));
            vm.setScore(ExamUtil.scoreToVM(q.getScore()));
            TextContent textContent = textContentService.selectById(q.getInfoTextContentId());
            QuestionBankObject questionBankObject = JsonUtil.toJsonObject(textContent.getContent(), QuestionBankObject.class);
            String clearHtml = HtmlUtil.clear(questionBankObject.getTitleContent());
            vm.setShortTitle(clearHtml);
            return vm;
        });
        return RestResponse.ok(page);
    }

    @ApiOperation(value = "问题编辑任务")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public RestResponse edit(@RequestBody @Valid QuestionBankEditRequestVM model) {
        RestResponse validQuestionBankEditRequestResult = validQuestionBankEditRequestVM(model);
        if (validQuestionBankEditRequestResult.getCode() != SystemCode.OK.getCode()) {
            return validQuestionBankEditRequestResult;
        }

        if (null == model.getId()) {
            questionBankService.insertFullQuestion(model, getCurrentUser().getId());
        } else {
            questionBankService.updateFullQuestion(model);
        }

        return RestResponse.ok();
    }

    @ApiOperation(value = "问题查询任务")
    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse<QuestionBankEditRequestVM> select(@PathVariable Integer id) {
        QuestionBankEditRequestVM newVM = questionBankService.getQuestionEditRequestVM(id);
        return RestResponse.ok(newVM);
    }

    @ApiOperation(value = "问题删除任务")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public RestResponse delete(@PathVariable Integer id) {
        QuestionBank questionBank = questionBankService.selectById(id);
        questionBank.setDeleted(true);
        questionBankService.updateByIdFilter(questionBank);
        return RestResponse.ok();
    }

   private RestResponse validQuestionBankEditRequestVM(QuestionBankEditRequestVM model) {
        int qType = model.getQuestionType().intValue();
        boolean requireCorrect = qType == QuestionBankTypeEnum.SingleChoice.getCode() || qType == QuestionBankTypeEnum.TrueFalse.getCode();
        if (requireCorrect) {
            if (StringUtils.isBlank(model.getCorrect())) {
                String errorMsg = ErrorUtil.parameterErrorFormat("correct", "不能为空");
                return new RestResponse<>(SystemCode.ParameterValidError.getCode(), errorMsg);
            }
        }

        if (qType == QuestionBankTypeEnum.GapFilling.getCode()) {
            Integer fillSumScore = model.getItems().stream().mapToInt(d -> ExamUtil.scoreFromVM(d.getScore())).sum();
            Integer questionScore = ExamUtil.scoreFromVM(model.getScore());
            if (!fillSumScore.equals(questionScore)) {
                String errorMsg = ErrorUtil.parameterErrorFormat("score", "空分数和与题目总分不相等");
                return new RestResponse<>(SystemCode.ParameterValidError.getCode(), errorMsg);
            }
        }
        return RestResponse.ok();
   }
}
