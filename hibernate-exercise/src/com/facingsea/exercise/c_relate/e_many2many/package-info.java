/**
 *  多对多关联测试
 *  
 *  多对多没有父子关系， 所以不建议级联
 *  * 当两个持久对象，建立关系， 在关系表插入一条数据   （防止重复数据 ： 
	 *  1) inverse=true 使一端放弃外键维护 
	 *  2) 不要建立双向关系）
 */
/**
 * @author wangzhf
 *
 */
package com.facingsea.exercise.c_relate.e_many2many;