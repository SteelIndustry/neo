------------------------------------------------------------
--�� 4����
-- 1��
SELECT *
FROM 
(
    SELECT *
    FROM EMP
    ORDER BY SAL DESC
)
WHERE ROWNUM <=5;

-- 2��
SELECT *
FROM
(
    SELECT E.*
         , ROW_NUMBER() OVER(ORDER BY SAL DESC) RN
    FROM EMP E
)
WHERE RN BETWEEN 6 AND 10;

-- 3��
-- ROWNUM ������ �������� ó���ǰ� �� ��, SORT, AGGREGATION(�����Լ�)��
-- ����Ǳ� ������ �Ҵ�ȴ�. ��, ROWNUM�� ���� �Ҵ�� ���Ŀ��� �����Ѵ�.
-- ���� ��� WHERE ROWNUM > 1�� ������ TRUE�� �ƴϱ� ������ ROWNUM�� 2�� ��������
-- �ʴ´�. ���� ROWNUM=2 �� ���,  ù ��° ���� ������ 2�� �ƴϴϱ� ������,
-- ���� ���� �ڵ����� 1�� �Ǿ������. �� �� ���� 2�� �������� �����Ƿ�
-- �ش� ���� �� ��������.
-- SELECT �� ó�� ������ ���ø���, FROM/WHERE ���� ���� ó���� ��
-- ������ ROW�� ���� ROWNUM�� �ο��ȴ�. ���� �ο��� ���� 1������ 1�� �����Ѵ�.
-- ���� WHERE ROWNUM > 5�� ���, ���ǿ� �ش��ϴ� ROW�� �������� �ʴ´�.
-- 

-- 4��
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

-- 5��
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
--�� 5����
-- 1��
SELECT EMPNO, NVL(MGR, 0) MGR, SAL
     , GREATEST(EMPNO, NVL(MGR, 0), SAL) AS MAX_VALUE
     , LEAST(EMPNO, NVL(MGR, 0), SAL) AS MIN_VALUE
FROM EMP
ORDER BY MAX_VALUE DESC;

-- 2��
SELECT DNAME, CASE WHEN LENGTH(DNAME) >= 6 THEN SUBSTR(DNAME, 1, 5) || '..'
                   ELSE DNAME END "DATA"
FROM DEPT;

-- 3��
SELECT EMPNO, HIREDATE, LAST_DAY(HIREDATE) "HIREDATE_MONTH_LASTDAY"
     , TO_DATE('2006/08/05', 'YYYY/MM/DD') - HIREDATE "WORK_DAY"
FROM EMP;

-- 4��
SELECT T.EMPNO, T.HIREDATE, T.WORK_DAY
FROM
(
    SELECT EMPNO, HIREDATE, LAST_DAY(HIREDATE) "HIREDATE_MONTH_LASTDAY"
         , TO_DATE('2006/08/05', 'YYYY/MM/DD') - HIREDATE "WORK_DAY"
    FROM EMP
    ORDER BY WORK_DAY DESC
) T
WHERE ROWNUM = 1;


--5��
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