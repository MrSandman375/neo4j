package com.mmg.neo4j.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Auther: fan
 * @Date: 2021/9/12
 * @Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KeywordsRelationVO {
    private List<Keywords> keywords;
    private List<KeywordsRelation> relationList;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class KeywordsRelation {
        private Integer startNodeId;
        private Integer endNodeId;
        private Integer relationId;
        private Integer relationWeight;

    }
}

