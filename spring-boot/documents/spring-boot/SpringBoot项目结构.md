
<div class="article-entry" itemprop="articleBody">

<h1 id="1-概述"><a href="#1-概述" class="headerlink" title="1. 概述"></a>1. 概述</h1><p>本文主要分享 <strong>Spring Boot 的项目结构</strong>。<br>希望通过本文能让胖友对 Spring Boot 的整体项目有个简单的了解。  </p>
<p><img src="http://static2.iocoder.cn/images/Spring-Boot/2021-01-04/01.jpg" alt="Spring Boot 项目结构"></p>
<h1 id="2-代码统计"><a href="#2-代码统计" class="headerlink" title="2. 代码统计"></a>2. 代码统计</h1><p>这里先分享一个小技巧。笔者在开始源码学习时，会首先了解项目的代码量。</p>
<p><strong>第一种方式</strong>，使用 <a href="https://plugins.jetbrains.com/plugin/4509-statistic" rel="external nofollow noopener noreferrer" target="_blank">IDEA Statistic</a> 插件，统计整体代码量。</p>
<p><img src="http://static2.iocoder.cn/images/Spring-Boot/2021-01-04/02.jpg" alt="Statistic 统计代码量"></p>
<p>我们可以粗略的看到，总的代码量在 268485 行。这其中还包括单元测试，示例等等代码。<br>所以，不慌。特别是 Spring 项目的代码，单元测试覆盖是超级全面的。</p>
<p><strong>第二种方式</strong>，使用 <a href="http://blog.csdn.net/yhhwatl/article/details/52623879" rel="external nofollow noopener noreferrer" target="_blank">Shell 脚本命令逐个 Maven 模块统计</a> 。</p>
<p>一般情况下，笔者使用 <code>find . -name "*.java"|xargs cat|grep -v -e ^$ -e ^\s*\/\/.*$|wc -l</code> 。这个命令只过滤了<strong>部分注释</strong>，所以相比 <a href="https://plugins.jetbrains.com/plugin/4509-statistic" rel="external nofollow noopener noreferrer" target="_blank">IDEA Statistic</a> 会<strong>偏多</strong>。</p>
<p>当然，考虑到准确性，胖友需要手动 <code>cd</code> 到每个 Maven 项目的 <code>src/main/java</code>  目录下，以达到排除单元测试的代码量。</p>
<p><img src="http://static2.iocoder.cn/images/Spring-Boot/2021-01-04/03.jpg" alt="Shell 脚本统计代码量"></p>
<p>统计完后，艿艿有点慌。哈哈哈哈。</p>
<h1 id="3-spring-boot-project-项目"><a href="#3-spring-boot-project-项目" class="headerlink" title="3. spring-boot-project 项目"></a>3. spring-boot-project 项目</h1><p><code>spring-boot-project</code> 项目，Spring Boot 的代码实现，大概在 20W 代码左右。当然，实际上我们并不需要看完全部。艿艿自己的话，也只会选择关注部分的看看，并且写成源码解析。所以啊，尾随艿艿，不要方。</p>
<h2 id="3-1-spring-boot-模块"><a href="#3-1-spring-boot-模块" class="headerlink" title="3.1 spring-boot 模块"></a>3.1 spring-boot 模块</h2><p><code>spring-boot</code> 模块，Spring Boot 的核心实现，大概在 4W 代码左右。提供了如下功能：</p>
<ul>
<li><p>在 <code>org.springframework.boot.SpringApplication</code> 类，提供了大量的静态方法，可以很容易运行一个独立的 Spring 应用程序。</p>
<blockquote>
<p>是不是超级熟悉。</p>
</blockquote>
</li>
<li><p>带有可选容器的嵌入式 Web 应用程序（Tomcat、Jetty、Undertow）    的支持。</p>
<blockquote>
<p>在 <code>org.springframework.boot.web</code> 包下实现。</p>
</blockquote>
</li>
<li><p>边界的外部配置支持。</p>
</li>
<li>… 省略其它。<blockquote>
<p>感兴趣的胖友，可以自己先简单翻翻每个 <code>package</code> 包，基本每个包下，都是对每个功能的支持。例如说，<code>web</code> 支持 Web 服务器，<code>jdbc</code> 支持 JDBC 功能，<code>task</code> 支持调度任务，以及等等。</p>
</blockquote>
</li>
</ul>
<h2 id="3-2-spring-boot-autoconfigure-模块"><a href="#3-2-spring-boot-autoconfigure-模块" class="headerlink" title="3.2 spring-boot-autoconfigure 模块"></a>3.2 spring-boot-autoconfigure 模块</h2><p><code>spring-boot-actuator-autoconfigure</code> 模块，大概 4W代码左右。<code>spring-boot-autoconfigure</code> 可以根据类路径的内容，自动配置大部分常用应用程序。通过使用 <code>org.springframework.boot.autoconfigure.@EnableAutoConfiguration</code> 注解，会触发 Spring 上下文的自动配置。</p>
<blockquote>
<p>这里的大部分，指的是常用的框架。例如说，Spring MVC、Quartz 等等。也就是说，如果 <code>spring-boot-actuator-autoconfigure</code> 模块，暂未提供的框架，需要我们自己去实现对应框架的自动装配。</p>
</blockquote>
<p>这个模块的代码，必须要看，没得商量。</p>
<p>所以到此处为止，我们已经看到对我们来研究 Spring Boot 最最最中航要的两个模块：<code>spring-boot</code> 和 <code>spring-boot-autoconfigure</code> ，一共是 9W 行代码左右。</p>
<h2 id="3-3-spring-boot-actuator-模块"><a href="#3-3-spring-boot-actuator-模块" class="headerlink" title="3.3 spring-boot-actuator 模块"></a>3.3 spring-boot-actuator 模块</h2><p><code>spring-boot-actuator</code> 模块，大概 2W 行代码左右。正如其模块的英文 actuator ，它完全是一个用于暴露应用自身信息的模块：</p>
<ul>
<li>提供了一个监控和管理生产环境的模块，可以使用 http、jmx、ssh、telnet 等管理和监控应用。</li>
<li>审计（Auditing）、 健康（health）、数据采集（metrics gathering）会自动加入到应用里面。</li>
</ul>
<blockquote>
<p>一般情况下，我们可以不看这块代码的代码。</p>
</blockquote>
<p>如果没有使用过 <code>spring-boot-actuator</code> 的胖友，可以看看下面两篇文章：</p>
<ul>
<li><a href="http://www.iocoder.cn/Spring-Boot/battcn/v2-actuator-introduce/?vip" rel="external nofollow noopener noreferrer" target="_blank">《一起来学 SpringBoot 2.x | 第十四篇：强大的 Actuator 服务监控与管理》</a></li>
<li><a href="http://www.iocoder.cn/Spring-Boot/battcn/v2-actuator-monitor/?vip" rel="external nofollow noopener noreferrer" target="_blank">《一起来学 SpringBoot 2.x | 第十五篇：actuator 与 spring-boot-admin 可以说的秘密》</a></li>
</ul>
<h2 id="3-4-spring-boot-actuator-autoconfigure-模块"><a href="#3-4-spring-boot-actuator-autoconfigure-模块" class="headerlink" title="3.4 spring-boot-actuator-autoconfigure 模块"></a>3.4 spring-boot-actuator-autoconfigure 模块</h2><p><code>spring-boot-actuator-autoconfigure</code> 模块，大概 1W7 行代码左右。它提供了 <code>spring-boot-actuator</code> 的自动配置功能。</p>
<blockquote>
<p>一般情况下，我们可以不看这块代码的代码。</p>
</blockquote>
<h2 id="3-5-spring-boot-starters-模块"><a href="#3-5-spring-boot-starters-模块" class="headerlink" title="3.5 spring-boot-starters 模块"></a>3.5 spring-boot-starters 模块</h2><p><code>spring-boot-starters</code> 模块，它不存在任何的代码，而是提供我们常用框架的 Starter 模块。例如：</p>
<ul>
<li><code>spring-boot-starter-web</code> 模块，提供了对 Spring MVC 的 Starter 模块。</li>
<li><code>spring-boot-starter-data-jpa</code> 模块，提供了对 Spring Data JPA 的 Starter 模块。 </li>
</ul>
<p>而每个 Starter 模块，里面只存在一个 <code>pom</code> 文件，这是为什么呢？简单来说，Spring Boot 可以根据项目中是否存在指定类，并且是否未生成对应的 Bean 对象，那么就自动创建 Bean 对象。因为有这样的机制，我们只需要使用 <code>pom</code> 文件，配置需要引入的框架，就可以实现该框架的使用所需要的类的自动装配。</p>
<blockquote>
<p>当然，正如我们在 <a href="#">「spring-boot-autoconfigure 模块」</a> 所提到的，如果不支持的框架，需要自己实现对应的 autoconfigure 功能。举个例子，Dubbo 框架并未在 <code>spring-boot-autoconfigure</code> 模块实现自动装配，所以 Dubbo 团队提供了 <a href="https://github.com/apache/incubator-dubbo-spring-boot-project" rel="external nofollow noopener noreferrer" target="_blank"><code>dubbo-spring-boot-project</code></a> 。</p>
</blockquote>
<p>😈 如果觉得神奇的胖友，不烦可以跟着 <a href="https://www.jianshu.com/p/45538b44e04e" rel="external nofollow noopener noreferrer" target="_blank">《快速开发一个自定义Spring Boot Starter》</a> 文章，来干一个自己的 Starter 模块。</p>
<h2 id="3-6-spring-boot-cli-模块"><a href="#3-6-spring-boot-cli-模块" class="headerlink" title="3.6 spring-boot-cli 模块"></a>3.6 spring-boot-cli 模块</h2><p><code>spring-boot-cli</code> 模块，大概 1W 行代码左右。它提供了 Spring 项目相关的命令行功能。它是 Spring Boot 的命令行界面。</p>
<ul>
<li>它可以用来快速启动 Spring 。</li>
<li>它可以运行 Groovy 脚本，开发人员不需要编写很多样板代码，只需要关注业务逻辑。</li>
<li>Spring Boot CLI 是创建基于Spring的应用程序的最快方法。</li>
</ul>
<p>想要详细了解的胖友，可以看看 <a href="https://docs.spring.io/spring-boot/docs/1.5.2.RELEASE/reference/htmlsingle/#cli" rel="external nofollow noopener noreferrer" target="_blank">《Spring Boot 文档 —— Part VII. Spring Boot CLI》</a> 文档。</p>
<blockquote>
<p>一般情况下，我们可以不看这块代码的代码。</p>
</blockquote>
<h2 id="3-7-spring-boot-test-模块"><a href="#3-7-spring-boot-test-模块" class="headerlink" title="3.7 spring-boot-test 模块"></a>3.7 spring-boot-test 模块</h2><p><code>spring-boot-test</code> 模块，大概 1W 行代码左右。Spring Boot 提供测试方面的支持，例如说：</p>
<ul>
<li>SpringBootTestRandomPortEnvironmentPostProcessor 类，提供随机端口。</li>
<li><code>org.springframework.boot.test.mock.mockito</code> 包，提供 Mockito 的增强。</li>
</ul>
<blockquote>
<p>一般情况下，我们可以不看这块代码的代码。</p>
</blockquote>
<h2 id="3-8-spring-boot-test-autoconfigure-模块"><a href="#3-8-spring-boot-test-autoconfigure-模块" class="headerlink" title="3.8 spring-boot-test-autoconfigure 模块"></a>3.8 spring-boot-test-autoconfigure 模块</h2><p><code>spring-boot-test-autoconfigure</code> 模块，大概 1W 行代码不到。它提供了 <code>spring-boot-test</code> 的自动配置功能。</p>
<blockquote>
<p>一般情况下，我们可以不看这块代码的代码。</p>
</blockquote>
<h2 id="3-9-spring-boot-devtools-模块"><a href="#3-9-spring-boot-devtools-模块" class="headerlink" title="3.9 spring-boot-devtools 模块"></a>3.9 spring-boot-devtools 模块</h2><p><code>spring-boot-devtools</code> 模块，大概 8000 行代码左右。通过它，来使 Spring Boot 应用支持热部署，提高开发者的开发效率，无需手动重启 Spring Boot 应用。</p>
<p>没有使用过的胖友，赶紧开始用啦。具体杂用，可以看看 <a href="https://blog.csdn.net/yaomingyang/article/details/78241988" rel="external nofollow noopener noreferrer" target="_blank">《Spring Boot 项目中使用 spring-boot-devtools 模块进行代码热部署，避免重新启动 web 项目》</a> 文章。</p>
<blockquote>
<p>一般情况下，我们可以不看这块代码的代码。</p>
</blockquote>
<h2 id="3-10-spring-boot-tools-模块"><a href="#3-10-spring-boot-tools-模块" class="headerlink" title="3.10 spring-boot-tools 模块"></a>3.10 spring-boot-tools 模块</h2><p><code>spring-boot-tools</code> 模块，大概 3W 行代码左右。它是 Spring Boot 提供的工具箱，所以在其内有多个子 Maven 项目。</p>
<p>注意哟，我们这里说的工具箱，并不是我们在 Java 里的工具类。困惑？我们来举个例子：<code>spring-boot-maven-plugin</code> 模块：提供 Maven 打包 Spring Boot 项目的插件。</p>
<p>关于 <code>spring-boot-tools</code> 模块的其它子模块，我们就暂时不多做介绍落。</p>
<blockquote>
<p>一般情况下，我们可以不看这块代码的代码。</p>
</blockquote>
<h2 id="3-11-其它"><a href="#3-11-其它" class="headerlink" title="3.11 其它"></a>3.11 其它</h2><p><code>spring-boot-project</code> 项目的其它子模块如下：</p>
<ul>
<li><code>spring-boot-properties-migrator</code> 模块：500 行代码左右，帮助开发者从 Spring Boot 1 迁移到 Spring Boot 2 。</li>
<li><code>spring-boot-dependencies</code> 模块：无代码，只有所有依赖和插件的版本号信息。</li>
<li><code>spring-boot-parent</code> 模块：无代码，该模块是其他项目的 parent，该模块的父模块是 <code>spring-boot-dependencies</code> 。</li>
<li><code>spring-boot-docs</code> 模块：1000 行代码左右，貌似是提供 Spring Boot 文档里的一些示例。不太确定，也并不重要。</li>
</ul>
<h1 id="4-spring-boot-samples-项目"><a href="#4-spring-boot-samples-项目" class="headerlink" title="4. spring-boot-samples 项目"></a>4. spring-boot-samples 项目</h1><p><code>spring-boot-samples</code> 项目，2W 行代码左右。丧心病狂，提供了超级多的示例，简直良心无敌啊。</p>
<blockquote>
<p>一般情况下，我们可以不看这块代码的代码。如果真的需要某个 Spring Boot 对某个框架的示例，大多数情况下，我们还是 Google 检索文章居多。</p>
</blockquote>
<h1 id="5-spring-boot-samples-invoker-项目"><a href="#5-spring-boot-samples-invoker-项目" class="headerlink" title="5. spring-boot-samples-invoker 项目"></a>5. spring-boot-samples-invoker 项目</h1><p><code>spring-boot-samples-invoker</code> 项目，无代码，有点不造用户。当然，也并不重要。</p>
<h1 id="6-spring-boot-tests"><a href="#6-spring-boot-tests" class="headerlink" title="6. spring-boot-tests"></a>6. spring-boot-tests</h1><p><code>spring-boot-tests</code> 项目，3000 行代码，主要是 Spring Boot 的集成测试、部署测试。</p>
<blockquote>
<p>一般情况下，我们可以不看这块代码的代码。</p>
</blockquote>
<h1 id="666-彩蛋"><a href="#666-彩蛋" class="headerlink" title="666. 彩蛋"></a>666. 彩蛋</h1><p>并没有什么实质性内容的一篇文章。感谢老田 <a href="http://www.54tianzhisheng.cn/2018/04/18/spring_boot2_project/#spring-boot-tests" rel="external nofollow noopener noreferrer" target="_blank">《Spring Boot 2.0系列文章(五)：Spring Boot 2.0 项目源码结构预览》</a> 文章，让自己更加方便的了解 Spring Boot 2 的项目结构。</p>
<p>😈 继续怼。有点偷懒的一个周六。</p>




</div>