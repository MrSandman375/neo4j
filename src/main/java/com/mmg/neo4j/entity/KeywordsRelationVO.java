package com.mmg.neo4j.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.w3c.dom.Node;

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
    private List<Node> nodes;
    private List<Link> links;
    private List<Categories> categories;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Link {
        private String source;
        private String target;
        private String weight;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Node{
        private String id;
        private String name;
        private Integer symbolSize;
        private Integer x;
        private Integer y;
        private Integer category;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Categories{
        private String name;
    }
}

