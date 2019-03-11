package com.ccz.votesystem.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/*
    投票表
 */
@Data
public class VoteDetail {


    private int voteDetailId;

    @NotBlank
    private String voter;


    private int voterId;


    private int optionId;

    public int getVoteDetailId() {
        return voteDetailId;
    }

    public void setVoteDetailId(int voteDetailId) {
        this.voteDetailId = voteDetailId;
    }

    public String getVoter() {
        return voter;
    }

    public void setVoter(String voter) {
        this.voter = voter;
    }

    public int getVoterId() {
        return voterId;
    }

    public void setVoterId(int voterId) {
        this.voterId = voterId;
    }

    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }
}
