#!/bin/bash
cd `dirname $0`/..
BASE_DIR=`pwd`
<#noparse>
#设置JVM参数
JAVA_OPT="-server -Xms512m -Xmx512m" #默认512m
JAVA_OPT="${JAVA_OPT} -Djava.io.tmpdir=$BASE_DIR/temp"
JAVA_OPT="${JAVA_OPT} -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${BASE_DIR}/logs/heapdump.hprof"
JAVA_OPT="${JAVA_OPT} -Xloggc:${BASE_DIR}/logs/gc.log -verbose:gc -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintGCTimeStamps -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=10 -XX:GCLogFileSize=100M"
JAVA_OPT="${JAVA_OPT} -jar ${BASE_DIR}/*.jar"

SPRING_OPT=" --spring.config.location=file:${BASE_DIR}/config/ --logging.config=${BASE_DIR}/config/logback.xml"


#配置日志目录
export LOG_PATH="${BASE_DIR}/logs"

#设置JAVA_HOME，优先取JAVA8_HOME，如果JAVA8_HOME为空，再取$JAVA_HOME
export JAVA_HOME=${JAVA8_HOME:-$JAVA_HOME}

pid=0
checkpid(){
    pid=`ps -ef|grep "$BASE_DIR/"|grep java| awk '{print $2}'`
    if [[ -z "$pid" ]]; then
        pid=0
    fi
}

start() {
  checkpid

  if [[ ${pid} -ne 0 ]]; then
      echo "================================"
      echo "info: ${artifactId} ${version} already started! (pid=$pid)"
      echo "================================"
  else
      echo -n "Starting ${artifactId} ..."
        if [[ ! -d "$BASE_DIR/logs/" ]]; then
	        mkdir "$BASE_DIR/logs/"
        fi
        if [[ ! -d "$BASE_DIR/temp/" ]]; then
	        mkdir "$BASE_DIR/temp/"
	    fi
	cd ${BASE_DIR}
	echo ${JAVA_HOME}/bin/java ${JAVA_OPT} ${SPRING_OPT}
	nohup ${JAVA_HOME}/bin/java ${JAVA_OPT} ${SPRING_OPT} > /dev/null 2>&1 &

    sleep 2
	checkpid
	if [[ ${pid} -ne 0 ]]; then
	    echo "(pid=$pid) [OK]"
    else
        echo "[Failed]"
    fi
  fi
}


stop() {
  checkpid

  if [[ ${pid} -ne 0 ]]; then
      echo -n "Stopping $BASE_DIR ${artifactId} ...(pid=$pid) "
      kill ${pid}
      if [[ $? -eq 0 ]]; then
        echo "[OK]"
      else
        echo "[Failed]"
      fi

      sleep 2
      rm -rf ${BASE_DIR}/temp/* #清空缓存
      checkpid
      if [[ ${pid} -ne 0 ]]; then
        #强制关闭
        kill -9 ${pid}
        sleep 2
      fi
  else
      echo "================================"
      echo "warn: $BASE_DIR ${artifactId} is not running"
      echo "================================"
  fi
}

status() {
  checkpid

  if [[ ${pid} -ne 0 ]];  then
      echo "$BASE_DIR ${artifactId} is running! (pid=$pid)"
  else
      echo "$BASE_DIR ${artifactId} is not running"
  fi
}

info() {
  echo "System Information:"
  echo "============================"
  echo `head -n 1 /etc/issue`
  echo `uname -a`
  echo
  echo "JAVA Information:"
  echo "============================"
  echo "JAVA_HOME=$JAVA_HOME"
  echo "JAVA_OPT=$JAVA_OPT"
  echo `${JAVA_HOME}/bin/java -version`
  echo
  echo "APP Information:"
  echo "============================"
  echo "BASE_DIR=$BASE_DIR"
  echo "APP_NAME=${artifactId}"
  echo "APP_VERSION=${version}"
}


case "$1" in
start)
      start

;;
stop)
    stop
;;
restart)
    stop
    start
    ;;
status)
      status
;;
info)
      info
 ;;
*)
      echo "Usage: $0 {start|stop|restart|status|info}"
      exit 1
esac


exit 0
</#noparse>