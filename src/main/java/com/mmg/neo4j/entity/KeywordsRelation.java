package com.mmg.neo4j.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: fan
 * @Date: 2021/9/12
 * @Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KeywordsRelation {

    private Integer startNodeId;
    private Integer endNodeId;
    private Integer relationId;
    private Integer relationWeight;

}
