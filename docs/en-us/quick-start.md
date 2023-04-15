# Quick Start

The main component of Flower framework are Flower Service, Flower Flow and Flower Container. Serivces implement business logic. Services communicate via messages, the return value (a.k.a message) from the previous service will become the input of the next service. Services are chained together to form Flows based on business logic. Containers delivery the messages between services. 


## System Requirements 

Flower requires Java 8. If you are using servlet, flower requires servlet 3.1+ for asynchornous support. Confirm your systems meet these requirements before you start.   

```shell script
$ java -version
```

## Installation 

The latest version of Flower is 1.0.3.

### Maven

```xml
<dependency>
  <groupId>com.ly.train</groupId>
  <artifactId>flower.core</artifactId>
  <version>A.B.C</version>
</dependency>
```

### Gradle

```text
compile group: 'com.ly.train', name: 'flower.core', version: 'A.B.C'
```

### SBT

```text
libraryDependencies += "com.ly.train" % "flower.core" % "A.B.C"
```

### Ivy

```xml
<dependency org="com.ly.train" name="flower.core" rev="A.B.C"/>
```

## Initialization  

Flower requires initialization before you start using it. The easiest way to set it up is as following:  

Initialize Flower

```java
 FlowerFactory flowerFactory = new SimpleFlowerFactory();
```

## Create a Flower service

To create a flower service, implement Service interface or extend AbstractService base class, and add your business logic in process().


UserServiceA

```java
public class UserServiceA implements Service<User, User> {
  static final Logger logger = LoggerFactory.getLogger(UserServiceA.class);
  @Override
  public User process(User message, ServiceContext context) throws Throwable {
    message.setDesc(message.getDesc() + " --> " + getClass().getSimpleName());
    message.setAge(message.getAge() + 1);
    logger.info("Message processing complete, message : {}", message);
    return message;
  }

}
```

UserServiceB

```java
public class UserServiceB implements Service<User, User> {
  static final Logger logger = LoggerFactory.getLogger(UserServiceB.class);
  @Override
  public User process(User message, ServiceContext context) throws Throwable {
    message.setDesc(message.getDesc() + " --> " + getClass().getSimpleName());
    message.setAge(message.getAge() + 1);
    logger.info("Message processing complete, message : {}", message);
    return message;
  }

}
```

UserServiceC1

```java
public class UserServiceC1 implements Service<User, User> {
  static final Logger logger = LoggerFactory.getLogger(UserServiceC1.class);
  @Override
  public User process(User message, ServiceContext context) throws Throwable {
    message.setDesc(message.getDesc() + " --> " + getClass().getSimpleName());
    message.setAge(message.getAge() + 1);
    logger.info("Message processing complete, message : {}", message);
    return message;
  }

}
```

## Register your services 

Flower provides two ways to register your services: by configuration files or by code.

### By code 

```java
 ServiceFactory serviceFactory = flowerFactory.getServiceFactory();
 serviceFactory.registerService(UserServiceA.class.getSimpleName(), UserServiceA.class);
 serviceFactory.registerService(UserServiceB.class.getSimpleName(), UserServiceB.class);
 serviceFactory.registerService(UserServiceC1.class.getSimpleName(), UserServiceC1.class);
```

### By configuration file
The extension for services configuration files is ".services". Simply put the configuration files under /classpath and Flower will load the configurations and register your services automatically.

flower_test.services

```java
UserServiceA = com.ly.train.flower.base.service.user.UserServiceA
UserServiceB = com.ly.train.flower.base.service.user.UserServiceB
UserServiceC1 = com.ly.train.flower.base.service.user.UserServiceC1
```

## Create a Flow 

Flower provide two ways to create a Flow: by configuration files or by code.

Both methods produce a similar result:

```text
UserServiceA -> UserServiceB -> UserServiceC1
```

### By code

```java
// UserServiceA -> UserServiceB -> UserServiceC1
final String flowName = "flower_test";
ServiceFlow serviceFlow = serviceFactory.getOrCreateServiceFlow(flowName);
serviceFlow.buildFlow(UserServiceA.class, UserServiceB.class);
serviceFlow.buildFlow(UserServiceB.class, UserServiceC1.class);
serviceFlow.build();
```

### By configuration file
The extension for flow configuration files is ".flow". Simply put it under /classpath and Flower will create the flows automatically.

flower_test.flow

```text
UserServiceA -> UserServiceB
UserServiceB -> UserServiceC1
```

## Use Flow 

We have create 3 Flower services and one flow called flower_test. How do we use them? 

1. Synchronous calls if you require a response from flow execution.
2. Asynchronous calls if you don't require a response from flow execution.

- Synchronous call 

```java
final FlowRouter flowRouter = flowerFactory.buildFlowRouter(flowName, 16);
Object result = flowRouter.syncCallService(user);
```

- Asynchronous call 

```java
final FlowRouter flowRouter = flowerFactory.buildFlowRouter(flowName, 16);
flowRouter.asyncCallService(user);
```

## Complete example 

```java
    FlowerFactory flowerFactory = new SimpleFlowerFactory();
    ServiceFactory serviceFactory = flowerFactory.getServiceFactory();
    serviceFactory.registerService(UserServiceA.class.getSimpleName(), UserServiceA.class);
    serviceFactory.registerService(UserServiceB.class.getSimpleName(), UserServiceB.class);
    serviceFactory.registerService(UserServiceC1.class.getSimpleName(), UserServiceC1.class);

    final String flowName = "flower_test";
    ServiceFlow serviceFlow = serviceFactory.getOrCreateServiceFlow(flowName);
    serviceFlow.buildFlow(UserServiceA.class, UserServiceB.class);
    serviceFlow.buildFlow(UserServiceB.class, UserServiceC1.class);
    serviceFlow.build();

    final FlowRouter flowRouter = flowerFactory.buildFlowRouter(flowName, 16);

    User user = new User();
    user.setName("reactive-programming ");
    user.setAge(2);

    Object o = flowRouter.syncCallService(user);
    System.out.println("reactive-programming: " + o);

    flowRouter.asyncCallService(user);
```

## Result 

```text
2019-07-11 15:13:19.739 [main] INFO  c.ly.train.flower.config.parser.FlowerConfigParser - parse FlowerConfig, configLocation : flower.yml
2019-07-11 15:13:19.839 [main] INFO  c.ly.train.flower.config.parser.FlowerConfigParser - flowerConfig : FlowerConfig [name=LocalFlower, host=127.0.0.1, port=25005, basePackage=com.ly.train.flower, registry=null]
2019-07-11 15:13:19.840 [main] DEBUG com.ly.train.flower.common.lifecyle.AbstractInit - init class : com.ly.train.flower.core.service.container.simple.SimpleFlowerFactory@39aeed2f
2019-07-11 15:13:19.844 [main] DEBUG com.ly.train.flower.common.lifecyle.AbstractInit - init class : com.ly.train.flower.core.service.container.ServiceFactory@724af044
2019-07-11 15:13:19.844 [main] DEBUG com.ly.train.flower.common.lifecyle.AbstractInit - init class : com.ly.train.flower.core.service.container.ServiceLoader@4678c730
2019-07-11 15:13:19.957 [main] INFO  c.l.t.flower.core.service.container.ServiceLoader - find service, path : file:/Volumes/Data/opt/code/yilong/flower/flower.core/target/test-classes/sample.services
2019-07-11 15:13:19.966 [main] DEBUG c.l.t.flower.core.service.container.ServiceLoader - init ServiceMeta. service1 : ServiceMeta [serviceName=service1, paramType=com.ly.train.flower.base.model.User, resultType=com.ly.train.flower.base.model.User, serviceClassName=com.ly.train.flower.base.service.user.UserServiceA, configs=[]]
2019-07-11 15:13:19.966 [main] INFO  c.l.t.flower.core.service.container.ServiceLoader - register service type -> service1 : class com.ly.train.flower.base.service.user.UserServiceA
2019-07-11 15:13:19.967 [main] DEBUG c.l.t.flower.core.service.container.ServiceLoader - init ServiceMeta. service2 : ServiceMeta [serviceName=service2, paramType=com.ly.train.flower.base.model.User, resultType=com.ly.train.flower.base.model.User, serviceClassName=com.ly.train.flower.base.service.user.UserServiceB, configs=[]]
2019-07-11 15:13:19.967 [main] INFO  c.l.t.flower.core.service.container.ServiceLoader - register service type -> service2 : class com.ly.train.flower.base.service.user.UserServiceB
2019-07-11 15:13:19.968 [main] DEBUG c.l.t.flower.core.service.container.ServiceLoader - init ServiceMeta. service3 : ServiceMeta [serviceName=service3, paramType=com.ly.train.flower.base.model.User, resultType=com.ly.train.flower.base.model.User, serviceClassName=com.ly.train.flower.base.service.user.UserServiceC1, configs=[]]
2019-07-11 15:13:19.968 [main] INFO  c.l.t.flower.core.service.container.ServiceLoader - register service type -> service3 : class com.ly.train.flower.base.service.user.UserServiceC1
2019-07-11 15:13:20.017 [main] DEBUG c.l.t.f.common.scanner.filter.AbstractClassFilter - scan url : file:/Volumes/Data/opt/code/yilong/flower/flower.core/target/test-classes/com/ly/train/flower
2019-07-11 15:13:20.059 [main] DEBUG c.l.t.f.common.scanner.filter.AbstractClassFilter - add class:com.ly.train.flower.base.service.OpenTracerService
2019-07-11 15:13:20.061 [main] DEBUG c.l.t.f.common.scanner.filter.AbstractClassFilter - add class:com.ly.train.flower.base.service.user.UserServiceD
2019-07-11 15:13:20.062 [main] DEBUG c.l.t.f.common.scanner.filter.AbstractClassFilter - add class:com.ly.train.flower.base.service.str.StringServiceD
2019-07-11 15:13:20.062 [main] DEBUG c.l.t.f.common.scanner.filter.AbstractClassFilter - scan url : file:/Volumes/Data/opt/code/yilong/flower/flower.core/target/classes/com/ly/train/flower
2019-07-11 15:13:20.123 [main] DEBUG c.l.t.f.common.scanner.filter.AbstractClassFilter - scan url : file:/Volumes/Data/opt/code/yilong/flower/flower.common/target/classes/com/ly/train/flower
2019-07-11 15:13:20.156 [main] DEBUG c.l.t.f.common.scanner.filter.AbstractClassFilter - scan url : file:/Volumes/Data/opt/code/yilong/flower/flower.config/target/classes/com/ly/train/flower
2019-07-11 15:13:20.157 [main] DEBUG c.l.t.f.common.scanner.filter.AbstractClassFilter - scan url : file:/Volumes/Data/opt/code/yilong/flower/flower.filter/flower.filter.api/target/classes/com/ly/train/flower
2019-07-11 15:13:20.160 [main] DEBUG c.l.t.f.common.scanner.filter.AbstractClassFilter - scan url : file:/Volumes/Data/opt/code/yilong/flower/flower.registry/flower.registry.api/target/classes/com/ly/train/flower
2019-07-11 15:13:20.164 [main] DEBUG c.l.t.f.common.scanner.filter.AbstractClassFilter - scan url : file:/Volumes/Data/opt/code/yilong/flower/flower.serializer/flower.serializer.hessian/target/classes/com/ly/train/flower
2019-07-11 15:13:20.166 [main] DEBUG c.l.t.f.common.scanner.filter.AbstractClassFilter - scan url : file:/Volumes/Data/opt/code/yilong/flower/flower.serializer/flower.serializer.api/target/classes/com/ly/train/flower
2019-07-11 15:13:20.167 [main] INFO  c.l.t.flower.core.service.container.ServiceFactory - scan flowerService, basePackage : com.ly.train.flower, find flowerService : 3
2019-07-11 15:13:20.168 [main] DEBUG c.l.t.flower.core.service.container.ServiceLoader - init ServiceMeta. UserServiceD : ServiceMeta [serviceName=UserServiceD, paramType=java.util.List, resultType=java.util.List, serviceClassName=com.ly.train.flower.base.service.user.UserServiceD, configs=[]]
2019-07-11 15:13:20.168 [main] INFO  c.l.t.flower.core.service.container.ServiceLoader - register service type -> UserServiceD : class com.ly.train.flower.base.service.user.UserServiceD
2019-07-11 15:13:20.168 [main] DEBUG c.l.t.flower.core.service.container.ServiceLoader - init ServiceMeta. OpenTracerService : ServiceMeta [serviceName=OpenTracerService, paramType=com.ly.train.flower.base.model.User, resultType=com.ly.train.flower.base.model.User, serviceClassName=com.ly.train.flower.base.service.OpenTracerService, configs=[]]
2019-07-11 15:13:20.168 [main] INFO  c.l.t.flower.core.service.container.ServiceLoader - register service type -> OpenTracerService : class com.ly.train.flower.base.service.OpenTracerService
2019-07-11 15:13:20.168 [main] DEBUG c.l.t.flower.core.service.container.ServiceLoader - init ServiceMeta. StringServiceD : ServiceMeta [serviceName=StringServiceD, paramType=java.util.List, resultType=java.util.List, serviceClassName=com.ly.train.flower.base.service.str.StringServiceD, configs=[]]
2019-07-11 15:13:20.168 [main] INFO  c.l.t.flower.core.service.container.ServiceLoader - register service type -> StringServiceD : class com.ly.train.flower.base.service.str.StringServiceD
2019-07-11 15:13:20.661 [main] INFO  com.ly.train.flower.core.akka.FlowerActorSystem - akka config ：akka.actor.provider = "remote"
akka.remote.enabled-transports = ["akka.remote.netty.tcp"]
akka.remote.netty.tcp.hostname = "127.0.0.1"
akka.remote.netty.tcp.port = "25005"
dispatcher.fork-join-executor.parallelism-min = "8"
dispatcher.fork-join-executor.parallelism-max = "256"
dispatcher.fork-join-executor.parallelism-factor = "8"

2019-07-11 15:13:21.378 [flower-dispatcher-4] INFO  akka.event.slf4j.Slf4jLogger - Slf4jLogger started
2019-07-11 15:13:21.411 [flower-dispatcher-4] INFO  akka.remote.Remoting - Starting remoting
2019-07-11 15:13:21.622 [flower-dispatcher-4] INFO  akka.remote.Remoting - Remoting started; listening on addresses :[akka.tcp://flower@127.0.0.1:25005]
2019-07-11 15:13:21.624 [flower-dispatcher-4] INFO  akka.remote.Remoting - Remoting now listens on addresses: [akka.tcp://flower@127.0.0.1:25005]
2019-07-11 15:13:21.677 [main] INFO  com.ly.train.flower.common.util.ExtensionLoader - url : file:/Volumes/Data/opt/code/yilong/flower/flower.serializer/flower.serializer.hessian/target/classes/META-INF/services/flower/com.ly.train.flower.serializer.Serializer
2019-07-11 15:13:21.677 [main] INFO  com.ly.train.flower.common.util.ExtensionLoader - url : file:/Volumes/Data/opt/code/yilong/flower/flower.serializer/flower.serializer.api/target/classes/META-INF/services/flower/com.ly.train.flower.serializer.Serializer
2019-07-11 15:13:21.705 [main] INFO  com.ly.train.flower.common.util.ExtensionLoader - load extend(hessian) : class com.ly.train.flower.serializer.hessian.HessianSerializer
2019-07-11 15:13:21.775 [flower-dispatcher-4] WARN  akka.serialization.Serialization(akka://flower) - Using serializer [com.ly.train.flower.core.akka.serializer.hessian.HessianSerializer] for message [akka.actor.ActorSelectionMessage]. Note that this serializer is not implemented by Akka. It's not recommended to replace serializers for messages provided by Akka.
2019-07-11 15:13:21.787 [main] DEBUG com.ly.train.flower.common.lifecyle.AbstractInit - init class : com.ly.train.flower.core.akka.ServiceActorFactory@7f132176
2019-07-11 15:13:21.788 [main] DEBUG com.ly.train.flower.common.lifecyle.AbstractInit - init class : com.ly.train.flower.core.akka.FlowerActorSystem@68b32e3e
2019-07-11 15:13:21.788 [main] INFO  c.l.t.f.c.s.container.simple.SimpleFlowerFactory - start FlowerFactory
2019-07-11 15:13:21.788 [main] DEBUG c.l.t.flower.core.service.container.ServiceLoader - init ServiceMeta. UserServiceA : ServiceMeta [serviceName=UserServiceA, paramType=com.ly.train.flower.base.model.User, resultType=com.ly.train.flower.base.model.User, serviceClassName=com.ly.train.flower.base.service.user.UserServiceA, configs=[]]
2019-07-11 15:13:21.788 [main] INFO  c.l.t.flower.core.service.container.ServiceLoader - register service type -> UserServiceA : class com.ly.train.flower.base.service.user.UserServiceA
2019-07-11 15:13:21.788 [main] DEBUG c.l.t.flower.core.service.container.ServiceLoader - init ServiceMeta. UserServiceB : ServiceMeta [serviceName=UserServiceB, paramType=com.ly.train.flower.base.model.User, resultType=com.ly.train.flower.base.model.User, serviceClassName=com.ly.train.flower.base.service.user.UserServiceB, configs=[]]
2019-07-11 15:13:21.788 [main] INFO  c.l.t.flower.core.service.container.ServiceLoader - register service type -> UserServiceB : class com.ly.train.flower.base.service.user.UserServiceB
2019-07-11 15:13:21.789 [main] DEBUG c.l.t.flower.core.service.container.ServiceLoader - init ServiceMeta. UserServiceC1 : ServiceMeta [serviceName=UserServiceC1, paramType=com.ly.train.flower.base.model.User, resultType=com.ly.train.flower.base.model.User, serviceClassName=com.ly.train.flower.base.service.user.UserServiceC1, configs=[]]
2019-07-11 15:13:21.789 [main] INFO  c.l.t.flower.core.service.container.ServiceLoader - register service type -> UserServiceC1 : class com.ly.train.flower.base.service.user.UserServiceC1
2019-07-11 15:13:21.790 [main] INFO  c.l.t.flower.core.service.container.ServiceFlow -  buildFlow : flower_test, preService : UserServiceA, nextService : UserServiceB
2019-07-11 15:13:21.790 [main] INFO  c.l.t.flower.core.service.container.ServiceFlow -  buildFlow : flower_test, preService : UserServiceB, nextService : UserServiceC1
2019-07-11 15:13:21.790 [main] INFO  c.l.t.flower.core.service.container.ServiceFlow -  build flower_test success.
 ServiceFlow [ flowName = flower_test
  UserServiceA(1) ---> UserServiceB(1),
  UserServiceB(1) ---> UserServiceC1(0)
]
2019-07-11 15:13:21.790 [main] INFO  c.l.t.flower.core.service.container.ServiceFlow - start register ServiceConfig : ServiceConfig [flowName=flower_test, serviceName=UserServiceA, jointSourceNumber=0]
2019-07-11 15:13:21.790 [main] DEBUG com.ly.train.flower.common.lifecyle.AbstractInit - init class : com.ly.train.flower.core.akka.router.FlowRouter@bcef303
2019-07-11 15:13:21.790 [main] INFO  com.ly.train.flower.common.util.ExtensionLoader - url : file:/Volumes/Data/opt/code/yilong/flower/flower.core/target/classes/META-INF/services/flower/com.ly.train.flower.core.loadbalance.LoadBalance
2019-07-11 15:13:21.791 [main] INFO  com.ly.train.flower.common.util.ExtensionLoader - load extend(round) : class com.ly.train.flower.core.loadbalance.RoundLoadBalance
2019-07-11 15:13:21.791 [main] DEBUG com.ly.train.flower.common.lifecyle.AbstractInit - init class : ServiceRouter [loadBalance=RoundLoadBalance [name=RoundLoadBalance], number=1, serviceConfig=ServiceConfig [flowName=flower_test, serviceName=UserServiceA, jointSourceNumber=0]]
2019-07-11 15:13:21.792 [main] INFO  com.ly.train.flower.core.akka.ServiceActorFactory - build service Router. serviceName : UserServiceA, actorNumber : 1
2019-07-11 15:13:21.793 [main] INFO  com.ly.train.flower.core.akka.ServiceActorFactory - build service Router. flowName : flower_test, serviceName : UserServiceA, flowNumber : 1
2019-07-11 15:13:21.928 [main] DEBUG com.ly.train.flower.common.lifecyle.AbstractInit - init class : com.ly.train.flower.core.akka.router.FlowRouter@5ef6ae06
2019-07-11 15:13:21.928 [main] DEBUG com.ly.train.flower.common.lifecyle.AbstractInit - init class : ServiceRouter [loadBalance=RoundLoadBalance [name=RoundLoadBalance], number=16, serviceConfig=ServiceConfig [flowName=flower_test, serviceName=UserServiceA, jointSourceNumber=0]]
2019-07-11 15:13:21.935 [main] INFO  com.ly.train.flower.core.akka.ServiceActorFactory - build service Router. serviceName : UserServiceA, actorNumber : 16
2019-07-11 15:13:21.936 [main] INFO  com.ly.train.flower.core.akka.ServiceActorFactory - build service Router. flowName : flower_test, serviceName : UserServiceA, flowNumber : 16
2019-07-11 15:13:21.953 [flower-dispatcher-20] INFO  c.l.t.flower.core.service.container.ServiceLoader - load flower service --> UserServiceA : com.ly.train.flower.base.service.user.UserServiceA@55967dc0
2019-07-11 15:13:21.958 [flower-dispatcher-20] INFO  com.ly.train.flower.base.service.user.UserServiceA - 结束处理消息, message : User [name=响应式编程 , desc= --> UserServiceA, age=3]
2019-07-11 15:13:21.961 [flower-dispatcher-20] DEBUG c.l.t.f.core.akka.actor.wrapper.ActorRefWrapper - Local message. serviceName : UserServiceB, actor : Actor[akka://flower/user/flower/UserServiceB_1#-39642247], message : ServiceContext [id=b66cc2e27fea491593b9e862e1b311a6, flowName=flower_test, currentServiceName=UserServiceB, sync=true, attachments=null, flowMessage=FlowMessage [transactionId=6e440adf3f8c41f793d7c700a4a36deb, message=[B@611ed8fb], web=null], sender : Actor[akka://flower/user/flower/UserServiceA_1#518152440]
2019-07-11 15:13:21.963 [flower-dispatcher-19] INFO  c.l.t.flower.core.service.container.ServiceLoader - load flower service --> UserServiceB : com.ly.train.flower.base.service.user.UserServiceB@ffcaad2
2019-07-11 15:13:21.963 [flower-dispatcher-19] INFO  com.ly.train.flower.base.service.user.UserServiceB - Message processing complete, message : User [name=reactive-programming, desc= --> UserServiceA --> UserServiceB, age=4]
2019-07-11 15:13:21.964 [flower-dispatcher-19] DEBUG c.l.t.f.core.akka.actor.wrapper.ActorRefWrapper - Local message. serviceName : UserServiceC1, actor : Actor[akka://flower/user/flower/UserServiceC1_1#-1935204307], message : ServiceContext [id=b66cc2e27fea491593b9e862e1b311a6, flowName=flower_test, currentServiceName=UserServiceC1, sync=true, attachments=null, flowMessage=FlowMessage [transactionId=6e440adf3f8c41f793d7c700a4a36deb, message=[B@40065f66], web=null], sender : Actor[akka://flower/user/flower/UserServiceB_1#-39642247]
2019-07-11 15:13:21.964 [flower-dispatcher-19] INFO  c.l.t.flower.core.service.container.ServiceLoader - load flower service --> UserServiceC1 : com.ly.train.flower.base.service.user.UserServiceC1@2a46d78e
2019-07-11 15:13:21.964 [flower-dispatcher-19] INFO  c.ly.train.flower.base.service.user.UserServiceC1 - Message processing complete, message : User [name=reactive-programming , desc= --> UserServiceA --> UserServiceB --> UserServiceC1, age=5]
Response： User [name=reactive-programming , desc= --> UserServiceA --> UserServiceB --> UserServiceC1, age=5]
2019-07-11 15:13:21.965 [main] DEBUG c.l.t.f.core.akka.actor.wrapper.ActorRefWrapper - Local message. serviceName : UserServiceA, actor : Actor[akka://flower/user/flower/UserServiceA_2#1690671066], message : ServiceContext [id=2e6fcef3852a48eeb7cb182d2bd62ef4, flowName=flower_test, currentServiceName=UserServiceA, sync=false, attachments=null, flowMessage=FlowMessage [transactionId=52565043086d49abaa2ca88c3527883e, message=[B@54dcfa5a], web=null], sender : null
2019-07-11 15:13:21.966 [flower-dispatcher-19] INFO  com.ly.train.flower.base.service.user.UserServiceA - Message processing complete, message : User [name=reactive-programming , desc= --> UserServiceA, age=3]
2019-07-11 15:13:21.967 [flower-dispatcher-19] DEBUG c.l.t.f.core.akka.actor.wrapper.ActorRefWrapper - Local message. serviceName : UserServiceB, actor : Actor[akka://flower/user/flower/UserServiceB_2#1505663876], message : ServiceContext [id=2e6fcef3852a48eeb7cb182d2bd62ef4, flowName=flower_test, currentServiceName=UserServiceB, sync=false, attachments=null, flowMessage=FlowMessage [transactionId=52565043086d49abaa2ca88c3527883e, message=[B@3ccff905], web=null], sender : Actor[akka://flower/user/flower/UserServiceA_2#1690671066]
2019-07-11 15:13:21.967 [flower-dispatcher-20] INFO  com.ly.train.flower.base.service.user.UserServiceB - Message processing complete, message : User [name=reactive-programming , desc= --> UserServiceA --> UserServiceB, age=4]
2019-07-11 15:13:21.968 [flower-dispatcher-20] DEBUG c.l.t.f.core.akka.actor.wrapper.ActorRefWrapper - Local message. serviceName : UserServiceC1, actor : Actor[akka://flower/user/flower/UserServiceC1_2#-770155619], message : ServiceContext [id=2e6fcef3852a48eeb7cb182d2bd62ef4, flowName=flower_test, currentServiceName=UserServiceC1, sync=false, attachments=null, flowMessage=FlowMessage [transactionId=52565043086d49abaa2ca88c3527883e, message=[B@76ecc10a], web=null], sender : Actor[akka://flower/user/flower/UserServiceB_2#1505663876]
2019-07-11 15:13:21.969 [flower-dispatcher-19] INFO  c.ly.train.flower.base.service.user.UserServiceC1 - Message processing complete, message : User [name=reactive-programming , desc= --> UserServiceA --> UserServiceB --> UserServiceC1, age=5]
```
