package com.ccz.votesystem.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/*
    问卷表
 */
@Data
public class Vote {


    private int voteId;//投票内容的id

    @NotBlank
    private String title;//标题

    @NotBlank
    private String author;//发起者

    private String startTime;//时间

    @NotBlank
    private String options;//Json 存投票的选项名字和id 格式为List<VoteOption>

    @NotBlank
    private String endTime;//默认为0

    public int getVoteId() {
        return voteId;
    }

    public void setVoteId(int voteId) {
        this.voteId = voteId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
