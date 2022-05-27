package kr.human.category.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import kr.human.category.dao.CategoryDAO;
import kr.human.category.vo.CategoryVO;
import kr.human.mybatis.MybatisApp;

public class CategoryService {
	private static CategoryService instance = new CategoryService();

	private CategoryService() {
	}

	public static CategoryService getInstance() {
		return instance;
	}
	//----------------------------------------------------------------
	public List<CategoryVO> selectList(){
		List<CategoryVO> list = null; // =====> 여기
		SqlSession sqlSession = null;
		CategoryDAO dao = null;
		try {
			sqlSession = MybatisApp.getSqlSessionFactory().openSession(false);
			dao = CategoryDAO.getInstance();
			//----------------------------------------------------------------------
			// =====> 여기만 변경된다.
			list = dao.selectList(sqlSession);
			//----------------------------------------------------------------------
			sqlSession.commit();
		}catch (Exception e) {
			sqlSession.rollback();
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
		return list; // =====> 여기
	}
	
	public void insert(CategoryVO vo) {
		SqlSession sqlSession = null;
		CategoryDAO dao = null;
		try {
			sqlSession = MybatisApp.getSqlSessionFactory().openSession(false);
			dao = CategoryDAO.getInstance();
			//----------------------------------------------------------------------
			// =====> 여기만 변경된다.
			if(vo!=null && vo.getItem()!=null) {
				dao.insert(sqlSession, vo);
			}
			//----------------------------------------------------------------------
			sqlSession.commit();
		}catch (Exception e) {
			sqlSession.rollback();
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
	}
	// 답변달기
	public void reply(CategoryVO vo) {
		SqlSession sqlSession = null;
		CategoryDAO dao = null;
		try {
			sqlSession = MybatisApp.getSqlSessionFactory().openSession(false);
			dao = CategoryDAO.getInstance();
			//----------------------------------------------------------------------
			// =====> 여기만 변경된다.
			if(vo!=null && vo.getItem()!=null) {
				dao.updateSeq(sqlSession, vo); // seq가 나보다 큰것의 값을 1 증가시킨다.
				dao.reply(sqlSession, vo); // ref는 그대로 seq는 +1해서 lev도 +1해서 저장한다.
			}
			//----------------------------------------------------------------------
			sqlSession.commit();
		}catch (Exception e) {
			sqlSession.rollback();
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
	}
	// 수정하기
	public void update(CategoryVO vo) {
		SqlSession sqlSession = null;
		CategoryDAO dao = null;
		try {
			sqlSession = MybatisApp.getSqlSessionFactory().openSession(false);
			dao = CategoryDAO.getInstance();
			//----------------------------------------------------------------------
			// =====> 여기만 변경된다.
			if(vo!=null && vo.getItem()!=null && vo.getIdx()>0) {
				dao.update(sqlSession, vo);
			}
			//----------------------------------------------------------------------
			sqlSession.commit();
		}catch (Exception e) {
			sqlSession.rollback();
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
	}
	// 삭제하기
	public void delete(CategoryVO vo) {
		SqlSession sqlSession = null;
		CategoryDAO dao = null;
		try {
			sqlSession = MybatisApp.getSqlSessionFactory().openSession(false);
			dao = CategoryDAO.getInstance();
			//----------------------------------------------------------------------
			// =====> 여기만 변경된다.
			if(vo!=null) {
				// 1. 해당 글번호의 VO를 가져온다
				CategoryVO categoryVO = dao.selectByIdx(sqlSession, vo.getIdx());
				// 2. 나의 자식들의 개수를 구하자
				List<CategoryVO> list = dao.selectSeqList(sqlSession, categoryVO);
				int childCount = 0;
				int level = categoryVO.getLev();
				if(list!=null) {
					for(int i=1;i<list.size();i++) {
						if(list.get(i).getLev()==level) break;
						childCount++;
					}
				}
				if(childCount==0) {
					// 자식이 없으면 자신만 지워지면 된다.
					dao.delete(sqlSession, categoryVO.getIdx());
				}else {
					// 자식이 있다. 같은 레벨이 나오기 전까지를 삭제한다.
					dao.delete(sqlSession, categoryVO.getIdx()); // 자신을 지우고
					for(int i=1;i<list.size();i++) {
						if(list.get(i).getLev()==level) break;
						dao.delete(sqlSession, list.get(i).getIdx());
					}
				}
			}
			//----------------------------------------------------------------------
			sqlSession.commit();
		}catch (Exception e) {
			sqlSession.rollback();
			e.printStackTrace();
		}finally {
			sqlSession.close();
		}
	}
	
}
