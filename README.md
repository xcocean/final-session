# final-session

## 介绍

final-session 一个轻量级分布式session框架

支持redis、数据库存储会话session，推荐使用redis存储方案。通过自定义生成不同集群ID，读写访问不同的redis集群，从而实现节点无限扩展，架构图如下：


![集群架构图](https://gitee.com/lingkang_top/final-session/blob/master/document/%E9%9B%86%E7%BE%A4%E6%9E%B6%E6%9E%84.png)