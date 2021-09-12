package com.mmg.neo4j.repository;

import com.mmg.neo4j.entity.Keywords;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Auther: fan
 * @Date: 2021/9/12
 * @Description:
 */
public interface KeywordsRepository extends Neo4jRepository<Keywords, Long> {

    @Query(" match (n:Keywords{appId:$appId})--(x:Keywords{appId:$appId})  return distinct x")
    List<Keywords> getAllNode(@Param("appId") String appId);
}
