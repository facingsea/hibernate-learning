/**
 * 各种查询测试

	Hibernate 提供了以下几种检索对象的方式
	•	导航对象图检索方式:  根据已经加载的对象导航到其他对象（Customer关联Order）
	•	OID 检索方式:  按照对象的 OID 来检索对象
	•	HQL 检索方式: 使用面向对象的 HQL 查询语言
	•	QBC 检索方式: 使用 QBC(Query By Criteria) API 来检索对象. 这种 API 封装了基于字符串形式的查询语句, 提供了更加面向对象的查询接口. 
	•	本地 SQL 检索方式: 使用本地数据库的 rSQL 查询语句

	HQL(Hibernate Query Language) 是面向对象的查询语言, 它和 SQL 查询语言有些相似. 在 Hibernate 提供的各种检索方式中, HQL 是使用最广的一种检索方式. 它有如下功能:
	•	在查询语句中设定各种查询条件
	•	支持投影查询, 即仅检索出对象的部分属性
	•	支持分页查询
	•	支持连接查询
	•	支持分组查询, 允许使用 HAVING 和 GROUP BY 关键字
	•	提供内置聚集函数, 如 sum(), min() 和 max()
	•	能够调用 用户定义的 SQL 函数或标准的 SQL 函数
	•	支持子查询
	•	支持动态绑定参数
	HQL 检索方式包括以下步骤:
	•	通过 Session 的 createQuery() 方法创建一个 Query 对象, 它包括一个 HQL 查询语句. HQL 查询语句中可以包含命名参数
	•	动态绑定参数
	•	调用 Query 的 list() 方法执行查询语句. 该方法返回 java.util.List 类型的查询结果, 在 List 集合中存放了符合查询条件的持久化对象. 
		Qurey 接口支持方法链编程风格（即.xxx.xxx的形式）, 它的 setXxx() 方法返回自身实例, 而不是 void 类型
		HQL vs SQL:
	•	HQL 查询语句是面向对象的, Hibernate 负责解析 HQL 查询语句, 然后根据对象-关系映射文件中的映射信息, 把 HQL 查询语句翻译成相应的 SQL 语句. HQL 查询语句中的主体是域模型中的类及类的属性
	•	SQL 查询语句是与关系数据库绑定在一起的. SQL 查询语句中的主体是数据库表及表的字段. 
		绑定参数:
	•	Hibernate 的参数绑定机制依赖于 JDBC API 中的 PreparedStatement 的预定义 SQL 语句功能.
	•	HQL 的参数绑定由两种形式:
		按参数名字绑定: 在 HQL 查询语句中定义命名参数, 命名参数以 “:” 开头.
		按参数位置绑定: 在 HQL 查询语句中用 “?” 来定义参数位置
	•	相关方法:
			setEntity(): 把参数与一个持久化类绑定
			setParameter(): 绑定任意类型的参数. 该方法的第三个参数显式指定 Hibernate 映射类型
	HQL 采用 ORDER BY 关键字对查询结果排序


 */
/**
 * @author wangzhf
 *
 */
package com.facingsea.exercise.h_select;