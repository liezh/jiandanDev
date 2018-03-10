#一、下载安装
下载地址：[https://www.mongodb.com/download-center?jmp=homepage#community](http://www.mongodb.org/downloads)
##（一）、Windows安装
win7及以下的用户需要打补丁，具体步骤不再简述，因为Windows的安装都是傻瓜似的，如果不会装，基本已经可以放弃了，开个玩笑啦。
记得配置环境变量啊。
##（二）、linux安装
首先下载的linux环境下的压缩包，放到自己想要的文件夹下，
解压命令 ------  `tar -zxvf mongodb-linux-i686-3.0.4.gz `
拷贝到指定目录 ----- ` mv  mongodb-linux-x86_64-3.0.6/ /usr/local/mongodb`
MongoDB 的可执行文件位于 bin 目录下，所以添加配置如下步骤，
打开配置文件 `sudo vim /ect/profile`，需不需要sudo，取决于你的系统权限，
将其添加到**PATH**路径中：
`export PATH=<mongodb-install-directory>/bin:$PATH`
如果你没打开默认的27017端口，在程序中是连接不上mongoDB的，
打开步骤如下：
>1. 修改iptables配置文件，添加端口权限
  `sudo vim /ect/sysconfig/iptables`
>2. 添加下面4句，开放端口
  `# mongoDB 开放27017，28017端口`
  `-A INPUT -p TCP --dport 27017 -j ACCEPT`
  `-A OUTPUT -p TCP --dport 27017 -j ACCEPT`
  `-A INPUT -p TCP --dport 28017 -j ACCEPT`
  `-A OUTPUT -p TCP --dport 28017 -j ACCEPT`
>3. 写入并退出   `:wq `
>4. 重启服务 `service iptables restart`

##特别注意：大坑在此   
32位系统开机时会报这个错误
2017-07-10T23:53:29.234+0800 W CONTROL  [main] 32-bit servers  don't have journaling enabled by default. Please use --journal if you want  durability.

开机命令需要加上  ` --journal --storageEngine=mmapv1` 的后缀命令，因为32位的系统是不支持默认开机的需要自己加上
即   
 `mongod --dbpath /root/data/db --journal --storageEngine=mmapv1`

#二、基本命令
##基本概念
  nosql不是传统的数据库系统，会有多种形式，而mongodb是其中的一个，mongoDB是一个以K-V为数据保存的数据库，而且是弱数据类型的。不像mysql、oracel、DB2这些老牌的结构化关系型数据库一样，在建表时必须要制定表的结构和字段的数据类型，并且建好的表只能保存制定好的数据结构。
  而mongoDB没有老数据库的包袱，它是可以保存任何数据结构的，并且在同一个集合上的每一条的记录的数据类型都可以不同，可以轻易的扩展。而传统的数据库要在一个表上增加一个属性时，则麻烦得多，需要在原有每一条记录中插入新增的字段，这就使得数据库的扩展性有了很大的制约，因此在实际的项目当中通常都不会轻易改动表结果的。
  但是传统的数据库也不是一事无成的，对于查询要求高的项目还是传统的数据库会比较好，因为结构化的数据和SQL的特性使得传统数据库的查询操作足够强大，适合查询密集的项目，并且由于**传统数据库都支持事务**，所以在数据一致性这一点上，相对于NoSQL有着压倒性的优势，所以**业务数据**只能存放在传统的关系型数据库上。
  而mongoDB则由于他个的可扩展性和吞吐性能，上适合存储大量存储的项目，如现在的大数据公司都是使用nosql来存储巨量的数据的，而**NoSQL则不支持事务的操作**，在数据一致性上存在着明显的缺陷。因此这就注定NoSQL只能用来存放**行为数据**了。但是优势也和明显极高的数据吞吐量，可扩展性高，这也是为什么大数据公司和人工智能公司对NoSQL如此看重了。
  并且mongodb没有表的概念，只有集合的概念，并且集合中也能有多个集合。mongoDB本质上是存储json的，数据就是以json为数据格式保存在文档中。
#常用命令
`mongod`   数据库开机命令
`--dbpath <路径>`   设置数据库的数据源路径，通常与上一个命令一起使用完成开机，如果是32位的系统，还需要加上`--journal --storageEngine=mmapv1`，因为32为系统不会默认添加
`mongo`    进入mongoDB的命令行环境
`show db`    显示当前所处的数据库
`show dbs`   显示所有的数据库
`use <数据库名>`   如果数据库已存在则**使用**，不存在则**创建并使用**
`show collections`   显示当前数据库下的**集合**
`db.<集合名>.find({"<key>":"<条件>"}) `   查询该数据库下符合的数据列表
`db.<集合名>.insert({"<key>":"<value>"}`)    插入一条记录
db.<未知集合名>.insert({"<key>":"<value>"})    会在数据库中创建这个集合并插入这条记录
`db.dropDataBase()`    删除数据库
`mongoimport --db <数据库名> --collection <集合名字> --drop  --file <文件名路径>`    批量导入
`$gt`   大于操作符
`$gte`    大于等于操作符
`$lt`   小于操作符
`$lte`   小于操作符
`$or`   或
`$and`  与
`$type`    操作符是基于BSON类型来检索集合中匹配的数据类型，并返回结果。

 | 类型 | 数字 | 备注 | 
 | :--------: | :--------: | :--: |
 | Double | 1 |  |
 | String | 2 |  |	 
 |Object | 3 |	|
 |Array	| 4 |  |	 
|Binary data | 5|	|
|Undefined|	6|	已废弃。|
|Object id |	7	 | |
|Boolean	|8	 | |
|Date	|9|	 |
|Null	|10|	|
|Regular Expression|	11| |
|JavaScript|13| |
|Symbol	|14| |
|JavaScript (with scope)	|15	 | |
|32-bit integer	|16	| |
|Timestamp	|17|	 |
|64-bit integer	|18|	|
|Min key	|255	|Query with -1.|
|Max key	|127|	| 




#功能强大的  .  
因为mongodb是一个键值对形式存储数据的数据库，也就是说value是会千变万化的，而面对着这不规则的数据结构MongoDB是怎么处理的呢？答案是使用 “点 ”语法，只要熟用点语法，那么基本上如何管理数据，就是小问题了。
例子：
```
{
  "name" : "Jon Snow",
  "gender" : "male",
  "age" : 25,
  "hobby" : ["read", "sleep", "eat", "play game"],
  "transcript" : [
    {
      "course" : "Mathematics",
      "score" : 18
    },
    {
      "course" : "English",
      "score" : 20
    },
    {
      "course" : "Chinse",
      "score" : 30
    }
  ]
}

{
  "name" : "Sansa Stark",
  "gender" : "female",
  "age" : 16,
  "hobby" : ["read", "sleep", "eat", "play game"],
  "transcript" : [
    {
      "course" : "Mathematics",
      "score" : 40
    },
    {
      "course" : "English",
      "score" : 60
    }
  ]
}

{
  "name" : "Daenerys Targaryen",
  "gender" : "male",
  "age" : 22,
  "hobby" : ["read", "sleep", "eat", "play game"],
  "transcript" : [
    {
      "course" : "Mathematics",
      "score" : 90
    }
  ]
}
```
>要通过课程名来获取用户信息，可以这样写：
`db.student.find({"transcript.course": "English"})`
结果会显示Jon Snow和Sansa Stark的记录。

>多条件查询则只需要在多个条件之间加逗号分隔即可
`db.student.find({"age" : {$gt : 18}, "transcript.course" : "English"})`
>结果会是年龄大于18，选择了英语课的同学Jon Snow。


基础教程就到这里了，往后还会继续更新，并会结合NodeJS做一些小demo，认为有用的请多关注，欢迎打赏。







