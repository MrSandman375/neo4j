package com.mmg.neo4j.api;

import com.mmg.neo4j.entity.Keywords;
import com.mmg.neo4j.entity.KeywordsRelationVO;
import com.mmg.neo4j.repository.KeywordsRepository;
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

/**
 * @Auther: fan
 * @Date: 2021/9/12
 * @Description:
 */
@RestController
@RequestMapping("/neo4j")
public class Neo4jController {

    @Autowired
    private Neo4jClient neo4jClient;
    @Autowired
    private KeywordsRepository keywordsRepository;

    @GetMapping
    public KeywordsRelationVO test() {
        String appId = "e833a3a88ec34fe383beb2212d6be2ea";
        Collection<KeywordsRelationVO.KeywordsRelation> result = neo4jClient.query("match (x:Keywords{appId: '" + appId + "' })-[y:BIND]->" +
                "(z:Keywords{appId: '" + appId + "'})" +
                "return id(x) as startNodeId,id(y) as relationId,y.weight as relationWeight, id(z) as endNodeId")
//                .in("neo4j")
                .fetchAs(KeywordsRelationVO.KeywordsRelation.class)
                .mappedBy((TypeSystem t, Record record) -> KeywordsRelationVO.KeywordsRelation.builder()
                        .startNodeId(record.get("startNodeId").asInt())
                        .endNodeId(record.get("endNodeId").asInt())
                        .relationId(record.get("relationId").asInt())
                        .relationWeight(Integer.parseInt(record.get("relationWeight").asString()))
                        .build())
                .all();
        List<KeywordsRelationVO.KeywordsRelation> relations = new ArrayList<>(result);
        //拿到有所有有关系的节点并去重
        List<Keywords> keywords = keywordsRepository.getAllNode(appId);
        return KeywordsRelationVO.builder().keywords(keywords).relationList(relations).build();
    }

}
