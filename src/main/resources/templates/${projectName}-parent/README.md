## ${projectCnName}（${projectName?upper_case}）

## 项目说明

`DDD 设计结构 微服务框架
 ${projectDesc}`

+ 核心功能
`...`

+ 技术框架

  + `spring-cloud`
  + `spring-boot`
  + `mybatis-plus`
  + `nacos`
  + `postgresql(自行替换即可，默认 pg)`
  + `flyway`
  + `spring-doc`

+ 启动默认端口 8080

+ 启动方式
```shell script
java -jar xx.jar --spring.cloud.nacos.config.server-addr=172.31.131.176:8848 \
                 --spring.cloud.nacos.config.file-extension=yaml \
                 --spring.cloud.nacos.config.namespace=ns-${projectName} \
                 --spring.application.name=${projectName} \
                 --server.port=8080
```
