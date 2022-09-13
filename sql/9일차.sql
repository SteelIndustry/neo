--�� 9���� 
-- 1��
SELECT DEPTNO
     , JOB
     , SUM(SAL) SUM_SAL
FROM EMP
WHERE DEPTNO IS NOT NULL
GROUP BY ROLLUP(DEPTNO, JOB)
ORDER BY DEPTNO;

-- 2��
SELECT DEPTNO
     , JOB
     , SUM(SAL) SUM_SAL
FROM EMP
WHERE DEPTNO IS NOT NULL
GROUP BY CUBE(DEPTNO, JOB);

-- 3��
SELECT CASE WHEN GROUPING(T.DNAME) = 1 THEN '�հ�'
            ELSE T.DNAME
            END DNAME
     , CASE WHEN GROUPING_ID(T.DNAME, T.JOB) = 1 THEN '�Ұ�'
            WHEN GROUPING_ID(T.DNAME, T.JOB) = 0 THEN T.JOB
            ELSE ' '
            END JOB
     , SUM(T.SAL) AS SUM_SAL
FROM
(
    SELECT E.*, D.DNAME
    FROM EMP E JOIN DEPT D ON E.DEPTNO = D.DEPTNO
) T
GROUP BY ROLLUP( T.DNAME, T.JOB)
ORDER BY GROUPING(T.DNAME), DNAME DESC, GROUPING(T.JOB);

-- 4��
SELECT CASE WHEN T.RANK = 1 THEN T.DNAME
            ELSE ' '
            END DNAME
     , T.JOB
     , T.SUM_SAL
FROM
(
    SELECT CASE WHEN GROUPING(D.DNAME) = 1 THEN '�հ�'
                ELSE D.DNAME
                END DNAME
         , CASE WHEN GROUPING_ID(D.DNAME, E.JOB) = 1 THEN '�Ұ�'
           WHEN GROUPING_ID(D.DNAME, E.JOB) = 0 THEN E.JOB
           ELSE ' '
           END JOB 
         , REGEXP_REPLACE(REVERSE(
             REGEXP_REPLACE(REVERSE(TO_CHAR(SUM(E.SAL))), '(\d{3})', '\1,'))
               , '^,', '') AS SUM_SAL 
         , RANK() OVER (PARTITION BY D.DNAME ORDER BY E.JOB) RANK
    FROM EMP E JOIN DEPT D ON E.DEPTNO = D.DEPTNO
    GROUP BY ROLLUP ( D.DNAME, E.JOB)
    ORDER BY GROUPING(D.DNAME), DNAME, GROUPING(E.JOB), JOB
) T;

-- 5��
SELECT CASE WHEN T2.RANK = 1 THEN T2.DNAME
            ELSE ' '
            END DNAME
     , T2.JOB, T2.SUM_SAL
FROM
(
    SELECT CASE (GROUPING(T1.DNAME)) WHEN 1 THEN '�հ�'
                                     ELSE T1.DNAME
                                     END AS DNAME
         , CASE (GROUPING_ID(T1.DNAME, T1.JOB)) WHEN 1 THEN '�Ұ�'
                                                WHEN 0 THEN T1.JOB
                                                ELSE ' '
                                                END AS JOB
         , REGEXP_REPLACE(REVERSE(
              REGEXP_REPLACE(REVERSE(TO_CHAR(SUM(T1.SAL))), '(\d{3})', '\1,'))
                 , '^,', '') AS SUM_SAL     
         , RANK() OVER (PARTITION BY T1.DNAME ORDER BY T1.JOB) RANK
    FROM
    (
        SELECT D.DNAME, E.JOB, SUM(E.SAL) SAL
        FROM EMP E JOIN DEPT D ON E.DEPTNO = D.DEPTNO
        GROUP BY D.DNAME, E.JOB
    ) T1
    WHERE T1.SAL >= 1000
    GROUP BY ROLLUP(T1.DNAME, T1.JOB)
) T2;