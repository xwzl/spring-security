<div id="content_views" class="markdown_views prism-atom-one-dark">
<!-- flowchart 箭头图标 勿删 -->

<p>学习本章内容的前提：<br>
1.能独立搭建SpringBoot项目。(<a href="https://blog.csdn.net/chen_2890/article/details/83722436" rel="nofollow" target="_blank">SpringBoot的快速入门</a>）<br>
2.Elasticsearch环境搭建完毕。（<a href="https://blog.csdn.net/chen_2890/article/details/83757022" rel="nofollow" target="_blank">Elasticsearch环境搭建和介绍（Windows）</a>）</p>
<h3><a name="t0"></a><a id="1__3" target="_blank"></a>1 前奏</h3>
<p>Elasticsearch提供的Java客户端有一些不太方便的地方：</p>
<ul>
<li>很多地方需要拼接Json字符串，在java中拼接字符串有多恐怖你应该懂的</li>
<li>需要自己把对象序列化为json存储</li>
<li>查询到结果也需要自己反序列化为对象</li>
</ul>
<p>因此，我们这里就不讲解原生的Elasticsearch客户端API了。</p>
<p>而是学习Spring提供的套件：Spring Data Elasticsearch</p>
<h3><a name="t1"></a><a id="11__15" target="_blank"></a>1.1 简介</h3>
<p>Spring Data Elasticsearch是Spring Data项目下的一个子模块。</p>
<p>查看 Spring Data的官网：<a href="http://projects.spring.io/spring-data/" rel="nofollow" target="_blank">http://projects.spring.io/spring-data/</a><br>
<img src="https://img-blog.csdnimg.cn/20181109111702125.png?x-oss-process=imageCode/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2NoZW5fMjg5MA==,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"><br>
Spring Data 的使命是给各种数据访问提供统一的编程接口，不管是关系型数据库（如MySQL），还是非关系数据库（如Redis），或者类似Elasticsearch这样的索引数据库。从而简化开发人员的代码，提高开发效率。</p>
<p>包含很多不同数据操作的模块：<br>
<img src="https://img-blog.csdnimg.cn/20181109113216741.png?x-oss-process=imageCode/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2NoZW5fMjg5MA==,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"></p>
<p>Spring Data Elasticsearch的页面：<a href="https://projects.spring.io/spring-data-elasticsearch/" rel="nofollow" target="_blank">https://projects.spring.io/spring-data-elasticsearch/</a><br>
<img src="https://img-blog.csdnimg.cn/20181109113312500.png?x-oss-process=imageCode/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2NoZW5fMjg5MA==,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"></p>
<p>特征：</p>
<ul>
<li>支持Spring的基于<validateCode>@Configuration</validateCode>的java配置方式，或者XML配置方式</li>
<li>提供了用于操作ES的便捷工具类<validateCode>ElasticsearchTemplate</validateCode>。包括实现文档到POJO之间的自动智能映射。</li>
<li>利用Spring的数据转换服务实现的功能丰富的对象映射</li>
<li>基于注解的元数据映射方式，而且可扩展以支持更多不同的数据格式</li>
<li>根据持久层接口自动生成对应实现方法，无需人工编写基本操作代码（类似mybatis，根据接口自动得到实现）。当然，也支持人工定制查询</li>
</ul>
<h3><a name="t2"></a><a id="12_Elasticsearch_39" target="_blank"></a>1.2 Elasticsearch基本概念</h3>
<p>Elasticsearch也是基于Lucene的全文检索库，本质也是存储数据，很多概念与MySQL类似的。</p>
<p>对比关系：</p>
<p>索引库（indices）--------------------------------Databases 数据库</p>
<pre class="prettyprint"><validateCode class="has-numbering" onclick="mdcp.copyCode(event)">类型（type）-----------------------------Table 数据表

文档（Document）----------------Row 行

字段（Field）-------------------Columns 列 
<div class="hljs-button {2}" data-title="复制"></div></validateCode></pre>
<p>详细说明：</p>

<div class="table-box"><table>
<thead>
<tr>
<th>概念</th>
<th>说明</th>
</tr>
</thead>
<tbody>
<tr>
<td>索引库（indices)</td>
<td>indices是index的复数，代表许多的索引，</td>
</tr>
<tr>
<td>类型（type）</td>
<td>类型是模拟mysql中的table概念，一个索引库下可以有不同类型的索引，比如商品索引，订单索引，其数据格式不同。不过这会导致索引库混乱，因此未来版本中会移除这个概念</td>
</tr>
<tr>
<td>文档（document）</td>
<td>存入索引库原始的数据。比如每一条商品信息，就是一个文档</td>
</tr>
<tr>
<td>字段（field）</td>
<td>文档中的属性</td>
</tr>
<tr>
<td>映射配置（mappings）</td>
<td>字段的数据类型、属性、是否索引、是否存储等特性</td>
</tr>
</tbody>
</table></div><p>是不是与Lucene中的概念类似。</p>
<p>另外，在Elasticsearch有一些集群相关的概念：</p>
<ul>
<li>索引集（Indices，index的复数）：逻辑上的完整索引</li>
<li>分片（shard）：数据拆分后的各个部分</li>
<li>副本（replica）：每个分片的复制</li>
</ul>
<h4><a id="_73" target="_blank"></a>注意：</h4>
<p>Elasticsearch本身就是分布式的，因此即便你只有一个节点，Elasticsearch默认也会对你的数据进行分片和副本操作，当你向集群添加新数据时，数据也会在新加入的节点中进行平衡。</p>
<h3><a name="t3"></a><a id="21_SpringBoot__77" target="_blank"></a>2.1 创建SpringBoot 项目</h3>
<p>首先我们要新建一个SpringBoot项目，再进行Elasticsearch的整合。</p>
<p>pom依赖：</p>
<pre class="prettyprint"><validateCode class="prism language-xml has-numbering" onclick="mdcp.copyCode(event)"><span class="token prolog">&lt;?xml version="1.0" encoding="UTF-8"?&gt;</span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>project</span> <span class="token attr-name">xmlns</span><span class="token attr-value"><span class="token punctuation">=</span><span class="token punctuation">"</span>http://maven.apache.org/POM/4.0.0<span class="token punctuation">"</span></span> <span class="token attr-name"><span class="token namespace">xmlns:</span>xsi</span><span class="token attr-value"><span class="token punctuation">=</span><span class="token punctuation">"</span>http://www.w3.org/2001/XMLSchema-instance<span class="token punctuation">"</span></span>
<span class="token attr-name"><span class="token namespace">xsi:</span>schemaLocation</span><span class="token attr-value"><span class="token punctuation">=</span><span class="token punctuation">"</span>http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd<span class="token punctuation">"</span></span><span class="token punctuation">&gt;</span></span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>modelVersion</span><span class="token punctuation">&gt;</span></span>4.0.0<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>modelVersion</span><span class="token punctuation">&gt;</span></span>

<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>groupId</span><span class="token punctuation">&gt;</span></span>com.czxy<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>groupId</span><span class="token punctuation">&gt;</span></span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>artifactId</span><span class="token punctuation">&gt;</span></span>es-demo<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>artifactId</span><span class="token punctuation">&gt;</span></span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>version</span><span class="token punctuation">&gt;</span></span>0.0.1-SNAPSHOT<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>version</span><span class="token punctuation">&gt;</span></span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>packaging</span><span class="token punctuation">&gt;</span></span>jar<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>packaging</span><span class="token punctuation">&gt;</span></span>

<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>name</span><span class="token punctuation">&gt;</span></span>es-demo<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>name</span><span class="token punctuation">&gt;</span></span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>description</span><span class="token punctuation">&gt;</span></span>Demo project for Spring Boot<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>description</span><span class="token punctuation">&gt;</span></span>

<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>parent</span><span class="token punctuation">&gt;</span></span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>groupId</span><span class="token punctuation">&gt;</span></span>org.springframework.boot<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>groupId</span><span class="token punctuation">&gt;</span></span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>artifactId</span><span class="token punctuation">&gt;</span></span>spring-boot-starter-parent<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>artifactId</span><span class="token punctuation">&gt;</span></span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>version</span><span class="token punctuation">&gt;</span></span>2.0.4.RELEASE<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>version</span><span class="token punctuation">&gt;</span></span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>relativePath</span><span class="token punctuation">/&gt;</span></span> 
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>parent</span><span class="token punctuation">&gt;</span></span>

<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>properties</span><span class="token punctuation">&gt;</span></span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>project.build.sourceEncoding</span><span class="token punctuation">&gt;</span></span>UTF-8<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>project.build.sourceEncoding</span><span class="token punctuation">&gt;</span></span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>project.reporting.outputEncoding</span><span class="token punctuation">&gt;</span></span>UTF-8<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>project.reporting.outputEncoding</span><span class="token punctuation">&gt;</span></span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>java.version</span><span class="token punctuation">&gt;</span></span>1.8<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>java.version</span><span class="token punctuation">&gt;</span></span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>properties</span><span class="token punctuation">&gt;</span></span>

<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>dependencies</span><span class="token punctuation">&gt;</span></span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>dependency</span><span class="token punctuation">&gt;</span></span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>groupId</span><span class="token punctuation">&gt;</span></span>org.springframework.boot<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>groupId</span><span class="token punctuation">&gt;</span></span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>artifactId</span><span class="token punctuation">&gt;</span></span>spring-boot-starter<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>artifactId</span><span class="token punctuation">&gt;</span></span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>dependency</span><span class="token punctuation">&gt;</span></span>

<span class="token comment">&lt;!-- elasticsearch启动器 (必须)--&gt;</span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>dependency</span><span class="token punctuation">&gt;</span></span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>groupId</span><span class="token punctuation">&gt;</span></span>org.springframework.boot<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>groupId</span><span class="token punctuation">&gt;</span></span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>artifactId</span><span class="token punctuation">&gt;</span></span>spring-boot-starter-data-elasticsearch<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>artifactId</span><span class="token punctuation">&gt;</span></span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>dependency</span><span class="token punctuation">&gt;</span></span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>dependency</span><span class="token punctuation">&gt;</span></span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>groupId</span><span class="token punctuation">&gt;</span></span>org.springframework.boot<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>groupId</span><span class="token punctuation">&gt;</span></span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>artifactId</span><span class="token punctuation">&gt;</span></span>spring-boot-starter-test<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>artifactId</span><span class="token punctuation">&gt;</span></span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>scope</span><span class="token punctuation">&gt;</span></span>test<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>scope</span><span class="token punctuation">&gt;</span></span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>dependency</span><span class="token punctuation">&gt;</span></span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>dependencies</span><span class="token punctuation">&gt;</span></span>

<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>build</span><span class="token punctuation">&gt;</span></span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>plugins</span><span class="token punctuation">&gt;</span></span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>plugin</span><span class="token punctuation">&gt;</span></span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>groupId</span><span class="token punctuation">&gt;</span></span>org.springframework.boot<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>groupId</span><span class="token punctuation">&gt;</span></span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;</span>artifactId</span><span class="token punctuation">&gt;</span></span>spring-boot-maven-plugin<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>artifactId</span><span class="token punctuation">&gt;</span></span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>plugin</span><span class="token punctuation">&gt;</span></span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>plugins</span><span class="token punctuation">&gt;</span></span>
<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>build</span><span class="token punctuation">&gt;</span></span>


<span class="token tag"><span class="token tag"><span class="token punctuation">&lt;/</span>project</span><span class="token punctuation">&gt;</span></span>

<div class="hljs-button {2}" data-title="复制"></div></validateCode></pre>
<p>application.properties文件配置：</p>
<pre class="prettyprint"><validateCode class="prism language-properties has-numbering" onclick="mdcp.copyCode(event)">## Elasticsearch配置文件（必须）
## 该配置和Elasticsearch的elasticsearch.yml中的配置信息有关

spring.data.elasticsearch.cluster-name=my-application
spring.data.elasticsearch.cluster-nodes=127.0.0.1:9300
<div class="hljs-button {2}" data-title="复制"></div></validateCode><ul class="pre-numbering" style=""><li style="color: rgb(153, 153, 153);">1</li><li style="color: rgb(153, 153, 153);">2</li><li style="color: rgb(153, 153, 153);">3</li><li style="color: rgb(153, 153, 153);">4</li><li style="color: rgb(153, 153, 153);">5</li></ul></pre>
<p>Elasticsearch的elasticsearch.yml中的配置信息如下：</p>
<pre class="prettyprint"><validateCode class="prism language-xml has-numbering" onclick="mdcp.copyCode(event)"># ======================== Elasticsearch Configuration =========================
#
# NOTE: Elasticsearch comes with reasonable defaults for most settings.
#       Before you set out to tweak and tune the configuration, make sure you
#       understand what are you trying to accomplish and the consequences.
#
# The primary way of configuring a node is via this file. This template lists
# the most important settings you may want to configure for a production cluster.
#
# Please consult the documentation for further information on configuration options:
# https://www.elastic.co/guide/en/elasticsearch/reference/index.html
#
# ---------------------------------- Cluster -----------------------------------
#
# Use a descriptive name for your cluster:
#
cluster.name: my-application
#
# ------------------------------------ Node ------------------------------------
#
# Use a descriptive name for the node:
#
node.name: node-1
#
# Add custom attributes to the node:
#
#node.attr.rack: r1
#
# ----------------------------------- Paths ------------------------------------
#
# Path to directory where to store the data (separate multiple locations by comma):
#
#path.data: /path/to/data
#
# Path to log files:
#
#path.logs: /path/to/logs
#
# ----------------------------------- Memory -----------------------------------
#
# Lock the memory on startup:
#
#bootstrap.memory_lock: true
#
# Make sure that the heap size is set to about half the memory available
# on the system and that the owner of the process is allowed to use this
# limit.
#
# Elasticsearch performs poorly when the system is swapping the memory.
#
# ---------------------------------- Network -----------------------------------
#
# Set the bind address to a specific IP (IPv4 or IPv6):
#
network.host: 0.0.0.0
#
# Set a custom port for HTTP:
#
http.port: 9200
#
# For more information, consult the network module documentation.
#
# --------------------------------- Discovery ----------------------------------
#
# Pass an initial list of hosts to perform discovery when new node is started:
# The default list of hosts is ["127.0.0.1", "[::1]"]
#
#discovery.zen.ping.unicast.hosts: ["host1", "host2"]
#
# Prevent the "split brain" by configuring the majority of nodes (total number of master-eligible nodes / 2 + 1):
#
#discovery.zen.minimum_master_nodes: 
#
# For more information, consult the zen discovery module documentation.
#
# ---------------------------------- Gateway -----------------------------------
#
# Block initial recovery after a full cluster restart until N nodes are started:
#
#gateway.recover_after_nodes: 3
#
# For more information, consult the gateway module documentation.
#
# ---------------------------------- Various -----------------------------------
#
# Require explicit names when deleting indices:
#
#action.destructive_requires_name: true

http.cors.enabled: true 
http.cors.allow-origin: "*"
node.master: true
node.data: true

<div class="hljs-button {2}" data-title="复制"></div></validateCode></pre>
<h3><a name="t4"></a><a id="22__251" target="_blank"></a>2.2 索引操作</h3>
<h4><a id="221__253" target="_blank"></a>2.2.1 创建索引和映射</h4>
<p>SpringBoot-data-elasticsearch提供了面向对象的方式操作elasticsearch</p>
<p>业务：创建一个商品对象，有这些属性：</p>
<pre class="prettyprint"><validateCode class="has-numbering" onclick="mdcp.copyCode(event)">id，title，category，brand，price，图片地址

在SpringDataElasticSearch中，只需要操作对象，就可以操作elasticsearch中的数据
<div class="hljs-button {2}" data-title="复制"></div></validateCode></pre>
<blockquote>
<p>实体类</p>
</blockquote>
<p>首先我们准备好实体类：</p>
<pre class="prettyprint"><validateCode class="prism language-java has-numbering" onclick="mdcp.copyCode(event)"><span class="token keyword">public</span> <span class="token keyword">class</span> <span class="token class-name">Item</span> <span class="token punctuation">{</span>
<span class="token keyword">private</span> Long id<span class="token punctuation">;</span>
<span class="token keyword">private</span> String title<span class="token punctuation">;</span> <span class="token comment">//标题</span>
<span class="token keyword">private</span> String category<span class="token punctuation">;</span><span class="token comment">// 分类</span>
<span class="token keyword">private</span> String brand<span class="token punctuation">;</span> <span class="token comment">// 品牌</span>
<span class="token keyword">private</span> Double price<span class="token punctuation">;</span> <span class="token comment">// 价格</span>
<span class="token keyword">private</span> String images<span class="token punctuation">;</span> <span class="token comment">// 图片地址</span>
<span class="token punctuation">}</span>
<div class="hljs-button {2}" data-title="复制"></div></validateCode></pre>
<blockquote>
<p>映射—注解</p>
</blockquote>
<p>Spring Data通过注解来声明字段的映射属性，有下面的三个注解：</p>
<ul>
<li><validateCode>@Document</validateCode> 作用在类，标记实体类为文档对象，一般有两个属性
<ul>
<li>indexName：对应索引库名称</li>
<li>type：对应在索引库中的类型</li>
<li>shards：分片数量，默认5</li>
<li>replicas：副本数量，默认1</li>
</ul>
</li>
<li><validateCode>@Id</validateCode> 作用在成员变量，标记一个字段作为id主键</li>
<li><validateCode>@Field</validateCode> 作用在成员变量，标记为文档的字段，并指定字段映射属性：
<ul>
<li>type：字段类型，是枚举：FieldType，可以是text、long、short、date、integer、object等
<ul>
<li>text：存储数据时候，会自动分词，并生成索引</li>
<li>keyword：存储数据时候，不会分词建立索引</li>
<li>Numerical：数值类型，分两类
<ul>
<li>基本数据类型：long、interger、short、byte、double、float、half_float</li>
<li>浮点数的高精度类型：scaled_float
<ul>
<li>需要指定一个精度因子，比如10或100。elasticsearch会把真实值乘以这个因子后存储，取出时再还原。</li>
</ul>
</li>
</ul>
</li>
<li>Date：日期类型
<ul>
<li>elasticsearch可以对日期格式化为字符串存储，但是建议我们存储为毫秒值，存储为long，节省空间。</li>
</ul>
</li>
</ul>
</li>
<li>index：是否索引，布尔类型，默认是true</li>
<li>store：是否存储，布尔类型，默认是false</li>
<li>analyzer：分词器名称，这里的<validateCode>ik_max_word</validateCode>即使用ik分词器</li>
</ul>
</li>
</ul>
<p>示例：</p>
<pre class="prettyprint"><validateCode class="prism language-java has-numbering" onclick="mdcp.copyCode(event)"><span class="token annotation punctuation">@Document</span><span class="token punctuation">(</span>indexName <span class="token operator">=</span> <span class="token string">"item"</span><span class="token punctuation">,</span>type <span class="token operator">=</span> <span class="token string">"docs"</span><span class="token punctuation">,</span> shards <span class="token operator">=</span> <span class="token number">1</span><span class="token punctuation">,</span> replicas <span class="token operator">=</span> <span class="token number">0</span><span class="token punctuation">)</span>
<span class="token keyword">public</span> <span class="token keyword">class</span> <span class="token class-name">Item</span> <span class="token punctuation">{</span>

<span class="token comment">/**
* @Description: @Id注解必须是springframework包下的
* org.springframework.data.annotation.Id						
*@Author: https://blog.csdn.net/chen_2890
*/</span>
<span class="token annotation punctuation">@Id</span> 
<span class="token keyword">private</span> Long id<span class="token punctuation">;</span>

<span class="token annotation punctuation">@Field</span><span class="token punctuation">(</span>type <span class="token operator">=</span> FieldType<span class="token punctuation">.</span>Text<span class="token punctuation">,</span> analyzer <span class="token operator">=</span> <span class="token string">"ik_max_word"</span><span class="token punctuation">)</span>
<span class="token keyword">private</span> String title<span class="token punctuation">;</span> <span class="token comment">//标题</span>

<span class="token annotation punctuation">@Field</span><span class="token punctuation">(</span>type <span class="token operator">=</span> FieldType<span class="token punctuation">.</span>Keyword<span class="token punctuation">)</span>
<span class="token keyword">private</span> String category<span class="token punctuation">;</span><span class="token comment">// 分类</span>

<span class="token annotation punctuation">@Field</span><span class="token punctuation">(</span>type <span class="token operator">=</span> FieldType<span class="token punctuation">.</span>Keyword<span class="token punctuation">)</span>
<span class="token keyword">private</span> String brand<span class="token punctuation">;</span> <span class="token comment">// 品牌</span>

<span class="token annotation punctuation">@Field</span><span class="token punctuation">(</span>type <span class="token operator">=</span> FieldType<span class="token punctuation">.</span>Double<span class="token punctuation">)</span>
<span class="token keyword">private</span> Double price<span class="token punctuation">;</span> <span class="token comment">// 价格</span>

<span class="token annotation punctuation">@Field</span><span class="token punctuation">(</span>index <span class="token operator">=</span> <span class="token boolean">false</span><span class="token punctuation">,</span> type <span class="token operator">=</span> FieldType<span class="token punctuation">.</span>Keyword<span class="token punctuation">)</span>
<span class="token keyword">private</span> String images<span class="token punctuation">;</span> <span class="token comment">// 图片地址</span>
<span class="token punctuation">}</span>
<div class="hljs-button {2}" data-title="复制"></div></validateCode></pre>
<blockquote>
<p>创建索引</p>
</blockquote>
<p>ElasticsearchTemplate中提供了创建索引的API：<br>
<img src="https://img-blog.csdnimg.cn/20181109151700856.png" alt="在这里插入图片描述"></p>
<ul>
<li>可以根据类的信息自动生成，也可以手动指定indexName和Settings</li>
</ul>
<blockquote>
<p>映射</p>
</blockquote>
<p>映射相关的API：<br>
<img src="https://img-blog.csdnimg.cn/20181109151744884.png" alt="在这里插入图片描述"></p>
<ul>
<li>一样，可以根据类的字节码信息（注解配置）来生成映射，或者手动编写映射</li>
</ul>
<p>我们这里采用类的字节码信息创建索引并映射，下面是测试类代码：</p>
<pre class="prettyprint"><validateCode class="prism language-java has-numbering" onclick="mdcp.copyCode(event)"><span class="token annotation punctuation">@RunWith</span><span class="token punctuation">(</span>SpringRunner<span class="token punctuation">.</span><span class="token keyword">class</span><span class="token punctuation">)</span>
<span class="token annotation punctuation">@SpringBootTest</span><span class="token punctuation">(</span>classes <span class="token operator">=</span> EsDemoApplication<span class="token punctuation">.</span><span class="token keyword">class</span><span class="token punctuation">)</span>
<span class="token keyword">public</span> <span class="token keyword">class</span> <span class="token class-name">EsDemoApplicationTests</span> <span class="token punctuation">{</span>

<span class="token annotation punctuation">@Autowired</span>
<span class="token keyword">private</span> ElasticsearchTemplate elasticsearchTemplate<span class="token punctuation">;</span>

<span class="token comment">/**
* @Description:创建索引，会根据Item类的@Document注解信息来创建
* @Author: https://blog.csdn.net/chen_2890
* @Date: 2018/9/29 0:51
*/</span>
<span class="token annotation punctuation">@Test</span>
<span class="token keyword">public</span> <span class="token keyword">void</span> <span class="token function">testCreateIndex</span><span class="token punctuation">(</span><span class="token punctuation">)</span> <span class="token punctuation">{</span>
elasticsearchTemplate<span class="token punctuation">.</span><span class="token function">createIndex</span><span class="token punctuation">(</span>Item<span class="token punctuation">.</span><span class="token keyword">class</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token punctuation">}</span>
<div class="hljs-button {2}" data-title="复制"></div></validateCode></pre>
<p>运行testCreateIndex()，索引创建成功后打开elasticsearch-head-master插件（<a href="https://blog.csdn.net/chen_2890/article/details/83757022" rel="nofollow" target="_blank">es-head插件的安装</a>）查看索引信息，<br>
索引信息：<br>
<img src="https://img-blog.csdnimg.cn/20181109152007368.png?x-oss-process=imageCode/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2NoZW5fMjg5MA==,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"></p>
<h4><a id="222__368" target="_blank"></a>2.2.2 删除索引</h4>
<p>删除索引的API：<br>
<img src="https://img-blog.csdnimg.cn/20181109154612514.png" alt="在这里插入图片描述"><br>
可以根据类名或索引名删除。</p>
<p>示例：</p>
<pre class="prettyprint"><validateCode class="prism language-java has-numbering" onclick="mdcp.copyCode(event)">    <span class="token comment">/**
* @Description:删除索引
* @Author: https://blog.csdn.net/chen_2890
* @Date: 2018/9/29 0:50
*/</span>     
<span class="token annotation punctuation">@Test</span>
<span class="token keyword">public</span> <span class="token keyword">void</span> <span class="token function">testDeleteIndex</span><span class="token punctuation">(</span><span class="token punctuation">)</span> <span class="token punctuation">{</span>
elasticsearchTemplate<span class="token punctuation">.</span><span class="token function">deleteIndex</span><span class="token punctuation">(</span>Item<span class="token punctuation">.</span><span class="token keyword">class</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token punctuation">}</span>
<div class="hljs-button {2}" data-title="复制"></div></validateCode></pre>
<p>运行testDeleteIndex()，索引删除成功后打开elasticsearch-head-master插件（<a href="https://blog.csdn.net/chen_2890/article/details/83757022" rel="nofollow" target="_blank">es-head插件的安装</a>）查看索引信息，发现item索引已经被删除。</p>
<h3><a name="t5"></a><a id="23__392" target="_blank"></a>2.3 新增文档数据</h3>
<h4><a id="231_Repository_394" target="_blank"></a>2.3.1 Repository接口</h4>
<p>Spring Data 的强大之处，就在于你不用写任何DAO处理，自动根据方法名或类的信息进行CRUD操作。只要你定义一个接口，然后继承Repository提供的一些子接口，就能具备各种基本的CRUD功能。</p>
<p>来看下Repository的继承关系：<br>
<img src="https://img-blog.csdnimg.cn/20181109155137285.png?x-oss-process=imageCode/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2NoZW5fMjg5MA==,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"><br>
我们看到有一个ElasticsearchCrudRepository接口：</p>
<p><img src="https://img-blog.csdnimg.cn/20181109155154210.png?x-oss-process=imageCode/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2NoZW5fMjg5MA==,size_16,color_FFFFFF,t_70" alt="">.png)</p>
<p>所以，我们只需要定义接口，然后继承它就OK了。</p>
<pre class="prettyprint"><validateCode class="prism language-java has-numbering" onclick="mdcp.copyCode(event)">	<span class="token comment">/**
* @Description:定义ItemRepository 接口
* @Param:
* 	Item:为实体类
* 	Long:为Item实体类中主键的数据类型
* @Author: https://blog.csdn.net/chen_2890
* @Date: 2018/9/29 0:50
*/</span>	 
<span class="token keyword">public</span> <span class="token keyword">interface</span> <span class="token class-name">ItemRepository</span> <span class="token keyword">extends</span> <span class="token class-name">ElasticsearchRepository</span><span class="token generics function"><span class="token punctuation">&lt;</span>Item<span class="token punctuation">,</span>Long<span class="token punctuation">&gt;</span></span> <span class="token punctuation">{</span>

<span class="token punctuation">}</span>
<div class="hljs-button {2}" data-title="复制"></div></validateCode></pre>
<p>接下来，我们测试新增数据：</p>
<h4><a id="232__422" target="_blank"></a>2.3.2 新增一个对象</h4>
<pre class="prettyprint"><validateCode class="prism language-java has-numbering" onclick="mdcp.copyCode(event)"><span class="token annotation punctuation">@Autowired</span>
<span class="token keyword">private</span> ItemRepository itemRepository<span class="token punctuation">;</span>

<span class="token comment">/**
* @Description:定义新增方法
* @Author: https://blog.csdn.net/chen_2890
*/</span>
<span class="token annotation punctuation">@Test</span>
<span class="token keyword">public</span> <span class="token keyword">void</span> <span class="token function">insert</span><span class="token punctuation">(</span><span class="token punctuation">)</span> <span class="token punctuation">{</span>
Item item <span class="token operator">=</span> <span class="token keyword">new</span> <span class="token class-name">Item</span><span class="token punctuation">(</span><span class="token number">1</span>L<span class="token punctuation">,</span> <span class="token string">"小米手机7"</span><span class="token punctuation">,</span> <span class="token string">" 手机"</span><span class="token punctuation">,</span>
<span class="token string">"小米"</span><span class="token punctuation">,</span> <span class="token number">3499.00</span><span class="token punctuation">,</span> <span class="token string">"http://imageCode.baidu.com/13123.jpg"</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
itemRepository<span class="token punctuation">.</span><span class="token function">save</span><span class="token punctuation">(</span>item<span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token punctuation">}</span>
<div class="hljs-button {2}" data-title="复制"></div></validateCode></pre>
<p>运行insert()，去页面查询看看：</p>
<p><img src="https://img-blog.csdnimg.cn/20181109160643109.png?x-oss-process=imageCode/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2NoZW5fMjg5MA==,size_16,color_FFFFFF,t_70" alt=""></p>
<p>OK，新增成功！</p>
<h4><a id="233__445" target="_blank"></a>2.3.3 批量新增</h4>
<p>代码：</p>
<pre class="prettyprint"><validateCode class="prism language-java has-numbering" onclick="mdcp.copyCode(event)">	<span class="token comment">/**
* @Description:定义批量新增方法
* @Author: https://blog.csdn.net/chen_2890
*/</span>
<span class="token annotation punctuation">@Test</span>
<span class="token keyword">public</span> <span class="token keyword">void</span> <span class="token function">insertList</span><span class="token punctuation">(</span><span class="token punctuation">)</span> <span class="token punctuation">{</span>
List<span class="token generics function"><span class="token punctuation">&lt;</span>Item<span class="token punctuation">&gt;</span></span> list <span class="token operator">=</span> <span class="token keyword">new</span> <span class="token class-name">ArrayList</span><span class="token operator">&lt;</span><span class="token operator">&gt;</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
list<span class="token punctuation">.</span><span class="token function">add</span><span class="token punctuation">(</span><span class="token keyword">new</span> <span class="token class-name">Item</span><span class="token punctuation">(</span><span class="token number">2</span>L<span class="token punctuation">,</span> <span class="token string">"坚果手机R1"</span><span class="token punctuation">,</span> <span class="token string">" 手机"</span><span class="token punctuation">,</span> <span class="token string">"锤子"</span><span class="token punctuation">,</span> <span class="token number">3699.00</span><span class="token punctuation">,</span> <span class="token string">"http://imageCode.baidu.com/13123.jpg"</span><span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
list<span class="token punctuation">.</span><span class="token function">add</span><span class="token punctuation">(</span><span class="token keyword">new</span> <span class="token class-name">Item</span><span class="token punctuation">(</span><span class="token number">3</span>L<span class="token punctuation">,</span> <span class="token string">"华为META10"</span><span class="token punctuation">,</span> <span class="token string">" 手机"</span><span class="token punctuation">,</span> <span class="token string">"华为"</span><span class="token punctuation">,</span> <span class="token number">4499.00</span><span class="token punctuation">,</span> <span class="token string">"http://imageCode.baidu.com/13123.jpg"</span><span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token comment">// 接收对象集合，实现批量新增</span>
itemRepository<span class="token punctuation">.</span><span class="token function">saveAll</span><span class="token punctuation">(</span>list<span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token punctuation">}</span>
<div class="hljs-button {2}" data-title="复制"></div></validateCode></pre>
<p>再次去页面查询：<br>
<img src="https://img-blog.csdnimg.cn/20181109161035558.png?x-oss-process=imageCode/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2NoZW5fMjg5MA==,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"><br>
OK，批量新增成功！</p>
<h4><a id="234__467" target="_blank"></a>2.3.4 修改</h4>
<p>elasticsearch中本没有修改，它的修改原理是该是先删除在新增</p>
<p>修改和新增是同一个接口，区分的依据就是id。</p>
<pre class="prettyprint"><validateCode class="prism language-java has-numbering" onclick="mdcp.copyCode(event)">	<span class="token comment">/**
* @Description:定义修改方法
* @Author: https://blog.csdn.net/chen_2890
*/</span>
<span class="token annotation punctuation">@Test</span>
<span class="token keyword">public</span> <span class="token keyword">void</span> <span class="token function">update</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">{</span>
Item item <span class="token operator">=</span> <span class="token keyword">new</span> <span class="token class-name">Item</span><span class="token punctuation">(</span><span class="token number">1</span>L<span class="token punctuation">,</span> <span class="token string">"苹果XSMax"</span><span class="token punctuation">,</span> <span class="token string">" 手机"</span><span class="token punctuation">,</span>
<span class="token string">"小米"</span><span class="token punctuation">,</span> <span class="token number">3499.00</span><span class="token punctuation">,</span> <span class="token string">"http://imageCode.baidu.com/13123.jpg"</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
itemRepository<span class="token punctuation">.</span><span class="token function">save</span><span class="token punctuation">(</span>item<span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token punctuation">}</span>
<div class="hljs-button {2}" data-title="复制"></div></validateCode></pre>
<p>查看结果：<br>
<img src="https://img-blog.csdnimg.cn/20181109161243210.png?x-oss-process=imageCode/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2NoZW5fMjg5MA==,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"></p>
<h3><a name="t6"></a><a id="24__488" target="_blank"></a>2.4 查询</h3>
<h4><a id="241__490" target="_blank"></a>2.4.1 基本查询</h4>
<p>ElasticsearchRepository提供了一些基本的查询方法：<br>
<img src="https://img-blog.csdnimg.cn/20181109161342463.png?x-oss-process=imageCode/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2NoZW5fMjg5MA==,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"><br>
我们来试试查询所有：</p>
<pre class="prettyprint"><validateCode class="prism language-java has-numbering" onclick="mdcp.copyCode(event)">	<span class="token comment">/**
* @Description:定义查询方法,含对价格的降序、升序查询
* @Author: https://blog.csdn.net/chen_2890
*/</span>
<span class="token annotation punctuation">@Test</span>
<span class="token keyword">public</span> <span class="token keyword">void</span> <span class="token function">testQueryAll</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">{</span>
<span class="token comment">// 查找所有</span>
<span class="token comment">//Iterable&lt;Item&gt; list = this.itemRepository.findAll();</span>
<span class="token comment">// 对某字段排序查找所有 Sort.by("price").descending() 降序</span>
<span class="token comment">// Sort.by("price").ascending():升序</span>
Iterable<span class="token generics function"><span class="token punctuation">&lt;</span>Item<span class="token punctuation">&gt;</span></span> list <span class="token operator">=</span> <span class="token keyword">this</span><span class="token punctuation">.</span>itemRepository<span class="token punctuation">.</span><span class="token function">findAll</span><span class="token punctuation">(</span>Sort<span class="token punctuation">.</span><span class="token function">by</span><span class="token punctuation">(</span><span class="token string">"price"</span><span class="token punctuation">)</span><span class="token punctuation">.</span><span class="token function">ascending</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">;</span>

<span class="token keyword">for</span> <span class="token punctuation">(</span>Item item<span class="token operator">:</span>list<span class="token punctuation">)</span><span class="token punctuation">{</span>
System<span class="token punctuation">.</span>out<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span>item<span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token punctuation">}</span>
<span class="token punctuation">}</span>
<div class="hljs-button {2}" data-title="复制"></div></validateCode></pre>
<p>结果：<br>
<img src="https://img-blog.csdnimg.cn/20181109161631501.png" alt="在这里插入图片描述"></p>
<h4><a id="242__518" target="_blank"></a>2.4.2 自定义方法</h4>
<p>Spring Data 的另一个强大功能，是根据方法名称自动实现功能。</p>
<p>比如：你的方法名叫做：findByTitle，那么它就知道你是根据title查询，然后自动帮你完成，无需写实现类。</p>
<p>当然，方法名称要符合一定的约定：</p>

<div class="table-box"><table>
<thead>
<tr>
<th>Keyword</th>
<th>Sample</th>
</tr>
</thead>
<tbody>
<tr>
<td><validateCode>And</validateCode></td>
<td><validateCode>findByNameAndPrice</validateCode></td>
</tr>
<tr>
<td><validateCode>Or</validateCode></td>
<td><validateCode>findByNameOrPrice</validateCode></td>
</tr>
<tr>
<td><validateCode>Is</validateCode></td>
<td><validateCode>findByName</validateCode></td>
</tr>
<tr>
<td><validateCode>Not</validateCode></td>
<td><validateCode>findByNameNot</validateCode></td>
</tr>
<tr>
<td><validateCode>Between</validateCode></td>
<td><validateCode>findByPriceBetween</validateCode></td>
</tr>
<tr>
<td><validateCode>LessThanEqual</validateCode></td>
<td><validateCode>findByPriceLessThan</validateCode></td>
</tr>
<tr>
<td><validateCode>GreaterThanEqual</validateCode></td>
<td><validateCode>findByPriceGreaterThan</validateCode></td>
</tr>
<tr>
<td><validateCode>Before</validateCode></td>
<td><validateCode>findByPriceBefore</validateCode></td>
</tr>
<tr>
<td><validateCode>After</validateCode></td>
<td><validateCode>findByPriceAfter</validateCode></td>
</tr>
<tr>
<td><validateCode>Like</validateCode></td>
<td><validateCode>findByNameLike</validateCode></td>
</tr>
<tr>
<td><validateCode>StartingWith</validateCode></td>
<td><validateCode>findByNameStartingWith</validateCode></td>
</tr>
<tr>
<td><validateCode>EndingWith</validateCode></td>
<td><validateCode>findByNameEndingWith</validateCode></td>
</tr>
<tr>
<td><validateCode>Contains/Containing</validateCode></td>
<td><validateCode>findByNameContaining</validateCode></td>
</tr>
<tr>
<td><validateCode>In</validateCode></td>
<td><validateCode>findByNameIn(Collection&lt;String&gt;names)</validateCode></td>
</tr>
<tr>
<td><validateCode>NotIn</validateCode></td>
<td><validateCode>findByNameNotIn(Collection&lt;String&gt;names)</validateCode></td>
</tr>
<tr>
<td><validateCode>Near</validateCode></td>
<td><validateCode>findByStoreNear</validateCode></td>
</tr>
<tr>
<td><validateCode>True</validateCode></td>
<td><validateCode>findByAvailableTrue</validateCode></td>
</tr>
<tr>
<td><validateCode>False</validateCode></td>
<td><validateCode>findByAvailableFalse</validateCode></td>
</tr>
<tr>
<td><validateCode>OrderBy</validateCode></td>
<td><validateCode>findByAvailableTrueOrderByNameDesc</validateCode></td>
</tr>
</tbody>
</table></div><p>例如，我们来按照价格区间查询，定义这样的一个方法：</p>
<pre class="prettyprint"><validateCode class="prism language-java has-numbering" onclick="mdcp.copyCode(event)"><span class="token keyword">public</span> <span class="token keyword">interface</span> <span class="token class-name">ItemRepository</span> <span class="token keyword">extends</span> <span class="token class-name">ElasticsearchRepository</span><span class="token generics function"><span class="token punctuation">&lt;</span>Item<span class="token punctuation">,</span>Long<span class="token punctuation">&gt;</span></span> <span class="token punctuation">{</span>

<span class="token comment">/**
* @Description:根据价格区间查询
* @Param price1
* @Param price2
* @Author: https://blog.csdn.net/chen_2890
*/</span>
List<span class="token generics function"><span class="token punctuation">&lt;</span>Item<span class="token punctuation">&gt;</span></span> <span class="token function">findByPriceBetween</span><span class="token punctuation">(</span><span class="token keyword">double</span> price1<span class="token punctuation">,</span> <span class="token keyword">double</span> price2<span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token punctuation">}</span>
<div class="hljs-button {2}" data-title="复制"></div></validateCode></pre>
<p>然后添加一些测试数据：</p>
<pre class="prettyprint"><validateCode class="prism language-java has-numbering" onclick="mdcp.copyCode(event)">	<span class="token comment">/**
* @Description:准备测试数据
* @Author: https://blog.csdn.net/chen_2890
*/</span>
<span class="token annotation punctuation">@Test</span>
<span class="token keyword">public</span> <span class="token keyword">void</span> <span class="token function">insertList</span><span class="token punctuation">(</span><span class="token punctuation">)</span> <span class="token punctuation">{</span>
List<span class="token generics function"><span class="token punctuation">&lt;</span>Item<span class="token punctuation">&gt;</span></span> list <span class="token operator">=</span> <span class="token keyword">new</span> <span class="token class-name">ArrayList</span><span class="token operator">&lt;</span><span class="token operator">&gt;</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
list<span class="token punctuation">.</span><span class="token function">add</span><span class="token punctuation">(</span><span class="token keyword">new</span> <span class="token class-name">Item</span><span class="token punctuation">(</span><span class="token number">1</span>L<span class="token punctuation">,</span> <span class="token string">"小米手机7"</span><span class="token punctuation">,</span> <span class="token string">"手机"</span><span class="token punctuation">,</span> <span class="token string">"小米"</span><span class="token punctuation">,</span> <span class="token number">3299.00</span><span class="token punctuation">,</span> <span class="token string">"http://imageCode.baidu.com/13123.jpg"</span><span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
list<span class="token punctuation">.</span><span class="token function">add</span><span class="token punctuation">(</span><span class="token keyword">new</span> <span class="token class-name">Item</span><span class="token punctuation">(</span><span class="token number">2</span>L<span class="token punctuation">,</span> <span class="token string">"坚果手机R1"</span><span class="token punctuation">,</span> <span class="token string">"手机"</span><span class="token punctuation">,</span> <span class="token string">"锤子"</span><span class="token punctuation">,</span> <span class="token number">3699.00</span><span class="token punctuation">,</span> <span class="token string">"http://imageCode.baidu.com/13123.jpg"</span><span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
list<span class="token punctuation">.</span><span class="token function">add</span><span class="token punctuation">(</span><span class="token keyword">new</span> <span class="token class-name">Item</span><span class="token punctuation">(</span><span class="token number">3</span>L<span class="token punctuation">,</span> <span class="token string">"华为META10"</span><span class="token punctuation">,</span> <span class="token string">"手机"</span><span class="token punctuation">,</span> <span class="token string">"华为"</span><span class="token punctuation">,</span> <span class="token number">4499.00</span><span class="token punctuation">,</span> <span class="token string">"http://imageCode.baidu.com/13123.jpg"</span><span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
list<span class="token punctuation">.</span><span class="token function">add</span><span class="token punctuation">(</span><span class="token keyword">new</span> <span class="token class-name">Item</span><span class="token punctuation">(</span><span class="token number">4</span>L<span class="token punctuation">,</span> <span class="token string">"小米Mix2S"</span><span class="token punctuation">,</span> <span class="token string">"手机"</span><span class="token punctuation">,</span> <span class="token string">"小米"</span><span class="token punctuation">,</span> <span class="token number">4299.00</span><span class="token punctuation">,</span> <span class="token string">"http://imageCode.baidu.com/13123.jpg"</span><span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
list<span class="token punctuation">.</span><span class="token function">add</span><span class="token punctuation">(</span><span class="token keyword">new</span> <span class="token class-name">Item</span><span class="token punctuation">(</span><span class="token number">5</span>L<span class="token punctuation">,</span> <span class="token string">"荣耀V10"</span><span class="token punctuation">,</span> <span class="token string">"手机"</span><span class="token punctuation">,</span> <span class="token string">"华为"</span><span class="token punctuation">,</span> <span class="token number">2799.00</span><span class="token punctuation">,</span> <span class="token string">"http://imageCode.baidu.com/13123.jpg"</span><span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token comment">// 接收对象集合，实现批量新增</span>
itemRepository<span class="token punctuation">.</span><span class="token function">saveAll</span><span class="token punctuation">(</span>list<span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token punctuation">}</span>
<div class="hljs-button {2}" data-title="复制"></div></validateCode></pre>
<p>不需要写实现类，然后我们直接去运行：</p>
<pre class="prettyprint"><validateCode class="prism language-java has-numbering" onclick="mdcp.copyCode(event)">	<span class="token comment">/**
* @Description:按照价格区间查询
* @Author: https://blog.csdn.net/chen_2890
*/</span>
<span class="token annotation punctuation">@Test</span>
<span class="token keyword">public</span> <span class="token keyword">void</span> <span class="token function">queryByPriceBetween</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">{</span>
List<span class="token generics function"><span class="token punctuation">&lt;</span>Item<span class="token punctuation">&gt;</span></span> list <span class="token operator">=</span> <span class="token keyword">this</span><span class="token punctuation">.</span>itemRepository<span class="token punctuation">.</span><span class="token function">findByPriceBetween</span><span class="token punctuation">(</span><span class="token number">2000.00</span><span class="token punctuation">,</span> <span class="token number">3500.00</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token keyword">for</span> <span class="token punctuation">(</span>Item item <span class="token operator">:</span> list<span class="token punctuation">)</span> <span class="token punctuation">{</span>
System<span class="token punctuation">.</span>out<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">"item = "</span> <span class="token operator">+</span> item<span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token punctuation">}</span>
<span class="token punctuation">}</span>
<div class="hljs-button {2}" data-title="复制"></div></validateCode></pre>
<p>结果：<br>
<img src="https://img-blog.csdnimg.cn/20181109162031260.png" alt="在这里插入图片描述"></p>
<p>OK，测试成功！</p>
<h4><a id="243__605" target="_blank"></a>2.4.3 自定义查询</h4>
<p>先来看最基本的matchQuery：</p>
<pre class="prettyprint"><validateCode class="prism language-java has-numbering" onclick="mdcp.copyCode(event)">	<span class="token comment">/**
* @Description:matchQuery底层采用的是词条匹配查询
* @Author: https://blog.csdn.net/chen_2890
*/</span>
<span class="token annotation punctuation">@Test</span>
<span class="token keyword">public</span> <span class="token keyword">void</span> <span class="token function">testMatchQuery</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">{</span>
<span class="token comment">// 构建查询条件</span>
NativeSearchQueryBuilder queryBuilder <span class="token operator">=</span> <span class="token keyword">new</span> <span class="token class-name">NativeSearchQueryBuilder</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token comment">// 添加基本分词查询</span>
queryBuilder<span class="token punctuation">.</span><span class="token function">withQuery</span><span class="token punctuation">(</span>QueryBuilders<span class="token punctuation">.</span><span class="token function">matchQuery</span><span class="token punctuation">(</span><span class="token string">"title"</span><span class="token punctuation">,</span> <span class="token string">"小米手机"</span><span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token comment">// 搜索，获取结果</span>
Page<span class="token generics function"><span class="token punctuation">&lt;</span>Item<span class="token punctuation">&gt;</span></span> items <span class="token operator">=</span> <span class="token keyword">this</span><span class="token punctuation">.</span>itemRepository<span class="token punctuation">.</span><span class="token function">search</span><span class="token punctuation">(</span>queryBuilder<span class="token punctuation">.</span><span class="token function">build</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token comment">// 总条数</span>
<span class="token keyword">long</span> total <span class="token operator">=</span> items<span class="token punctuation">.</span><span class="token function">getTotalElements</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
System<span class="token punctuation">.</span>out<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">"total = "</span> <span class="token operator">+</span> total<span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token keyword">for</span> <span class="token punctuation">(</span>Item item <span class="token operator">:</span> items<span class="token punctuation">)</span> <span class="token punctuation">{</span>
System<span class="token punctuation">.</span>out<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span>item<span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token punctuation">}</span>
<span class="token punctuation">}</span>
<div class="hljs-button {2}" data-title="复制"></div></validateCode></pre>
<ul>
<li>
<p>NativeSearchQueryBuilder：Spring提供的一个查询条件构建器，帮助构建json格式的请求体</p>
</li>
<li>
<p>QueryBuilders.matchQuery(“title”, “小米手机”)：利用QueryBuilders来生成一个查询。QueryBuilders提供了大量的静态方法，用于生成各种不同类型的查询：<br>
<img src="https://img-blog.csdnimg.cn/20181109175636406.png?x-oss-process=imageCode/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2NoZW5fMjg5MA==,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"></p>
</li>
<li>
<p><validateCode>Page&lt;item&gt;</validateCode>：默认是分页查询，因此返回的是一个分页的结果对象，包含属性：</p>
<ul>
<li>
<p>totalElements：总条数</p>
</li>
<li>
<p>totalPages：总页数</p>
</li>
<li>
<p>Iterator：迭代器，本身实现了Iterator接口，因此可直接迭代得到当前页的数据</p>
</li>
<li>
<p>其它属性：<br>
<img src="https://img-blog.csdnimg.cn/2018110917580099.png?x-oss-process=imageCode/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2NoZW5fMjg5MA==,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"></p>
</li>
</ul>
</li>
</ul>
<p>结果：</p>
<p><img src="https://img-blog.csdnimg.cn/20181109175830283.png" alt="在这里插入图片描述"></p>
<p>总的测试代码如下：</p>
<pre class="prettyprint"><validateCode class="prism language-java has-numbering" onclick="mdcp.copyCode(event)">	<span class="token comment">/**
*
* @Description:matchQuery
*@Author: https://blog.csdn.net/chen_2890
*/</span>
<span class="token annotation punctuation">@Test</span>
<span class="token keyword">public</span> <span class="token keyword">void</span> <span class="token function">testMathQuery</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">{</span>
<span class="token comment">// 创建对象</span>
NativeSearchQueryBuilder queryBuilder <span class="token operator">=</span> <span class="token keyword">new</span> <span class="token class-name">NativeSearchQueryBuilder</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token comment">// 在queryBuilder对象中自定义查询</span>
<span class="token comment">//matchQuery:底层就是使用的termQuery</span>
queryBuilder<span class="token punctuation">.</span><span class="token function">withQuery</span><span class="token punctuation">(</span>QueryBuilders<span class="token punctuation">.</span><span class="token function">matchQuery</span><span class="token punctuation">(</span><span class="token string">"title"</span><span class="token punctuation">,</span><span class="token string">"坚果"</span><span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token comment">//查询，search 默认就是分页查找</span>
Page<span class="token generics function"><span class="token punctuation">&lt;</span>Item<span class="token punctuation">&gt;</span></span> page <span class="token operator">=</span> <span class="token keyword">this</span><span class="token punctuation">.</span>itemRepository<span class="token punctuation">.</span><span class="token function">search</span><span class="token punctuation">(</span>queryBuilder<span class="token punctuation">.</span><span class="token function">build</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token comment">//获取数据</span>
<span class="token keyword">long</span> totalElements <span class="token operator">=</span> page<span class="token punctuation">.</span><span class="token function">getTotalElements</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
System<span class="token punctuation">.</span>out<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">"获取的总条数:"</span><span class="token operator">+</span>totalElements<span class="token punctuation">)</span><span class="token punctuation">;</span>

<span class="token keyword">for</span><span class="token punctuation">(</span>Item item<span class="token operator">:</span>page<span class="token punctuation">)</span><span class="token punctuation">{</span>
System<span class="token punctuation">.</span>out<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span>item<span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token punctuation">}</span>


<span class="token punctuation">}</span>


<span class="token comment">/**
* @Description:
* termQuery:功能更强大，除了匹配字符串以外，还可以匹配
* int/long/double/float/....	
* @Author: https://blog.csdn.net/chen_2890			
*/</span>
<span class="token annotation punctuation">@Test</span>
<span class="token keyword">public</span> <span class="token keyword">void</span> <span class="token function">testTermQuery</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">{</span>
NativeSearchQueryBuilder builder <span class="token operator">=</span> <span class="token keyword">new</span> <span class="token class-name">NativeSearchQueryBuilder</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
builder<span class="token punctuation">.</span><span class="token function">withQuery</span><span class="token punctuation">(</span>QueryBuilders<span class="token punctuation">.</span><span class="token function">termQuery</span><span class="token punctuation">(</span><span class="token string">"price"</span><span class="token punctuation">,</span><span class="token number">998.0</span><span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token comment">// 查找</span>
Page<span class="token generics function"><span class="token punctuation">&lt;</span>Item<span class="token punctuation">&gt;</span></span> page <span class="token operator">=</span> <span class="token keyword">this</span><span class="token punctuation">.</span>itemRepository<span class="token punctuation">.</span><span class="token function">search</span><span class="token punctuation">(</span>builder<span class="token punctuation">.</span><span class="token function">build</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">;</span>

<span class="token keyword">for</span><span class="token punctuation">(</span>Item item<span class="token operator">:</span>page<span class="token punctuation">)</span><span class="token punctuation">{</span>
System<span class="token punctuation">.</span>out<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span>item<span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token punctuation">}</span>
<span class="token punctuation">}</span>
<span class="token comment">/**
* @Description:布尔查询
* @Author: https://blog.csdn.net/chen_2890			
*/</span>
<span class="token annotation punctuation">@Test</span>
<span class="token keyword">public</span> <span class="token keyword">void</span> <span class="token function">testBooleanQuery</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">{</span>
NativeSearchQueryBuilder builder <span class="token operator">=</span> <span class="token keyword">new</span> <span class="token class-name">NativeSearchQueryBuilder</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span>

builder<span class="token punctuation">.</span><span class="token function">withQuery</span><span class="token punctuation">(</span>
QueryBuilders<span class="token punctuation">.</span><span class="token function">boolQuery</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">.</span><span class="token function">must</span><span class="token punctuation">(</span>QueryBuilders<span class="token punctuation">.</span><span class="token function">matchQuery</span><span class="token punctuation">(</span><span class="token string">"title"</span><span class="token punctuation">,</span><span class="token string">"华为"</span><span class="token punctuation">)</span><span class="token punctuation">)</span>
<span class="token punctuation">.</span><span class="token function">must</span><span class="token punctuation">(</span>QueryBuilders<span class="token punctuation">.</span><span class="token function">matchQuery</span><span class="token punctuation">(</span><span class="token string">"brand"</span><span class="token punctuation">,</span><span class="token string">"华为"</span><span class="token punctuation">)</span><span class="token punctuation">)</span>
<span class="token punctuation">)</span><span class="token punctuation">;</span>

<span class="token comment">// 查找</span>
Page<span class="token generics function"><span class="token punctuation">&lt;</span>Item<span class="token punctuation">&gt;</span></span> page <span class="token operator">=</span> <span class="token keyword">this</span><span class="token punctuation">.</span>itemRepository<span class="token punctuation">.</span><span class="token function">search</span><span class="token punctuation">(</span>builder<span class="token punctuation">.</span><span class="token function">build</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token keyword">for</span><span class="token punctuation">(</span>Item item<span class="token operator">:</span>page<span class="token punctuation">)</span><span class="token punctuation">{</span>
System<span class="token punctuation">.</span>out<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span>item<span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token punctuation">}</span>
<span class="token punctuation">}</span>

<span class="token comment">/**
* @Description:模糊查询
* @Author: https://blog.csdn.net/chen_2890			
*/</span>
<span class="token annotation punctuation">@Test</span>
<span class="token keyword">public</span> <span class="token keyword">void</span> <span class="token function">testFuzzyQuery</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">{</span>
NativeSearchQueryBuilder builder <span class="token operator">=</span> <span class="token keyword">new</span> <span class="token class-name">NativeSearchQueryBuilder</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
builder<span class="token punctuation">.</span><span class="token function">withQuery</span><span class="token punctuation">(</span>QueryBuilders<span class="token punctuation">.</span><span class="token function">fuzzyQuery</span><span class="token punctuation">(</span><span class="token string">"title"</span><span class="token punctuation">,</span><span class="token string">"faceoooo"</span><span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
Page<span class="token generics function"><span class="token punctuation">&lt;</span>Item<span class="token punctuation">&gt;</span></span> page <span class="token operator">=</span> <span class="token keyword">this</span><span class="token punctuation">.</span>itemRepository<span class="token punctuation">.</span><span class="token function">search</span><span class="token punctuation">(</span>builder<span class="token punctuation">.</span><span class="token function">build</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token keyword">for</span><span class="token punctuation">(</span>Item item<span class="token operator">:</span>page<span class="token punctuation">)</span><span class="token punctuation">{</span>
System<span class="token punctuation">.</span>out<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span>item<span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token punctuation">}</span>

<span class="token punctuation">}</span>
<div class="hljs-button {2}" data-title="复制"></div></validateCode></pre>
<h4><a id="244__734" target="_blank"></a>2.4.4 分页查询</h4>
<p>利用<validateCode>NativeSearchQueryBuilder</validateCode>可以方便的实现分页：</p>
<pre class="prettyprint"><validateCode class="prism language-java has-numbering" onclick="mdcp.copyCode(event)">	<span class="token comment">/**
* @Description:分页查询
* @Author: https://blog.csdn.net/chen_2890			
*/</span>
<span class="token annotation punctuation">@Test</span>
<span class="token keyword">public</span> <span class="token keyword">void</span> <span class="token function">searchByPage</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">{</span>
<span class="token comment">// 构建查询条件</span>
NativeSearchQueryBuilder queryBuilder <span class="token operator">=</span> <span class="token keyword">new</span> <span class="token class-name">NativeSearchQueryBuilder</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token comment">// 添加基本分词查询</span>
queryBuilder<span class="token punctuation">.</span><span class="token function">withQuery</span><span class="token punctuation">(</span>QueryBuilders<span class="token punctuation">.</span><span class="token function">termQuery</span><span class="token punctuation">(</span><span class="token string">"category"</span><span class="token punctuation">,</span> <span class="token string">"手机"</span><span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token comment">// 分页：</span>
<span class="token keyword">int</span> page <span class="token operator">=</span> <span class="token number">0</span><span class="token punctuation">;</span>
<span class="token keyword">int</span> size <span class="token operator">=</span> <span class="token number">2</span><span class="token punctuation">;</span>
queryBuilder<span class="token punctuation">.</span><span class="token function">withPageable</span><span class="token punctuation">(</span>PageRequest<span class="token punctuation">.</span><span class="token function">of</span><span class="token punctuation">(</span>page<span class="token punctuation">,</span>size<span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">;</span>

<span class="token comment">// 搜索，获取结果</span>
Page<span class="token generics function"><span class="token punctuation">&lt;</span>Item<span class="token punctuation">&gt;</span></span> items <span class="token operator">=</span> <span class="token keyword">this</span><span class="token punctuation">.</span>itemRepository<span class="token punctuation">.</span><span class="token function">search</span><span class="token punctuation">(</span>queryBuilder<span class="token punctuation">.</span><span class="token function">build</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token comment">// 总条数</span>
<span class="token keyword">long</span> total <span class="token operator">=</span> items<span class="token punctuation">.</span><span class="token function">getTotalElements</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
System<span class="token punctuation">.</span>out<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">"总条数 = "</span> <span class="token operator">+</span> total<span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token comment">// 总页数</span>
System<span class="token punctuation">.</span>out<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">"总页数 = "</span> <span class="token operator">+</span> items<span class="token punctuation">.</span><span class="token function">getTotalPages</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token comment">// 当前页</span>
System<span class="token punctuation">.</span>out<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">"当前页："</span> <span class="token operator">+</span> items<span class="token punctuation">.</span><span class="token function">getNumber</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token comment">// 每页大小</span>
System<span class="token punctuation">.</span>out<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">"每页大小："</span> <span class="token operator">+</span> items<span class="token punctuation">.</span><span class="token function">getSize</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">;</span>

<span class="token keyword">for</span> <span class="token punctuation">(</span>Item item <span class="token operator">:</span> items<span class="token punctuation">)</span> <span class="token punctuation">{</span>
System<span class="token punctuation">.</span>out<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span>item<span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token punctuation">}</span>
<span class="token punctuation">}</span>
<div class="hljs-button {2}" data-title="复制"></div></validateCode></pre>
<p>结果：<br>
<img src="https://img-blog.csdnimg.cn/20181109180520496.png" alt="在这里插入图片描述"></p>
<p>可以发现，<strong>Elasticsearch中的分页是从第0页开始</strong>。</p>
<h4><a id="245__777" target="_blank"></a>2.4.5 排序</h4>
<p>排序也通用通过<validateCode>NativeSearchQueryBuilder</validateCode>完成：</p>
<pre class="prettyprint"><validateCode class="prism language-java has-numbering" onclick="mdcp.copyCode(event)">	<span class="token comment">/**
* @Description:排序查询
* @Author: https://blog.csdn.net/chen_2890			
*/</span>
<span class="token annotation punctuation">@Test</span>
<span class="token keyword">public</span> <span class="token keyword">void</span> <span class="token function">searchAndSort</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">{</span>
<span class="token comment">// 构建查询条件</span>
NativeSearchQueryBuilder queryBuilder <span class="token operator">=</span> <span class="token keyword">new</span> <span class="token class-name">NativeSearchQueryBuilder</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token comment">// 添加基本分词查询</span>
queryBuilder<span class="token punctuation">.</span><span class="token function">withQuery</span><span class="token punctuation">(</span>QueryBuilders<span class="token punctuation">.</span><span class="token function">termQuery</span><span class="token punctuation">(</span><span class="token string">"category"</span><span class="token punctuation">,</span> <span class="token string">"手机"</span><span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">;</span>

<span class="token comment">// 排序</span>
queryBuilder<span class="token punctuation">.</span><span class="token function">withSort</span><span class="token punctuation">(</span>SortBuilders<span class="token punctuation">.</span><span class="token function">fieldSort</span><span class="token punctuation">(</span><span class="token string">"price"</span><span class="token punctuation">)</span><span class="token punctuation">.</span><span class="token function">order</span><span class="token punctuation">(</span>SortOrder<span class="token punctuation">.</span>ASC<span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">;</span>

<span class="token comment">// 搜索，获取结果</span>
Page<span class="token generics function"><span class="token punctuation">&lt;</span>Item<span class="token punctuation">&gt;</span></span> items <span class="token operator">=</span> <span class="token keyword">this</span><span class="token punctuation">.</span>itemRepository<span class="token punctuation">.</span><span class="token function">search</span><span class="token punctuation">(</span>queryBuilder<span class="token punctuation">.</span><span class="token function">build</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token comment">// 总条数</span>
<span class="token keyword">long</span> total <span class="token operator">=</span> items<span class="token punctuation">.</span><span class="token function">getTotalElements</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
System<span class="token punctuation">.</span>out<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">"总条数 = "</span> <span class="token operator">+</span> total<span class="token punctuation">)</span><span class="token punctuation">;</span>

<span class="token keyword">for</span> <span class="token punctuation">(</span>Item item <span class="token operator">:</span> items<span class="token punctuation">)</span> <span class="token punctuation">{</span>
System<span class="token punctuation">.</span>out<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span>item<span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token punctuation">}</span>
<span class="token punctuation">}</span>
<div class="hljs-button {2}" data-title="复制"></div></validateCode></pre>
<p>结果：<br>
<img src="https://img-blog.csdnimg.cn/20181109180715231.png" alt="在这里插入图片描述"></p>
<h3><a name="t7"></a><a id="25_solr_813" target="_blank"></a>2.5 聚合（牛逼！！solr无此功能）</h3>
<p>聚合可以让我们极其方便的实现对数据的统计、分析。例如：</p>
<ul>
<li>什么品牌的手机最受欢迎？</li>
<li>这些手机的平均价格、最高价格、最低价格？</li>
<li>这些手机每月的销售情况如何？</li>
</ul>
<p>实现这些统计功能的比数据库的sql要方便的多，而且查询速度非常快，可以实现近实时搜索效果。</p>
<h3><a name="t8"></a><a id="251__823" target="_blank"></a>2.5.1 聚合基本概念</h3>
<p>Elasticsearch中的聚合，包含多种类型，最常用的两种，一个叫<validateCode>桶</validateCode>，一个叫<validateCode>度量</validateCode>：</p>
<blockquote>
<p><strong>桶（bucket）</strong></p>
</blockquote>
<p>桶的作用，是按照某种方式对数据进行分组，每一组数据在ES中称为一个<validateCode>桶</validateCode>，例如我们根据国籍对人划分，可以得到<validateCode>中国桶</validateCode>、<validateCode>英国桶</validateCode>，<validateCode>日本桶</validateCode>……或者我们按照年龄段对人进行划分：0<sub>10,10</sub>20,20<sub>30,30</sub>40等。</p>
<p>Elasticsearch中提供的划分桶的方式有很多：</p>
<ul>
<li>Date Histogram Aggregation：根据日期阶梯分组，例如给定阶梯为周，会自动每周分为一组</li>
<li>Histogram Aggregation：根据数值阶梯分组，与日期类似</li>
<li>Terms Aggregation：根据词条内容分组，词条内容完全匹配的为一组</li>
<li>Range Aggregation：数值和日期的范围分组，指定开始和结束，然后按段分组</li>
<li>……</li>
</ul>
<p>综上所述，我们发现bucket aggregations 只负责对数据进行分组，并不进行计算，因此往往bucket中往往会嵌套另一种聚合：metrics aggregations即度量</p>
<blockquote>
<p><strong>度量（metrics）</strong></p>
</blockquote>
<p>分组完成以后，我们一般会对组中的数据进行聚合运算，例如求平均值、最大、最小、求和等，这些在ES中称为<validateCode>度量</validateCode></p>
<p>比较常用的一些度量聚合方式：</p>
<ul>
<li>Avg Aggregation：求平均值</li>
<li>Max Aggregation：求最大值</li>
<li>Min Aggregation：求最小值</li>
<li>Percentiles Aggregation：求百分比</li>
<li>Stats Aggregation：同时返回avg、max、min、sum、count等</li>
<li>Sum Aggregation：求和</li>
<li>Top hits Aggregation：求前几</li>
<li>Value Count Aggregation：求总数</li>
<li>……</li>
</ul>
<p><strong>注意</strong>：在ES中，需要进行聚合、排序、过滤的字段其处理方式比较特殊，因此不能被分词。这里我们将color和make这两个文字类型的字段设置为keyword类型，这个类型不会被分词，将来就可以参与聚合</p>
<h4><a id="252__867" target="_blank"></a>2.5.2 聚合为桶</h4>
<p>桶就是分组，比如这里我们按照品牌brand进行分组：</p>
<pre class="prettyprint"><validateCode class="prism language-java has-numbering" onclick="mdcp.copyCode(event)">	<span class="token comment">/**
* @Description:按照品牌brand进行分组
* @Author: https://blog.csdn.net/chen_2890			
*/</span>
<span class="token annotation punctuation">@Test</span>
<span class="token keyword">public</span> <span class="token keyword">void</span> <span class="token function">testAgg</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">{</span>
NativeSearchQueryBuilder queryBuilder <span class="token operator">=</span> <span class="token keyword">new</span> <span class="token class-name">NativeSearchQueryBuilder</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token comment">// 不查询任何结果</span>
queryBuilder<span class="token punctuation">.</span><span class="token function">withSourceFilter</span><span class="token punctuation">(</span><span class="token keyword">new</span> <span class="token class-name">FetchSourceFilter</span><span class="token punctuation">(</span><span class="token keyword">new</span> <span class="token class-name">String</span><span class="token punctuation">[</span><span class="token punctuation">]</span><span class="token punctuation">{</span><span class="token string">""</span><span class="token punctuation">}</span><span class="token punctuation">,</span> null<span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token comment">// 1、添加一个新的聚合，聚合类型为terms，聚合名称为brands，聚合字段为brand</span>
queryBuilder<span class="token punctuation">.</span><span class="token function">addAggregation</span><span class="token punctuation">(</span>
AggregationBuilders<span class="token punctuation">.</span><span class="token function">terms</span><span class="token punctuation">(</span><span class="token string">"brands"</span><span class="token punctuation">)</span><span class="token punctuation">.</span><span class="token function">field</span><span class="token punctuation">(</span><span class="token string">"brand"</span><span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token comment">// 2、查询,需要把结果强转为AggregatedPage类型</span>
AggregatedPage<span class="token generics function"><span class="token punctuation">&lt;</span>Item<span class="token punctuation">&gt;</span></span> aggPage <span class="token operator">=</span> <span class="token punctuation">(</span>AggregatedPage<span class="token generics function"><span class="token punctuation">&lt;</span>Item<span class="token punctuation">&gt;</span></span><span class="token punctuation">)</span> <span class="token keyword">this</span><span class="token punctuation">.</span>itemRepository<span class="token punctuation">.</span><span class="token function">search</span><span class="token punctuation">(</span>queryBuilder<span class="token punctuation">.</span><span class="token function">build</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token comment">// 3、解析</span>
<span class="token comment">// 3.1、从结果中取出名为brands的那个聚合，</span>
<span class="token comment">// 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型</span>
StringTerms agg <span class="token operator">=</span> <span class="token punctuation">(</span>StringTerms<span class="token punctuation">)</span> aggPage<span class="token punctuation">.</span><span class="token function">getAggregation</span><span class="token punctuation">(</span><span class="token string">"brands"</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token comment">// 3.2、获取桶</span>
List<span class="token generics function"><span class="token punctuation">&lt;</span>StringTerms<span class="token punctuation">.</span>Bucket<span class="token punctuation">&gt;</span></span> buckets <span class="token operator">=</span> agg<span class="token punctuation">.</span><span class="token function">getBuckets</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token comment">// 3.3、遍历</span>
<span class="token keyword">for</span> <span class="token punctuation">(</span>StringTerms<span class="token punctuation">.</span>Bucket bucket <span class="token operator">:</span> buckets<span class="token punctuation">)</span> <span class="token punctuation">{</span>
<span class="token comment">// 3.4、获取桶中的key，即品牌名称</span>
System<span class="token punctuation">.</span>out<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span>bucket<span class="token punctuation">.</span><span class="token function">getKeyAsString</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token comment">// 3.5、获取桶中的文档数量</span>
System<span class="token punctuation">.</span>out<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span>bucket<span class="token punctuation">.</span><span class="token function">getDocCount</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token punctuation">}</span>

<span class="token punctuation">}</span>
<div class="hljs-button {2}" data-title="复制"></div></validateCode></pre>
<p>显示的结果：<br>
<img src="https://img-blog.csdnimg.cn/20181109181206285.png" alt="在这里插入图片描述"><br>
关键API：</p>
<ul>
<li><validateCode>AggregationBuilders</validateCode>：聚合的构建工厂类。所有聚合都由这个类来构建，看看他的静态方法：<br>
<img src="https://img-blog.csdnimg.cn/20181109181233839.png?x-oss-process=imageCode/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2NoZW5fMjg5MA==,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"></li>
</ul>
<pre class="prettyprint"><validateCode class="has-numbering" onclick="mdcp.copyCode(event)">（1）统计某个字段的数量
ValueCountBuilder vcb=  AggregationBuilders.count("count_uid").field("uid");
（2）去重统计某个字段的数量（有少量误差）
CardinalityBuilder cb= AggregationBuilders.cardinality("distinct_count_uid").field("uid");
（3）聚合过滤
FilterAggregationBuilder fab= AggregationBuilders.filter("uid_filter").filter(QueryBuilders.queryStringQuery("uid:001"));
（4）按某个字段分组
TermsBuilder tb=  AggregationBuilders.terms("group_name").field("name");
（5）求和
SumBuilder  sumBuilder=	AggregationBuilders.sum("sum_price").field("price");
（6）求平均
AvgBuilder ab= AggregationBuilders.avg("avg_price").field("price");
（7）求最大值
MaxBuilder mb= AggregationBuilders.max("max_price").field("price"); 
（8）求最小值
MinBuilder min=	AggregationBuilders.min("min_price").field("price");
（9）按日期间隔分组
DateHistogramBuilder dhb= AggregationBuilders.dateHistogram("dh").field("date");
（10）获取聚合里面的结果
TopHitsBuilder thb=  AggregationBuilders.topHits("top_result");
（11）嵌套的聚合
NestedBuilder nb= AggregationBuilders.nested("negsted_path").path("quests");
（12）反转嵌套
AggregationBuilders.reverseNested("res_negsted").path("kps ");

<div class="hljs-button {2}" data-title="复制"></div></validateCode></pre>
<ul>
<li><validateCode>AggregatedPage</validateCode>：聚合查询的结果类。它是<validateCode>Page&lt;T&gt;</validateCode>的子接口：<br>
<img src="https://img-blog.csdnimg.cn/20181109181304811.png" alt="在这里插入图片描述"><br>
<validateCode>AggregatedPage</validateCode>在<validateCode>Page</validateCode>功能的基础上，拓展了与聚合相关的功能，它其实就是对聚合结果的一种封装。<br>
<img src="https://img-blog.csdnimg.cn/20181109181321609.png?x-oss-process=imageCode/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2NoZW5fMjg5MA==,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"><br>
而返回的结果都是Aggregation类型对象，不过根据字段类型不同，又有不同的子类表示<br>
<img src="https://img-blog.csdnimg.cn/20181109181339456.png?x-oss-process=imageCode/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2NoZW5fMjg5MA==,size_16,color_FFFFFF,t_70" alt="在这里插入图片描述"></li>
</ul>
<h4><a id="253__948" target="_blank"></a>2.5.3 嵌套聚合，求平均值</h4>
<p>代码：</p>
<pre class="prettyprint"><validateCode class="prism language-java has-numbering" onclick="mdcp.copyCode(event)">	<span class="token comment">/**
* @Description:嵌套聚合，求平均值
* @Author: https://blog.csdn.net/chen_2890			
*/</span>
<span class="token annotation punctuation">@Test</span>
<span class="token keyword">public</span> <span class="token keyword">void</span> <span class="token function">testSubAgg</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">{</span>
NativeSearchQueryBuilder queryBuilder <span class="token operator">=</span> <span class="token keyword">new</span> <span class="token class-name">NativeSearchQueryBuilder</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token comment">// 不查询任何结果</span>
queryBuilder<span class="token punctuation">.</span><span class="token function">withSourceFilter</span><span class="token punctuation">(</span><span class="token keyword">new</span> <span class="token class-name">FetchSourceFilter</span><span class="token punctuation">(</span><span class="token keyword">new</span> <span class="token class-name">String</span><span class="token punctuation">[</span><span class="token punctuation">]</span><span class="token punctuation">{</span><span class="token string">""</span><span class="token punctuation">}</span><span class="token punctuation">,</span> null<span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token comment">// 1、添加一个新的聚合，聚合类型为terms，聚合名称为brands，聚合字段为brand</span>
queryBuilder<span class="token punctuation">.</span><span class="token function">addAggregation</span><span class="token punctuation">(</span>
AggregationBuilders<span class="token punctuation">.</span><span class="token function">terms</span><span class="token punctuation">(</span><span class="token string">"brands"</span><span class="token punctuation">)</span><span class="token punctuation">.</span><span class="token function">field</span><span class="token punctuation">(</span><span class="token string">"brand"</span><span class="token punctuation">)</span>
<span class="token punctuation">.</span><span class="token function">subAggregation</span><span class="token punctuation">(</span>AggregationBuilders<span class="token punctuation">.</span><span class="token function">avg</span><span class="token punctuation">(</span><span class="token string">"priceAvg"</span><span class="token punctuation">)</span><span class="token punctuation">.</span><span class="token function">field</span><span class="token punctuation">(</span><span class="token string">"price"</span><span class="token punctuation">)</span><span class="token punctuation">)</span> <span class="token comment">// 在品牌聚合桶内进行嵌套聚合，求平均值</span>
<span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token comment">// 2、查询,需要把结果强转为AggregatedPage类型</span>
AggregatedPage<span class="token generics function"><span class="token punctuation">&lt;</span>Item<span class="token punctuation">&gt;</span></span> aggPage <span class="token operator">=</span> <span class="token punctuation">(</span>AggregatedPage<span class="token generics function"><span class="token punctuation">&lt;</span>Item<span class="token punctuation">&gt;</span></span><span class="token punctuation">)</span> <span class="token keyword">this</span><span class="token punctuation">.</span>itemRepository<span class="token punctuation">.</span><span class="token function">search</span><span class="token punctuation">(</span>queryBuilder<span class="token punctuation">.</span><span class="token function">build</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token comment">// 3、解析</span>
<span class="token comment">// 3.1、从结果中取出名为brands的那个聚合，</span>
<span class="token comment">// 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型</span>
StringTerms agg <span class="token operator">=</span> <span class="token punctuation">(</span>StringTerms<span class="token punctuation">)</span> aggPage<span class="token punctuation">.</span><span class="token function">getAggregation</span><span class="token punctuation">(</span><span class="token string">"brands"</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token comment">// 3.2、获取桶</span>
List<span class="token generics function"><span class="token punctuation">&lt;</span>StringTerms<span class="token punctuation">.</span>Bucket<span class="token punctuation">&gt;</span></span> buckets <span class="token operator">=</span> agg<span class="token punctuation">.</span><span class="token function">getBuckets</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token comment">// 3.3、遍历</span>
<span class="token keyword">for</span> <span class="token punctuation">(</span>StringTerms<span class="token punctuation">.</span>Bucket bucket <span class="token operator">:</span> buckets<span class="token punctuation">)</span> <span class="token punctuation">{</span>
<span class="token comment">// 3.4、获取桶中的key，即品牌名称  3.5、获取桶中的文档数量</span>
System<span class="token punctuation">.</span>out<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span>bucket<span class="token punctuation">.</span><span class="token function">getKeyAsString</span><span class="token punctuation">(</span><span class="token punctuation">)</span> <span class="token operator">+</span> <span class="token string">"，共"</span> <span class="token operator">+</span> bucket<span class="token punctuation">.</span><span class="token function">getDocCount</span><span class="token punctuation">(</span><span class="token punctuation">)</span> <span class="token operator">+</span> <span class="token string">"台"</span><span class="token punctuation">)</span><span class="token punctuation">;</span>

<span class="token comment">// 3.6.获取子聚合结果：</span>
InternalAvg avg <span class="token operator">=</span> <span class="token punctuation">(</span>InternalAvg<span class="token punctuation">)</span> bucket<span class="token punctuation">.</span><span class="token function">getAggregations</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">.</span><span class="token function">asMap</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">.</span><span class="token function">get</span><span class="token punctuation">(</span><span class="token string">"priceAvg"</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
System<span class="token punctuation">.</span>out<span class="token punctuation">.</span><span class="token function">println</span><span class="token punctuation">(</span><span class="token string">"平均售价："</span> <span class="token operator">+</span> avg<span class="token punctuation">.</span><span class="token function">getValue</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token punctuation">}</span>

<span class="token punctuation">}</span>
<div class="hljs-button {2}" data-title="复制"></div></validateCode></pre>
<p>结果：<br>
<img src="https://img-blog.csdnimg.cn/20181109181500390.png" alt="在这里插入图片描述"></p>
<p><validateCode>ok，SpringBoot整合Spring Data Elasticsearch到此完结</validateCode><br>
<validateCode>要是还有不太明白的地方请留言，评论必回</validateCode><br>
<validateCode>要是对我的文章感兴趣的话，关注一下吧，谢谢！</validateCode></p>
<h4><a id="ElasticsearchWindowshttpsblogcsdnnetchen_2890articledetails83757022_995" target="_blank"></a>上一篇：<a href="https://blog.csdn.net/chen_2890/article/details/83757022" rel="nofollow" target="_blank">Elasticsearch环境搭建和介绍（Windows）</a></h4>
<h4><a id="SpringBootShirohttpsblogcsdnnetchen_2890articledetails84933679_996" target="_blank"></a>下一篇：<a href="https://blog.csdn.net/chen_2890/article/details/84933679" rel="nofollow" target="_blank">SpringBoot整合Shiro</a></h4>

</div>