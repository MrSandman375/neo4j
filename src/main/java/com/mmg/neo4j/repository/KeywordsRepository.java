package com.mmg.neo4j.repository;

import com.mmg.neo4j.entity.Keywords;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

/**
 * @Auther: fan
 * @Date: 2021/9/12
 * @Description:
 */
public interface KeywordsRepository extends Neo4jRepository<Keywords, Long> {

    List<Keywords> getAllByAppId(String AppId);
}
