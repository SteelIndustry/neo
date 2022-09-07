--○ 6일차 
-- 1번
SELECT EMPNO, ENAME, SAL
     , SUM(SAL) OVER(ORDER BY EMPNO) AS SAL_CUMULATIVE
FROM EMP;

-- 2번
SELECT JOB, ENAME, SAL
     , ROW_NUMBER() OVER (ORDER BY SAL) AS RANK
FROM EMP;

-- 3번
SELECT JOB, ENAME, SAL
     , RANK() OVER (ORDER BY SAL) AS RANK
FROM EMP;

-- 4번
SELECT JOB, ENAME, SAL
     , DENSE_RANK() OVER (ORDER BY SAL) AS RANK
FROM EMP;

-- 5번
CREATE TABLE EMP_BACKTABLE 
AS
SELECT *
FROM EMP

-- 6번
INSERT INTO EMP (EMPNO, ENAME, JOB, MGR, HIREDATE, SAL, COMM, DEPTNO)
SELECT EMPNO, ENAME, JOB
     , CASE WHEN JOB = 'PRESIDENT' THEN NULL
            WHEN JOB = 'MANAGER' THEN 1 
            ELSE TRUNC (DBMS_RANDOM.VALUE(1, 5001))
            END AS MGR
     , HIREDATE
     , SAL
     , COMM
     , DEPTNO
FROM
(
SELECT LEVEL AS EMPNO
     , DBMS_RANDOM.STRING('U', 4) AS ENAME
     , CASE WHEN LEVEL = 1 THEN 'PRESIDENT'
            WHEN DBMS_RANDOM.VALUE <= 0.25 THEN 'MANAGER'
            WHEN DBMS_RANDOM.VALUE < 0.5 THEN 'ANALYST'
            WHEN DBMS_RANDOM.VALUE < 0.5 THEN 'CLERK'
            ELSE 'SALESMAN' END AS JOB
     , NULL AS MGR
     , TRUNC ( SYSDATE - DBMS_RANDOM.VALUE (0, 3650)) AS HIREDATE
     , ROUND( DBMS_RANDOM.VALUE (800, 5000), -2) AS SAL
     , TRUNC ( DBMS_RANDOM.VALUE(0, 1400), -2) AS COMM
     , TRUNC ( DBMS_RANDOM.VALUE(10, 40), -1) AS DEPTNO
FROM DUAL
CONNECT BY LEVEL <=5000
)
COMMIT;

--○ 7일차
-- 1번
UPDATE EMP
SET SAL= SAL+400
WHERE JOB='SALESMAN';

-- 2번
UPDATE EMP
SET HIREDATE = (HIREDATE + INTERVAL '1' YEAR)
WHERE SAL > (SELECT AVG(SAL)
             FROM EMP);

-- 3번
UPDATE EMP E1
SET (COMM, SAL) = ( SELECT NVL(E2.COMM, 0)+100
                         , CASE WHEN E2.JOB='CLERK' THEN E2.SAL*2
                                WHEN E2.JOB='MANAGER' THEN E2.SAL*3
                                ELSE E2.SAL*4
                                END
                    FROM EMP E2
                    WHERE E1.EMPNO=E2.EMPNO);

-- 4번
DELETE 
FROM EMP
WHERE ENAME  LIKE 'M%';


-- 5번
DELETE
FROM EMP
WHERE SAL > (SELECT AVG(SAL)
             FROM EMP);