<div class="article-entry" itemprop="articleBody">

<p>以下面试题，基于网络整理，和自己编辑。具体参考的文章，会在文末给出所有的链接。</p>
<p>如果胖友有自己的疑问，欢迎在星球提问，我们一起整理吊吊的 Spring Boot 面试题的大保健。</p>
<p>而题目的难度，艿艿尽量按照从容易到困难的顺序，逐步下去。</p>
<p>在内容上，我们会分成两大块：</p>
<ul>
<li>核心技术篇，分享 Spring Boot 的核心技术相关的内容。</li>
<li>整合篇，分享 Spring Boot 整合一些框架的面试题，例如 JPA 如何集成到 Spring Boot 中。</li>
</ul>
<h1 id="核心技术篇"><a href="#核心技术篇" class="headerlink" title="核心技术篇"></a>核心技术篇</h1><h2 id="Spring-Boot-是什么？"><a href="#Spring-Boot-是什么？" class="headerlink" title="Spring Boot 是什么？"></a>Spring Boot 是什么？</h2><p><a href="https://github.com/spring-projects/spring-boot" rel="external nofollow noopener noreferrer" target="_blank">Spring Boot</a> 是 Spring 的<strong>子项目</strong>，正如其名字，提供 Spring 的引导( <strong>Boot</strong> )的功能。</p>
<p>通过 Spring Boot ，我们开发者可以快速配置 Spring 项目，引入各种 Spring MVC、Spring Transaction、Spring AOP、MyBatis 等等框架，而无需不断重复编写繁重的 Spring 配置，降低了 Spring 的使用成本。</p>
<blockquote>
<p>艿艿：犹记当年，Spring XML 为主的时代，大晚上各种搜索 Spring 的配置，苦不堪言。现在有了 Spring Boot 之后，生活真美好。</p>
</blockquote>
<p>Spring Boot 提供了各种 Starter 启动器，提供标准化的默认配置。例如：</p>
<ul>
<li><a href="https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-web/2.1.1.RELEASE" rel="external nofollow noopener noreferrer" target="_blank"><code>spring-boot-starter-web</code></a> 启动器，可以快速配置 Spring MVC 。</li>
<li><a href="https://mvnrepository.com/artifact/org.mybatis.spring.boot/mybatis-spring-boot-starter/1.3.2" rel="external nofollow noopener noreferrer" target="_blank"><code>mybatis-spring-boot-starter</code></a> 启动器，可以快速配置 MyBatis 。 </li>
</ul>
<p>并且，Spring Boot 基本已经一统 Java 项目的开发，大量的开源项目都实现了其的 Starter 启动器。例如：</p>
<ul>
<li><a href="https://github.com/apache/incubator-dubbo-spring-boot-project" rel="external nofollow noopener noreferrer" target="_blank"><code>incubator-dubbo-spring-boot-project</code></a> 启动器，可以快速配置 Dubbo 。</li>
<li><a href="https://github.com/maihaoche/rocketmq-spring-boot-starter" rel="external nofollow noopener noreferrer" target="_blank"><code>rocketmq-spring-boot-starter</code></a> 启动器，可以快速配置 RocketMQ 。</li>
</ul>
<h2 id="Spring-Boot-提供了哪些核心功能？"><a href="#Spring-Boot-提供了哪些核心功能？" class="headerlink" title="Spring Boot 提供了哪些核心功能？"></a>Spring Boot 提供了哪些核心功能？</h2><ul>
<li><p>1、独立运行 Spring 项目</p>
<p>  Spring Boot 可以以 jar 包形式独立运行，运行一个 Spring Boot 项目只需要通过 <code>java -jar xx.jar</code> 来运行。</p>
</li>
</ul>
<ul>
<li><p>2、内嵌 Servlet 容器</p>
<p>  Spring Boot 可以选择内嵌 Tomcat、Jetty 或者 Undertow，这样我们无须以 war 包形式部署项目。</p>
<blockquote>
<p>第 2 点是对第 1 点的补充，在 Spring Boot 未出来的时候，大多数 Web 项目，是打包成 war 包，部署到 Tomcat、Jetty 等容器。</p>
</blockquote>
</li>
<li><p>3、提供 Starter 简化 Maven 配置</p>
<p>  Spring 提供了一系列的 starter pom 来简化 Maven 的依赖加载。例如，当你使用了 <code>spring-boot-starter-web</code> ，会自动加入如下依赖：<img src="http://static2.iocoder.cn/images/Spring/2018-12-26/01.png" alt="`spring-boot-starter-web` 的 pom 文件"></p>
</li>
<li><p>4、<a href="https://www.jianshu.com/p/ddb6e32e3faf" rel="external nofollow noopener noreferrer" target="_blank">自动配置 Spring Bean</a></p>
<p>  Spring Boot 检测到特定类的存在，就会针对这个应用做一定的配置，进行自动配置 Bean ，这样会极大地减少我们要使用的配置。</p>
<p>  当然，Spring Boot 只考虑大多数的开发场景，并不是所有的场景，若在实际开发中我们需要配置Bean ，而 Spring Boot 没有提供支持，则可以自定义自动配置进行解决。</p>
</li>
<li><p>5、<a href="https://blog.csdn.net/wangshuang1631/article/details/72810412" rel="external nofollow noopener noreferrer" target="_blank">准生产的应用监控</a></p>
<p>  Spring Boot 提供基于 HTTP、JMX、SSH 对运行时的项目进行监控。</p>
</li>
<li><p>6、无代码生成和 XML 配置</p>
<p>  Spring Boot 没有引入任何形式的代码生成，它是使用的 Spring 4.0 的条件 <code>@Condition</code> 注解以实现根据条件进行配置。同时使用了  Maven /Gradle 的<strong>依赖传递解析机制</strong>来实现 Spring 应用里面的自动配置。</p>
<blockquote>
<p>第 6 点是第 3 点的补充。</p>
</blockquote>
</li>
</ul>
<h2 id="Spring-Boot-有什么优缺点？"><a href="#Spring-Boot-有什么优缺点？" class="headerlink" title="Spring Boot 有什么优缺点？"></a>Spring Boot 有什么优缺点？</h2><blockquote>
<p>艿艿：任何技术栈，有优点必有缺点，没有银弹。</p>
<p>另外，这个问题的回答，我们是基于 <a href="https://blog.csdn.net/fly_zhyu/article/details/76407830" rel="external nofollow noopener noreferrer" target="_blank">《Spring Boot浅谈(是什么/能干什么/优点和不足)》</a> 整理，所以胖友主要看下这篇文章。</p>
</blockquote>
<p><strong>Spring Boot 的优点</strong></p>
<blockquote>
<p>艿艿：优点和 <a href="#">「Spring Boot 提供了哪些核心功能？」</a> 问题的答案，是比较重叠的。</p>
</blockquote>
<ul>
<li>1、使【编码】变简单。</li>
<li>2、使【配置】变简单。</li>
<li>3、使【部署】变简单。</li>
<li>4、使【监控】变简单。</li>
</ul>
<p><strong>Spring Boot 的缺点</strong></p>
<blockquote>
<p>艿艿：如下的缺点，基于 <a href="https://blog.csdn.net/fly_zhyu/article/details/76407830" rel="external nofollow noopener noreferrer" target="_blank">《Spring Boot浅谈(是什么/能干什么/优点和不足)》</a>，考虑的出发点是把 Spring Boot 作为微服务的框架的选型的角度进行考虑。</p>
</blockquote>
<ul>
<li><p>1、没有提供相应的【服务发现和注册】的配套功能。</p>
<blockquote>
<p>艿艿：当然，实际上 Spring Boot 本身是不需要提供这样的功能。服务发现和注册的功能，是在 Spring Cloud 中进行提供。</p>
</blockquote>
</li>
<li><p>2、自身的 acturator 所提供的【监控功能】，也需要与现有的监控对接。</p>
</li>
<li><p>3、没有配套的【安全管控】方案。</p>
<blockquote>
<p>艿艿：关于这一点，艿艿也有点迷糊，Spring Security 是可以比较方便的集成到 Spring Boot 中，所以不晓得这里的【安全管控】的定义是什么。所以这一点，面试的时候回答，可以暂时先省略。</p>
</blockquote>
</li>
<li><p>4、对于 REST 的落地，还需要自行结合实际进行 URI 的规范化工作    </p>
<blockquote>
<p>艿艿：这个严格来说，不算缺点。本身，是规范的范畴。</p>
</blockquote>
</li>
</ul>
<p>所以，上面的缺点，严格来说可能不太适合在面试中回答。艿艿认为，Spring Boot 的缺点主要是，因为自动配置 Spring Bean 的功能，我们可能无法知道，哪些 Bean 被进行创建了。这个时候，如果我们想要自定义一些 Bean ，可能存在冲突，或者不知道实际注入的情况。</p>
<h2 id="Spring-Boot、Spring-MVC-和-Spring-有什么区别？"><a href="#Spring-Boot、Spring-MVC-和-Spring-有什么区别？" class="headerlink" title="Spring Boot、Spring MVC 和 Spring 有什么区别？"></a>Spring Boot、Spring MVC 和 Spring 有什么区别？</h2><p>Spring 的完整名字，应该是 Spring Framework 。它提供了多个模块，Spring IoC、Spring AOP、Spring MVC 等等。所以，Spring MVC 是 Spring Framework 众多模块中的一个。</p>
<p>而 Spring Boot 是构造在 Spring Framework 之上的 Boot 启动器，旨在更容易的配置一个 Spring 项目。</p>
<p>总结说来，如下图所示：<img src="http://static2.iocoder.cn/images/Spring/2018-12-26/02.png" alt="Spring Boot 对比 Spring MVC 对比 Spring ？"></p>
<h2 id="Spring-Boot-中的-Starter-是什么？"><a href="#Spring-Boot-中的-Starter-是什么？" class="headerlink" title="Spring Boot 中的 Starter 是什么？"></a>Spring Boot 中的 Starter 是什么？</h2><p>比较<strong>通俗</strong>的说法：</p>
<blockquote>
<p>FROM <a href="https://www.cnblogs.com/EasonJim/p/7615801.html" rel="external nofollow noopener noreferrer" target="_blank">《Spring Boot 中 Starter 是什么》</a></p>
<p>比如我们要在 Spring Boot 中引入 Web MVC 的支持时，我们通常会引入这个模块 <code>spring-boot-starter-web</code> ，而这个模块如果解压包出来会发现里面什么都没有，只定义了一些 <strong>POM</strong> 依赖。如下图所示：<img src="http://static2.iocoder.cn/images/Spring/2018-12-26/03.png" alt="`spring-boot-starter-web`"></p>
<p>经过研究，Starter 主要用来简化依赖用的。比如我们之前做MVC时要引入日志组件，那么需要去找到log4j的版本，然后引入，现在有了Starter之后，直接用这个之后，log4j就自动引入了，也不用关心版本这些问题。</p>
</blockquote>
<p>比较<strong>书名</strong>的说法：</p>
<blockquote>
<p>FROM <a href="http://www.importnew.com/27101.html" rel="external nofollow noopener noreferrer" target="_blank">《Spring Boot Starter 介绍》</a></p>
<p>依赖管理是任何复杂项目的关键部分。以手动的方式来实现依赖管理不太现实，你得花更多时间，同时你在项目的其他重要方面能付出的时间就会变得越少。</p>
<p>Spring Boot Starter 就是为了解决这个问题而诞生的。Starter <strong>POM</strong> 是一组方便的依赖描述符，您可以将其包含在应用程序中。您可以获得所需的所有 Spring 和相关技术的一站式服务，无需通过示例代码搜索和复制粘贴依赖。</p>
</blockquote>
<h2 id="Spring-Boot-常用的-Starter-有哪些？"><a href="#Spring-Boot-常用的-Starter-有哪些？" class="headerlink" title="Spring Boot 常用的 Starter 有哪些？"></a>Spring Boot 常用的 Starter 有哪些？</h2><ul>
<li><code>spring-boot-starter-web</code> ：提供 Spring MVC + 内嵌的 Tomcat 。</li>
<li><code>spring-boot-starter-data-jpa</code> ：提供 Spring JPA + Hibernate 。</li>
<li><code>spring-boot-starter-data-redis</code> ：提供 Redis 。</li>
<li><code>mybatis-spring-boot-starter</code> ：提供 MyBatis 。</li>
</ul>
<h2 id="创建一个-Spring-Boot-Project-的最简单的方法是什么？"><a href="#创建一个-Spring-Boot-Project-的最简单的方法是什么？" class="headerlink" title="创建一个 Spring Boot Project 的最简单的方法是什么？"></a>创建一个 Spring Boot Project 的最简单的方法是什么？</h2><p>Spring Initializr 是创建 Spring Boot Projects 的一个很好的工具。打开 <code>"https://start.spring.io/"</code> 网站，我们可以看到 Spring Initializr 工具，如下图所示：</p>
<p><img src="http://static2.iocoder.cn/images/Spring/2018-12-26/04.png" alt="Spring Initializr"></p>
<ul>
<li>图中的每一个<strong>红线</strong>，都可以填写相应的配置。相信胖友都很熟悉，就不哔哔了。</li>
<li>点击生 GenerateProject ，生成 Spring Boot Project 。</li>
<li>将项目导入 IDEA ，记得选择现有的 Maven 项目。</li>
</ul>
<hr>
<p>当然，我们以前使用 IDEA 创建 Spring 项目的方式，也一样能创建 Spring Boot Project 。Spring Initializr 更多的是，提供一个便捷的工具。</p>
<h2 id="如何统一引入-Spring-Boot-版本？"><a href="#如何统一引入-Spring-Boot-版本？" class="headerlink" title="如何统一引入 Spring Boot 版本？"></a>如何统一引入 Spring Boot 版本？</h2><p><strong>目前有两种方式</strong>。</p>
<p>① 方式一：继承 <code>spring-boot-starter-parent</code> 项目。配置代码如下：</p>
<figure class="highlight xml"><table><tbody><tr><td class="code"><pre><span class="line"><span class="tag">&lt;<span class="name">parent</span>&gt;</span></span><br><span class="line">    <span class="tag">&lt;<span class="name">groupId</span>&gt;</span>org.springframework.boot<span class="tag">&lt;/<span class="name">groupId</span>&gt;</span></span><br><span class="line">    <span class="tag">&lt;<span class="name">artifactId</span>&gt;</span>spring-boot-starter-parent<span class="tag">&lt;/<span class="name">artifactId</span>&gt;</span></span><br><span class="line">    <span class="tag">&lt;<span class="name">version</span>&gt;</span>1.5.1.RELEASE<span class="tag">&lt;/<span class="name">version</span>&gt;</span></span><br><span class="line"><span class="tag">&lt;/<span class="name">parent</span>&gt;</span></span><br></pre></td></tr></tbody></table></figure>
<p>② 方式二：导入 spring-boot-dependencies 项目依赖。配置代码如下：</p>
<figure class="highlight xml"><table><tbody><tr><td class="code"><pre><span class="line"><span class="tag">&lt;<span class="name">dependencyManagement</span>&gt;</span></span><br><span class="line">    <span class="tag">&lt;<span class="name">dependencies</span>&gt;</span></span><br><span class="line">        <span class="tag">&lt;<span class="name">dependency</span>&gt;</span></span><br><span class="line">            <span class="tag">&lt;<span class="name">groupId</span>&gt;</span>org.springframework.boot<span class="tag">&lt;/<span class="name">groupId</span>&gt;</span></span><br><span class="line">            <span class="tag">&lt;<span class="name">artifactId</span>&gt;</span>spring-boot-dependencies<span class="tag">&lt;/<span class="name">artifactId</span>&gt;</span></span><br><span class="line">            <span class="tag">&lt;<span class="name">version</span>&gt;</span>1.5.1.RELEASE<span class="tag">&lt;/<span class="name">version</span>&gt;</span></span><br><span class="line">            <span class="tag">&lt;<span class="name">type</span>&gt;</span>pom<span class="tag">&lt;/<span class="name">type</span>&gt;</span></span><br><span class="line">            <span class="tag">&lt;<span class="name">scope</span>&gt;</span>import<span class="tag">&lt;/<span class="name">scope</span>&gt;</span></span><br><span class="line">        <span class="tag">&lt;/<span class="name">dependency</span>&gt;</span></span><br><span class="line">    <span class="tag">&lt;/<span class="name">dependencies</span>&gt;</span></span><br><span class="line"><span class="tag">&lt;/<span class="name">dependencyManagement</span>&gt;</span></span><br></pre></td></tr></tbody></table></figure>
<p><strong>如何选择？</strong></p>
<p>因为一般我们的项目中，都有项目自己的 Maven parent 项目，所以【方式一】显然会存在冲突。所以实际场景下，推荐使用【方式二】。</p>
<p>详细的，推荐阅读 <a href="https://blog.csdn.net/rainbow702/article/details/55046298" rel="external nofollow noopener noreferrer" target="_blank">《Spring Boot 不使用默认的 parent，改用自己的项目的 parent》</a> 文章。</p>
<p>另外，在使用 Spring Cloud 的时候，也可以使用这样的方式。</p>
<h2 id="运行-Spring-Boot-有哪几种方式？"><a href="#运行-Spring-Boot-有哪几种方式？" class="headerlink" title="运行 Spring Boot 有哪几种方式？"></a>运行 Spring Boot 有哪几种方式？</h2><ul>
<li>1、打包成 Fat Jar ，直接使用 <code>java -jar</code> 运行。目前主流的做法，推荐。</li>
<li>2、在 IDEA 或 Eclipse 中，直接运行应用的 Spring Boot 启动类的 <code>#main(String[] args)</code> 启动。适用于开发调试场景。</li>
<li>3、如果是 Web 项目，可以打包成 War 包，使用外部 Tomcat 或 Jetty 等容器。</li>
</ul>
<h2 id="如何打包-Spring-Boot-项目？"><a href="#如何打包-Spring-Boot-项目？" class="headerlink" title="如何打包 Spring Boot 项目？"></a>如何打包 Spring Boot 项目？</h2><p>通过引入 <code>spring-boot-maven-plugin</code> 插件，执行 <code>mvn clean package</code> 命令，将 Spring Boot 项目打成一个 Fat Jar 。后续，我们就可以直接使用 <code>java -jar</code> 运行。</p>
<p>关于 <code>spring-boot-maven-plugin</code> 插件，更多详细的可以看看 <a href="https://qbgbook.gitbooks.io/spring-boot-reference-guide-zh/II.%20Getting%20started/11.5.%20Creating%20an%20executable%20jar.html" rel="external nofollow noopener noreferrer" target="_blank">《创建可执行 jar》</a> 。</p>
<h2 id="如果更改内嵌-Tomcat-的端口？"><a href="#如果更改内嵌-Tomcat-的端口？" class="headerlink" title="如果更改内嵌 Tomcat 的端口？"></a>如果更改内嵌 Tomcat 的端口？</h2><ul>
<li><p>方式一，修改 <code>application.properties</code> 配置文件的 <code>server.port</code> 属性。</p>
<figure class="highlight plain"><table><tbody><tr><td class="code"><pre><span class="line">server.port=9090</span><br></pre></td></tr></tbody></table></figure>
</li>
<li><p>方式二，通过启动命令增加 <code>server.port</code> 参数进行修改。</p>
<figure class="highlight plain"><table><tbody><tr><td class="code"><pre><span class="line">java -jar xxx.jar --server.port=9090</span><br></pre></td></tr></tbody></table></figure>
</li>
</ul>
<p>当然，以上的方式，不仅仅适用于 Tomcat ，也适用于 Jetty、Undertow 等服务器。</p>
<h2 id="如何重新加载-Spring-Boot-上的更改，而无需重新启动服务器？"><a href="#如何重新加载-Spring-Boot-上的更改，而无需重新启动服务器？" class="headerlink" title="如何重新加载 Spring Boot 上的更改，而无需重新启动服务器？"></a>如何重新加载 Spring Boot 上的更改，而无需重新启动服务器？</h2><p>一共有三种方式，可以实现效果：</p>
<ul>
<li>【推荐】<code>spring-boot-devtools</code> 插件。注意，这个工具需要配置 IDEA 的自动编译。</li>
<li><p>Spring Loaded 插件。</p>
<blockquote>
<p>Spring Boot 2.X 后，官方宣布不再支持 Spring Loaded 插件 的更新，所以基本可以无视它了。</p>
</blockquote>
</li>
<li><p><a href="https://www.jianshu.com/p/bab43eaa4e14" rel="external nofollow noopener noreferrer" target="_blank">JRebel</a> 插件，需要付费。</p>
</li>
</ul>
<p>关于如何使用 <code>spring-boot-devtools</code> 和 Spring Loaded 插件，胖友可以看看 <a href="https://segmentfault.com/a/1190000014488100" rel="external nofollow noopener noreferrer" target="_blank">《Spring Boot 学习笔记：Spring Boot Developer Tools 与热部署》</a> 。</p>
<h2 id="Spring-Boot-的配置文件有哪几种格式？"><a href="#Spring-Boot-的配置文件有哪几种格式？" class="headerlink" title="Spring Boot 的配置文件有哪几种格式？"></a>Spring Boot 的配置文件有哪几种格式？</h2><p>Spring Boot 目前支持两种格式的配置文件：</p>
<ul>
<li><p><code>.properties</code> 格式。示例如下：</p>
<figure class="highlight plain"><table><tbody><tr><td class="code"><pre><span class="line">server.port = 9090</span><br></pre></td></tr></tbody></table></figure>
</li>
<li><p><code>.yaml</code> 格式。示例如下：</p>
<figure class="highlight yaml"><table><tbody><tr><td class="code"><pre><span class="line"><span class="attr">server:</span></span><br><span class="line"><span class="attr">    port:</span> <span class="number">9090</span></span><br></pre></td></tr></tbody></table></figure>
</li>
</ul>
<hr>
<p>可能有胖友不了解 <strong>YAML 格式</strong>？</p>
<p>YAML 是一种人类可读的数据序列化语言，它通常用于配置文件。</p>
<ul>
<li>与 Properties 文件相比，如果我们想要在配置文件中添加复杂的属性 YAML 文件就更加<strong>结构化</strong>。从上面的示例，我们可以看出 YAML 具有<strong>分层</strong>配置数据。</li>
<li>当然 YAML 在 Spring 会存在一个缺陷，<a href="https://blog.csdn.net/u011659172/article/details/51274345" rel="external nofollow noopener noreferrer" target="_blank"><code>@PropertySource</code></a> 注解不支持读取 YAML 配置文件，仅支持 Properties 配置文件。<ul>
<li>不过这个问题也不大，可以麻烦一点使用 <a href="https://blog.csdn.net/lafengwnagzi/article/details/74178374" rel="external nofollow noopener noreferrer" target="_blank"><code>@Value</code></a> 注解，来读取 YAML 配置项。</li>
</ul>
</li>
</ul>
<p>实际场景下，艿艿相对比较喜欢使用 Properties 配置文件。个人喜欢~当然，YAML 已经越来越流行了。</p>
<h2 id="Spring-Boot-默认配置文件是什么？"><a href="#Spring-Boot-默认配置文件是什么？" class="headerlink" title="Spring Boot 默认配置文件是什么？"></a>Spring Boot 默认配置文件是什么？</h2><p>对于 Spring Boot 应用，默认的配置文件根目录下的 <strong>application</strong> 配置文件，当然可以是 Properties 格式，也可以是 YAML 格式。</p>
<p>可能有胖友说，我在网上看到面试题中，说还有一个根目录下的 <strong>bootstrap</strong> 配置文件。这个是 Spring Cloud 新增的启动配置文件，<a href="https://my.oschina.net/freeskyjs/blog/1843048" rel="external nofollow noopener noreferrer" target="_blank">需要引入 <code>spring-cloud-context</code> 依赖后，才会进行加载</a>。它的特点和用途主要是：</p>
<blockquote>
<p>参考 <a href="https://my.oschina.net/neverforget/blog/1525947" rel="external nofollow noopener noreferrer" target="_blank">《Spring Cloud 中配置文件名 bootstrap.yml 和 application.yml 区别》</a> 文章。</p>
</blockquote>
<ul>
<li>【特点】因为 bootstrap 由父 ApplicationContext 加载，比 application 优先加载。</li>
<li>【特点】因为 bootstrap 优先于 application 加载，所以不会被它覆盖。 </li>
<li>【用途】使用配置中心 Spring Cloud Config 时，需要在 bootstrap 中配置配置中心的地址，从而实现父 ApplicationContext 加载时，从配置中心拉取相应的配置到应用中。</li>
</ul>
<p>另外，<a href="https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html" rel="external nofollow noopener noreferrer" target="_blank">《Appendix A. Common application properties》</a> 中，有 application 配置文件的通用属性列表。</p>
<h2 id="Spring-Boot-如何定义多套不同环境配置？"><a href="#Spring-Boot-如何定义多套不同环境配置？" class="headerlink" title="Spring Boot 如何定义多套不同环境配置？"></a>Spring Boot 如何定义多套不同环境配置？</h2><p>可以参考 <a href="https://blog.csdn.net/top_code/article/details/78570047" rel="external nofollow noopener noreferrer" target="_blank">《Spring Boot 教程 - Spring Boot Profiles 实现多环境下配置切换》</a> 一文。</p>
<p>但是，需要考虑一个问题，生产环境的配置文件的安全性，显然我们不能且不应该把生产的配置放到项目的 Git 仓库中进行管理。那么应该怎么办呢？</p>
<ul>
<li>方案一，生产环境的配置文件放在生产环境的服务器中，以 <code>java -jar myproject.jar --spring.config.location=/xxx/yyy/application-prod.properties</code> 命令，设置 参数 <code>spring.config.location</code> 指向配置文件。</li>
<li>方案二，使用 Jenkins 在执行打包，配置上 Maven Profile 功能，使用服务器上的配置文件。😈 整体来说，和【方案一】的差异是，将配置文件打包进了 Jar 包中。</li>
<li>方案三，使用配置中心。</li>
</ul>
<h2 id="Spring-Boot-配置加载顺序？"><a href="#Spring-Boot-配置加载顺序？" class="headerlink" title="Spring Boot 配置加载顺序？"></a>Spring Boot 配置加载顺序？</h2><p>在 Spring Boot 中，除了我们常用的 application 配置文件之外，还有：</p>
<ul>
<li>系统环境变量</li>
<li>命令行参数</li>
<li>等等…</li>
</ul>
<p>参考 <a href="https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html" rel="external nofollow noopener noreferrer" target="_blank">《Externalized Configuration》</a> 文档，我们整理顺序如下：</p>
<ol>
<li><code>spring-boot-devtools</code> 依赖的 <code>spring-boot-devtools.properties</code> 配置文件。<blockquote>
<p>这个灰常小众，具体说明可以看看 <a href="https://blog.csdn.net/u011499747/article/details/71746325" rel="external nofollow noopener noreferrer" target="_blank">《Spring Boot参考文档（12）开发者工具》</a> ，建议无视。</p>
</blockquote>
</li>
<li>单元测试上的 <code>@TestPropertySource</code> 和 <code>@SpringBootTest</code> 注解指定的参数。<blockquote>
<p>前者的优先级高于后者。可以看看 <a href="https://segmentfault.com/a/1190000010854607" rel="external nofollow noopener noreferrer" target="_blank">《Spring、Spring Boot 和TestNG 测试指南 - @TestPropertySource》</a> 一文。</p>
</blockquote>
</li>
<li>命令行指定的参数。例如 <code>java -jar springboot.jar --server.port=9090</code> 。</li>
<li>命令行中的 <code>spring.application.json</code> 指定参数。例如 <code>java -Dspring.application.json='{"name":"Java"}' -jar springboot.jar</code> 。</li>
<li>ServletConfig 初始化参数。</li>
<li>ServletContext 初始化参数。</li>
<li>JNDI 参数。例如 <code>java:comp/env</code> 。</li>
<li>Java 系统变量，即 <code>System#getProperties()</code> 方法对应的。</li>
<li>操作系统环境变量。</li>
<li>RandomValuePropertySource 配置的 <code>random.*</code> 属性对应的值。</li>
<li>Jar <strong>外部</strong>的带指定 profile 的 application 配置文件。例如 <code>application-{profile}.yaml</code> 。</li>
<li>Jar <strong>内部</strong>的带指定 profile 的 application 配置文件。例如 <code>application-{profile}.yaml</code> 。</li>
<li>Jar <strong>外部</strong> application 配置文件。例如 <code>application.yaml</code> 。</li>
<li>Jar <strong>内部</strong> application 配置文件。例如 <code>application.yaml</code> 。</li>
<li>在自定义的 <code>@Configuration</code> 类中定于的 <code>@PropertySource</code> 。</li>
<li>启动的 main 方法中，定义的默认配置。即通过 <code>SpringApplication#setDefaultProperties(Map&lt;String, Object&gt; defaultProperties)</code> 方法进行设置。</li>
</ol>
<p>嘿嘿，是不是很多很长，不用真的去记住。</p>
<ul>
<li>一般来说，面试官不会因为这个题目回答的不好，对你扣分。</li>
<li>实际使用时，做下测试即可。</li>
<li>每一种配置方式的详细说明，可以看看 <a href="https://segmentfault.com/a/1190000015069140" rel="external nofollow noopener noreferrer" target="_blank">《Spring Boot 参考指南（外部化配置）》</a> 。</li>
</ul>
<h2 id="Spring-Boot-有哪些配置方式？"><a href="#Spring-Boot-有哪些配置方式？" class="headerlink" title="Spring Boot 有哪些配置方式？"></a>Spring Boot 有哪些配置方式？</h2><p>和 Spring 一样，一共提供了三种方式。</p>
<ul>
<li><p>1、XML 配置文件。</p>
<p>  Bean 所需的依赖项和服务在 XML 格式的配置文件中指定。这些配置文件通常包含许多 bean 定义和特定于应用程序的配置选项。它们通常以 bean 标签开头。例如：</p>
<figure class="highlight xml"><table><tbody><tr><td class="code"><pre><span class="line"><span class="tag">&lt;<span class="name">bean</span> <span class="attr">id</span>=<span class="string">"studentBean"</span> <span class="attr">class</span>=<span class="string">"org.edureka.firstSpring.StudentBean"</span>&gt;</span></span><br><span class="line">    <span class="tag">&lt;<span class="name">property</span> <span class="attr">name</span>=<span class="string">"name"</span> <span class="attr">value</span>=<span class="string">"Edureka"</span>&gt;</span><span class="tag">&lt;/<span class="name">property</span>&gt;</span></span><br><span class="line"><span class="tag">&lt;/<span class="name">bean</span>&gt;</span></span><br></pre></td></tr></tbody></table></figure>
</li>
<li><p>2、注解配置。</p>
<p>  您可以通过在相关的类，方法或字段声明上使用注解，将 Bean 配置为组件类本身，而不是使用 XML 来描述 Bean 装配。默认情况下，Spring 容器中未打开注解装配。因此，您需要在使用它之前在 Spring 配置文件中启用它。例如：</p>
<figure class="highlight xml"><table><tbody><tr><td class="code"><pre><span class="line"><span class="tag">&lt;<span class="name">beans</span>&gt;</span></span><br><span class="line"><span class="tag">&lt;<span class="name">context:annotation-config</span>/&gt;</span></span><br><span class="line"><span class="comment">&lt;!-- bean definitions go here --&gt;</span></span><br><span class="line"><span class="tag">&lt;/<span class="name">beans</span>&gt;</span></span><br></pre></td></tr></tbody></table></figure>
</li>
<li><p>3、Java Config 配置。</p>
<p>  Spring 的 Java 配置是通过使用 @Bean 和 @Configuration 来实现。</p>
<ul>
<li><code>@Bean</code> 注解扮演与 <code>&lt;bean /&gt;</code> 元素相同的角色。</li>
<li><code>@Configuration</code> 类允许通过简单地调用同一个类中的其他 <code>@Bean</code> 方法来定义 Bean 间依赖关系。</li>
<li><p>例如：</p>
<figure class="highlight java"><table><tbody><tr><td class="code"><pre><span class="line"><span class="meta">@Configuration</span></span><br><span class="line"><span class="keyword">public</span> <span class="class"><span class="keyword">class</span> <span class="title">StudentConfig</span> </span>{</span><br><span class="line">    </span><br><span class="line">    <span class="meta">@Bean</span></span><br><span class="line">    <span class="function"><span class="keyword">public</span> StudentBean <span class="title">myStudent</span><span class="params">()</span> </span>{</span><br><span class="line">        <span class="keyword">return</span> <span class="keyword">new</span> StudentBean();</span><br><span class="line">    }</span><br><span class="line">    </span><br><span class="line">}</span><br></pre></td></tr></tbody></table></figure>
<ul>
<li>是不是很熟悉 😈</li>
</ul>
</li>
</ul>
</li>
</ul>
<p>目前主要使用 <strong>Java Config</strong> 配置为主。当然，三种配置方式是可以混合使用的。例如说：</p>
<ul>
<li>Dubbo 服务的配置，艿艿喜欢使用 XML 。</li>
<li>Spring MVC 请求的配置，艿艿喜欢使用 <code>@RequestMapping</code> 注解。</li>
<li>Spring MVC 拦截器的配置，艿艿喜欢 Java Config 配置。</li>
</ul>
<hr>
<p>另外，现在已经是 Spring Boot 的天下，所以更加是 <strong>Java Config</strong> 配置为主。</p>
<h2 id="Spring-Boot-的核心注解是哪个？"><a href="#Spring-Boot-的核心注解是哪个？" class="headerlink" title="Spring Boot 的核心注解是哪个？"></a>Spring Boot 的核心注解是哪个？</h2><figure class="highlight java"><table><tbody><tr><td class="code"><pre><span class="line"><span class="keyword">package</span> cn.iocoder.skywalking.web01;</span><br><span class="line"></span><br><span class="line"><span class="keyword">import</span> org.springframework.boot.SpringApplication;</span><br><span class="line"><span class="keyword">import</span> org.springframework.boot.autoconfigure.SpringBootApplication;</span><br><span class="line"></span><br><span class="line"><span class="meta">@SpringBootApplication</span></span><br><span class="line"><span class="keyword">public</span> <span class="class"><span class="keyword">class</span> <span class="title">Web01Application</span> </span>{</span><br><span class="line"></span><br><span class="line">    <span class="function"><span class="keyword">public</span> <span class="keyword">static</span> <span class="keyword">void</span> <span class="title">main</span><span class="params">(String[] args)</span> </span>{</span><br><span class="line">        SpringApplication.run(Web01Application.class, args);</span><br><span class="line">    }</span><br><span class="line"></span><br><span class="line">}</span><br></pre></td></tr></tbody></table></figure>
<ul>
<li><code>@SpringBootApplication</code> 注解，就是 Spring Boot 的核心注解。</li>
</ul>
<p><code>org.springframework.boot.autoconfigure.@SpringBootApplication</code> 注解的代码如下：</p>
<figure class="highlight java"><table><tbody><tr><td class="code"><pre><span class="line"><span class="comment">// SpringBootApplication.java</span></span><br><span class="line"></span><br><span class="line"><span class="meta">@Target</span>({ElementType.TYPE})</span><br><span class="line"><span class="meta">@Retention</span>(RetentionPolicy.RUNTIME)</span><br><span class="line"><span class="meta">@Documented</span></span><br><span class="line"><span class="meta">@Inherited</span></span><br><span class="line"><span class="meta">@SpringBootConfiguration</span></span><br><span class="line"><span class="meta">@EnableAutoConfiguration</span></span><br><span class="line"><span class="meta">@ComponentScan</span>(</span><br><span class="line">    excludeFilters = {<span class="meta">@Filter</span>(</span><br><span class="line">    type = FilterType.CUSTOM,</span><br><span class="line">    classes = {TypeExcludeFilter.class}</span><br><span class="line">), <span class="meta">@Filter</span>(</span><br><span class="line">    type = FilterType.CUSTOM,</span><br><span class="line">    classes = {AutoConfigurationExcludeFilter.class}</span><br><span class="line">)}</span><br><span class="line">)</span><br><span class="line"><span class="keyword">public</span> <span class="meta">@interface</span> SpringBootApplication {</span><br><span class="line">    <span class="meta">@AliasFor</span>(</span><br><span class="line">        annotation = EnableAutoConfiguration.class</span><br><span class="line">    )</span><br><span class="line">    Class&lt;?&gt;[] exclude() <span class="keyword">default</span> {};</span><br><span class="line"></span><br><span class="line">    <span class="meta">@AliasFor</span>(</span><br><span class="line">        annotation = EnableAutoConfiguration.class</span><br><span class="line">    )</span><br><span class="line">    String[] excludeName() <span class="keyword">default</span> {};</span><br><span class="line"></span><br><span class="line">    <span class="meta">@AliasFor</span>(</span><br><span class="line">        annotation = ComponentScan.class,</span><br><span class="line">        attribute = <span class="string">"basePackages"</span></span><br><span class="line">    )</span><br><span class="line">    String[] scanBasePackages() <span class="keyword">default</span> {};</span><br><span class="line"></span><br><span class="line">    <span class="meta">@AliasFor</span>(</span><br><span class="line">        annotation = ComponentScan.class,</span><br><span class="line">        attribute = <span class="string">"basePackageClasses"</span></span><br><span class="line">    )</span><br><span class="line">    Class&lt;?&gt;[] scanBasePackageClasses() <span class="keyword">default</span> {};</span><br><span class="line">}</span><br></pre></td></tr></tbody></table></figure>
<ul>
<li>它组合了 3 个注解，详细说明，胖友看看 <a href="https://blog.csdn.net/claram/article/details/75125749" rel="external nofollow noopener noreferrer" target="_blank">《Spring Boot 系列：@SpringBootApplication 注解》</a> 。</li>
<li><p><code>@Configuration</code> 注解，指定类是 <strong>Bean 定义</strong>的配置类。</p>
<blockquote>
<p><code>@Configuration</code> 注解，来自 <code>spring-context</code> 项目，用于 Java Config ，不是 Spring Boot 新带来的。</p>
</blockquote>
</li>
<li><p><code>#ComponentScan</code> 注解，扫描指定包下的 Bean 们。</p>
<blockquote>
<p><code>@ComponentScan</code> 注解，来自 <code>spring-context</code> 项目，用于 Java Config ，不是 Spring Boot 新带来的。</p>
</blockquote>
</li>
<li><p><code>@EnableAutoConfiguration</code> 注解，打开自动配置的功能。如果我们想要关闭某个类的自动配置，可以设置注解的 <code>exclude</code> 或 <code>excludeName</code> 属性。</p>
<blockquote>
<p><code>@EnableAutoConfiguration</code> 注解，来自 <code>spring-boot-autoconfigure</code> 项目，<strong>它才是 Spring Boot 新带来的</strong>。</p>
</blockquote>
</li>
</ul>
<h2 id="什么是-Spring-Boot-自动配置？"><a href="#什么是-Spring-Boot-自动配置？" class="headerlink" title="什么是 Spring Boot 自动配置？"></a>什么是 Spring Boot 自动配置？</h2><p>在 <a href="#">「Spring Boot 的核心注解是哪个？」</a> 中，我们已经看到，使用 <code>@@EnableAutoConfiguration</code> 注解，打开 Spring Boot 自动配置的功能。具体如何实现的，可以看看如下两篇文章：</p>
<ul>
<li><a href="https://www.jianshu.com/p/464d04c36fb1" rel="external nofollow noopener noreferrer" target="_blank">《@EnableAutoConfiguration 注解的工作原理》</a> 。</li>
<li><p><a href="https://juejin.im/post/5b679fbc5188251aad213110" rel="external nofollow noopener noreferrer" target="_blank">《一个面试题引起的 Spring Boot 启动解析》</a></p>
</li>
<li><p>建议，能一边调试，一边看这篇文章。调试很简单，任一搭建一个 Spring Boot 项目即可。</p>
</li>
</ul>
<p>如下是一个比较简单的总结：</p>
<ol>
<li>Spring Boot 在启动时扫描项目所依赖的 jar 包，寻找包含<code>spring.factories</code> 文件的 jar 包。</li>
<li>根据 <code>spring.factories</code> 配置加载 AutoConfigure 类。</li>
<li>根据 <a href="Spring Boot 条件注解"><code>@Conditional</code> 等条件注解</a> 的条件，进行自动配置并将 Bean 注入 Spring IoC 中。</li>
</ol>
<h2 id="Spring-Boot-有哪几种读取配置的方式？"><a href="#Spring-Boot-有哪几种读取配置的方式？" class="headerlink" title="Spring Boot 有哪几种读取配置的方式？"></a>Spring Boot 有哪几种读取配置的方式？</h2><p>Spring Boot 目前支持 <strong>2</strong> 种读取配置：</p>
<ol>
<li><p><code>@Value</code> 注解，读取配置到属性。最最最常用。</p>
<blockquote>
<p>另外，支持和 <code>@PropertySource</code> 注解一起使用，指定使用的配置文件。</p>
</blockquote>
</li>
<li><p><code>@ConfigurationProperties</code> 注解，读取配置到类上。</p>
<blockquote>
<p>另外，支持和 <code>@PropertySource</code> 注解一起使用，指定使用的配置文件。</p>
</blockquote>
</li>
</ol>
<p>详细的使用方式，可以参考 <a href="https://aoyouzi.iteye.com/blog/2422837" rel="external nofollow noopener noreferrer" target="_blank">《Spring Boot 读取配置的几种方式》</a> 。</p>
<h2 id="使用-Spring-Boot-后，项目结构是怎么样的呢？"><a href="#使用-Spring-Boot-后，项目结构是怎么样的呢？" class="headerlink" title="使用 Spring Boot 后，项目结构是怎么样的呢？"></a>使用 Spring Boot 后，项目结构是怎么样的呢？</h2><p>我们先来说说项目的分层。一般来说，主流的有两种方式：</p>
<ul>
<li>方式一，<code>controller</code>、<code>service</code>、<code>dao</code> 三个包，每个包下面添加相应的 XXXController、YYYService、ZZZDAO 。</li>
<li>方式二，按照业务模块分包，每个包里面放 Controller、Service、DAO 类。例如，业务模块分成 <code>user</code>、<code>order</code>、<code>item</code> 等等包，在 <code>user</code> 包里放 UserController、UserService、UserDAO 类。</li>
</ul>
<p>那么，使用 Spring Boot 的项目怎么分层呢？艿艿自己的想法</p>
<ul>
<li>现在项目都会进行服务化分拆，每个项目不会特别复杂，所以建议使用【方式一】。</li>
<li>以前的项目，大多是单体的项目，动则项目几万到几十万的代码，当时多采用【方式二】。</li>
</ul>
<p>下面是一个简单的 Spring Boot 项目的 Demo ，如下所示：<img src="http://static2.iocoder.cn/images/Spring/2018-12-26/05.png" alt="Spring Boot 项目的 Demo"></p>
<h2 id="如何在-Spring-Boot-启动的时候运行一些特殊的代码？"><a href="#如何在-Spring-Boot-启动的时候运行一些特殊的代码？" class="headerlink" title="如何在 Spring Boot 启动的时候运行一些特殊的代码？"></a>如何在 Spring Boot 启动的时候运行一些特殊的代码？</h2><p>如果需要在 SpringApplication 启动后执行一些特殊的代码，你可以实现 ApplicationRunner 或 CommandLineRunner 接口，这两个接口工作方式相同，都只提供单一的 run 方法，该方法仅在 <code>SpringApplication#run(...)</code> 方法<strong>完成之前调用</strong>。</p>
<p>一般情况下，我们不太会使用该功能。如果真需要，胖友可以详细看看 <a href="https://qbgbook.gitbooks.io/spring-boot-reference-guide-zh/IV.%20Spring%20Boot%20features/23.8%20Using%20the%20ApplicationRunner%20or%20CommandLineRunner.html" rel="external nofollow noopener noreferrer" target="_blank">《使用 ApplicationRunner 或 CommandLineRunner 》</a> 。</p>
<h2 id="Spring-Boot-2-X-有什么新特性？"><a href="#Spring-Boot-2-X-有什么新特性？" class="headerlink" title="Spring Boot 2.X 有什么新特性？"></a>Spring Boot 2.X 有什么新特性？</h2><ol>
<li>起步 JDK 8 和支持 JDK 9</li>
<li>第三方库的升级</li>
<li>Reactive Spring</li>
<li>HTTP/2 支持</li>
<li>配置属性的绑定</li>
<li>Gradle 插件</li>
<li>Actuator 改进</li>
<li>数据支持的改进</li>
<li>Web 的改进</li>
<li>支持 Quartz 自动配置</li>
<li>测试的改进</li>
<li>其它…</li>
</ol>
<p>详细的说明，可以看看 <a href="http://www.54tianzhisheng.cn/2018/03/06/SpringBoot2-new-features" rel="external nofollow noopener noreferrer" target="_blank">《Spring Boot 2.0系列文章(二)：Spring Boot 2.0 新特性详解》</a> 。</p>
<h1 id="整合篇"><a href="#整合篇" class="headerlink" title="整合篇"></a>整合篇</h1><h2 id="如何将内嵌服务器换成-Jetty-？"><a href="#如何将内嵌服务器换成-Jetty-？" class="headerlink" title="如何将内嵌服务器换成 Jetty ？"></a>如何将内嵌服务器换成 Jetty ？</h2><p>默认情况下，<code>spring-boot-starter-web</code> 模块使用 Tomcat 作为内嵌的服务器。所以需要去除对 <code>spring-boot-starter-tomcat</code> 模块的引用，添加 <code>spring-boot-starter-jetty</code> 模块的引用。代码如下：</p>
<figure class="highlight xml"><table><tbody><tr><td class="code"><pre><span class="line"><span class="tag">&lt;<span class="name">dependency</span>&gt;</span></span><br><span class="line">    <span class="tag">&lt;<span class="name">groupId</span>&gt;</span>org.springframework.boot<span class="tag">&lt;/<span class="name">groupId</span>&gt;</span></span><br><span class="line">    <span class="tag">&lt;<span class="name">artifactId</span>&gt;</span>spring-boot-starter-web<span class="tag">&lt;/<span class="name">artifactId</span>&gt;</span></span><br><span class="line">    <span class="tag">&lt;<span class="name">exclusions</span>&gt;</span></span><br><span class="line">        <span class="tag">&lt;<span class="name">exclusion</span>&gt;</span> <span class="comment">&lt;!-- 去除 Tomcat --&gt;</span></span><br><span class="line">            <span class="tag">&lt;<span class="name">groupId</span>&gt;</span>org.springframework.boot<span class="tag">&lt;/<span class="name">groupId</span>&gt;</span></span><br><span class="line">            <span class="tag">&lt;<span class="name">artifactId</span>&gt;</span>spring-boot-starter-tomcat<span class="tag">&lt;/<span class="name">artifactId</span>&gt;</span></span><br><span class="line">        <span class="tag">&lt;/<span class="name">exclusion</span>&gt;</span></span><br><span class="line">    <span class="tag">&lt;/<span class="name">exclusions</span>&gt;</span></span><br><span class="line"><span class="tag">&lt;/<span class="name">dependency</span>&gt;</span></span><br><span class="line"><span class="tag">&lt;<span class="name">dependency</span>&gt;</span> <span class="comment">&lt;!-- 引入 Jetty --&gt;</span></span><br><span class="line">    <span class="tag">&lt;<span class="name">groupId</span>&gt;</span>org.springframework.boot<span class="tag">&lt;/<span class="name">groupId</span>&gt;</span></span><br><span class="line">    <span class="tag">&lt;<span class="name">artifactId</span>&gt;</span>spring-boot-starter-jetty<span class="tag">&lt;/<span class="name">artifactId</span>&gt;</span></span><br><span class="line"><span class="tag">&lt;/<span class="name">dependency</span>&gt;</span></span><br></pre></td></tr></tbody></table></figure>
<h2 id="Spring-Boot-中的监视器-Actuator-是什么？"><a href="#Spring-Boot-中的监视器-Actuator-是什么？" class="headerlink" title="Spring Boot 中的监视器 Actuator 是什么？"></a>Spring Boot 中的监视器 Actuator 是什么？</h2><p><code>spring-boot-actuator</code> 提供 Spring Boot 的监视器功能，可帮助我们访问生产环境中正在运行的应用程序的<strong>当前状态</strong>。</p>
<ul>
<li>关于 Spring Boot Actuator 的教程，可以看看 <a href="https://www.jianshu.com/p/af9738634a21" rel="external nofollow noopener noreferrer" target="_blank">《Spring Boot Actuator 使用》</a> 。</li>
<li>上述教程是基于 Spring Boot 1.X 的版本，如果胖友使用 Spring Boot 2.X 的版本，你将会发现 <code>/beans</code> 等 Endpoint 是不存在的，参考 <a href="https://stackoverflow.com/questions/49174700/spring-boot-2-actuator-endpoint-where-is-beans-endpoint" rel="external nofollow noopener noreferrer" target="_blank">《Spring boot 2 - Actuator endpoint, where is /beans endpoint》</a> 问题来解决。</li>
</ul>
<p><strong>安全性</strong></p>
<p>Spring Boot 2.X 默认情况下，<code>spring-boot-actuator</code> 产生的 Endpoint 是没有安全保护的，但是 Actuator 可能暴露敏感信息。</p>
<p>所以一般的做法是，引入 <code>spring-boot-start-security</code> 依赖，使用 Spring Security 对它们进行安全保护。</p>
<h2 id="如何集成-Spring-Boot-和-Spring-MVC-？"><a href="#如何集成-Spring-Boot-和-Spring-MVC-？" class="headerlink" title="如何集成 Spring Boot 和 Spring MVC ？"></a>如何集成 Spring Boot 和 Spring MVC ？</h2><ol>
<li>引入 <code>spring-boot-starter-web</code> 的依赖。</li>
<li><p>实现 WebMvcConfigurer 接口，可添加自定义的 Spring MVC 配置。</p>
<blockquote>
<p>因为 Spring Boot 2 基于 JDK 8 的版本，而 JDK 8 提供 <code>default</code> 方法，所以 Spring Boot 2 废弃了 WebMvcConfigurerAdapter 适配类，直接使用 WebMvcConfigurer 即可。</p>
</blockquote>
<figure class="highlight java"><table><tbody><tr><td class="code"><pre><span class="line"><span class="comment">// WebMvcConfigurer.java</span></span><br><span class="line"><span class="keyword">public</span> <span class="class"><span class="keyword">interface</span> <span class="title">WebMvcConfigurer</span> </span>{</span><br><span class="line"></span><br><span class="line">    <span class="comment">/** 配置路径匹配器 **/</span></span><br><span class="line">    <span class="function"><span class="keyword">default</span> <span class="keyword">void</span> <span class="title">configurePathMatch</span><span class="params">(PathMatchConfigurer configurer)</span> </span>{}</span><br><span class="line">    </span><br><span class="line">    <span class="comment">/** 配置内容裁决的一些选项 **/</span></span><br><span class="line">    <span class="function"><span class="keyword">default</span> <span class="keyword">void</span> <span class="title">configureContentNegotiation</span><span class="params">(ContentNegotiationConfigurer configurer)</span> </span>{ }</span><br><span class="line"></span><br><span class="line">    <span class="comment">/** 异步相关的配置 **/</span></span><br><span class="line">    <span class="function"><span class="keyword">default</span> <span class="keyword">void</span> <span class="title">configureAsyncSupport</span><span class="params">(AsyncSupportConfigurer configurer)</span> </span>{ }</span><br><span class="line"></span><br><span class="line">    <span class="function"><span class="keyword">default</span> <span class="keyword">void</span> <span class="title">configureDefaultServletHandling</span><span class="params">(DefaultServletHandlerConfigurer configurer)</span> </span>{ }</span><br><span class="line"></span><br><span class="line">    <span class="function"><span class="keyword">default</span> <span class="keyword">void</span> <span class="title">addFormatters</span><span class="params">(FormatterRegistry registry)</span> </span>{</span><br><span class="line">    }</span><br><span class="line"></span><br><span class="line">    <span class="comment">/** 添加拦截器 **/</span></span><br><span class="line">    <span class="function"><span class="keyword">default</span> <span class="keyword">void</span> <span class="title">addInterceptors</span><span class="params">(InterceptorRegistry registry)</span> </span>{ }</span><br><span class="line"></span><br><span class="line">    <span class="comment">/** 静态资源处理 **/</span></span><br><span class="line">    <span class="function"><span class="keyword">default</span> <span class="keyword">void</span> <span class="title">addResourceHandlers</span><span class="params">(ResourceHandlerRegistry registry)</span> </span>{ }</span><br><span class="line"></span><br><span class="line">    <span class="comment">/** 解决跨域问题 **/</span></span><br><span class="line">    <span class="function"><span class="keyword">default</span> <span class="keyword">void</span> <span class="title">addCorsMappings</span><span class="params">(CorsRegistry registry)</span> </span>{ }</span><br><span class="line"></span><br><span class="line">    <span class="function"><span class="keyword">default</span> <span class="keyword">void</span> <span class="title">addViewControllers</span><span class="params">(ViewControllerRegistry registry)</span> </span>{ }</span><br><span class="line"></span><br><span class="line">    <span class="comment">/** 配置视图解析器 **/</span></span><br><span class="line">    <span class="function"><span class="keyword">default</span> <span class="keyword">void</span> <span class="title">configureViewResolvers</span><span class="params">(ViewResolverRegistry registry)</span> </span>{ }</span><br><span class="line"></span><br><span class="line">    <span class="comment">/** 添加参数解析器 **/</span></span><br><span class="line">    <span class="function"><span class="keyword">default</span> <span class="keyword">void</span> <span class="title">addArgumentResolvers</span><span class="params">(List&lt;HandlerMethodArgumentResolver&gt; resolvers)</span> </span>{</span><br><span class="line">    }</span><br><span class="line"></span><br><span class="line">    <span class="comment">/** 添加返回值处理器 **/</span></span><br><span class="line">    <span class="function"><span class="keyword">default</span> <span class="keyword">void</span> <span class="title">addReturnValueHandlers</span><span class="params">(List&lt;HandlerMethodReturnValueHandler&gt; handlers)</span> </span>{ }</span><br><span class="line"></span><br><span class="line">    <span class="comment">/** 这里配置视图解析器 **/</span></span><br><span class="line">    <span class="function"><span class="keyword">default</span> <span class="keyword">void</span> <span class="title">configureMessageConverters</span><span class="params">(List&lt;HttpMessageConverter&lt;?&gt;&gt; converters)</span> </span>{ }</span><br><span class="line"></span><br><span class="line">    <span class="comment">/** 配置消息转换器 **/</span></span><br><span class="line">    <span class="function"><span class="keyword">default</span> <span class="keyword">void</span> <span class="title">extendMessageConverters</span><span class="params">(List&lt;HttpMessageConverter&lt;?&gt;&gt; converters)</span> </span>{ }</span><br><span class="line"></span><br><span class="line">   <span class="comment">/** 配置异常处理器 **/</span></span><br><span class="line">    <span class="function"><span class="keyword">default</span> <span class="keyword">void</span> <span class="title">configureHandlerExceptionResolvers</span><span class="params">(List&lt;HandlerExceptionResolver&gt; resolvers)</span> </span>{ }</span><br><span class="line"></span><br><span class="line">    <span class="function"><span class="keyword">default</span> <span class="keyword">void</span> <span class="title">extendHandlerExceptionResolvers</span><span class="params">(List&lt;HandlerExceptionResolver&gt; resolvers)</span> </span>{ }</span><br><span class="line"></span><br><span class="line">    <span class="meta">@Nullable</span></span><br><span class="line">    <span class="function"><span class="keyword">default</span> Validator <span class="title">getValidator</span><span class="params">()</span> </span>{ <span class="keyword">return</span> <span class="keyword">null</span>; }</span><br><span class="line"></span><br><span class="line">    <span class="meta">@Nullable</span></span><br><span class="line">    <span class="function"><span class="keyword">default</span> MessageCodesResolver <span class="title">getMessageCodesResolver</span><span class="params">()</span> </span>{  <span class="keyword">return</span> <span class="keyword">null</span>; }</span><br><span class="line"></span><br><span class="line">}</span><br></pre></td></tr></tbody></table></figure>
</li>
</ol>
<hr>
<p>在使用 Spring MVC 时，我们一般会做如下几件事情：</p>
<ol>
<li>实现自己项目需要的拦截器，并在 WebMvcConfigurer 实现类中配置。可参见 <a href="https://github.com/YunaiV/oceans/blob/2a2d3746905f1349e260e88049e7e28346c7648f/bff/webapp-bff/src/main/java/cn/iocoder/oceans/webapp/bff/config/MVCConfiguration.java" rel="external nofollow noopener noreferrer" target="_blank">MVCConfiguration</a> 类。</li>
<li>配置 <code>@ControllerAdvice</code> + <code>@ExceptionHandler</code> 注解，实现全局异常处理。可参见 <a href="https://github.com/YunaiV/oceans/blob/2a2d3746905f1349e260e88049e7e28346c7648f/bff/webapp-bff/src/main/java/cn/iocoder/oceans/webapp/bff/config/GlobalExceptionHandler.java" rel="external nofollow noopener noreferrer" target="_blank">GlobalExceptionHandler</a> 类。</li>
<li>配置 <code>@ControllerAdvice</code> ，实现 ResponseBodyAdvice 接口，实现全局统一返回。可参见 <a href="https://github.com/YunaiV/oceans/blob/2a2d3746905f1349e260e88049e7e28346c7648f/bff/webapp-bff/src/main/java/cn/iocoder/oceans/webapp/bff/config/GlobalResponseBodyAdvice.java" rel="external nofollow noopener noreferrer" target="_blank">GlobalResponseBodyAdvice</a> 。</li>
</ol>
<p>当然，有一点需要注意，WebMvcConfigurer、ResponseBodyAdvice、<code>@ControllerAdvice</code>、<code>@ExceptionHandler</code> 接口，都是 Spring MVC 框架自身已经有的东西。</p>
<ul>
<li><code>spring-boot-starter-web</code> 的依赖，帮我们解决的是 Spring MVC 的依赖以及相关的 Tomcat 等组件。</li>
</ul>
<h2 id="如何集成-Spring-Boot-和-Spring-Security-？"><a href="#如何集成-Spring-Boot-和-Spring-Security-？" class="headerlink" title="如何集成 Spring Boot 和 Spring Security ？"></a>如何集成 Spring Boot 和 Spring Security ？</h2><p>目前比较主流的安全框架有两个：</p>
<ol>
<li>Spring Security</li>
<li>Apache Shiro</li>
</ol>
<p>对于任何项目来说，安全认证总是少不了，同样适用于使用 Spring Boot 的项目。相对来说，Spring Security 现在会比 Apache Shiro 更流行。</p>
<p>Spring Boot 和 Spring Security 的配置方式比较简单：</p>
<ol>
<li>引入 <code>spring-boot-starter-security</code> 的依赖。</li>
<li>继承 WebSecurityConfigurerAdapter ，添加<strong>自定义</strong>的安全配置。</li>
</ol>
<p>当然，每个项目的安全配置是不同的，需要胖友自己选择。更多详细的使用，建议认真阅读如下文章：</p>
<ul>
<li><a href="http://blog.didispace.com/springbootsecurity/" rel="external nofollow noopener noreferrer" target="_blank">《Spring Boot中 使用 Spring Security 进行安全控制》</a> ，快速上手。</li>
<li><a href="http://www.iocoder.cn/Spring-Security/good-collection/" rel="external nofollow noopener noreferrer" target="_blank">《Spring Security 实现原理与源码解析系统 —— 精品合集》</a> ，深入源码。</li>
</ul>
<p>另外，安全是一个很大的话题，感兴趣的胖友，可以看看 <a href="https://www.jdon.com/49653" rel="external nofollow noopener noreferrer" target="_blank">《Spring Boot 十种安全措施》</a> 一文。</p>
<h2 id="如何集成-Spring-Boot-和-Spring-Security-OAuth2-？"><a href="#如何集成-Spring-Boot-和-Spring-Security-OAuth2-？" class="headerlink" title="如何集成 Spring Boot 和 Spring Security OAuth2 ？"></a>如何集成 Spring Boot 和 Spring Security OAuth2 ？</h2><p>参见 <a href="http://www.iocoder.cn/Spring-Security/OAuth2-learning/" rel="external nofollow noopener noreferrer" target="_blank">《Spring Security OAuth2 入门》</a> 文章，内容有点多。</p>
<h2 id="如何集成-Spring-Boot-和-JPA-？"><a href="#如何集成-Spring-Boot-和-JPA-？" class="headerlink" title="如何集成 Spring Boot 和 JPA ？"></a>如何集成 Spring Boot 和 JPA ？</h2><ol>
<li>引入 <code>spring-boot-starter-data-jpa</code> 的依赖。</li>
<li>在 application 配置文件中，加入 JPA 相关的少量配置。当然，数据库的配置也要添加进去。</li>
<li>具体编码。</li>
</ol>
<p>详细的使用，胖友可以参考：</p>
<ul>
<li><a href="http://www.iocoder.cn/Spring-Boot/battcn/v2-orm-jpa/" rel="external nofollow noopener noreferrer" target="_blank">《一起来学 SpringBoot 2.x | 第六篇：整合 Spring Data JPA》</a></li>
</ul>
<p>有两点需要注意：</p>
<ul>
<li>Spring Boot 2 默认使用的数据库连接池是 <a href="https://github.com/brettwooldridge/HikariCP" rel="external nofollow noopener noreferrer" target="_blank">HikariCP</a> ，目前最好的性能的数据库连接池的实现。</li>
<li><code>spring-boot-starter-data-jpa</code> 的依赖，使用的默认 JPA 实现是 Hibernate 5.X 。</li>
</ul>
<h2 id="如何集成-Spring-Boot-和-MyBatis-？"><a href="#如何集成-Spring-Boot-和-MyBatis-？" class="headerlink" title="如何集成 Spring Boot 和 MyBatis ？"></a>如何集成 Spring Boot 和 MyBatis ？</h2><ol>
<li>引入 <code>mybatis-spring-boot-starter</code> 的依赖。</li>
<li>在 application 配置文件中，加入 MyBatis 相关的少量配置。当然，数据库的配置也要添加进去。</li>
<li>具体编码。</li>
</ol>
<p>详细的使用，胖友可以参考：</p>
<ul>
<li><a href="http://www.iocoder.cn/Spring-Boot/battcn/v2-orm-mybatis/" rel="external nofollow noopener noreferrer" target="_blank">《一起来学 SpringBoot 2.x | 第七篇：整合 Mybatis》</a></li>
</ul>
<h2 id="如何集成-Spring-Boot-和-RabbitMQ-？"><a href="#如何集成-Spring-Boot-和-RabbitMQ-？" class="headerlink" title="如何集成 Spring Boot 和 RabbitMQ ？"></a>如何集成 Spring Boot 和 RabbitMQ ？</h2><ol>
<li>引入 <code>spring-boot-starter-amqp</code> 的依赖</li>
<li>在 application 配置文件中，加入 RabbitMQ 相关的少量配置。</li>
<li>具体编码。</li>
</ol>
<p>详细的使用，胖友可以参考：</p>
<ul>
<li><a href="http://www.iocoder.cn/Spring-Boot/battcn/v2-queue-rabbitmq/" rel="external nofollow noopener noreferrer" target="_blank">《一起来学 SpringBoot 2.x | 第十二篇：初探 RabbitMQ 消息队列》</a></li>
<li><a href="http://www.iocoder.cn/Spring-Boot/battcn/v2-queue-rabbitmq-delay/" rel="external nofollow noopener noreferrer" target="_blank">《一起来学 SpringBoot 2.x | 第十三篇：RabbitMQ 延迟队列》</a></li>
</ul>
<h2 id="如何集成-Spring-Boot-和-Kafka-？"><a href="#如何集成-Spring-Boot-和-Kafka-？" class="headerlink" title="如何集成 Spring Boot 和 Kafka ？"></a>如何集成 Spring Boot 和 Kafka ？</h2><ol>
<li>引入 <code>spring-kafka</code> 的依赖。</li>
<li>在 application 配置文件中，加入 Kafka 相关的少量配置。</li>
<li>具体编码。</li>
</ol>
<p>详细的使用，胖友可以参考：</p>
<ul>
<li><a href="http://www.54tianzhisheng.cn/2018/01/05/SpringBoot-Kafka/" rel="external nofollow noopener noreferrer" target="_blank">《Spring Boot系列文章（一）：SpringBoot Kafka 整合使用》</a></li>
</ul>
<h2 id="如何集成-Spring-Boot-和-RocketMQ-？"><a href="#如何集成-Spring-Boot-和-RocketMQ-？" class="headerlink" title="如何集成 Spring Boot 和 RocketMQ ？"></a>如何集成 Spring Boot 和 RocketMQ ？</h2><ol>
<li>引入 <code>rocketmq-spring-boot</code> 的依赖。</li>
<li>在 application 配置文件中，加入 RocketMQ 相关的少量配置。</li>
<li>具体编码。</li>
</ol>
<p>详细的使用，胖友可以参考：</p>
<ul>
<li><a href="http://www.iocoder.cn/RocketMQ/start/spring-boot-example" rel="external nofollow noopener noreferrer" target="_blank">《我用这种方法在 Spring 中实现消息的发送和消费》</a></li>
</ul>
<h2 id="Spring-Boot-支持哪些日志框架？"><a href="#Spring-Boot-支持哪些日志框架？" class="headerlink" title="Spring Boot 支持哪些日志框架？"></a>Spring Boot 支持哪些日志框架？</h2><p>Spring Boot 支持的日志框架有：</p>
<ul>
<li>Logback</li>
<li>Log4j2</li>
<li>Log4j</li>
<li>Java Util  Logging</li>
</ul>
<p>默认使用的是 Logback 日志框架，也是目前较为推荐的，具体配置，可以参见 <a href="http://www.iocoder.cn/Spring-Boot/battcn/v2-config-logs/" rel="external nofollow noopener noreferrer" target="_blank">《一起来学 SpringBoot 2.x | 第三篇：SpringBoot 日志配置》</a> 。</p>
<p>因为 Log4j2 的性能更加优秀，也有人在生产上使用，可以参考 <a href="https://www.jianshu.com/p/f18a9cff351d" rel="external nofollow noopener noreferrer" target="_blank">《Spring Boot Log4j2 日志性能之巅》</a> 配置。</p>
<h1 id="666-彩蛋"><a href="#666-彩蛋" class="headerlink" title="666. 彩蛋"></a>666. 彩蛋</h1><p>😈 看完之后，复习复习 Spring Boot 美滋滋。有一种奇怪的感觉，把面试题写成了 Spring 的学习指南。</p>
<p>当然，如果胖友有新的面试题，欢迎在星球一起探讨补充。</p>
<p>参考和推荐如下文章：</p>
<ul>
<li>我有面试宝典 <a href="http://www.wityx.com/post/242_1_1.html" rel="external nofollow noopener noreferrer" target="_blank">《[经验分享] Spring Boot面试题总结》</a></li>
<li>Java 知音 <a href="https://cloud.tencent.com/developer/article/1348086" rel="external nofollow noopener noreferrer" target="_blank">《Spring Boot 面试题精华》</a></li>
<li>祖大帅 <a href="https://juejin.im/post/5b679fbc5188251aad213110" rel="external nofollow noopener noreferrer" target="_blank">《一个面试题引起的 Spring Boot 启动解析》</a></li>
<li>大胡子叔叔_ <a href="https://blog.csdn.net/panhaigang123/article/details/79587612" rel="external nofollow noopener noreferrer" target="_blank">《Spring Boot + Spring Cloud 相关面试题》</a></li>
<li>墨斗鱼博客 <a href="https://www.mudouyu.com/article/26" rel="external nofollow noopener noreferrer" target="_blank">《20 道 Spring Boot 面试题》</a></li>
<li>夕阳雨晴 <a href="https://blog.csdn.net/sun1021873926/article/details/78176354" rel="external nofollow noopener noreferrer" target="_blank">《Spring Boot Starter 的面试题》</a></li>
</ul>




</div>