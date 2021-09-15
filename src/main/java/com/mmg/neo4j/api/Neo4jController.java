package com.mmg.neo4j.api;

import cn.hutool.core.util.RandomUtil;
import com.mmg.neo4j.entity.Keywords;
import com.mmg.neo4j.entity.KeywordsRelationVO;
import com.mmg.neo4j.repository.KeywordsRepository;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.TypeSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @Auther: fan
 * @Date: 2021/9/12
 * @Description:
 */
@RestController
@RequestMapping("/neo4j")
@Slf4j
public class Neo4jController {

    @Autowired
    private Neo4jClient neo4jClient;
    @Autowired
    private KeywordsRepository keywordsRepository;

    @GetMapping
    public KeywordsRelationVO test() {
        long l = System.currentTimeMillis();
        String appId = "e833a3a88ec34fe383beb2212d6be2ea";
        Collection<KeywordsRelationVO.Link> result = neo4jClient.query("match (x:Keywords{appId: '" + appId + "' })-[y:BIND]->" +
                "(z:Keywords{appId: '" + appId + "'})" +
                "return id(x) as startNodeId,id(y) as relationId,y.weight as relationWeight, id(z) as endNodeId")
                .in("neo4j")
                .fetchAs(KeywordsRelationVO.Link.class)
                .mappedBy((TypeSystem t, Record record) -> {
                    int startNodeId = record.get("startNodeId").asInt();
                    int endNodeId = record.get("endNodeId").asInt();
                    return KeywordsRelationVO.Link.builder()
                            .source(String.valueOf(startNodeId))
                            .target(String.valueOf(endNodeId))
                            .build();
                })
                .all();
        List<KeywordsRelationVO.Link> links = new ArrayList<>(result);
        //拿到有所有有关系的节点
//        List<Keywords> keywords = keywordsRepository.getAllNode(appId);
        List<Keywords> keywords = keywordsRepository.findAll();
        List<KeywordsRelationVO.Node> nodes = new ArrayList<>();
        keywords.forEach(item -> {
            try {
                nodes.add(KeywordsRelationVO.Node.builder()
                        .id(item.getId().toString())
                        .name(item.getWords())
                        .symbolSize(Float.valueOf(item.getFrequency()))
                        .x(nextFloat(-700, 400))
                        .y(nextFloat(50, 500))
                        .category(nextInt(0, 9))
                        .build());
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        });
        List<KeywordsRelationVO.Categories> categories = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            categories.add(KeywordsRelationVO.Categories.builder().name("类目" + i).build());
        }
        System.out.println((System.currentTimeMillis() - l) + "ms");
        return KeywordsRelationVO.builder().nodes(nodes).links(links).categories(categories).build();
    }

    private static Set<Float> set = new HashSet<>();

    public static int nextInt(final int min, final int max) {
        return RandomUtil.randomInt(min, max);
    }

    public static float nextFloat(final float min, final float max) throws Exception {
        if (max < min) {
            throw new Exception("min < max");
        }
        if (min == max) {
            return min;
        }
        float a = 0;
        while (set.add(a)) {
            a = min + ((max - min) * new Random().nextFloat());
        }
        if (a != 0) {
            return a;
        } else {
            return min + ((max - min) * new Random().nextFloat());
        }


    }

}
