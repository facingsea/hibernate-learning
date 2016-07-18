/**
 * subClass 方式进行建表，父子类数据同一张表   -------- 引入 辨别者列(discriminator) 用来区分数据记录 是父类还是子类 
 *  * 注意点： 子类表字段 都不能使用 非空约束
 *  
 *  1. 采用 subclass 的继承映射可以实现对于继承关系中父类和子类使用同一张表
	2. 因为父类和子类的实例全部保存在同一个表中，因此需要在该表内增加一列，使用该列来区分每行记录到底是哪个类的实例----这个列被称为辨别者列(discriminator).
	3. 在这种映射策略下，使用 subclass 来映射子类，使用 class 或 subclass 的 discriminator-value 属性指定辨别者列的值
	4. 所有子类定义的字段都不能有非空约束。如果为那些字段添加非空约束，那么父类的实例在那些列其实并没有值，这将引起数据库完整性冲突，导致父类的实例无法保存到数据库中

 */
/**
 * @author wangzhf
 *
 */
package com.facingsea.exercise.e_extend.a_subclass;