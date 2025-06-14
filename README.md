# qi-rpc

## 项目介绍

本项目是厦门大学软件工程中间件技术课程的大作业，目标是构建一个功能全面的轻量级RPC（Remote Procedure Call）框架。

`qi-rpc` 是一款基于 Java 的远程过程调用框架，实现了服务注册与发现、动态代理、多种序列化与负载均衡策略、以及丰富的容错机制。该框架还提供了与 Spring Boot 的无缝集成，开发者可以通过简单的注解快速地发布和引用远程服务。

## 项目架构

项目采用模块化设计，结构清晰，各模块职责分明：

```
qi-rpc
├── rpc-common              # 通用模块，定义了服务接口和数据模型 (POJO)
├── rpc-core                # 核心实现模块，包含框架的核心功能
├── rpc-provider            # 基础服务提供者示例（非 Spring Boot）
├── rpc-consumer            # 基础服务消费者示例（非 Spring Boot）
├── rpc-spring-boot-starter # Spring Boot Starter，实现与 Spring Boot 的集成
├── example-springboot-provider # Spring Boot 版服务提供者示例
└── example-springboot-consumer # Spring Boot 版服务消费者示例
```
![image](https://github.com/user-attachments/assets/bf8b26a7-51f7-4d4f-af3a-52dcbfd09f3a)


## 主要功能

本 RPC 框架实现了以下核心功能：

* **服务注册与发现**:
    * 采用 **Etcd** 作为注册中心，支持服务提供者的动态注册与服务消费者的服务发现功能。
    * 提供心跳机制，自动续约服务租约，并能及时剔除宕机的服务节点。
    * 消费端通过 **Watch** 机制监听服务节点变化，并动态更新本地服务缓存。

* **通信协议与网络传输**:
    * 基于 **Vert.x** 和 **Netty** 实现高性能的 TCP 网络通信。
    * 自定义了一套通信协议，包含魔数、版本号、序列化方式、消息类型等，并通过消息头中的长度字段解决TCP粘包、半包问题。

* **序列化机制**:
    * 采用 **SPI**（Service Provider Interface）机制，支持多种序列化/反序列化方案的动态扩展。
    * 内置 **Jdk**, **Json**, **Hessian**, **Kryo** 四种序列化实现，可根据需求在配置文件中灵活切换。

* **动态代理**:
    * 消费端通过 **JDK 动态代理** 为服务接口生成代理对象，使得远程调用对用户透明，如同调用本地方法一样。

* **负载均衡**:
    * 同样基于 **SPI** 机制，支持多种负载均衡策略。
    * 内置了 **轮询（RoundRobin）**, **随机（Random）**, 和 **一致性哈希（ConsistentHash）** 三种负载均衡算法。

* **容错与重试**:
    * **容错策略**: 提供了多种服务容错方案，包括：
        * `Fail-Fast`: 快速失败，一次调用失败后立即抛出异常。
        * `Fail-Over`: 故障转移，自动重试调用其他服务节点。
        * `Fail-Safe`: 静默处理，发生异常时返回一个空响应，不影响主流程。
        * `Fail-Back`: 服务降级，调用失败时返回一个预设的兜底（Mock）数据。
    * **重试机制**:
        * 支持配置重试策略，如 `固定时间间隔重试` 和 `不重试`，增强了系统的健壮性。

* **Spring Boot 集成**:
    * 提供了 `rpc-spring-boot-starter` 模块，简化了在 Spring Boot 项目中的使用。
    * 通过 `@EnableRpc` 注解一键启动RPC框架。
    * 通过 `@RpcService` 注解将一个 Bean 发布为 RPC 服务。
    * 通过 `@RpcReference` 注解为字段注入一个远程服务的代理对象。

## 快速启动

以下步骤将引导您启动并测试本 RPC 框架。

### 1. 环境准备

* Java 11 或更高版本
* Maven 3.x
* Etcd

您可以使用 Docker 快速启动一个 Etcd 实例：

```bash
docker run -d -p 2379:2379 --name etcd-gcr-v3.5.0 gcr.io/etcd-development/etcd:v3.5.0 --advertise-client-urls http://0.0.0.0:2379 --listen-client-urls http://0.0.0.0:2379
```

### 2. 克隆并构建项目

```bash
git clone https://github.com/rickey-c/qi-rpc
cd qi-rpc
mvn clean install
```

### 3. 启动服务提供者

* 进入 `example-springboot-provider` 模块。
* **重要**: 为了测试 `qi-rpc`，请取消 `UserServiceImpl.java` 中的 `@RpcService` 注解的注释，并取消 `ExampleSpringbootProviderApplication.java` 中 `@EnableRpc` 的注释，同时可以注释掉 `@EnableDubbo`。

```java
// in UserServiceImpl.java
@Service
@RpcService 
@Slf4j
public class UserServiceImpl implements UserService { ... }

// in ExampleSpringbootProviderApplication.java
@SpringBootApplication
@EnableRpc
public class ExampleSpringbootProviderApplication { ... }
```

* 运行 `ExampleSpringbootProviderApplication` 的 `main` 方法启动服务提供者。

### 4. 启动服务消费者

* 进入 `example-springboot-consumer` 模块。
* **重要**: 同样地，请在 `ExampleSpringbootConsumerApplication.java` 中取消 `@EnableRpc` 注解的注释（并确保其 `needServer` 属性为 `false`）。

```java
// in ExampleSpringbootConsumerApplication.java
@SpringBootApplication
@EnableRpc(needServer = false)
public class ExampleSpringbootConsumerApplication { ... }
```

* 运行 `ExampleSpringbootConsumerApplication` 的 `main` 方法启动服务消费者。

### 5. 测试 RPC 调用

消费者应用启动在 `9999` 端口，并提供了一个 REST 接口用于触发 RPC 调用。您可以使用 `curl` 或其他工具进行测试：

```bash
curl -X POST -H "Content-Type: application/json" -d '{"name":"rickey"}' http://localhost:9999/rpc/qi
```

如果一切正常，您将会收到响应： `rickey after PRC`。同时，在服务提供者的控制台，您将看到类似以下的日志输出：

```
INFO ... : user name is : rickey
```

## 配置说明

本框架提供了丰富的配置项，可以在 Spring Boot 的 `application.properties` 或 `application.yaml` 文件中进行配置，所有配置项均以 `rpc.` 为前缀。

以下是一些核心配置项示例（`rpc-core/src/main/java/com/rickey/rpc/config/RpcConfig.java`）：

```yaml
rpc:
  name: qi-rpc                 # 应用名称
  version: "1.0"              # 版本号
  serverHost: localhost       # 服务暴露主机
  serverPort: 8080            # 服务暴露端口
  mock: false                 # 是否开启模拟调用
  serializer: hessian         # 序列化器 (jdk, json, kryo, hessian)
  loadBalancer: roundRobin    # 负载均衡策略 (roundRobin, random, consistentHash)
  retryStrategy: no           # 重试策略 (no, fixedInterval)
  tolerantStrategy: failFast  # 容错策略 (failFast, failOver, failSafe, failBack)
  registryConfig:
    registry: etcd            # 注册中心类型 (etcd, zookeeper)
    address: http://localhost:2379 # 注册中心地址
    timeout: 10000            # 连接超时时间
```
