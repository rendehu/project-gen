# 使用官方Java镜像作为基础镜像
FROM openjdk:8-jdk-alpine
<#noparse>
# 修改软件源
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories
# 修改时区
# 在 Docker 中设置时区-基于 Alpine 镜像
# Alpine 镜像中并没有包含 tzdata，所以只设置 TZ 环境变量并不能达到我们想要的效果，因此需要安装 tzdata
ENV TZ=Asia/Shanghai
RUN apk update \
    && apk add tzdata \
    && echo "${TZ}" > /etc/timezone \
    && ln -sf /usr/share/zoneinfo/${TZ} /etc/localtime \
    && rm /var/cache/apk/*
# 添加 jattach 能调用jdk一些工具
RUN apk add --no-cache jattach --repository http://mirrors.aliyun.com/alpine/latest-stable/community/
</#noparse>
#工作目录
WORKDIR /data/server/${group}-${projectName}

# 将当前目录下的文件复制到容器中的/app目录下
COPY ${group}-${projectName}-web/target/*.jar dprs-eval.jar

# 安装 可选工具（可选）
#RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.aliyun.com/g' /etc/apk/repositories
#RUN apk add vim

ENV JAVA_OPTS="-server \
           -Djava.io.tmpdir=/data/server/dprs-eval/temp \
           -XX:+HeapDumpOnOutOfMemoryError \
           -XX:HeapDumpPath=/data/server/dprs-eval/logs/heapdump.hprof \
           -Xloggc:/data/server/dprs-eval/logs/gc.log \
           -verbose:gc \
           -XX:+PrintGCDetails \
           -XX:+PrintGCDateStamps \
           -XX:+PrintGCTimeStamps \
           -XX:+UseGCLogFileRotation \
           -XX:NumberOfGCLogFiles=10 \
           -XX:GCLogFileSize=30M"

# 设置启动命令
ENTRYPOINT ["java", "-jar" ,"dprs-eval.jar"]