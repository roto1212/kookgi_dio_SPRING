
CREATE TABLE "MVCBOARD" (	
    "IDX" NUMBER(*,0) NOT NULL, 
	"NAME" CHAR(20 BYTE) NOT NULL, 
	"SUBJECT" VARCHAR2(200 BYTE) NOT NULL, 
	"CONTENT" VARCHAR2(2000 BYTE) NOT NULL, 
	"REF" NUMBER(*,0), 
	"LEV" NUMBER(*,0), 
	"SEQ" NUMBER(*,0), 
	"HIT" NUMBER DEFAULT 0, 
	"WRITEDATE" TIMESTAMP (6) DEFAULT sysdate, 
	 PRIMARY KEY ("IDX")
     );
     
DELETE from mvcboard;
drop SEQUENCE mvcboard_idx_seq;
CREATE SEQUENCE mvcboard_idx_seq;
COMMIT;

select * from mvcboard order by idx desc;