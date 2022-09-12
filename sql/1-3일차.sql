------------------------------------------------------------
--○ 1일차
-- 1번
SELECT EMPNO, ENAME
FROM EMP
WHERE EMPNO=7369 OR EMPNO=7698;

-- 2번
SELECT EMPNO, ENAME
FROM EMP
WHERE NOT ( EMPNO=7369 OR EMPNO=7698 );

-- 3번
SELECT *
FROM EMP
WHERE SAL >= 3000 AND SAL <= 5000;

-- 4번
SELECT *
FROM EMP
WHERE HIREDATE >= TO_DATE('1981/12/01', 'YYYY/MM/DD');

-- 5번
SELECT MAX(TO_NUMBER(EMPNO)) AS EMPNO
FROM EMP
WHERE JOB='SALESMAN';

------------------------------------------------------------
--○ 2일차
-- 1번
SELECT D.DNAME, E.EMPNO, E.ENAME
FROM DEPT D JOIN EMP E
  ON D.DEPTNO = E.DEPTNO
ORDER BY D.DNAME, E.ENAME;

SELECT D.DNAME, E.EMPNO, E.ENAME
FROM DEPT D JOIN EMP E ON D.DEPTNO =E.DEPTNO
ORDER BY D.DNAME, E.ENAME;

-- 2번
SELECT NVL(D.DNAME, ' ') DNAME, E.EMPNO EMPNO, E.ENAME ENAME
FROM DEPT D RIGHT JOIN EMP E
  ON D.DEPTNO = E.DEPTNO
ORDER BY D.DNAME, E.ENAME;

SELECT NVL(D.DNAME, ' ') DNAME, E.EMPNO, E.ENAME
FROM DEPT D RIGHT JOIN EMP E
ON D.DEPTNO = E.DEPTNO
ORDER BY D.DNAME, E.ENAME;

-- 3번
SELECT D.LOC, E.EMPNO, E.ENAME
FROM DEPT D JOIN EMP E
  ON D.DEPTNO = E.DEPTNO
WHERE D.LOC IN ('DALLAS', 'CHICAGO')
ORDER BY D.LOC DESC, E.ENAME ASC;

-- 4번
SELECT DEPTNO, MAX(SAL) AS SAL
FROM EMP
GROUP BY DEPTNO
HAVING DEPTNO IS NOT NULL
ORDER BY DEPTNO;

-- 5번
SELECT DEPTNO, SAL, EMPNO, ENAME, JOB
FROM EMP
WHERE (DEPTNO, SAL) IN ( SELECT DEPTNO, MAX(SAL) SAL
                         FROM EMP
                         GROUP BY DEPTNO )
ORDER BY DEPTNO;

------------------------------------------------------------
--○ 3일차

-- 1번
SELECT E.EMPNO, E.ENAME, E.SAL, S.GRADE
FROM EMP E JOIN SALGRADE S
  ON E.SAL BETWEEN S.LOSAL AND S.HISAL;

-- 2번
SELECT EMPNO, ENAME, JOB, SAL
FROM EMP
WHERE SAL > ( SELECT AVG(SAL)
              FROM EMP )
ORDER BY SAL DESC;

-- 3번
SELECT D.DNAME, E.EMPNO, E.ENAME, E.JOB, E.SAL
FROM EMP E JOIN ( SELECT DEPTNO, AVG(SAL) AS SAL
                  FROM EMP
                  GROUP BY DEPTNO ) A
             ON E.DEPTNO = A.DEPTNO
           JOIN DEPT D
             ON E.DEPTNO = D.DEPTNO
WHERE E.SAL > A.SAL
ORDER BY E.SAL DESC;
  
SELECT T2.DNAME, T1.EMPNO, T1.ENAME, T1.JOB, T1.SAL
FROM EMP T1 LEFT JOIN ( SELECT D.DEPTNO, D.DNAME, AVG(E.SAL) AVGSAL
                        FROM EMP E JOIN DEPT D
                        ON E.DEPTNO = D.DEPTNO
                        GROUP BY D.DEPTNO, D.DNAME) T2
ON T1.DEPTNO = T2.DEPTNO
WHERE T1.SAL > T2.AVGSAL;
  
-- 4번
SELECT S1.GRADE, S1.LOSAL, S1.HISAL, S2.CNT
FROM SALGRADE S1 JOIN ( SELECT S.GRADE, COUNT(S.GRADE) CNT
                       FROM EMP E JOIN SALGRADE S
                       ON E.SAL BETWEEN S.LOSAL AND S.HISAL
                       GROUP BY S.GRADE ) S2
                   ON S1.GRADE = S2.GRADE
ORDER BY S1.GRADE;

SELECT S.GRADE, S.LOSAL, S.HISAL, COUNT(S.GRADE) CNT
FROM EMP E JOIN SALGRADE S
  ON E.SAL BETWEEN S.LOSAL AND S.HISAL
GROUP BY S.GRADE, S.LOSAL, S.HISAL
ORDER BY S.GRADE;

-- 5번
SELECT D.DNAME, D.LOC, E.EMPNO, E.ENAME
FROM EMP E JOIN DEPT D
             ON E.DEPTNO = D.DEPTNO
WHERE D.DNAME='RESEARCH' OR D.LOC = 'NEW YORK'
ORDER BY D.DNAME;
