package com.koreait.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.koreait.vo.MvcboardVO;

public interface MybatisDAO {
//	mapper �������̽��� �߻� �޼ҵ� ������ dataType methodName(parameter) = resultType id(parameterType)�� ���� �������� ����� ����Ѵ�.
//	MybatisDAO �������̽��� �߻� �޼ҵ� �̸��� xml ������ sql ����� �ĺ��ϴ� id�� ���ǰ� �߻� �޼ҵ��� �Ű������� ������ �����Ͱ� xml������ sql������� ���޵ȴ�.

	int selectCount();
	ArrayList<MvcboardVO> selectList(HashMap<String, Integer> hmap);
	
//	sql ����� �����ϴ� xml ������ parameterType �Ӽ����� �Ѱ��� �ڷ����� �� �� �ִµ� �Ʒ��� ���� �������� �����͸� �Ѱܾ� �� ��� �μ��� �Ѿ�� �����͸� ��� ��������� ������ �մ� Ŭ���� �̸��� ����ϸ� �ȴ�.
//	void insert(String name, String subject, String content);
	void insert(MvcboardVO mvcboardVO);
	void increment(int idx);
	MvcboardVO selectByIdx(int idx);
	void delete(int idx);
	void update(MvcboardVO mvcboardVO);
	void replyIncrement(HashMap<String, Integer> hmap);
	void replyInsert(MvcboardVO mvcboardVO);

}
