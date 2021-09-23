package com.mmg.neo4j.api;

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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

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
                    String weight = record.get("relationWeight").asString();
                    return KeywordsRelationVO.Link.builder()
                            .source(String.valueOf(startNodeId))
                            .target(String.valueOf(endNodeId))
                            .weight(weight)
                            .build();
                })
                .all();
        List<KeywordsRelationVO.Link> links = new ArrayList<>(result);
        //拿到有所有有关系的节点
        List<Keywords> keywords = keywordsRepository.getAllNode(appId);
//        List<Keywords> keywords = keywordsRepository.findAll();
        System.out.println(keywords.size());
        List<KeywordsRelationVO.Node> nodes = new ArrayList<>();
        keywords.forEach(item -> {
            try {
                Random random = new Random(item.getWords().hashCode());
                KeywordsRelationVO.Node node = KeywordsRelationVO.Node.builder()
                        .id(item.getId().toString())
                        .name(item.getWords())
                        .symbolSize(Integer.parseInt(item.getFrequency()) / 3)
                        .category(random.nextInt(11))
                        .build();
                if (Integer.parseInt(item.getFrequency()) > 50) {
                    node.setX(random.nextInt(50));
                    node.setY(random.nextInt(50));
                } else {
                    node.setX(random.nextInt(100));
                    node.setY(random.nextInt(100));
                }
                nodes.add(node);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        });
        List<KeywordsRelationVO.Categories> categories = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            categories.add(KeywordsRelationVO.Categories.builder().name("类目" + i).build());
        }
        System.out.println((System.currentTimeMillis() - l) + "ms");
        return KeywordsRelationVO.builder().nodes(nodes).links(links).categories(categories).build();
    }

}
