package com.java.boot.base.controller;

import com.java.boot.base.repository.ItemRepository;
import com.java.boot.base.entity.Item;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * ElasticSearch 全文检索测试
 *
 * @author xuweizhi
 * @date 2019/04/27 23:18
 */
@RestController
@RequestMapping("/item")
@Api
public class ItemController {

    private final ElasticsearchTemplate template;

    private final ItemRepository itemRepository;

    @Contract(pure = true)
    public ItemController(ElasticsearchTemplate template, ItemRepository itemRepository) {
        this.template = template;
        this.itemRepository = itemRepository;
    }

    /**
     * 创建索引，会根据Item类的@Document注解信息来创建,仅用来创建索引
     * 其实项目启动的时候，会自动创建索引
     */
    @GetMapping("creatItemIndex")
    public void createItemIndex() {
        template.createIndex(Item.class);
    }

    @GetMapping("deleteItmeIndex")
    public void deleteItemIndex() {
        template.deleteIndex(Item.class);
    }

    @ApiOperation(value = "新增条目索引", notes = "测试")
    @ApiParam(name = "title", value = "索引名称")
    @GetMapping("insert")
    public Item insertItem(Long id, String title) {
        Assert.notNull(title, "title 不能为空 !");
        Item item = new Item(id, title, "目录", "商标", 12D, "111");
        itemRepository.save(item);
        return item;
    }

    /**
     * 定义批量新增方法
     */
    @GetMapping("insertList")
    public void insertList() {
        List<Item> list = new ArrayList<>();
        list.add(new Item(1L, "小米手机7", "手机", "小米", 3299.00, "http://image.baidu.com/13123.jpg"));
        list.add(new Item(2L, "坚果手机R1", "手机", "锤子", 3699.00, "http://image.baidu.com/13123.jpg"));
        list.add(new Item(3L, "华为META10", "手机", "华为", 4499.00, "http://image.baidu.com/13123.jpg"));
        list.add(new Item(4L, "小米Mix2S", "手机", "小米", 4299.00, "http://image.baidu.com/13123.jpg"));
        list.add(new Item(5L, "荣耀V10", "手机", "华为", 2799.00, "http://image.baidu.com/13123.jpg"));
        // 接收对象集合，实现批量新增
        itemRepository.saveAll(list);
    }

    /**
     * 修改 1L 的索引
     */
    @GetMapping("updateList")
    public void update() {
        Item item = new Item(1L, "苹果XSMax", " 手机",
                "小米", 3499.00, "http://image.baidu.com/13123.jpg");
        itemRepository.save(item);
    }

    /**
     * 定义查询方法,含对价格的降序、升序查询
     */
    @GetMapping("queryAll")
    public void queryAll() {
        // 查找所有
        //Iterable<Item> list = this.itemRepository.findAll();
        // 对某字段排序查找所有 Sort.by("price").descending() 降序
        // Sort.by("price").ascending():升序
        Iterable<Item> list = this.itemRepository.findAll(Sort.by("price").ascending());

        for (Item item : list) {
            System.out.println(item);
        }
    }

    /**
     * 按照价格区间查询
     */
    @GetMapping("queryByPriceBetween")
    public void queryByPriceBetween() {
        List<Item> list = this.itemRepository.findByPriceBetween(2000.00, 3500.00);
        for (Item item : list) {
            System.out.println("item = " + item);
        }
    }

    /**
     * 自定义查询
     * <p>
     * NativeSearchQueryBuilder：Spring提供的一个查询条件构建器，帮助构建json格式的请求体
     * <p>
     * QueryBuilders.matchQuery(“title”, “小米手机”)：利用QueryBuilders来生成一个查询。
     * QueryBuilders提供了大量的静态方法，用于生成各种不同类型的查询。
     * <p>
     * Page<item>：默认是分页查询，因此返回的是一个分页的结果对象，包含属性：
     * <p>
     * totalElements：总条数
     * <p>
     * totalPages：总页数
     * <p>
     * Iterator：迭代器，本身实现了Iterator接口，因此可直接迭代得到当前页的数据
     */
    @GetMapping("matchQuery")
    public List<Item> matchQuery(String title) {
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        queryBuilder.withQuery(QueryBuilders.matchQuery("title", title));
        // 搜索，获取结果
        Page<Item> items = this.itemRepository.search(queryBuilder.build());
        // 总条数
        long total = items.getTotalElements();
        System.out.println("total = " + total);
        List<Item> itemList = new ArrayList<>();
        for (Item item : items) {
            System.out.println(item);
            itemList.add(item);
        }
        return itemList;
    }

    /**
     * termQuery:功能更强大，除了匹配字符串以外，还可以匹配
     * int/long/double/float/....
     */
    @GetMapping("termQuery")
    public List<Item> termQuery() {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(QueryBuilders.termQuery("price", 998.0));
        // 查找
        return getItems(builder);
    }

    @NotNull
    private List<Item> getItems(NativeSearchQueryBuilder builder) {
        Page<Item> page = this.itemRepository.search(builder.build());

        List<Item> itemList = new ArrayList<>();

        for (Item item : page) {
            System.out.println(item);
            itemList.add(item);
        }
        return itemList;
    }

    /**
     * 测试布尔查询
     */
    @GetMapping("booleanQuery")
    public List<Item> booleanQuery() {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();

        builder.withQuery(
                QueryBuilders.boolQuery().must(QueryBuilders.matchQuery("title", "华为"))
                        .must(QueryBuilders.matchQuery("brand", "华为"))
        );

        // 查找
        return getItems(builder);
    }

    /**
     * 模糊查询
     */
    @GetMapping("fuzzyQuery")
    public List<Item> fuzzyQuery() {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        builder.withQuery(QueryBuilders.fuzzyQuery("title", "手"));
        return getItems(builder);
    }

    /**
     * 分页查询,可以发现，Elasticsearch中的分页是从第0页开始。
     */
    @GetMapping("searchByPage")
    public List<Item> searchByPage() {
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        queryBuilder.withQuery(QueryBuilders.termQuery("category", "手机"));
        // 分页：
        int page = 0;
        int size = 2;
        queryBuilder.withPageable(PageRequest.of(page, size));

        // 搜索，获取结果
        Page<Item> items = this.itemRepository.search(queryBuilder.build());
        // 总条数
        long total = items.getTotalElements();
        System.out.println("总条数 = " + total);
        // 总页数
        System.out.println("总页数 = " + items.getTotalPages());
        // 当前页
        System.out.println("当前页：" + items.getNumber());
        // 每页大小
        System.out.println("每页大小：" + items.getSize());

        return getItems(queryBuilder);
    }

    /**
     * 排序查询
     */
    @GetMapping("searchAndSort")
    public @NotNull List<Item> searchAndSort() {
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        queryBuilder.withQuery(QueryBuilders.termQuery("category", "手机"));

        // 排序
        queryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC));

        // 搜索，获取结果
        Page<Item> items = this.itemRepository.search(queryBuilder.build());
        // 总条数
        long total = items.getTotalElements();
        System.out.println("总条数 = " + total);

        return getItems(queryBuilder);
    }

    /**
     * <h2>聚合（牛逼！！solr无此功能）</h2>
     * 聚合可以让我们极其方便的实现对数据的统计、分析。例如：
     * <ul>
     * <li>什么品牌的手机最受欢迎？</li>
     * <li>这些手机的平均价格、最高价格、最低价格？</li>
     * <li>这些手机每月的销售情况如何？</li>
     * <li>实现这些统计功能的比数据库的sql要方便的多，而且查询速度非常快，可以实现近实时搜索效果。</li>
     * </ul>
     * <h2> 聚合基本概念</h2>
     * Elasticsearch中的聚合，包含多种类型，最常用的两种，一个叫桶，一个叫度量：
     *
     * <h3>桶（bucket）</h3>
     * 桶的作用，是按照某种方式对数据进行分组，每一组数据在ES中称为一个桶，例如我们根据国籍对人划分，可以得到中国桶、
     * 英国桶，日本桶……或者我们按照年龄段对人进行划分：010,1020,2030,3040等。
     * <p>
     * Elasticsearch中提供的划分桶的方式有很多：
     *
     * <ul>
     * <li>Date Histogram Aggregation：根据日期阶梯分组，例如给定阶梯为周，会自动每周分为一组</li>
     * <li>Histogram Aggregation：根据数值阶梯分组，与日期类似</li>
     * <li>Terms Aggregation：根据词条内容分组，词条内容完全匹配的为一组</li>
     * <li>Range Aggregation：数值和日期的范围分组，指定开始和结束，然后按段分组</li>
     * </ul>
     * <p>
     * 综上所述，我们发现bucket aggregations 只负责对数据进行分组，并不进行计算，因此往往bucket中往往会嵌套
     * 另一种聚合：metrics aggregations即度量
     *
     * <h3>度量（metrics）</h3>
     * 分组完成以后，我们一般会对组中的数据进行聚合运算，例如求平均值、最大、最小、求和等，这些在ES中称为度量
     * <p>
     * 比较常用的一些度量聚合方式：
     *
     * <ul>
     * <li>Avg Aggregation：求平均值</li>
     * <li>Max Aggregation：求最大值</li>
     * <li>Min Aggregation：求最小值</li>
     * <li>Stats Aggregation：同时返回avg、max、min、sum、count等</li>
     * <li>Percentiles Aggregation：求百分比</li>
     * <li>Sum Aggregation：求和</li>
     * <li>Top hits Aggregation：求前几</li>
     * <li>Value Count Aggregation：求总数</li>
     * </ul>
     * <p>
     * 注意：在ES中，需要进行聚合、排序、过滤的字段其处理方式比较特殊，因此不能被分词。
     * 这里我们将color和make这两个文字类型的字段设置为keyword类型，这个类型不会被分词，将来就可以参与聚合
     */
    public void defineSum() {

    }

    /**
     * 聚合为桶
     * <p>
     * 桶就是分组，比如这里我们按照品牌brand进行分组
     * <p>
     * 关键API：AggregationBuilders：聚合的构建工厂类。所有聚合都由这个类来构建，看看他的静态方法：
     * <p>
     * （1）统计某个字段的数量
     * ValueCountBuilder vcb=  AggregationBuilders.count("count_uid").field("uid");
     * （2）去重统计某个字段的数量（有少量误差）
     * CardinalityBuilder cb= AggregationBuilders.cardinality("distinct_count_uid").field("uid");
     * （3）聚合过滤
     * FilterAggregationBuilder fab= AggregationBuilders.filter("uid_filter").filter(QueryBuilders.queryStringQuery("uid:001"));
     * （4）按某个字段分组
     * TermsBuilder tb=  AggregationBuilders.terms("group_name").field("name");
     * （5）求和
     * SumBuilder  sumBuilder=	AggregationBuilders.sum("sum_price").field("price");
     * （6）求平均
     * AvgBuilder ab= AggregationBuilders.avg("avg_price").field("price");
     * （7）求最大值
     * MaxBuilder mb= AggregationBuilders.max("max_price").field("price");
     * （8）求最小值
     * MinBuilder min=	AggregationBuilders.min("min_price").field("price");
     * （9）按日期间隔分组
     * DateHistogramBuilder dhb= AggregationBuilders.dateHistogram("dh").field("date");
     * （10）获取聚合里面的结果
     * TopHitsBuilder thb=  AggregationBuilders.topHits("top_result");
     * （11）嵌套的聚合
     * NestedBuilder nb= AggregationBuilders.nested("negsted_path").path("quests");
     * （12）反转嵌套
     * AggregationBuilders.reverseNested("res_negsted").path("kps ");
     */
    @GetMapping("agg")
    public void agg() {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 不查询任何结果
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));
        // 1、添加一个新的聚合，聚合类型为terms，聚合名称为brands，聚合字段为brand
        queryBuilder.addAggregation(
                AggregationBuilders.terms("brands").field("brand"));
        // 2、查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<Item> aggPage = (AggregatedPage<Item>) this.itemRepository.search(queryBuilder.build());
        // 3、解析
        // 3.1、从结果中取出名为brands的那个聚合，
        // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
        StringTerms agg = (StringTerms) aggPage.getAggregation("brands");
        // 3.2、获取桶
        List<StringTerms.Bucket> buckets = agg.getBuckets();
        // 3.3、遍历
        for (StringTerms.Bucket bucket : buckets) {
            // 3.4、获取桶中的key，即品牌名称
            System.out.println(bucket.getKeyAsString());
            // 3.5、获取桶中的文档数量
            System.out.println(bucket.getDocCount());
        }

    }

    /**
     * 嵌套聚合，求平均值
     */
    @GetMapping("/subAgg")
    public void subAgg() {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 不查询任何结果
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));
        // 1、添加一个新的聚合，聚合类型为terms，聚合名称为brands，聚合字段为brand
        queryBuilder.addAggregation(
                AggregationBuilders.terms("brands").field("brand")
                        // 在品牌聚合桶内进行嵌套聚合，求平均值
                        .subAggregation(AggregationBuilders.avg("priceAvg").field("price"))
        );
        // 2、查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<Item> aggPage = (AggregatedPage<Item>) this.itemRepository.search(queryBuilder.build());
        // 3、解析
        // 3.1、从结果中取出名为brands的那个聚合，
        // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
        StringTerms agg = (StringTerms) aggPage.getAggregation("brands");
        // 3.2、获取桶
        List<StringTerms.Bucket> buckets = agg.getBuckets();
        // 3.3、遍历
        for (StringTerms.Bucket bucket : buckets) {
            // 3.4、获取桶中的key，即品牌名称  3.5、获取桶中的文档数量
            System.out.println(bucket.getKeyAsString() + "，共" + bucket.getDocCount() + "台");

            // 3.6.获取子聚合结果：
            InternalAvg avg = (InternalAvg) bucket.getAggregations().asMap().get("priceAvg");
            System.out.println("平均售价：" + avg.getValue());
        }

    }

}
