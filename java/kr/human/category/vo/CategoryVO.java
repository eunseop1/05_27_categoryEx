package kr.human.category.vo;

import lombok.Data;

/*
CREATE TABLE CATEGORY (
	IDX NUMBER PRIMARY KEY ,
	REF NUMBER DEFAULT 0, --- 원본글의 번호
	seq NUMBER DEFAULT 0, --- 나타날 순서 0, 1, 2, 3 ....
	lev NUMBER DEFAULT 0, ---몇단계? 0이면 원본, 1이면 답변 2면 답변의 답변.....
	item varchar(50) NOT NULL,  -- 항목
	del char(1) DEFAULT 'N' check(del IN ('Y','N'))  -- 삭제유무저장. 값제한 Y/N만 입력 가능
);
 */
@Data
public class CategoryVO {
	private int idx;
	private int ref;
	private int seq;
	private int lev;
	private String item;
	private String del;
}
