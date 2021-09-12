package com.mmg.neo4j.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

/**
 * @Auther: fan
 * @Date: 2021/9/12
 * @Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Node(value = "Keywords")
public class Keywords {

    @Id
    @GeneratedValue
    private Long id;
    private String words;
    private String frequency;
    private String appId;
}
