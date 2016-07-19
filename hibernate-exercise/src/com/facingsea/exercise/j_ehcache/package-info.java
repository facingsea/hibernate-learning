/**
 * 整合ehcache测试
 * 
 * ehcache相关配置如：
 * 	<diskStore>:指定一个目录, 当 EHCache 把数据写到硬盘上时, 将把数据写到这个文件目录下.  默认是C:\WINDOWS\Temp 
		<defaultCache>: 设置缓存的默认数据过期策略 
		<cache> 设定具体的命名缓存的数据过期策略
		每个命名缓存代表一个缓存区域，每个缓存区域有各自的数据过期策略。命名缓存机制使得用户能够在每个类以及类的每个集合的粒度上设置数据过期策略。 
		cache元素的属性   
			name:设置缓存的名字,它的取值为类的全限定名或类的集合的名字 
			maxElementsInMemory :设置基于内存的缓存中可存放的对象最大数目 
			eternal:设置对象是否为永久的,true表示永不过期,此时将忽略timeToIdleSeconds 和 timeToLiveSeconds属性; 默认值是false 
			timeToIdleSeconds:设置对象空闲最长时间,以秒为单位, 超过这个时间,对象过期。当对象过期时,EHCache会把它从缓存中清除。如果此值为0,表示对象可以无限期地处于空闲状态。 
			timeToLiveSeconds:设置对象生存最长时间,超过这个时间,对象过期。
					如果此值为0,表示对象可以无限期地存在于缓存中. 该属性值必须大于或等于 timeToIdleSeconds 属性值 
			overflowToDisk:设置基于内在的缓存中的对象数目达到上限后,是否把溢出的对象写到基于硬盘的缓存中 
			diskPersistent 当jvm结束时是否持久化对象 true false 默认是false
			diskExpiryThreadIntervalSeconds 指定专门用于清除过期对象的监听线程的轮询时间
			 memoryStoreEvictionPolicy - 当内存缓存达到最大，有新的element加入的时候， 移除缓存中element的策略。默认是LRU（最近最少使用），可选的有LFU（最不常使用）和FIFO（先进先出）

 * 
 * 
 */
/**
 * @author wangzhf
 *
 */
package com.facingsea.exercise.j_ehcache;