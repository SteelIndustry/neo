------------------------------------------------------------
--○ 4일차
-- 1번
SELECT *
FROM 
(
    SELECT *
    FROM EMP
    ORDER BY SAL DESC
)
WHERE ROWNUM <=5;

-- 2번
SELECT *
FROM
(
    SELECT E.*
         , ROW_NUMBER() OVER(ORDER BY SAL DESC) RN
    FROM EMP E
)
WHERE RN BETWEEN 6 AND 10;

-- 3번
-- ROWNUM 쿼리의 조건절이 처리되고 난 후, SORT, AGGREGATION(집합함수)이
-- 수행되기 이전에 할당된다. 또, ROWNUM은 값이 할당된 이후에만 증가한다.
-- 예를 들어 WHERE ROWNUM > 1의 조건이 TRUE가 아니기 때문에 ROWNUM은 2로 증가하지
-- 않는다. 만약 ROWNUM=2 일 경우,  첫 번째 행의 순번이 2가 아니니까 버리고,
-- 다음 행은 자동으로 1이 되어버린다. 이 때 조건 2에 부합하지 않으므로
-- 해당 값이 또 버려진다.
-- SELECT 절 처리 순서를 떠올리면, FROM/WHERE 절이 먼저 처리된 후
-- 각각의 ROW에 대해 ROWNUM이 부여된다. 각각 로우의 값은 1번부터 1씩 증가한다.
-- 따라서 WHERE ROWNUM > 5일 경우, 조건에 해당하는 ROW가 존재하지 않는다.
-- 

-- 4번
SELECT *
FROM
(
    SELECT GRADE, LOSAL || '~' || HISAL AS LOWHI
    FROM SALGRADE
)
PIVOT
(
    MAX(LOWHI)
    FOR GRADE 
    IN (1 AS GRADE_1
      , 2 AS GRADE_2
      , 3 AS GRADE_3
      , 4 AS GRADE_4
      , 5 AS GRADE_5)
);

-- 5번
WITH SALGRADE_TEMP AS
(
    SELECT *
    FROM
    (
        SELECT GRADE, LOSAL || '~' || HISAL AS LOWHI
        FROM SALGRADE
    )
    PIVOT
    (
        MAX(LOWHI)
        FOR GRADE 
        IN (1 AS GRADE_1
          , 2 AS GRADE_2
          , 3 AS GRADE_3
          , 4 AS GRADE_4
          , 5 AS GRADE_5)
    )
)
SELECT * FROM SALGRADE_TEMP
UNPIVOT ( LOSAL_HISAL  
          FOR GRADE 
          IN (GRADE_1 AS 1
            , GRADE_2 AS 2
            , GRADE_3 AS 3
            , GRADE_4 AS 4
            , GRADE_5 AS 5));

------------------------------------------------------------
--○ 5일차
-- 1번
SELECT EMPNO, NVL(MGR, 0) MGR, SAL
     , GREATEST(EMPNO, NVL(MGR, 0), SAL) AS MAX_VALUE
     , LEAST(EMPNO, NVL(MGR, 0), SAL) AS MIN_VALUE
FROM EMP
ORDER BY MAX_VALUE DESC;

-- 2번
SELECT DNAME, CASE WHEN LENGTH(DNAME) >= 6 THEN SUBSTR(DNAME, 1, 5) || '..'
                   ELSE DNAME END "DATA"
FROM DEPT;

-- 3번
SELECT EMPNO, HIREDATE, LAST_DAY(HIREDATE) "HIREDATE_MONTH_LASTDAY"
     , TO_DATE('2006/08/05', 'YYYY/MM/DD') - HIREDATE "WORK_DAY"
FROM EMP;

-- 4번
SELECT T.EMPNO, T.HIREDATE, T.WORK_DAY
FROM
(
    SELECT EMPNO, HIREDATE, LAST_DAY(HIREDATE) "HIREDATE_MONTH_LASTDAY"
         , TO_DATE('2006/08/05', 'YYYY/MM/DD') - HIREDATE "WORK_DAY"
    FROM EMP
    ORDER BY WORK_DAY DESC
) T
WHERE ROWNUM = 1;


--5번
SELECT T.EMPNO, T.HIREDATE, T.WORK_DAY
FROM
(
    SELECT EMPNO, HIREDATE, LAST_DAY(HIREDATE) "HIREDATE_MONTH_LASTDAY"
         , TO_DATE('2006/08/05', 'YYYY/MM/DD') - HIREDATE "WORK_DAY"
    FROM EMP
    ORDER BY WORK_DAY DESC
) T
WHERE ROWNUM = 1
UNION ALL
SELECT T.EMPNO, T.HIREDATE, T.WORK_DAY
FROM
(
    SELECT EMPNO, HIREDATE, LAST_DAY(HIREDATE) "HIREDATE_MONTH_LASTDAY"
         , TO_DATE('2006/08/05', 'YYYY/MM/DD') - HIREDATE "WORK_DAY"
    FROM EMP
    ORDER BY WORK_DAY
) T
WHERE ROWNUM = 1;