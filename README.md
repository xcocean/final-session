# final-session

## 介绍

final-session 一个轻量级分布式session框架

支持redis、数据库存储会话session，推荐使用redis存储方案。

理论上单体应用集群的扩充上限为redis集群的读写上限，假设redis写并发10w/s，那么你的应用集群并发处理能达10w/s。

若对session进一步优化，除去每次更新最后访问，则为读多写少，理论上集群可以无限扩展。