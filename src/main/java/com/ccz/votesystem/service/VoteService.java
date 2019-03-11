package com.ccz.votesystem.service;

import com.ccz.votesystem.dao.VoteDao;
import com.ccz.votesystem.entity.JsonWrapper;
import com.ccz.votesystem.entity.Vote;
import com.ccz.votesystem.entity.VoteDetail;
import com.ccz.votesystem.entity.VoteOption;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class VoteService {

    @Autowired
    private VoteDao dao;

    //插入问卷
    public JsonWrapper insertVote(Vote vote){
         if(vote!=null){
             dao.insertVote(vote);
             return new JsonWrapper(1,"success!",200,vote);
         }
         return new JsonWrapper("failed",400);
    }

    //插入问卷选项
    public JsonWrapper insertVoteOption(String content){
        if(!content.isEmpty()){
            dao.insertVoteOption(content);
            return new JsonWrapper("success!",200);
        }
        return new JsonWrapper("failed",400);
    }

    //插入每一票
    public JsonWrapper insertVoteDetail(VoteDetail voteDetail){
        if(voteDetail!=null){
            dao.insertVoteDetail(voteDetail);
            return new JsonWrapper(1,"success!",200,voteDetail);
        }
        return new JsonWrapper("failed",400);
    }

    //获取所有问卷，这里可以分页获取
    public JsonWrapper getAllVote(int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Vote> list = dao.getAllVote();
        PageInfo<Vote> pageInfo = new PageInfo<>(list);
        List<Vote> pageList;
        pageList = pageInfo.getList();
        return new JsonWrapper(pageList.size(),"success!",200,pageList);
    }

    //删除问卷
    public JsonWrapper deleteVote(int voteId){
        if(dao.getVoteById(voteId)!=null){
            dao.deleteVoteById(voteId);
            return new JsonWrapper("success!",200);
        }else{
            return new JsonWrapper("该问卷不存在!",400);
        }

    }

    //根据问卷的选项，获取该选项下所有的投票
    public JsonWrapper getAllVoteDetail(int optionId){
        List<VoteDetail> list = dao.getVoteDetailByVoteOption(optionId);
        return new JsonWrapper(list.size(),"success!",200,list);
    }


    public BigInteger getLastedOptionId(){
        return dao.getLastedId();
    }

    public VoteDetail checkIsVote(int voterId,int optionId){
        return dao.checkIsVote(voterId,optionId);
    }

    public VoteOption checkIfHasOption(int optionId){
        return dao.checkIfHadOption(optionId);
    }

    public BigInteger getLastedVoteId(){
        return dao.getLastedVoteId();
    }
}
