package com.mmg.neo4j.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;

/**
 * @Auther: fan
 * @Date: 2021/9/12
 * @Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RelationshipProperties
public class Relationships {
    @Id
    @GeneratedValue
    private Long id;
    private String weight;
}
