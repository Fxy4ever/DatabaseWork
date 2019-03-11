package com.ccz.votesystem;

import com.ccz.votesystem.dao.DBOperator;
import com.ccz.votesystem.util.RSAUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.KeyPair;

@SpringBootApplication
public class VotesystemApplication {
	public static String publicKeyStr;
	public static String privateKeyStr;

	public static void main(String[] args) {
		SpringApplication.run(VotesystemApplication.class, args);
		DBOperator operator = new DBOperator();
		String createUser = "create table if not exists user(" +
				"userId INT UNSIGNED AUTO_INCREMENT," +
				"userName VARCHAR(100) NOT NULL," +
				"password VARCHAR (100) NOT NULL," +
				"phone VARCHAR (30) NOT NULL," +
				"age INTEGER NOT NULL,"+
				"PRIMARY KEY(userId)" +
				")DEFAULT CHARSET=utf8;";
		String createVote = "create table if not exists vote("+
				"voteId INT UNSIGNED AUTO_INCREMENT,"+
				"title VARCHAR(100) NOT NULL,"+
				"author VARCHAR(100) NOT NULL,"+
				"startTime VARCHAR(100) NOT NULL,"+
				"options VARCHAR(1000) NOT NULL,"+
				"endTime VARCHAR(100) NOT NULL,"+
				"PRIMARY KEY(voteId)"+
				")DEFAULT CHARSET=utf8;";

		String createVoteDetail = "create table if not exists voteDetail("+
				"voteDetailId INT UNSIGNED AUTO_INCREMENT,"+
				"voter VARCHAR(100) NOT NULL,"+
				"voterId INTEGER NOT NULL,"+
				"optionId INTEGER NOT NULL,"+
				"PRIMARY KEY(voteDetailId)"+
				")DEFAULT CHARSET=utf8;";

		String createOption = "create table if not exists voteOption("+
				"voteOptionId INT UNSIGNED AUTO_INCREMENT,"+
				"content VARCHAR(100) NOT NULL,"+
				"PRIMARY KEY(voteOptionId)"+
				")DEFAULT CHARSET=utf8;";

		operator.getConn("localhost","votesystem","root","123456");
		operator.executeSql(createUser);
		operator.executeSql(createVote);
		operator.executeSql(createVoteDetail);
		operator.executeSql(createOption);

		KeyPair keyPair = RSAUtil.getKeyPair();
		if (keyPair != null) {
			publicKeyStr = RSAUtil.getPublicKey(keyPair);
			privateKeyStr = RSAUtil.getPrivateKey(keyPair);
		}
	}

}
