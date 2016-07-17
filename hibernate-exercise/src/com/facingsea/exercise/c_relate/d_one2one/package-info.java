/**
 * 一对一测试
 * 在任意一方  添加对方主键，作为外键列 
	* 在需要存放外键一端，增加 many-to-one 元素 
	* 在<many-to-one />再添加一个属性unique=true，这才表示一对一
	* 另一端需要使用one-to-one元素，该元素使用 property-ref 属性指定使用被关联实体主键以外的字段作为关联字段

 */
/**
 * @author wangzhf
 *
 */
package com.facingsea.exercise.c_relate.d_one2one;