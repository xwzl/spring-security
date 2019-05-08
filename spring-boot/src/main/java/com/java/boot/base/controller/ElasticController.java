package com.java.boot.base.controller;

import com.java.boot.base.entity.Article;
import com.java.boot.base.entity.Author;
import com.java.boot.base.entity.GoodsInfo;
import com.java.boot.base.entity.Tutorial;
import com.java.boot.base.repository.ArticleSearchRepository;
import com.java.boot.base.repository.GoodsRepository;
import io.swagger.annotations.Api;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.jetbrains.annotations.Contract;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.Optional;

/**
 * @author xuweizhi
 * @since  2019/04/25 16:42
 */
@Api
@RestController
@RequestMapping("/elastic")
public class ElasticController {


    private final GoodsRepository goodsRepository;

    private final ArticleSearchRepository articleSearchRepository;

    @Contract(pure = true)
    public ElasticController(GoodsRepository goodsRepository, ArticleSearchRepository articleSearchRepository) {
        this.goodsRepository = goodsRepository;
        this.articleSearchRepository = articleSearchRepository;
    }


    /**
     * http://localhost:8082/save
     */
    @GetMapping("save")
    public String save() {
        GoodsInfo goodsInfo = new GoodsInfo(System.currentTimeMillis(),
                "商品" + System.currentTimeMillis(), "这是一个测试商品");
        goodsRepository.save(goodsInfo);
        return "success";
    }

    /**
     * http://localhost:8082/delete?id=1525415333329
     */
    @GetMapping("delete")
    public String delete(long id) {
        goodsRepository.deleteById(id);
        return "success";
    }

    //http://localhost:8082/update?id=1525417362754&name=修改&description=修改
    @GetMapping("update")
    public String update(long id, String name, String description) {
        GoodsInfo goodsInfo = new GoodsInfo(id,
                name, description);
        goodsRepository.save(goodsInfo);
        return "success";
    }

    //http://localhost:8082/getOne?id=1525417362754
    @GetMapping("getOne")
    public GoodsInfo getOne(long id) {
        Optional<GoodsInfo> goodsInfo = goodsRepository.findById(id);
        GoodsInfo goodsInfo1 = goodsInfo.get();
        return goodsInfo1;
    }

    @GetMapping("saveArticleIndex")
    public void saveArticleIndex(){
        Author author = new Author();
        author.setId(1L);
        author.setName("slp");
        author.setRemark("test");

        Tutorial tutorial = new Tutorial();
        tutorial.setId(1L);
        tutorial.setName("elastic search");

        Article article = new Article();
        article.setId(1L);
        article.setTitle("spring boot integrate elasticsearch");
        article.setAbstracts("elasticsearch is very easy");
        article.setTutorial(tutorial);
        article.setAuthor(author);
        article.setContent("elasticsearch based on lucene");
        article.setPostTime("20180704");
        article.setClickCount("1");
        articleSearchRepository.save(article);

    }

    @GetMapping("testSearch")
    public void testSearch(){
        //搜索关键字
        String queryString="spring";
        QueryStringQueryBuilder builder=new QueryStringQueryBuilder(queryString);
        Iterable<Article> searchResult = articleSearchRepository.search(builder);
        Iterator<Article> iterator = searchResult.iterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next().getAbstracts());
        }
    }

    //每页数量
    //private Integer PAGESIZE = 10;

    /**
     * http://localhost:8082/getGoodsList?query=商品
     * http://localhost:8082/getGoodsList?query=商品&pageNumber=1
     * 根据关键字"商品"去查询列表，name或者description包含的都查询
     */
    //@GetMapping("getGoodsList")
    //public List<GoodsInfo> getList(Integer pageNumber, String query) {
    //    if (pageNumber == null) {
    //        pageNumber = 0;
    //    }
    //    //es搜索默认第一页页码是0
    //    SearchQuery searchQuery = getEntitySearchQuery(pageNumber, PAGESIZE, query);
    //    Page<GoodsInfo> goodsPage = goodsRepository.search(searchQuery);
    //    return goodsPage.getContent();
    //}


    //private SearchQuery getEntitySearchQuery(int pageNumber, int pageSize, String searchContent) {
    //    QueryBuilder queryBuilder = ;
    //    FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(queryBuilder)
    //            .add(QueryBuilders.matchPhraseQuery("name", searchContent),
    //                    ScoreFunctionBuilders.weightFactorFunction(100))
    //            .add(QueryBuilders.matchPhraseQuery("description", searchContent),
    //                    ScoreFunctionBuilders.weightFactorFunction(100))
    //            //设置权重分 求和模式
    //            .scoreMode("sum")
    //            //设置权重分最低分
    //            .setMinScore(10);
    //
    //    // 设置分页
    //    Pageable pageable = new PageRequest(pageNumber, pageSize);
    //    return new NativeSearchQueryBuilder()
    //            .withPageable(pageable)
    //            .withQuery(functionScoreQueryBuilder).build();
    //}


}
