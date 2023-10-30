package com.exercise.spring.service;

import com.exercise.spring.dao.MemberDAOImpl;

public class MemberServiceImpl {
    private MemberDAOImpl memberDAO;

    public MemberServiceImpl() {
    }

    public MemberDAOImpl getMemberDAO() {
        return memberDAO;
    }

    public void setMemberDAO(MemberDAOImpl memberDAO) {
        this.memberDAO = memberDAO;
    }

    public void add() {
        System.out.println("MemberServiceImpl add() 方法");
        memberDAO.add();
    }
}
