## 一 、DDL (数据定义语言)
### 基本操作
    查看所有数据库 ： show database ;
    切换数据库 ：use mydb1 ; 切换到 mydb1 数据库;

#### 1、操作数据库

    创建数据库 ： CREATE DATABASE [IF NOT EXISTS] mydb1;

#### 3、操作表
+ 创建表 
    ```
    CREATE TABLE stu1(
    	sid CHAR(6), 
    	sname VARCHAR(20),
    	age INT,
        gender VARCHAR(10)
    );
    ```

+ 查看表的结构 ：DESC 表名;
    
+ 删除表 ：DROP TABLE 表名;
    ```
    DROP TABLE stu1;
    ```

#### 修改表
    
+ a、添加列：给 stu 表添加 classname 列
    ```
    ALTER TABLE stu ADD classname varchar(100);
    ```

+ 修改列的数据类型：修改 stu 表的 gender 列类型为 CHAR(2)
    ```
    ALTER TABLE stu MODIFY gender CHAR(2);
    ```

+ c、修改列名：修改 stu 表的 gender 列名为 sex
    ```
    ALTER TABLE stu change gender sex CHAR(2);
    ```
+ d、删除列：删除 stu 表的 classname 列
    ```
    ALTER TABLE stu DROP classname;
    ```
+ e、修改表名称：修改 stu 表名称为 student
    ```
    ALTER TABLE stu RENAME TO student;
    ```

## 二、DML 数据操作语言
#### 1、插入数据  *** 所有字符串数据必须使用单引号
+ 语法 1 : INSERT INTO 表名(列名1,列名2,....);
    ```
    INSERT INTO stu (sid,sname,age,gender) VALUES ('s_1001','zhangSan',23,'male');
    INSERT INTO stu (sid,sname) VALUES ('s_1002','LiSi');
    ```
+ 语法 2 : INSERT INTO 表名 VALUES (值 1,值 2,....);
    ```
    INSERT INTO stu VALUES ('s_1004','WangWu',21,'female');
    ```
#### 2、修改数据
+ 语法: UPDATE 表名 SET 列名 1 = 值 1, ...列名 n = 值 n [WHERE 条件];
    ```
    PDATE stu SET sname='zhangSanSan',age='32',gender='female' WHERE sid='s_1001';
    UPDATE stu SET sname='liSi',age='20' WHERE age>50 AND gender='male';
    UPDATE stu SET sname='Wang' WHERE age>60 OR gender='female';
    UPDATE stu SET sname='wangWU',age='30' WHERE gender IS NULL;
    UPDATE stu SET age=age+1 WHERE sname='WangWu'
    ```

      
#### 3、删除数据
+ 语法 1: DELETE FROM 表名 [WHERE 条件]
    ```
    DELETE FROM stu WHERE sid='s_1003' ;
    DELETE FROM stu WHERE sname='chenQi' OR age>30;
    DELETE FROM stu ;
    ```
+ 语法 2: TRUNCATE TABLE 表名;
    ```
    TRUNCATE TABLE stu;
    ```

    > 两者区别: 
    a、虽然 TRUNCATE 和 DELETE 都可以删除表的所有记录,但原理不同 . DELETE 的效率没TRUNCATE 高
    b、TRUNCATE 其实属性 DDL 语句,因为它是先 DROP TABLE ,再CREATE TABLE. 而且 TRUNCATE 删除的记录是无法回滚的,但DELETE删除的记录是可以回滚的(回滚是事务的只是)

## 三、DCL 数据控制语言
#### 1、创建用户
+ 语法: CREATE USER '用户名'@地址 IDENTIFIED BY '密码';
    ```
    CREATE USER 'user1'@localhost IDENTIFIED BY '123';
    ```
#### 2、给用户授权 
+ 语法：GRANT 权限 1, … , 权限 n ON 数据库.* TO ‘用户名’@地址;
    ```
    GRANT CREATE,ALTER,DROP,INSERT,UPDATE,DELETE,SELECT ON mydb1.* TO 'user1'@localhost;
    GRANT ALL ON mydb1.* TO user2@localhost;
    ```

#### 3、撤销授权
+ 语法：REVOKE 权限 1, … , 权限 n ON 数据库.* FROM ‘用户名’@地址;
    ```
    REVOKE CREATE,ALTER,DROP ON mydb1.* FROM 'user1'@localhost;
    ```
#### 4、查看用户权限
    语法：SHOW GRANTS FOR ‘用户名’@地址;
    SHOW GRANTS FOR 'user1'@localhost;
#### 5、删除用户
+ 语法：DROP USER ‘用户名’@地址;

```
DROP USER ‘user1’@localhost;
```
#### 6、修改用户密码（以root身份）

## 四、DQL 数据查询语言

数据库执行 DQL 语句不会对数据进行改变，而是让数据库发送结果集给客户端。

    语法： 
         select 列名 ---> 要查询的列名称
         from 表名   ---> 要查询的表名称
         where 条件  ---> 行条件
         group by 分组列 ---> 对结果分组
         having 分组条件 ---> 分组后的行条件
         order by 排序列 ---> 对结果排序
         limit 起始行，行数 ---> 结果限定

    --CREATE TABLE stu (
    --	sid CHAR(6), 
    --	sname VARCHAR(50), 
    --	age INT, 
    --	gender VARCHAR(50)
    --);

    --INSERT INTO stu VALUES('S_1001', 'liuYi', 35, 'male');
    --INSERT INTO stu VALUES('S_1002', 'chenEr', 15, 'female');
    --INSERT INTO stu VALUES('S_1003', 'zhangSan', 95, 'male');
    --INSERT INTO stu VALUES('S_1004', 'liSi', 65, 'female');
    --INSERT INTO stu VALUES('S_1005', 'wangWu', 55, 'male');
    --INSERT INTO stu VALUES('S_1006', 'zhaoLiu', 75, 'female');
    --INSERT INTO stu VALUES('S_1007', 'sunQi', 25, 'male');
    --INSERT INTO stu VALUES('S_1008', 'zhouBa', 45, 'female');
    --INSERT INTO stu VALUES('S_1009', 'wuJiu', 85, 'male');
    --INSERT INTO stu VALUES('S_1010', 'zhengShi', 5, 'female');
    --INSERT INTO stu VALUES('S_1011', 'xxx', NULL, NULL);

    --CREATE TABLE emp(
    --	empno INT, 
    --	ename VARCHAR(50), 
    --	job VARCHAR(50), 
    --	mgr INT, 
    --	hiredate DATE,
    --	sal DECIMAL(7,2), 
    --	comm decimal(7,2), 
    --	deptno INT
    --) ;
    --INSERT INTO emp values(7369,'SMITH','CLERK',7902,'1980-12-17',800,NULL,20);
    --INSERT INTO emp values(7499,'ALLEN','SALESMAN',7698,'1981-02-20',1600,300,30);
    --INSERT INTO emp values(7521,'WARD','SALESMAN',7698,'1981-02-22',1250,500,30);
    --INSERT INTO emp values(7566,'JONES','MANAGER',7839,'1981-04-02',2975,NULL,20);
    --INSERT INTO emp values(7654,'MARTIN','SALESMAN',7698,'1981-09-28',1250,1400,30);
    --INSERT INTO emp values(7698,'BLAKE','MANAGER',7839,'1981-05-01',2850,NULL,30);
    --INSERT INTO emp values(7782,'CLARK','MANAGER',7839,'1981-06-09',2450,NULL,10);
    --INSERT INTO emp values(7788,'SCOTT','ANALYST',7566,'1987-04-19',3000,NULL,20);
    --INSERT INTO emp values(7839,'KING','PRESIDENT',NULL,'1981-11-17',5000,NULL,10);
    --INSERT INTO emp values(7844,'TURNER','SALESMAN',7698,'1981-09-08',1500,0,30);
    --INSERT INTO emp values(7876,'ADAMS','CLERK',7788,'1987-05-23',1100,NULL,20);
    --INSERT INTO emp values(7900,'JAMES','CLERK',7698,'1981-12-03',950,NULL,30);
    --INSERT INTO emp values(7902,'FORD','ANALYST',7566,'1981-12-03',3000,NULL,20);
    --INSERT INTO emp values(7934,'MILLER','CLERK',7782,'1982-01-23',1300,NULL,10);

    --CREATE TABLE dept(
    --	deptno INT, 
    --	dname varchar(14),
    --	loc varchar(13)
    --);
    --INSERT INTO dept values(10, 'ACCOUNTING', 'NEW YORK');
    --INSERT INTO dept values(20, 'RESEARCH', 'DALLAS');
    --INSERT INTO dept values(30, 'SALES', 'CHICAGO');
    --INSERT INTO dept values(40, 'OPERATIONS','BEIJING');

#### 1、基础查询 
+ 查询所有列 SELECT * FROM 表名；( * : 通配符，表示所有列)
    ```
    SELECT * FROM stu;
    ```
+ 查询指定列 SELECT 列名 1，列名 2,...列名 n FROM 表名;
    ```
    SELECT sid, sname FROM stu;
    ```
#### 2、条件查询
+ 条件查询就是在查询时给出 WHERE 子句，在 WHERE 子句中可以使用如下运算符及关键字：
    =、!=、<>、<、>、<=、>=;
    BETWEEN...AND；
    IN(set);
    IS NULL;
    AND;
    OR;
    NOT;

    
+ a、查询性别为女，并且年龄小于 50 的记录
    ```
    SELECT * FROM stu WHERE gender='female' AND age<50;
    ```
+ b、查询学号为 s_1001,或者姓名为 liSi 的记录
    ```
    SELECT * FROM stu WHERE sid='s_1001' OR sname='liSi';
    ```
+ c、查询学号为 S_1001,S_1002,S_1003 的记录
    ```
    SELECT * FROM stu WHERE sid IN ('S_1001','S_1002','S_1003')
    ```
+ d、查询学号不是 S_1001,S_1002,S_1003 的记录
    ```
    SELECT * FROM stu WHERE sid NOT IN('S_1001','S_1002','S_1003')
    ```
+ e、查询年龄为null的记录
    ``` 
    SELECT * FROM stu WHERE age IS NULL;
    ```
+ f、查询年龄在 20 到 40 之间的学生记录
    ```
    SELECT * FROM stu WHERE age>=20 AND age<=40;
    SELECT * FROM stu WHERE BETWEEN 20 AND 40;
    ```
+ g、查询性别非男的学生记录
    ```
    SELECT * FROM stu WHERE gender!='male';
    SELECT * FROM stu WHERE NOT gender='male';
    ```
+ h、查询姓名不为null的学生记录
    ```
    SELECT * FROM stu WHERE NOT sname IS NULL;
    SELECT * FROM stu WHERE sname IS NOT NULL;
    ```
    

#### 3、模糊查询
+ SELECT 字段 FROM 表 WHERE 某字段 Like 条件

    > 其中关于条件,SQL 提供了两种匹配模式：
    a、% ：表示任意 0个或多个字符。可匹配任意类型和长度的字符，有些情况下若是中文，请使用两个百分号（%%）表示
    b、_ : 表示任意单个字符。匹配单个任意字符，它常用来限制表达式的字符长度语句。

    
+ a 、查询姓名由5个字母构成的学生记录
    ```
    SELECT * FROM stu WHERE sname LIKE '_____';
    ```
+ b、查询姓名由5个字母组成，并且第5个字母为 "i"的学生记录
    ```
    SELECT * FROM stu WHERE sname LIKE '____i';
    ```
+ c、查询姓名以“z”开头的学生记录 其中 “%”匹配 0~n 个任何字母
    ```
    SELECT * FROM stu WHERE sname LIKE 'z%';
    ```
+ d、查询姓名中第2个字母为 'i' 的学生记录
    ```
    SELECT * FROM stu WHERE sname LIKE '_i%';
    ```
+ e、查询姓名中包含 'a' 字母的学生记录
    ```
    SELECT * FROM stu WHERE sname LIKE '%a%';
    ```

#### 4、字段控制插叙
+ a、去掉重复记录
    去除重复记录（两行或两行以上记录中系列上的数据都相同），例如 emp 表中 sal 字段就存在相同的记录。
    当只查询 emp 表的 sal字段时，那么会出现重复记录，那么想去除重复记录，需要使用 DISTINCT : 
    ```
    SELECT DISTINCT sal FROM emp;
    ```
+ b、查看雇员的月薪与佣金之和
    因为 sal 和 comm 两列的类型都是数值类型，所以可以做加运算。如果 sal 或 comm 中有一个字段不是数值类型，那么会出错。
    ```
    SELECT *, sal+comm FROM emp;
    ```
    comm 列有很多记录的值为NULL,因为任何东西与NULL相加结果还是NULL，所以结果运算可能会出现NULL。下面使用了把 NULl转换为数值 0 的函数 IFNULL
    ```
    SELECT *,sal+IFNULL(comm,0) FROM emp;
    ```
+ c、给列名添加别名
    在上面查询中出现列名为 sal+IFNULL(comm,0) ,这很不美观，现在我们给这一列给出一个别名，为 total：
    ```
    SELECT *,sal+IFNULL(comm,0) AS total FROM emp;
    ```
    给列起别名时，是可以省略 AS 关键字的
    ```
    SELECT *,sal+IFNULL(comm,0) total FROM emp;
    ```

#### 5、排序
+ a、查询所有学生记录，按年龄升序排序
    ```
    SELECT * FROM stu ORDER BY age ASC;
    SELECT * FROM stu ORDER BY age;
    ```
+ b、查询所有学生记录，按年龄降序排序
    ```
    SELECT * FROM stu ORDER by age DESC;
    ```
+ c、查询所有雇员，按月薪降序排序，如果月薪相同时，按编号升序排序
    ```
    SELECT * FROM emp ORDER BY sal DESC , empno ASC;
    ```

#### 6、聚合函数
> 聚合函数是用来做纵向运算的函数：
    COUNT() : 统计指定列不为 NULL 的记录行数；
    MAX() : 计算指定列的最大值，如果指定列是字符串类型，那么使用字符串排序运算；
    MIN() : 计算指定列的最小值，如果指定列是字符串类型，那么使用字符串排序运算；
    SUM() : 计算指定列的数值和，如果指定列类型不是数值类型，那么计算结果为 0 ；
    AVG() : 计算指定列的平均值，如果指定列类型不是数值类型，那么计算结果为 0 ；

+ a、COUNT 当需要纵向统计时可以使用 COUNT()

    查询 emp 表中记录数
    ```
    SELECT COUNT(*) AS cnt FROM emp;
    ```
    查询 emp 表中有佣金的人数,注意，因为 count() 函数中给出的是 comm 列，那么只统计 comm 列非 NULL 的行数
    ```
    SELECT COUNT(comm) cnt FROM emp;
    ```
    查询 emp 表中月薪大于 2500 的人数
    ```
    SELECT COUNT(*) cnt FROM emp WHERE sal+IFNULL(comm,0) > 2500;
    ```
    查询有佣金的人数,以及有领导的人数
    ```
    SELECT COUNT(comm), COUNT(mgr) FROM emp;
    ```
+ b、SUM 和 AVG :当需要纵向求和时使用 sum()函数

    查询所有雇员月薪和:
    ```
    SELECT SUM(sal) totalSal FROM emp;
    ```
    查询所有雇员月薪和,以及所有雇员佣金和
    ```
    SELECT SUM(sal),SUM(comm) FROM emp;
    ```
    查询所有雇员月薪+佣金和
    ```
    SELECT SUM(sal+IFNULL(comm,0)) FROM emp;
    ```
    统计所有员工平均工资
    ```
    SELECT SUM(sal), COUNT(sal) FROM emp;
    SELECT AVG(sal) FROM emp;
    ```
    MAX 和 MIN 
    查询最高工资和最低工资
    ```
    SELECT MAX(sal),MIN(sal) FROM emp;
    ```


#### 7、分组查询
+ a、分组查询 : 当需要分组查询时使用 GROUP BY 子句,例如查询每个部门的工资和,这说明要使用部分来分组

    查询每个部门的部门编号和每个部门的工资和
    ```
    SELECT deptno, SUM(sal)FROM empGROUP BY deptno;
    ```
    查询每个部门的部门编号以及每个部门的人数：
    ```
    SELECT deptno,COUNT(*) FROM emp GROUP BY deptno;
    ```
    查询每个部门的部门编号以及每个部门工资大于 1500 的人数：
    ```
    SELECT deptno ,COUNT(*) FROM emp WHERE sal>1500 GROUP BY deptno;
    ```
+ b、HAVING 子句

    查询工资总和大于 9000 的部门编号以及工资和：
    ```
    SELECT deptno, SUM(sal) FROM emp GROUP BY deptno HAVING SUM(sal) > 9000;
    ```
    注意，WHERE 是对分组前记录的条件，如果某行记录没有满足 WHERE 子句的条件，那么这行记录不会参加分组；而 HAVING 是对分组后数据的约束。

#### 8.LIMIT：用来限定查询结果的起始行，以及总行数。

+ 查询 5 行记录，起始行从 0 开始

    ```             
    SELECT * FROM emp LIMIT 0, 5;
    ```
    注意，起始行从 0 开始，即第一行开始！

+ 查询 10 行记录，起始行从 3 开始
    ```
    SELECT * FROM emp LIMIT 3, 10;
    ```

#### 9.多表连接查询： 表连接分为内连接和外连接。
    区别：内连接仅选出两张表中互相匹配的记录，外连接会选出其他不匹配的记录。

+ 内连接
    ```
    select staff.name,deptname from staff,deptno1 where staff.name=deptno1.name;
    ```
+ 外连接分为左连接和右连接

    左连接：包含左边表中所有的记录，右边表中没有匹配的记录显示为 NULL。
    右连接：包含右边表中所有的记录，左边表中没有匹配的记录显示为 NULL。

    外连接(左连接)：
    ```
    SELECT staff.name,deptname FROM staff LEFT JOIN deptno1 ON staff.name=deptno1.name;
    ```

    外连接(右连接)
    ```
    SELECT deptname, deptno1.name 
    FROM staff 
    RIGHT JOIN deptno1 ON deptno1.name = staff.name;
    ```