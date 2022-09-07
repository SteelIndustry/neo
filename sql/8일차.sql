--○ 8일차 

-- ALTER SESSION SET NLS_DATE_FORMAT = 'YYYY/MM/DD HH24:MI:SS';
-- 1번
SELECT JOB,
       COUNT(*)      AS CNT,
       MIN(HIREDATE) AS FIRST_HIREDATE
FROM   EMP
GROUP  BY JOB;

-- 2번
SELECT TO_CHAR(HIREDATE, 'YYYY-MM') AS HIREDATE,
       COUNT(*)                     AS CNT
FROM   EMP
GROUP  BY TO_CHAR(HIREDATE, 'YYYY-MM')
ORDER  BY HIREDATE; 

-- 3번
SELECT TO_CHAR(HIREDATE, 'YYYY-MM') AS HIREDATE,
       COUNT(*)                     AS CNT
FROM   EMP
GROUP  BY TO_CHAR(HIREDATE, 'YYYY-MM')
HAVING COUNT(*) > 1
ORDER  BY HIREDATE; 

-- 4번
SELECT T.DNAME,
       COUNT(*)   AS CNT,
       SUM(T.SAL) AS SAL
FROM   (SELECT E.*,
               NVL(D.DNAME, 'NO DEPT') AS DNAME
        FROM   EMP E
               LEFT OUTER JOIN DEPT D
                            ON E.DEPTNO = D.DEPTNO) T
GROUP  BY T.DNAME;

-- 5번
SELECT T1.MM,
       NVL(T2.CNT, 0) AS CNT
FROM   (SELECT LEVEL AS MM
        FROM   DUAL
        CONNECT BY LEVEL <= 12) T1
       LEFT OUTER JOIN (SELECT TO_CHAR(HIREDATE, 'FMMM') AS MM,
                               COUNT(*)                  AS CNT
                        FROM   EMP
                        GROUP  BY TO_CHAR(HIREDATE, 'FMMM')) T2
                    ON T1.MM = T2.MM
ORDER  BY MM;