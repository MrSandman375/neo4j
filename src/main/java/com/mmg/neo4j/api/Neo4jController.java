package com.mmg.neo4j.api;

import com.mmg.neo4j.entity.Keywords;
import com.mmg.neo4j.entity.KeywordsRelation;
import com.mmg.neo4j.entity.KeywordsRelationVO;
import com.mmg.neo4j.repository.KeywordsRepository;
import org.neo4j.driver.Record;
import org.neo4j.driver.Value;
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
        Collection<KeywordsRelation> result = neo4jClient.query("match (x:Keywords{appId: '" + appId + "' })-[y:BIND]->" +
                "(z:Keywords{appId: '" + appId + "'})" +
                "return id(x) as startNodeId,id(y) as relationId,y.weight as relationWeight, id(z) as endNodeId")
                .in("neo4j")
                .fetchAs(KeywordsRelation.class)
                .mappedBy((TypeSystem t, Record record) -> {
                    Value startNodeId = record.get("startNodeId");
                    Value endNodeId = record.get("endNodeId");
                    Value relationId = record.get("relationId");
                    Value relationWeight = record.get("relationWeight");
                    return KeywordsRelation.builder()
                            .startNodeId(startNodeId.asInt())
                            .endNodeId(endNodeId.asInt())
                            .relationId(relationId.asInt())
                            .relationWeight(Integer.parseInt(relationWeight.asString()))
                            .build();
                })
                .all();
        List<KeywordsRelation> relations = new ArrayList<>(result);
        List<Keywords> keywords = keywordsRepository.getAllByAppId(appId);
        return KeywordsRelationVO.builder().keywords(keywords).relationList(relations).build();
    }

}
