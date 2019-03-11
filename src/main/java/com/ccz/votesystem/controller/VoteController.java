package com.ccz.votesystem.controller;

import com.ccz.votesystem.annotation.Encrypt;
import com.ccz.votesystem.annotation.NeedUserToken;
import com.ccz.votesystem.entity.JsonWrapper;
import com.ccz.votesystem.entity.Vote;
import com.ccz.votesystem.entity.VoteDetail;
import com.ccz.votesystem.entity.VoteOption;
import com.ccz.votesystem.service.VoteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/voteSystem")
public class VoteController {

    @Autowired
    private VoteService service;

    @NeedUserToken
    @PostMapping(value = "/addVote")
    public Object addVote(@Valid Vote vote, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder msg = new StringBuilder();
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                msg.append(fieldError.getDefaultMessage()).append(",");
            }
            return new JsonWrapper(msg.toString().substring(0, msg.length() - 1), 400);
        } else {
            if (vote.getOptions() != null && vote.getOptions().length() > 0) {
                String[] options = vote.getOptions().split(" ");//选项用空格隔开
                List<VoteOption> list = new ArrayList<>();
                for (int i = 0; i < options.length; i++) {
                    service.insertVoteOption(options[i]);//向数据库里插入每一条选项
                    VoteOption option = new VoteOption();
                    option.setContent(options[i]);//为VoteOption重新设置每一条content和id
                    option.setVoteOptionId(service.getLastedOptionId().intValue());
                    list.add(option);
                }
                ObjectMapper mapper = new ObjectMapper();//JackJson库
                try {
                    String jsonOption = mapper.writeValueAsString(list);//对象转Json
                    vote.setOptions(jsonOption);//重新设置Options
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                vote.setVoteId(service.getLastedVoteId().intValue());
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                vote.setStartTime(df.format(new Date()));//设置开始时间
                return service.insertVote(vote);
            } else {
                return new JsonWrapper("请填写问卷选项!", 400);
            }
        }
    }

    @Encrypt
    @NeedUserToken
    @GetMapping(value = "/getAllVote")
    public Object getAllVote(int pageNum, int pageSize) {
        return service.getAllVote(pageNum, pageSize);
    }

    @NeedUserToken
    @PostMapping(value = "/vote")
    public Object vote(@NonNull String voter, @NotNull int voterId, @NotNull int optionId) {
        VoteOption option = service.checkIfHasOption(optionId);
        if (option == null) {
            return new JsonWrapper("没有该投票选项!", 400);
        } else {
            VoteDetail detail = service.checkIsVote(voterId, optionId);//验证数据库是否有该投票
            if (detail != null) {
                return new JsonWrapper("您已经投过票!", 400);
            } else {
                detail = new VoteDetail();
                detail.setOptionId(optionId);
                detail.setVoter(voter);
                detail.setVoterId(voterId);
                return service.insertVoteDetail(detail);
            }
        }
    }

    @NeedUserToken
    @GetMapping(value = "/getVoteByOptionId")
    public Object getVoteByOptionId(int optionId) {
        return service.getAllVoteDetail(optionId);
    }

    @NeedUserToken
    @PostMapping(value = "/deleteVote")
    public Object deleteVote(@NotNull int voteId){
        return service.deleteVote(voteId);
    }
}
