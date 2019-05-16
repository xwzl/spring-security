[Redis 缓存](https://www.jianshu.com/p/275cb42080d9)


<div class="show-content-free">
<h1>问题描述</h1>
<p>Spring  Cache提供的@Cacheable注解不支持配置过期时间，还有缓存的自动刷新。<br>
我们可以通过配置CacheManneg来配置默认的过期时间和针对每个缓存容器（value）单独配置过期时间，但是总是感觉不太灵活。下面是一个示例:</p>
<pre class="hljs java"><validateCode class="java"><span class="hljs-meta">@Bean</span>
<span class="hljs-function"><span class="hljs-keyword">public</span> CacheManager <span class="hljs-title">cacheManager</span><span class="hljs-params">(RedisTemplate redisTemplate)</span> </span>{
RedisCacheManager cacheManager= <span class="hljs-keyword">new</span> RedisCacheManager(redisTemplate);
cacheManager.setDefaultExpiration(<span class="hljs-number">60</span>);
Map&lt;String,Long&gt; expiresMap=<span class="hljs-keyword">new</span> HashMap&lt;&gt;();
expiresMap.put(<span class="hljs-string">"Product"</span>,<span class="hljs-number">5L</span>);
cacheManager.setExpires(expiresMap);
<span class="hljs-keyword">return</span> cacheManager;
}
</validateCode></pre>
<p>我们想在注解上直接配置过期时间和自动刷新时间，就像这样：</p>
<pre class="hljs java"><validateCode class="java"><span class="hljs-meta">@Cacheable</span>(value = <span class="hljs-string">"people#120#90"</span>, key = <span class="hljs-string">"#person.id"</span>)
<span class="hljs-function"><span class="hljs-keyword">public</span> Person <span class="hljs-title">findOne</span><span class="hljs-params">(Person person)</span> </span>{
Person p = personRepository.findOne(person.getId());
System.out.println(<span class="hljs-string">"为id、key为:"</span> + p.getId() + <span class="hljs-string">"数据做了缓存"</span>);
<span class="hljs-keyword">return</span> p;
}
</validateCode></pre>
<p>value属性上用#号隔开，第一个是原始的缓存容器名称，第二个是缓存的有效时间，第三个是缓存的自动刷新时间，单位都是秒。</p>
<p>缓存的有效时间和自动刷新时间支持SpEl表达式，支持在配置文件中配置，如：</p>
<pre class="hljs java"><validateCode class="java"><span class="hljs-meta">@Cacheable</span>(value = <span class="hljs-string">"people#${select.cache.timeout:1800}#${select.cache.refresh:600}"</span>, key = <span class="hljs-string">"#person.id"</span>, sync = <span class="hljs-keyword">true</span>)<span class="hljs-comment">//3</span>
<span class="hljs-function"><span class="hljs-keyword">public</span> Person <span class="hljs-title">findOne</span><span class="hljs-params">(Person person)</span> </span>{
Person p = personRepository.findOne(person.getId());
System.out.println(<span class="hljs-string">"为id、key为:"</span> + p.getId() + <span class="hljs-string">"数据做了缓存"</span>);
<span class="hljs-keyword">return</span> p;
}
</validateCode></pre>
<h1>解决思路</h1>
<p>查看源码你会发现缓存最顶级的接口就是CacheManager和Cache接口。</p>
<h2>CacheManager说明</h2>
<p>CacheManager功能其实很简单就是管理cache，接口只有两个方法，根据容器名称获取一个Cache。还有就是返回所有的缓存名称。</p>
<pre class="hljs java"><validateCode class="java"><span class="hljs-keyword">public</span> <span class="hljs-class"><span class="hljs-keyword">interface</span> <span class="hljs-title">CacheManager</span> </span>{

<span class="hljs-comment">/**
* 根据名称获取一个Cache（在实现类里面是如果有这个Cache就返回，没有就新建一个Cache放到Map容器中）
* <span class="hljs-doctag">@param</span> name the cache identifier (must not be {<span class="hljs-doctag">@validateCode</span> null})
* <span class="hljs-doctag">@return</span> the associated cache, or {<span class="hljs-doctag">@validateCode</span> null} if none found
*/</span>
<span class="hljs-function">Cache <span class="hljs-title">getCache</span><span class="hljs-params">(String name)</span></span>;

<span class="hljs-comment">/**
* 返回一个缓存名称的集合
* <span class="hljs-doctag">@return</span> the names of all caches known by the cache manager
*/</span>
<span class="hljs-function">Collection&lt;String&gt; <span class="hljs-title">getCacheNames</span><span class="hljs-params">()</span></span>;

}
</validateCode></pre>
<h2>Cache说明</h2>
<p>Cache接口主要是操作缓存的。get根据缓存key从缓存服务器获取缓存中的值，put根据缓存key将数据放到缓存服务器，evict根据key删除缓存中的数据。</p>
<pre class="hljs java"><validateCode class="java"><span class="hljs-keyword">public</span> <span class="hljs-class"><span class="hljs-keyword">interface</span> <span class="hljs-title">Cache</span> </span>{

<span class="hljs-function">ValueWrapper <span class="hljs-title">get</span><span class="hljs-params">(Object key)</span></span>;

<span class="hljs-function"><span class="hljs-keyword">void</span> <span class="hljs-title">put</span><span class="hljs-params">(Object key, Object value)</span></span>;

<span class="hljs-function"><span class="hljs-keyword">void</span> <span class="hljs-title">evict</span><span class="hljs-params">(Object key)</span></span>;

...
}

</validateCode></pre>
<h2>请求步骤</h2>
<ol>
<li>请求进来，在方法上面扫描@Cacheable注解，那么会触发org.springframework.cache.interceptor.CacheInterceptor缓存的拦截器。</li>
<li>然后会调用CacheManager的getCache方法，获取Cache，如果没有（第一次访问）就新建一Cache并返回。</li>
<li>根据获取到的Cache去调用get方法获取缓存中的值。RedisCache这里有个bug，源码是先判断key是否存在，再去缓存获取值，在高并发下有bug。</li>
</ol>
<h2>代码分析</h2>
<p>在最上面我们说了Spring Cache可以通过配置CacheManager来配置过期时间。那么这个过期时间是在哪里用的呢？设置默认的时间setDefaultExpiration，根据特定名称设置有效时间setExpires，获取一个缓存名称（value属性）的有效时间computeExpiration，真正使用有效时间是在createCache方法里面，而这个方法是在父类的getCache方法调用。通过RedisCacheManager源码我们看到：</p>
<pre class="hljs java"><validateCode class="java"><span class="hljs-comment">// 设置默认的时间</span>
<span class="hljs-function"><span class="hljs-keyword">public</span> <span class="hljs-keyword">void</span> <span class="hljs-title">setDefaultExpiration</span><span class="hljs-params">(<span class="hljs-keyword">long</span> defaultExpireTime)</span> </span>{
<span class="hljs-keyword">this</span>.defaultExpiration = defaultExpireTime;
}

<span class="hljs-comment">// 根据特定名称设置有效时间</span>
<span class="hljs-function"><span class="hljs-keyword">public</span> <span class="hljs-keyword">void</span> <span class="hljs-title">setExpires</span><span class="hljs-params">(Map&lt;String, Long&gt; expires)</span> </span>{
<span class="hljs-keyword">this</span>.expires = (expires != <span class="hljs-keyword">null</span> ? <span class="hljs-keyword">new</span> ConcurrentHashMap&lt;String, Long&gt;(expires) : <span class="hljs-keyword">null</span>);
}
<span class="hljs-comment">// 获取一个key的有效时间</span>
<span class="hljs-function"><span class="hljs-keyword">protected</span> <span class="hljs-keyword">long</span> <span class="hljs-title">computeExpiration</span><span class="hljs-params">(String name)</span> </span>{
Long expiration = <span class="hljs-keyword">null</span>;
<span class="hljs-keyword">if</span> (expires != <span class="hljs-keyword">null</span>) {
expiration = expires.get(name);
}
<span class="hljs-keyword">return</span> (expiration != <span class="hljs-keyword">null</span> ? expiration.longValue() : defaultExpiration);
}

<span class="hljs-meta">@SuppressWarnings</span>(<span class="hljs-string">"unchecked"</span>)
<span class="hljs-function"><span class="hljs-keyword">protected</span> RedisCache <span class="hljs-title">createCache</span><span class="hljs-params">(String cacheName)</span> </span>{
<span class="hljs-comment">// 调用了上面的方法获取缓存名称的有效时间</span>
<span class="hljs-keyword">long</span> expiration = computeExpiration(cacheName);
<span class="hljs-comment">// 创建了Cache对象，并使用了这个有效时间</span>
<span class="hljs-keyword">return</span> <span class="hljs-keyword">new</span> RedisCache(cacheName, (usePrefix ? cachePrefix.prefix(cacheName) : <span class="hljs-keyword">null</span>), redisOperations, expiration,
cacheNullValues);
}

<span class="hljs-comment">// 重写父类的getMissingCache。去创建Cache</span>
<span class="hljs-meta">@Override</span>
<span class="hljs-function"><span class="hljs-keyword">protected</span> Cache <span class="hljs-title">getMissingCache</span><span class="hljs-params">(String name)</span> </span>{
<span class="hljs-keyword">return</span> <span class="hljs-keyword">this</span>.dynamic ? createCache(name) : <span class="hljs-keyword">null</span>;
}
</validateCode></pre>
<p>AbstractCacheManager父类源码：</p>
<pre class="hljs java"><validateCode class="java"><span class="hljs-comment">// 根据名称获取Cache如果没有调用getMissingCache方法，生成新的Cache，并将其放到Map容器中去。</span>
<span class="hljs-meta">@Override</span>
<span class="hljs-function"><span class="hljs-keyword">public</span> Cache <span class="hljs-title">getCache</span><span class="hljs-params">(String name)</span> </span>{
Cache cache = <span class="hljs-keyword">this</span>.cacheMap.get(name);
<span class="hljs-keyword">if</span> (cache != <span class="hljs-keyword">null</span>) {
<span class="hljs-keyword">return</span> cache;
}
<span class="hljs-keyword">else</span> {
<span class="hljs-comment">// Fully synchronize now for missing cache creation...</span>
<span class="hljs-keyword">synchronized</span> (<span class="hljs-keyword">this</span>.cacheMap) {
cache = <span class="hljs-keyword">this</span>.cacheMap.get(name);
<span class="hljs-keyword">if</span> (cache == <span class="hljs-keyword">null</span>) {
<span class="hljs-comment">// 如果没找到Cache调用该方法，这个方法默认返回值NULL由子类自己实现。上面的就是子类自己实现的方法</span>
cache = getMissingCache(name);
<span class="hljs-keyword">if</span> (cache != <span class="hljs-keyword">null</span>) {
cache = decorateCache(cache);
<span class="hljs-keyword">this</span>.cacheMap.put(name, cache);
updateCacheNames(name);
}
}
<span class="hljs-keyword">return</span> cache;
}
}
}
</validateCode></pre>
<p>由此这个有效时间的设置关键就是在getCache方法上，这里的name参数就是我们注解上的value属性。所以在这里解析这个特定格式的名称我就可以拿到配置的过期时间和刷新时间。getMissingCache方法里面在新建缓存的时候将这个过期时间设置进去，生成的Cache对象操作缓存的时候就会带上我们的配置的过期时间，然后过期就生效了。解析SpEL表达式获取配置文件中的时间也在也一步完成。</p>
<p>CustomizedRedisCacheManager源码：</p>
<pre class="hljs java"><validateCode class="java"><span class="hljs-keyword">package</span> com.xiaolyuh.redis.cache;

<span class="hljs-keyword">import</span> com.xiaolyuh.redis.cache.helper.SpringContextHolder;
<span class="hljs-keyword">import</span> com.xiaolyuh.redis.utils.ReflectionUtils;
<span class="hljs-keyword">import</span> org.apache.commons.lang3.StringUtils;
<span class="hljs-keyword">import</span> org.slf4j.Logger;
<span class="hljs-keyword">import</span> org.slf4j.LoggerFactory;
<span class="hljs-keyword">import</span> org.springframework.beans.factory.annotation.Autowired;
<span class="hljs-keyword">import</span> org.springframework.beans.factory.support.DefaultListableBeanFactory;
<span class="hljs-keyword">import</span> org.springframework.cache.Cache;
<span class="hljs-keyword">import</span> org.springframework.data.redis.cache.RedisCacheManager;
<span class="hljs-keyword">import</span> org.springframework.data.redis.core.RedisOperations;

<span class="hljs-keyword">import</span> java.util.Collection;
<span class="hljs-keyword">import</span> java.util.concurrent.ConcurrentHashMap;

<span class="hljs-comment">/**
* 自定义的redis缓存管理器
* 支持方法上配置过期时间
* 支持热加载缓存：缓存即将过期时主动刷新缓存
*
* <span class="hljs-doctag">@author</span> yuhao.wang
*/</span>
<span class="hljs-keyword">public</span> <span class="hljs-class"><span class="hljs-keyword">class</span> <span class="hljs-title">CustomizedRedisCacheManager</span> <span class="hljs-keyword">extends</span> <span class="hljs-title">RedisCacheManager</span> </span>{

<span class="hljs-keyword">private</span> <span class="hljs-keyword">static</span> <span class="hljs-keyword">final</span> Logger logger = LoggerFactory.getLogger(CustomizedRedisCacheManager.class);

<span class="hljs-comment">/**
* 父类cacheMap字段
*/</span>
<span class="hljs-keyword">private</span> <span class="hljs-keyword">static</span> <span class="hljs-keyword">final</span> String SUPER_FIELD_CACHEMAP = <span class="hljs-string">"cacheMap"</span>;

<span class="hljs-comment">/**
* 父类dynamic字段
*/</span>
<span class="hljs-keyword">private</span> <span class="hljs-keyword">static</span> <span class="hljs-keyword">final</span> String SUPER_FIELD_DYNAMIC = <span class="hljs-string">"dynamic"</span>;

<span class="hljs-comment">/**
* 父类cacheNullValues字段
*/</span>
<span class="hljs-keyword">private</span> <span class="hljs-keyword">static</span> <span class="hljs-keyword">final</span> String SUPER_FIELD_CACHENULLVALUES = <span class="hljs-string">"cacheNullValues"</span>;

<span class="hljs-comment">/**
* 父类updateCacheNames方法
*/</span>
<span class="hljs-keyword">private</span> <span class="hljs-keyword">static</span> <span class="hljs-keyword">final</span> String SUPER_METHOD_UPDATECACHENAMES = <span class="hljs-string">"updateCacheNames"</span>;

<span class="hljs-comment">/**
* 缓存参数的分隔符
* 数组元素0=缓存的名称
* 数组元素1=缓存过期时间TTL
* 数组元素2=缓存在多少秒开始主动失效来强制刷新
*/</span>
<span class="hljs-keyword">private</span> <span class="hljs-keyword">static</span> <span class="hljs-keyword">final</span> String SEPARATOR = <span class="hljs-string">"#"</span>;

<span class="hljs-comment">/**
* SpEL标示符
*/</span>
<span class="hljs-keyword">private</span> <span class="hljs-keyword">static</span> <span class="hljs-keyword">final</span> String MARK = <span class="hljs-string">"$"</span>;

RedisCacheManager redisCacheManager = <span class="hljs-keyword">null</span>;

<span class="hljs-meta">@Autowired</span>
DefaultListableBeanFactory beanFactory;

<span class="hljs-function"><span class="hljs-keyword">public</span> <span class="hljs-title">CustomizedRedisCacheManager</span><span class="hljs-params">(RedisOperations redisOperations)</span> </span>{
<span class="hljs-keyword">super</span>(redisOperations);
}

<span class="hljs-function"><span class="hljs-keyword">public</span> <span class="hljs-title">CustomizedRedisCacheManager</span><span class="hljs-params">(RedisOperations redisOperations, Collection&lt;String&gt; cacheNames)</span> </span>{
<span class="hljs-keyword">super</span>(redisOperations, cacheNames);
}

<span class="hljs-function"><span class="hljs-keyword">public</span> RedisCacheManager <span class="hljs-title">getInstance</span><span class="hljs-params">()</span> </span>{
<span class="hljs-keyword">if</span> (redisCacheManager == <span class="hljs-keyword">null</span>) {
redisCacheManager = SpringContextHolder.getBean(RedisCacheManager.class);
}
<span class="hljs-keyword">return</span> redisCacheManager;
}

<span class="hljs-meta">@Override</span>
<span class="hljs-function"><span class="hljs-keyword">public</span> Cache <span class="hljs-title">getCache</span><span class="hljs-params">(String name)</span> </span>{
String[] cacheParams = name.split(SEPARATOR);
String cacheName = cacheParams[<span class="hljs-number">0</span>];

<span class="hljs-keyword">if</span> (StringUtils.isBlank(cacheName)) {
<span class="hljs-keyword">return</span> <span class="hljs-keyword">null</span>;
}

<span class="hljs-comment">// 有效时间，初始化获取默认的有效时间</span>
Long expirationSecondTime = getExpirationSecondTime(cacheName, cacheParams);
<span class="hljs-comment">// 自动刷新时间，默认是0</span>
Long preloadSecondTime = getExpirationSecondTime(cacheParams);

<span class="hljs-comment">// 通过反射获取父类存放缓存的容器对象</span>
Object object = ReflectionUtils.getFieldValue(getInstance(), SUPER_FIELD_CACHEMAP);
<span class="hljs-keyword">if</span> (object != <span class="hljs-keyword">null</span> &amp;&amp; object <span class="hljs-keyword">instanceof</span> ConcurrentHashMap) {
ConcurrentHashMap&lt;String, Cache&gt; cacheMap = (ConcurrentHashMap&lt;String, Cache&gt;) object;
<span class="hljs-comment">// 生成Cache对象，并将其保存到父类的Cache容器中</span>
<span class="hljs-keyword">return</span> getCache(cacheName, expirationSecondTime, preloadSecondTime, cacheMap);
} <span class="hljs-keyword">else</span> {
<span class="hljs-keyword">return</span> <span class="hljs-keyword">super</span>.getCache(cacheName);
}

}

<span class="hljs-comment">/**
* 获取过期时间
*
* <span class="hljs-doctag">@return</span>
*/</span>
<span class="hljs-function"><span class="hljs-keyword">private</span> <span class="hljs-keyword">long</span> <span class="hljs-title">getExpirationSecondTime</span><span class="hljs-params">(String cacheName, String[] cacheParams)</span> </span>{
<span class="hljs-comment">// 有效时间，初始化获取默认的有效时间</span>
Long expirationSecondTime = <span class="hljs-keyword">this</span>.computeExpiration(cacheName);

<span class="hljs-comment">// 设置key有效时间</span>
<span class="hljs-keyword">if</span> (cacheParams.length &gt; <span class="hljs-number">1</span>) {
String expirationStr = cacheParams[<span class="hljs-number">1</span>];
<span class="hljs-keyword">if</span> (!StringUtils.isEmpty(expirationStr)) {
<span class="hljs-comment">// 支持配置过期时间使用EL表达式读取配置文件时间</span>
<span class="hljs-keyword">if</span> (expirationStr.contains(MARK)) {
expirationStr = beanFactory.resolveEmbeddedValue(expirationStr);
}
expirationSecondTime = Long.parseLong(expirationStr);
}
}

<span class="hljs-keyword">return</span> expirationSecondTime;
}

<span class="hljs-comment">/**
* 获取自动刷新时间
*
* <span class="hljs-doctag">@return</span>
*/</span>
<span class="hljs-function"><span class="hljs-keyword">private</span> <span class="hljs-keyword">long</span> <span class="hljs-title">getExpirationSecondTime</span><span class="hljs-params">(String[] cacheParams)</span> </span>{
<span class="hljs-comment">// 自动刷新时间，默认是0</span>
Long preloadSecondTime = <span class="hljs-number">0L</span>;
<span class="hljs-comment">// 设置自动刷新时间</span>
<span class="hljs-keyword">if</span> (cacheParams.length &gt; <span class="hljs-number">2</span>) {
String preloadStr = cacheParams[<span class="hljs-number">2</span>];
<span class="hljs-keyword">if</span> (!StringUtils.isEmpty(preloadStr)) {
<span class="hljs-comment">// 支持配置刷新时间使用EL表达式读取配置文件时间</span>
<span class="hljs-keyword">if</span> (preloadStr.contains(MARK)) {
preloadStr = beanFactory.resolveEmbeddedValue(preloadStr);
}
preloadSecondTime = Long.parseLong(preloadStr);
}
}
<span class="hljs-keyword">return</span> preloadSecondTime;
}

<span class="hljs-comment">/**
* 重写父类的getCache方法，真假了三个参数
*
* <span class="hljs-doctag">@param</span> cacheName            缓存名称
* <span class="hljs-doctag">@param</span> expirationSecondTime 过期时间
* <span class="hljs-doctag">@param</span> preloadSecondTime    自动刷新时间
* <span class="hljs-doctag">@param</span> cacheMap             通过反射获取的父类的cacheMap对象
* <span class="hljs-doctag">@return</span> Cache
*/</span>
<span class="hljs-function"><span class="hljs-keyword">public</span> Cache <span class="hljs-title">getCache</span><span class="hljs-params">(String cacheName, <span class="hljs-keyword">long</span> expirationSecondTime, <span class="hljs-keyword">long</span> preloadSecondTime, ConcurrentHashMap&lt;String, Cache&gt; cacheMap)</span> </span>{
Cache cache = cacheMap.get(cacheName);
<span class="hljs-keyword">if</span> (cache != <span class="hljs-keyword">null</span>) {
<span class="hljs-keyword">return</span> cache;
} <span class="hljs-keyword">else</span> {
<span class="hljs-comment">// Fully synchronize now for missing cache creation...</span>
<span class="hljs-keyword">synchronized</span> (cacheMap) {
cache = cacheMap.get(cacheName);
<span class="hljs-keyword">if</span> (cache == <span class="hljs-keyword">null</span>) {
<span class="hljs-comment">// 调用我们自己的getMissingCache方法创建自己的cache</span>
cache = getMissingCache(cacheName, expirationSecondTime, preloadSecondTime);
<span class="hljs-keyword">if</span> (cache != <span class="hljs-keyword">null</span>) {
cache = decorateCache(cache);
cacheMap.put(cacheName, cache);

<span class="hljs-comment">// 反射去执行父类的updateCacheNames(cacheName)方法</span>
Class&lt;?&gt;[] parameterTypes = {String.class};
Object[] parameters = {cacheName};
ReflectionUtils.invokeMethod(getInstance(), SUPER_METHOD_UPDATECACHENAMES, parameterTypes, parameters);
}
}
<span class="hljs-keyword">return</span> cache;
}
}
}

<span class="hljs-comment">/**
* 创建缓存
*
* <span class="hljs-doctag">@param</span> cacheName            缓存名称
* <span class="hljs-doctag">@param</span> expirationSecondTime 过期时间
* <span class="hljs-doctag">@param</span> preloadSecondTime    制动刷新时间
* <span class="hljs-doctag">@return</span>
*/</span>
<span class="hljs-function"><span class="hljs-keyword">public</span> CustomizedRedisCache <span class="hljs-title">getMissingCache</span><span class="hljs-params">(String cacheName, <span class="hljs-keyword">long</span> expirationSecondTime, <span class="hljs-keyword">long</span> preloadSecondTime)</span> </span>{

logger.info(<span class="hljs-string">"缓存 cacheName：{}，过期时间:{}, 自动刷新时间:{}"</span>, cacheName, expirationSecondTime, preloadSecondTime);
Boolean dynamic = (Boolean) ReflectionUtils.getFieldValue(getInstance(), SUPER_FIELD_DYNAMIC);
Boolean cacheNullValues = (Boolean) ReflectionUtils.getFieldValue(getInstance(), SUPER_FIELD_CACHENULLVALUES);
<span class="hljs-keyword">return</span> dynamic ? <span class="hljs-keyword">new</span> CustomizedRedisCache(cacheName, (<span class="hljs-keyword">this</span>.isUsePrefix() ? <span class="hljs-keyword">this</span>.getCachePrefix().prefix(cacheName) : <span class="hljs-keyword">null</span>),
<span class="hljs-keyword">this</span>.getRedisOperations(), expirationSecondTime, preloadSecondTime, cacheNullValues) : <span class="hljs-keyword">null</span>;
}
}
</validateCode></pre>
<p>那自动刷新时间呢？</p>
<p>在RedisCache的属性里面没有刷新时间，所以我们继承该类重写我们自己的Cache的时候要多加一个属性preloadSecondTime来存储这个刷新时间。并在getMissingCache方法创建Cache对象的时候指定该值。</p>
<p>CustomizedRedisCache部分源码：</p>
<pre class="hljs java"><validateCode class="java"><span class="hljs-comment">/**
* 缓存主动在失效前强制刷新缓存的时间
* 单位：秒
*/</span>
<span class="hljs-keyword">private</span> <span class="hljs-keyword">long</span> preloadSecondTime = <span class="hljs-number">0</span>;

<span class="hljs-comment">// 重写后的构造方法</span>
<span class="hljs-function"><span class="hljs-keyword">public</span> <span class="hljs-title">CustomizedRedisCache</span><span class="hljs-params">(String name, <span class="hljs-keyword">byte</span>[] prefix, RedisOperations&lt;? extends Object, ? extends Object&gt; redisOperations, <span class="hljs-keyword">long</span> expiration, <span class="hljs-keyword">long</span> preloadSecondTime)</span> </span>{
<span class="hljs-keyword">super</span>(name, prefix, redisOperations, expiration);
<span class="hljs-keyword">this</span>.redisOperations = redisOperations;
<span class="hljs-comment">// 指定自动刷新时间</span>
<span class="hljs-keyword">this</span>.preloadSecondTime = preloadSecondTime;
<span class="hljs-keyword">this</span>.prefix = prefix;
}

<span class="hljs-comment">// 重写后的构造方法</span>
<span class="hljs-function"><span class="hljs-keyword">public</span> <span class="hljs-title">CustomizedRedisCache</span><span class="hljs-params">(String name, <span class="hljs-keyword">byte</span>[] prefix, RedisOperations&lt;? extends Object, ? extends Object&gt; redisOperations, <span class="hljs-keyword">long</span> expiration, <span class="hljs-keyword">long</span> preloadSecondTime, <span class="hljs-keyword">boolean</span> allowNullValues)</span> </span>{
<span class="hljs-keyword">super</span>(name, prefix, redisOperations, expiration, allowNullValues);
<span class="hljs-keyword">this</span>.redisOperations = redisOperations;
<span class="hljs-comment">// 指定自动刷新时间</span>
<span class="hljs-keyword">this</span>.preloadSecondTime = preloadSecondTime;
<span class="hljs-keyword">this</span>.prefix = prefix;
}
</validateCode></pre>
<p>那么这个自动刷新时间有了，怎么来让他自动刷新呢？</p>
<p>在调用Cache的get方法的时候我们都会去缓存服务查询缓存，这个时候我们在多查一个缓存的有效时间，和我们配置的自动刷新时间对比，如果缓存的有效时间小于这个自动刷新时间我们就去刷新缓存（这里注意一点在高并发下我们最好只放一个请求去刷新数据，尽量减少数据的压力，所以在这个位置加一个分布式锁）。所以我们重写这个get方法。</p>
<p>CustomizedRedisCache部分源码：</p>
<pre class="hljs java"><validateCode class="java"><span class="hljs-comment">/**
* 重写get方法，获取到缓存后再次取缓存剩余的时间，如果时间小余我们配置的刷新时间就手动刷新缓存。
* 为了不影响get的性能，启用后台线程去完成缓存的刷。
* 并且只放一个线程去刷新数据。
*
* <span class="hljs-doctag">@param</span> key
* <span class="hljs-doctag">@return</span>
*/</span>
<span class="hljs-meta">@Override</span>
<span class="hljs-function"><span class="hljs-keyword">public</span> ValueWrapper <span class="hljs-title">get</span><span class="hljs-params">(<span class="hljs-keyword">final</span> Object key)</span> </span>{
RedisCacheKey cacheKey = getRedisCacheKey(key);
String cacheKeyStr = <span class="hljs-keyword">new</span> String(cacheKey.getKeyBytes());
<span class="hljs-comment">// 调用重写后的get方法</span>
ValueWrapper valueWrapper = <span class="hljs-keyword">this</span>.get(cacheKey);

<span class="hljs-keyword">if</span> (<span class="hljs-keyword">null</span> != valueWrapper) {
<span class="hljs-comment">// 刷新缓存数据</span>
refreshCache(key, cacheKeyStr);
}
<span class="hljs-keyword">return</span> valueWrapper;
}

<span class="hljs-comment">/**
* 重写父类的get函数。
* 父类的get方法，是先使用exists判断key是否存在，不存在返回null，存在再到redis缓存中去取值。这样会导致并发问题，
* 假如有一个请求调用了exists函数判断key存在，但是在下一时刻这个缓存过期了，或者被删掉了。
* 这时候再去缓存中获取值的时候返回的就是null了。
* 可以先获取缓存的值，再去判断key是否存在。
*
* <span class="hljs-doctag">@param</span> cacheKey
* <span class="hljs-doctag">@return</span>
*/</span>
<span class="hljs-meta">@Override</span>
<span class="hljs-function"><span class="hljs-keyword">public</span> RedisCacheElement <span class="hljs-title">get</span><span class="hljs-params">(<span class="hljs-keyword">final</span> RedisCacheKey cacheKey)</span> </span>{

Assert.notNull(cacheKey, <span class="hljs-string">"CacheKey must not be null!"</span>);

<span class="hljs-comment">// 根据key获取缓存值</span>
RedisCacheElement redisCacheElement = <span class="hljs-keyword">new</span> RedisCacheElement(cacheKey, fromStoreValue(lookup(cacheKey)));
<span class="hljs-comment">// 判断key是否存在</span>
Boolean exists = (Boolean) redisOperations.execute(<span class="hljs-keyword">new</span> RedisCallback&lt;Boolean&gt;() {

<span class="hljs-meta">@Override</span>
<span class="hljs-function"><span class="hljs-keyword">public</span> Boolean <span class="hljs-title">doInRedis</span><span class="hljs-params">(RedisConnection connection)</span> <span class="hljs-keyword">throws</span> DataAccessException </span>{
<span class="hljs-keyword">return</span> connection.exists(cacheKey.getKeyBytes());
}
});

<span class="hljs-keyword">if</span> (!exists.booleanValue()) {
<span class="hljs-keyword">return</span> <span class="hljs-keyword">null</span>;
}

<span class="hljs-keyword">return</span> redisCacheElement;
}

<span class="hljs-comment">/**
* 刷新缓存数据
*/</span>
<span class="hljs-function"><span class="hljs-keyword">private</span> <span class="hljs-keyword">void</span> <span class="hljs-title">refreshCache</span><span class="hljs-params">(Object key, String cacheKeyStr)</span> </span>{
Long ttl = <span class="hljs-keyword">this</span>.redisOperations.getExpire(cacheKeyStr);
<span class="hljs-keyword">if</span> (<span class="hljs-keyword">null</span> != ttl &amp;&amp; ttl &lt;= CustomizedRedisCache.<span class="hljs-keyword">this</span>.preloadSecondTime) {
<span class="hljs-comment">// 尽量少的去开启线程，因为线程池是有限的</span>
ThreadTaskHelper.run(<span class="hljs-keyword">new</span> Runnable() {
<span class="hljs-meta">@Override</span>
<span class="hljs-function"><span class="hljs-keyword">public</span> <span class="hljs-keyword">void</span> <span class="hljs-title">run</span><span class="hljs-params">()</span> </span>{
<span class="hljs-comment">// 加一个分布式锁，只放一个请求去刷新缓存</span>
RedisLock redisLock = <span class="hljs-keyword">new</span> RedisLock((RedisTemplate) redisOperations, cacheKeyStr + <span class="hljs-string">"_lock"</span>);
<span class="hljs-keyword">try</span> {
<span class="hljs-keyword">if</span> (redisLock.lock()) {
<span class="hljs-comment">// 获取锁之后再判断一下过期时间，看是否需要加载数据</span>
Long ttl = CustomizedRedisCache.<span class="hljs-keyword">this</span>.redisOperations.getExpire(cacheKeyStr);
<span class="hljs-keyword">if</span> (<span class="hljs-keyword">null</span> != ttl &amp;&amp; ttl &lt;= CustomizedRedisCache.<span class="hljs-keyword">this</span>.preloadSecondTime) {
<span class="hljs-comment">// 通过获取代理方法信息重新加载缓存数据</span>
CustomizedRedisCache.<span class="hljs-keyword">this</span>.getCacheSupport().refreshCacheByKey(CustomizedRedisCache.<span class="hljs-keyword">super</span>.getName(), key.toString());
}
}
} <span class="hljs-keyword">catch</span> (Exception e) {
logger.info(e.getMessage(), e);
} <span class="hljs-keyword">finally</span> {
redisLock.unlock();
}
}
});
}
}
</validateCode></pre>
<p>那么自动刷新肯定要掉用方法访问数据库，获取值后去刷新缓存。这时我们又怎么能去调用方法呢？</p>
<p>我们利用java的反射机制。所以我们要用一个容器来存放缓存方法的方法信息，包括对象，方法名称，参数等等。我们创建了CachedInvocation类来存放这些信息，再将这个类的对象维护到容器中。</p>
<p>CachedInvocation源码：</p>
<pre class="hljs java"><validateCode class="java"><span class="hljs-keyword">public</span> <span class="hljs-keyword">final</span> <span class="hljs-class"><span class="hljs-keyword">class</span> <span class="hljs-title">CachedInvocation</span> </span>{

<span class="hljs-keyword">private</span> Object key;
<span class="hljs-keyword">private</span> <span class="hljs-keyword">final</span> Object targetBean;
<span class="hljs-keyword">private</span> <span class="hljs-keyword">final</span> Method targetMethod;
<span class="hljs-keyword">private</span> Object[] arguments;

<span class="hljs-function"><span class="hljs-keyword">public</span> <span class="hljs-title">CachedInvocation</span><span class="hljs-params">(Object key, Object targetBean, Method targetMethod, Object[] arguments)</span> </span>{
<span class="hljs-keyword">this</span>.key = key;
<span class="hljs-keyword">this</span>.targetBean = targetBean;
<span class="hljs-keyword">this</span>.targetMethod = targetMethod;
<span class="hljs-keyword">if</span> (arguments != <span class="hljs-keyword">null</span> &amp;&amp; arguments.length != <span class="hljs-number">0</span>) {
<span class="hljs-keyword">this</span>.arguments = Arrays.copyOf(arguments, arguments.length);
}
}

<span class="hljs-keyword">public</span> Object[] getArguments() {
<span class="hljs-keyword">return</span> arguments;
}

<span class="hljs-function"><span class="hljs-keyword">public</span> Object <span class="hljs-title">getTargetBean</span><span class="hljs-params">()</span> </span>{
<span class="hljs-keyword">return</span> targetBean;
}

<span class="hljs-function"><span class="hljs-keyword">public</span> Method <span class="hljs-title">getTargetMethod</span><span class="hljs-params">()</span> </span>{
<span class="hljs-keyword">return</span> targetMethod;
}

<span class="hljs-function"><span class="hljs-keyword">public</span> Object <span class="hljs-title">getKey</span><span class="hljs-params">()</span> </span>{
<span class="hljs-keyword">return</span> key;
}

<span class="hljs-comment">/**
* 必须重写equals和hashCode方法，否则放到set集合里没法去重
* <span class="hljs-doctag">@param</span> o
* <span class="hljs-doctag">@return</span>
*/</span>
<span class="hljs-meta">@Override</span>
<span class="hljs-function"><span class="hljs-keyword">public</span> <span class="hljs-keyword">boolean</span> <span class="hljs-title">equals</span><span class="hljs-params">(Object o)</span> </span>{
<span class="hljs-keyword">if</span> (<span class="hljs-keyword">this</span> == o) {
<span class="hljs-keyword">return</span> <span class="hljs-keyword">true</span>;
}
<span class="hljs-keyword">if</span> (o == <span class="hljs-keyword">null</span> || getClass() != o.getClass()) {
<span class="hljs-keyword">return</span> <span class="hljs-keyword">false</span>;
}

CachedInvocation that = (CachedInvocation) o;

<span class="hljs-keyword">return</span> key.equals(that.key);
}

<span class="hljs-meta">@Override</span>
<span class="hljs-function"><span class="hljs-keyword">public</span> <span class="hljs-keyword">int</span> <span class="hljs-title">hashCode</span><span class="hljs-params">()</span> </span>{
<span class="hljs-keyword">return</span> key.hashCode();
}
}
</validateCode></pre>
<p>（方案一）维护缓存方法信息的容器（在内存中建一个MAP）和刷新缓存的类CacheSupportImpl 关键代码：</p>
<pre class="hljs java"><validateCode class="java"><span class="hljs-keyword">private</span> <span class="hljs-keyword">final</span> String SEPARATOR = <span class="hljs-string">"#"</span>;

<span class="hljs-comment">/**
* 记录缓存执行方法信息的容器。
* 如果有很多无用的缓存数据的话，有可能会照成内存溢出。
*/</span>
<span class="hljs-keyword">private</span> Map&lt;String, Set&lt;CachedInvocation&gt;&gt; cacheToInvocationsMap = <span class="hljs-keyword">new</span> ConcurrentHashMap&lt;&gt;();

<span class="hljs-meta">@Autowired</span>
<span class="hljs-keyword">private</span> CacheManager cacheManager;

<span class="hljs-comment">// 刷新缓存</span>
<span class="hljs-function"><span class="hljs-keyword">private</span> <span class="hljs-keyword">void</span> <span class="hljs-title">refreshCache</span><span class="hljs-params">(CachedInvocation invocation, String cacheName)</span> </span>{

<span class="hljs-keyword">boolean</span> invocationSuccess;
Object computed = <span class="hljs-keyword">null</span>;
<span class="hljs-keyword">try</span> {
<span class="hljs-comment">// 通过代理调用方法，并记录返回值</span>
computed = invoke(invocation);
invocationSuccess = <span class="hljs-keyword">true</span>;
} <span class="hljs-keyword">catch</span> (Exception ex) {
invocationSuccess = <span class="hljs-keyword">false</span>;
}
<span class="hljs-keyword">if</span> (invocationSuccess) {
<span class="hljs-keyword">if</span> (!CollectionUtils.isEmpty(cacheToInvocationsMap.get(cacheName))) {
<span class="hljs-comment">// 通过cacheManager获取操作缓存的cache对象</span>
Cache cache = cacheManager.getCache(cacheName);
<span class="hljs-comment">// 通过Cache对象更新缓存</span>
cache.put(invocation.getKey(), computed);
}
}
}

<span class="hljs-function"><span class="hljs-keyword">private</span> Object <span class="hljs-title">invoke</span><span class="hljs-params">(CachedInvocation invocation)</span>
<span class="hljs-keyword">throws</span> ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException </span>{

<span class="hljs-keyword">final</span> MethodInvoker invoker = <span class="hljs-keyword">new</span> MethodInvoker();
invoker.setTargetObject(invocation.getTargetBean());
invoker.setArguments(invocation.getArguments());
invoker.setTargetMethod(invocation.getTargetMethod().getName());
invoker.prepare();

<span class="hljs-keyword">return</span> invoker.invoke();
}

<span class="hljs-comment">// 注册缓存方法的执行类信息</span>
<span class="hljs-meta">@Override</span>
<span class="hljs-function"><span class="hljs-keyword">public</span> <span class="hljs-keyword">void</span> <span class="hljs-title">registerInvocation</span><span class="hljs-params">(Object targetBean, Method targetMethod, Object[] arguments,
Set&lt;String&gt; annotatedCacheNames, String cacheKey)</span> </span>{

<span class="hljs-comment">// 获取注解上真实的value值</span>
Collection&lt;String&gt; cacheNames = generateValue(annotatedCacheNames);

<span class="hljs-comment">// 获取注解上的key属性值</span>
Class&lt;?&gt; targetClass = getTargetClass(targetBean);
Collection&lt;? extends Cache&gt; caches = getCache(cacheNames);
Object key = generateKey(caches, cacheKey, targetMethod, arguments, targetBean, targetClass,
CacheOperationExpressionEvaluator.NO_RESULT);

<span class="hljs-comment">// 新建一个代理对象（记录了缓存注解的方法类信息）</span>
<span class="hljs-keyword">final</span> CachedInvocation invocation = <span class="hljs-keyword">new</span> CachedInvocation(key, targetBean, targetMethod, arguments);
<span class="hljs-keyword">for</span> (<span class="hljs-keyword">final</span> String cacheName : cacheNames) {
<span class="hljs-keyword">if</span> (!cacheToInvocationsMap.containsKey(cacheName)) {
cacheToInvocationsMap.put(cacheName, <span class="hljs-keyword">new</span> CopyOnWriteArraySet&lt;&gt;());
}
cacheToInvocationsMap.get(cacheName).add(invocation);
}
}

<span class="hljs-meta">@Override</span>
<span class="hljs-function"><span class="hljs-keyword">public</span> <span class="hljs-keyword">void</span> <span class="hljs-title">refreshCache</span><span class="hljs-params">(String cacheName)</span> </span>{
<span class="hljs-keyword">this</span>.refreshCacheByKey(cacheName, <span class="hljs-keyword">null</span>);
}


<span class="hljs-comment">// 刷新特定key缓存</span>
<span class="hljs-meta">@Override</span>
<span class="hljs-function"><span class="hljs-keyword">public</span> <span class="hljs-keyword">void</span> <span class="hljs-title">refreshCacheByKey</span><span class="hljs-params">(String cacheName, String cacheKey)</span> </span>{
<span class="hljs-comment">// 如果根据缓存名称没有找到代理信息类的set集合就不执行刷新操作。</span>
<span class="hljs-comment">// 只有等缓存有效时间过了，再走到切面哪里然后把代理方法信息注册到这里来。</span>
<span class="hljs-keyword">if</span> (!CollectionUtils.isEmpty(cacheToInvocationsMap.get(cacheName))) {
<span class="hljs-keyword">for</span> (<span class="hljs-keyword">final</span> CachedInvocation invocation : cacheToInvocationsMap.get(cacheName)) {
<span class="hljs-keyword">if</span> (!StringUtils.isBlank(cacheKey) &amp;&amp; invocation.getKey().toString().equals(cacheKey)) {
logger.info(<span class="hljs-string">"缓存：{}-{}，重新加载数据"</span>, cacheName, cacheKey.getBytes());
refreshCache(invocation, cacheName);
}
}
}
}

</validateCode></pre>
<p>（方案二）维护缓存方法信息的容器（放到Redis）这个部分代码贴出来，直接看源码。</p>
<p>现在刷新缓存和注册缓存执行方法的信息都有了，我们怎么来把这个执行方法信息注册到容器里面呢？这里还少了触发点。</p>
<p>所以我们还需要一个切面，当执行@Cacheable注解获取缓存信息的时候我们还需要注册执行方法的信息，所以我们写了一个切面：</p>
<pre class="hljs java"><validateCode class="java"><span class="hljs-comment">/**
* 缓存拦截，用于注册方法信息
* <span class="hljs-doctag">@author</span> yuhao.wang
*/</span>
<span class="hljs-meta">@Aspect</span>
<span class="hljs-meta">@Component</span>
<span class="hljs-keyword">public</span> <span class="hljs-class"><span class="hljs-keyword">class</span> <span class="hljs-title">CachingAnnotationsAspect</span> </span>{

<span class="hljs-keyword">private</span> <span class="hljs-keyword">static</span> <span class="hljs-keyword">final</span> Logger logger = LoggerFactory.getLogger(CachingAnnotationsAspect.class);

<span class="hljs-meta">@Autowired</span>
<span class="hljs-keyword">private</span> InvocationRegistry cacheRefreshSupport;

<span class="hljs-keyword">private</span> &lt;T extends Annotation&gt; <span class="hljs-function">List&lt;T&gt; <span class="hljs-title">getMethodAnnotations</span><span class="hljs-params">(AnnotatedElement ae, Class&lt;T&gt; annotationType)</span> </span>{
List&lt;T&gt; anns = <span class="hljs-keyword">new</span> ArrayList&lt;T&gt;(<span class="hljs-number">2</span>);
<span class="hljs-comment">// look for raw annotation</span>
T ann = ae.getAnnotation(annotationType);
<span class="hljs-keyword">if</span> (ann != <span class="hljs-keyword">null</span>) {
anns.add(ann);
}
<span class="hljs-comment">// look for meta-annotations</span>
<span class="hljs-keyword">for</span> (Annotation metaAnn : ae.getAnnotations()) {
ann = metaAnn.annotationType().getAnnotation(annotationType);
<span class="hljs-keyword">if</span> (ann != <span class="hljs-keyword">null</span>) {
anns.add(ann);
}
}
<span class="hljs-keyword">return</span> (anns.isEmpty() ? <span class="hljs-keyword">null</span> : anns);
}

<span class="hljs-function"><span class="hljs-keyword">private</span> Method <span class="hljs-title">getSpecificmethod</span><span class="hljs-params">(ProceedingJoinPoint pjp)</span> </span>{
MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
Method method = methodSignature.getMethod();
<span class="hljs-comment">// The method may be on an interface, but we need attributes from the</span>
<span class="hljs-comment">// target class. If the target class is null, the method will be</span>
<span class="hljs-comment">// unchanged.</span>
Class&lt;?&gt; targetClass = AopProxyUtils.ultimateTargetClass(pjp.getTarget());
<span class="hljs-keyword">if</span> (targetClass == <span class="hljs-keyword">null</span> &amp;&amp; pjp.getTarget() != <span class="hljs-keyword">null</span>) {
targetClass = pjp.getTarget().getClass();
}
Method specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass);
<span class="hljs-comment">// If we are dealing with method with generic parameters, find the</span>
<span class="hljs-comment">// original method.</span>
specificMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);
<span class="hljs-keyword">return</span> specificMethod;
}

<span class="hljs-meta">@Pointcut</span>(<span class="hljs-string">"@annotation(org.springframework.cache.annotation.Cacheable)"</span>)
<span class="hljs-function"><span class="hljs-keyword">public</span> <span class="hljs-keyword">void</span> <span class="hljs-title">pointcut</span><span class="hljs-params">()</span> </span>{
}

<span class="hljs-meta">@Around</span>(<span class="hljs-string">"pointcut()"</span>)
<span class="hljs-function"><span class="hljs-keyword">public</span> Object <span class="hljs-title">registerInvocation</span><span class="hljs-params">(ProceedingJoinPoint joinPoint)</span> <span class="hljs-keyword">throws</span> Throwable </span>{

Method method = <span class="hljs-keyword">this</span>.getSpecificmethod(joinPoint);

List&lt;Cacheable&gt; annotations = <span class="hljs-keyword">this</span>.getMethodAnnotations(method, Cacheable.class);

Set&lt;String&gt; cacheSet = <span class="hljs-keyword">new</span> HashSet&lt;String&gt;();
String cacheKey = <span class="hljs-keyword">null</span>;
<span class="hljs-keyword">for</span> (Cacheable cacheables : annotations) {
cacheSet.addAll(Arrays.asList(cacheables.value()));
cacheKey = cacheables.key();
}
cacheRefreshSupport.registerInvocation(joinPoint.getTarget(), method, joinPoint.getArgs(), cacheSet, cacheKey);
<span class="hljs-keyword">return</span> joinPoint.proceed();

}
}
</validateCode></pre>
<p><strong>注意：一个缓存名称（@Cacheable的value属性），也只能配置一个过期时间，如果配置多个以第一次配置的为准。</strong></p>
<p>至此我们就把完整的设置过期时间和刷新缓存都实现了，当然还可能存在一定问题，希望大家多多指教。</p>
<p>使用这种方式有个不好的地方，我们破坏了Spring Cache的结构，导致我们切换Cache的方式的时候要改代码，有很大的依赖性。</p>
<p><a href="https://www.jianshu.com/p/e53c1b60c6e1" target="_blank">下一篇</a>我将对 redisCacheManager.setExpires()方法进行扩展来实现过期时间和自动刷新，进而不会去破坏Spring Cache的原有结构，切换缓存就不会有问题了。</p>
<p>代码结构图：</p>
<br>
<div class="imageCode-package">
<div class="imageCode-container" style="max-width: 418px; max-height: 594px; background-color: transparent;">
<div class="imageCode-container-fill" style="padding-bottom: 142.11%;"></div>
<div class="imageCode-view" data-width="418" data-height="594"><img data-original-src="//upload-images.jianshu.io/upload_images/6464086-ae81068a0ade2a41" data-original-width="418" data-original-height="594" data-original-format="" data-original-filesize="43946" style="cursor: zoom-in;" class="" src="//upload-images.jianshu.io/upload_images/6464086-ae81068a0ade2a41?imageMogr2/auto-orient/strip%7CimageView2/2/w/418/format/webp"></div>
</div>
<div class="imageCode-caption">imageCode</div>
</div>
<p>源码地址：<br>
<a href="https://github.com/wyh-spring-ecosystem-student/spring-boot-student/tree/releases" target="_blank" rel="nofollow">https://github.com/wyh-spring-ecosystem-student/spring-boot-student/tree/releases</a></p>
<p>spring-boot-student-cache-redis 工程</p>
<p>参考：</p>
<ul>
<li><a href="http://www.cnblogs.com/ASPNET2008/p/6511500.html" target="_blank" rel="nofollow">http://www.cnblogs.com/ASPNET2008/p/6511500.html</a></li>
<li><a href="http://www.cnblogs.com/bobsha/p/6507165.html" target="_blank" rel="nofollow">http://www.cnblogs.com/bobsha/p/6507165.html</a></li>
<li><a href="http://m.blog.csdn.net/whatlookingfor/article/details/51833378" target="_blank" rel="nofollow">http://m.blog.csdn.net/whatlookingfor/article/details/51833378</a></li>
<li><a href="http://hbxflihua.iteye.com/blog/2354414" target="_blank" rel="nofollow">http://hbxflihua.iteye.com/blog/2354414</a></li>
</ul>
<p><a href="https://github.com/xiaolyuh/layering-cache" target="_blank" rel="nofollow">为监控而生的多级缓存框架 layering-cache</a>这是我开源的一个多级缓存框架的实现，如果有兴趣可以看一下</p>
<div class="imageCode-package">
<div class="imageCode-container" style="max-width: 700px; max-height: 971px; background-color: transparent;">
<div class="imageCode-container-fill" style="padding-bottom: 138.74%;"></div>
<div class="imageCode-view" data-width="1079" data-height="1497"><img data-original-src="//upload-images.jianshu.io/upload_images/6464086-66015db4df45091e" data-original-width="1079" data-original-height="1497" data-original-format="" data-original-filesize="134083" style="cursor: zoom-in;" class="" src="//upload-images.jianshu.io/upload_images/6464086-66015db4df45091e?imageMogr2/auto-orient/strip%7CimageView2/2/w/1000/format/webp"></div>
</div>
<div class="imageCode-caption">扫码有红包哦</div>
</div>

</div>