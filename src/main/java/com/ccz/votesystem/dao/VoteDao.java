package com.ccz.votesystem.dao;

import com.ccz.votesystem.entity.Vote;
import com.ccz.votesystem.entity.VoteDetail;
import com.ccz.votesystem.entity.VoteOption;
import org.apache.ibatis.annotations.*;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface VoteDao {

    //插入问卷表
    @Insert("insert into vote(title,author,startTime,options,endTime)" +
            "values (" +
            "#{title,jdbcType=VARCHAR}," +
            "#{author,jdbcType=VARCHAR}," +
            "#{startTime,jdbcType=VARCHAR}," +
            "#{options,jdbcType=VARCHAR}," +
            "#{endTime,jdbcType=VARCHAR})")
     void insertVote(Vote vote);

    //插入问卷的选项
    @Insert("insert into voteOption(content)" +
            "values (" +
            "#{content,jdbcType=VARCHAR})")
    void insertVoteOption(@Param("content") String content);

    //插入每一票的内容，用作投票
    @Insert("insert into voteDetail(voter,voterId,optionId)" +
            "values (" +
            "#{voter,jdbcType=VARCHAR}," +
            "#{voterId,jdbcType=INTEGER}," +
            "#{optionId,jdbcType=INTEGER})")
    void insertVoteDetail(VoteDetail voteDetail);

    //获取所有的问卷
    @Select("select * from vote order by startTime Desc")
    List<Vote> getAllVote();

    //通过问卷id删除问卷，这里建议比较结束时间删除
    @Delete("delete from vote where voteId = #{voteId,jdbcType=INTEGER}")
    void deleteVoteById(@Param("voteId")int voteId);

    //获取每一票，通过选项的id。这样可以获取所有选了这个选项的票
    @Select("select * from voteDetail where optionId = #{optionId,jdbcType=INTEGER}")
    List<VoteDetail> getVoteDetailByVoteOption(@Param("optionId") int optionId);

    @Select("select voteId from vote where voteId = #{voteId,jdbcType=INTEGER} ")
    Vote getVoteById(@Param("voteId") int voteId);

    @Select("select LAST_INSERT_ID() FROM voteOption  limit 1")
    BigInteger getLastedId();

    @Select("select * from voteDetail where voterId = #{voterId,jdbcType=INTEGER} and optionId = #{optionId,jdbcType=INTEGER}")
    VoteDetail checkIsVote(@Param("voterId") int voterId,@Param("optionId") int optionId);

    @Select("select * from voteOption where voteOptionId = #{voteOptionId,jdbcType=INTEGER}")
    VoteOption checkIfHadOption(@Param("voteOptionId") int voteOptionId);

    @Select("select LAST_INSERT_ID() FROM vote limit 1")
    BigInteger getLastedVoteId();
}
