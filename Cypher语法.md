- 添加节点

```cypher
CREATE (n:Person {name:'John'}) RETURN n
```

- 删除节点

```cypher
MATCH (n) DETACH DELETE n
```

- 添加关系

```cypher
MATCH (a:Person {name:'Liz'}), 
      (b:Person {name:'Mike'}) 
MERGE (a)-[:FRIENDS{since:2001}]->(b)
```

- 查询有关系的节点

```cypher
match (x:Keywords)--(y:Keywords) return distinct x
```

- 以id为条件或者查询id

```cypher
match (x:Keywords) where id(x)=2464 return id(x)
```

- 查看节点或关系的属性键

```cypher
match (x:Keywords) return keys(x)
```

- 查看节点或关系的属性值

```cypher
match (x:Keywords)-[y]-(z:Keywords) return properties(y),properties(x),properties(z)
```

- 查看绑定关系属性

```cypher
match (x:Keywords)-[y]-(z:Keywords) return type(y)
```

- 导入csv文件（节点）

```cypher
:auto USING PERIODIC COMMIT
LOAD CSV FROM "file:///node.csv" AS line
CREATE (k:Keywords {words:line[0], frequency:line[1], appId:line[2]})
```

- 导入csv文件（关系）

```cypher
:auto USING PERIODIC COMMIT
 LOAD CSV FROM "file:///relationship.csv" AS line
 MATCH (from:Keywords{words:line[0]}),(to:Keywords{words:line[1]})
 merge (from)-[r:BIND{weight:line[2]}]-> (to)
```

