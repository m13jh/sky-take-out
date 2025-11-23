1. redis 字符串常用命令: 
   - SET key value
   - GET key
   - SETEX key second value // 到second时间后清除缓存
   - SETNX key value // 不存在则创建
2. 哈希:   
   - HSET key field value
   - HGET key field
   - HDEL key field
   - HKEYS key
   - HVALS key
3. 列表: 
   - LPUSH key value1 [value2]
   - RPOP key
   - LRANGE key start stop
   - LLEN key
4. 集合: 
   - SADD key member1 [member2] // 添加
   - SMEMBERS key // 获取所有成员
   - SCARD key1 [key2] // 获取成员数量
   - SINTER key1 [key2] // 集合交集
   - SUNION key1 [key2] // 集合并集
   - SREM key member1 [member2] // 删除成员
5. 有序集合, 通过score分数进行排序: 
   - ZADD key score member1 [score2 member2] //添加
   - ZRANGE key start stop [WITHSCORES]
   - ZINCRBY key increment member // 给member添加increment分数
   - ZREM key member1 [member2] // 删除
6. 通用命令: 
   - keys * // 展示所有键值
   - exists * // 存在返回1, 否则返回0
   - type * // 返回键值类型
   - del * // 删除键值
7. spring data redis
   - 查看RedisConfiguration代码