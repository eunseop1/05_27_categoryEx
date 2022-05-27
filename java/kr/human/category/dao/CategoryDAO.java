package kr.human.category.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import kr.human.category.vo.CategoryVO;

public class CategoryDAO {
	private static CategoryDAO instance = new CategoryDAO();

	private CategoryDAO() {
	}

	public static CategoryDAO getInstance() {
		return instance;
	}
	//----------------------------------------------------------------
	// 모두 얻기
	public List<CategoryVO> selectList(SqlSession sqlSession){
		return sqlSession.selectList("category.selectList");
	}
	// 원본글 저장
	public void insert(SqlSession sqlSession, CategoryVO vo) {
		sqlSession.insert("category.insert", vo);
	}
	// 답변달기전 seq증가
	public void updateSeq(SqlSession sqlSession, CategoryVO vo) {
		sqlSession.update("category.updateSeq", vo);
	}
	// 답변달기
	public void reply(SqlSession sqlSession, CategoryVO vo) {
		sqlSession.insert("category.reply", vo);
	}
	// 수정
	public void update(SqlSession sqlSession, CategoryVO vo) {
		sqlSession.update("category.update", vo);
	}
	// 삭제
	public CategoryVO selectByIdx(SqlSession sqlSession, int idx) {
		return sqlSession.selectOne("category.selectByIdx", idx);
	}
	public void delete(SqlSession sqlSession, int idx) {
		sqlSession.delete("category.delete", idx);
	}
	public List<CategoryVO> selectSeqList(SqlSession sqlSession, CategoryVO vo){
		return sqlSession.selectList("category.selectSeqList", vo);
	}
}
