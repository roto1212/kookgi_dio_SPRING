CREATE TABLE "AJAX" (	
    "IDX" NUMBER(*,0) NOT NULL , 
	"NAME" VARCHAR2(20 BYTE) NOT NULL , 
	"AGE" NUMBER(*,0) NOT NULL , 
	"GENDER" VARCHAR2(20 BYTE) NOT NULL , 
	"EMAIL" VARCHAR2(50 BYTE) NOT NULL , 
    PRIMARY KEY ("IDX")
    );
    
delete from AJAX;
drop sequence AJAX_idx_seq;
create sequence AJAX_idx_seq;
commit;

insert into ajax (idx, name, age, gender, email) values (ajax_idx_seq.nextval, '홍길동', 20, '남자', 'hg@naber.com');
insert into ajax (idx, name, age, gender, email) values (ajax_idx_seq.nextval, '임꺽정', 20, '남자', 'im@naber.com');
insert into ajax (idx, name, age, gender, email) values (ajax_idx_seq.nextval, '장길산', 20, '남자', 'jg@naber.com');
insert into ajax (idx, name, age, gender, email) values (ajax_idx_seq.nextval, '일지매', 20, '남자', 'il@naber.com');
insert into ajax (idx, name, age, gender, email) values (ajax_idx_seq.nextval, '손오공', 20, '남자', 'son@naber.com');
insert into ajax (idx, name, age, gender, email) values (ajax_idx_seq.nextval, '유재석', 20, '남자', 'u@naber.com');
insert into ajax (idx, name, age, gender, email) values (ajax_idx_seq.nextval, '정지훈', 20, '남자', 'jj@naber.com');
insert into ajax (idx, name, age, gender, email) values (ajax_idx_seq.nextval, '하동훈', 20, '남자', 'haha@naber.com');
insert into ajax (idx, name, age, gender, email) values (ajax_idx_seq.nextval, '강호동', 20, '남자', 'kang@naber.com');
insert into ajax (idx, name, age, gender, email) values (ajax_idx_seq.nextval, '이수근', 20, '남자', 'su@naber.com');
insert into ajax (idx, name, age, gender, email) values (ajax_idx_seq.nextval, '김희철', 20, '남자', 'hee@naber.com');
insert into ajax (idx, name, age, gender, email) values (ajax_idx_seq.nextval, '쌈자', 20, '남자', 'ssam@naber.com');
insert into ajax (idx, name, age, gender, email) values (ajax_idx_seq.nextval, '서장훈', 20, '남자', 'long@naber.com');

select * from ajax order by idx desc; 