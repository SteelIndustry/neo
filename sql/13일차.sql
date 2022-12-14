--○ 13일차
-- 1번
SELECT EMPNO, LEVEL
     , LPAD(' ',(LEVEL-1)*1)||ENAME AS ENAME
     , SUBSTR(SYS_CONNECT_BY_PATH(ENAME, '-'), 2) FULL_ENAMES 
     , SAL
     , (SELECT SUM(SAL)
        FROM EMP
        START WITH EMPNO=E1.EMPNO
        CONNECT BY PRIOR EMPNO = MGR) AS SUM_SAL
FROM EMP E1
START WITH MGR IS NULL
CONNECT BY PRIOR EMPNO = MGR;

-- 2번
SELECT LEVEL, SUM(SAL) SALTOTAL, COUNT(EMPNO) EMPNCNT
FROM EMP
GROUP BY LEVEL
START WITH MGR IS NULL
CONNECT BY PRIOR EMPNO = MGR;

-- 3번
SELECT JOB, EMPNO, ENAME, MGR
FROM
(
SELECT LPAD(' ', (LEVEL-1)*1)||JOB AS JOB, EMPNO, ENAME, MGR
     , LEVEL, SUBSTR(SYS_CONNECT_BY_PATH(ENAME, '-'), 2) FULL_ENAMES
FROM EMP
START WITH MGR IS NULL
CONNECT BY PRIOR EMPNO = MGR
ORDER SIBLINGS BY EMPNO DESC
)
WHERE ROWNUM <= 10;

-- 4번
MERGE
INTO EMP_TEST E1
USING (SELECT EMPNO, DEPTNO, SAL
       FROM EMP
       WHERE DEPTNO IN(20,30)) E2
ON (E1.EMPNO =E2.EMPNO)
WHEN MATCHED THEN
     UPDATE
     SET E1.SAL = ROUND(E1.SAL*1.1)
WHEN NOT MATCHED THEN
     INSERT (E1.EMPNO, E1.DEPTNO, E1.SAL)
     VALUES (E2.EMPNO, E2.DEPTNO, E2.SAL)
     WHERE E2.SAL > 1000;

-- 5번
MERGE
INTO EMP_TEST E1
USING EMP E2
ON (E1.EMPNO=E2.EMPNO)
WHEN MATCHED THEN
     UPDATE
     SET E1.SAL = ROUND(E1.SAL*1.1)
     DELETE 
     WHERE E1.DEPTNO = 20
WHEN NOT MATCHED THEN
     INSERT (E1.EMPNO, E1.DEPTNO, E1.SAL)
     VALUES (E2.EMPNO, E2.DEPTNO, ROUND(E2.SAL*1.2));