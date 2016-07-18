/**
 * Hibernate检索策略测试
 * 
hiebenate检索策略分为类级别的检索策略包括：立即检索、延迟检索。关系级别的检索策略包括：立即检索、延迟检索、迫切左外连接检索。
类级别的检索默认的检索策略是立即检索。在Hibernate映射文件中，通过在<class>上配置 lazy属性来确定检索策略。对于Session的检索方式，类级别检索策略仅适用于load方法； 也就说，对于get、qurey检索，持久化对象都会被立即加载而不管lazy是false还是true.一般来说，我们检索对象就是要访问它，因此立即 检索是通常的选择。由于load方法在检索不到对象时会抛出异常（立即检索的情况下），因此我个人并不建议使用load检索；而由 于<class>中的lazy属性还影响到多对一及一对一的检索策略，因此使用load方法就更没必要了。
   关系级别的检索策略主要体现在set集合标签中，对检索策略的影响取决于lazy和fecth属性：
   Lazy：主要决定orders集合被初始化的时机，即到底是在加载Customer对象时就被初始化，还是在程序访问orders集合时被初始化
   Fetch：取值为select或subselect时，决定初始化orders的查询语句的形式，若取值为join，则决定orders集合被初始化的时机
   若把fecth设置为join，lazy属性将被忽略
注：fetch默认值是select，lazy的默认值是true

迫切左外连接检索(优先级最高)
fetch 属性有三种值
1.当用到fetch=join时一定是迫切左外连接检索.
2.当用到fetch=select一定不采用迫切左外连接检索,检索方式与lazy保持一致.
3.当用到fetch=subselect,检索多个Customer时,采用嵌套的子查询订单. 

注：fecth和lazy相比，fecth的优先级高



 */
/**
 * @author wangzhf
 *
 */
package com.facingsea.exercise.f_search;