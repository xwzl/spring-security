# 1. 下面是官方套话介绍elasticsearch：

ElasticSearch是一个基于Lucene的搜索服务器。它提供了一个分布式多用户能力的全文搜索引擎，基于RESTful web接口。
Elasticsearch是用Java开发的，并作为Apache许可条款下的开放源码发布，是当前流行的企业级搜索引擎。设计用于云计算
中，能够达到实时搜索，稳定，可靠，快速，安装使用方便。

我们建立一个网站或应用程序，并要添加搜索功能，但是想要完成搜索工作的创建是非常困难的。我们希望搜索解决方案要运行
速度快，我们希望能有一个零配置和一个完全免费的搜索模式，我们希望能够简单地使用JSON通过HTTP来索引数据，我们希望我
们的搜索服务器始终可用，我们希望能够从一台开始并扩展到数百台，我们要实时搜索，我们要简单的多租户，我们希望建立一
个云的解决方案。因此我们利用Elasticsearch来解决所有这些问题及可能出现的更多其它问题。

# 2. docker安装elasticsearch

![](https://images2018.cnblogs.com/blog/546172/201803/546172-20180319184654784-2073785322.png)

### 2.1 选择一个版本，拉取镜像

docker pull elasticsearch:2.4.4

![](https://images2018.cnblogs.com/blog/546172/201803/546172-20180319184728844-824596594.png)

**查看镜像** 

docker images

### 2.2 通过镜像，启动一个容器，并将9200和9300端口映射到本机

docker run -d -p 9200:9200 -p 9300:9300 --name elasticsearch elasticsearch:2.4.4

**查看已启动容器**

docker ps

验证是否安装成功？访问：

http://linuxport:9200/

### 2.3 安装插件，先进入容器：

    docker exec -it 4d34fbf944a5 /bin/bash
    
    cd bin
    
    ls
    
    plugin install mobz/elasticsearch-head
     
    /**（低版本执行命令有所不同）**/
    plugin -install mobz/elasticsearch-head
    
http://linuxport:9200/_plugin/head/


