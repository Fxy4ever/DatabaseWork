package com.ccz.votesystem.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/*
投票选项
 */
@Data
public class VoteOption {


    private int voteOptionId;

    @NotBlank
    private String content;

    public int getVoteOptionId() {
        return voteOptionId;
    }

    public void setVoteOptionId(int voteOptionId) {
        this.voteOptionId = voteOptionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
