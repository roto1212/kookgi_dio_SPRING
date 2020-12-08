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

insert into ajax (idx, name, age, gender, email) values (ajax_idx_seq.nextval, 'ȫ�浿', 20, '����', 'hg@naber.com');
insert into ajax (idx, name, age, gender, email) values (ajax_idx_seq.nextval, '�Ӳ���', 20, '����', 'im@naber.com');
insert into ajax (idx, name, age, gender, email) values (ajax_idx_seq.nextval, '����', 20, '����', 'jg@naber.com');
insert into ajax (idx, name, age, gender, email) values (ajax_idx_seq.nextval, '������', 20, '����', 'il@naber.com');
insert into ajax (idx, name, age, gender, email) values (ajax_idx_seq.nextval, '�տ���', 20, '����', 'son@naber.com');
insert into ajax (idx, name, age, gender, email) values (ajax_idx_seq.nextval, '���缮', 20, '����', 'u@naber.com');
insert into ajax (idx, name, age, gender, email) values (ajax_idx_seq.nextval, '������', 20, '����', 'jj@naber.com');
insert into ajax (idx, name, age, gender, email) values (ajax_idx_seq.nextval, '�ϵ���', 20, '����', 'haha@naber.com');
insert into ajax (idx, name, age, gender, email) values (ajax_idx_seq.nextval, '��ȣ��', 20, '����', 'kang@naber.com');
insert into ajax (idx, name, age, gender, email) values (ajax_idx_seq.nextval, '�̼���', 20, '����', 'su@naber.com');
insert into ajax (idx, name, age, gender, email) values (ajax_idx_seq.nextval, '����ö', 20, '����', 'hee@naber.com');
insert into ajax (idx, name, age, gender, email) values (ajax_idx_seq.nextval, '����', 20, '����', 'ssam@naber.com');
insert into ajax (idx, name, age, gender, email) values (ajax_idx_seq.nextval, '������', 20, '����', 'long@naber.com');

select * from ajax order by idx desc; 