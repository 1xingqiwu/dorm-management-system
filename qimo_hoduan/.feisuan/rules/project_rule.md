
# 开发规范指南
为保证代码质量、可维护性、安全性与可扩展性，请在开发过程中严格遵循以下规范。

## 一、项目基本信息

- **操作系统版本**：Windows 10
- **工作区路径**：D:\test\qimo_hoduan
- **代码作者**：xingqiwu
- **项目名称**：dorm-system
- **项目描述**：大学宿舍管理系统后端

## 二、技术栈要求

- **主框架**：Spring Boot 3.2.0
- **语言版本**：Java 17.0.9
- **核心依赖**：
  - `spring-boot-starter-web`
  - `spring-boot-starter-security`
  - `mysql-connector-j`
  - `mybatis-spring-boot-starter`
  - `spring-boot-starter-data-redis`
  - `lombok`
  - `jjwt-api`, `jjwt-impl`, `jjwt-jackson`
  - `knife4j-openapi3-jakarta-spring-boot-starter`
  - `minio`

## 三、目录结构

```
qimo_hoduan
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── ylb
│   │   │           ├── common
│   │   │           │   ├── config
│   │   │           │   ├── consts
│   │   │           │   ├── exception
│   │   │           │   ├── filter
│   │   │           │   ├── result
│   │   │           │   ├── security
│   │   │           │   └── utils
│   │   │           ├── controller
│   │   │           ├── dto
│   │   │           │   └── vo
│   │   │           ├── entity
│   │   │           ├── mapper
│   │   │           └── service
│   │   │               └── impl
│   │   └── resources
│   │       ├── mapper
│   │       ├── static
│   │       └── templates
│   └── test
│       └── java
│           └── com
│               └── ylb
└── uploads
```

## 四、分层架构规范

| 层级        | 职责说明                         | 开发约束与注意事项                                               |
|-------------|----------------------------------|----------------------------------------------------------------|
| **Controller** | 处理 HTTP 请求与响应，定义 API 接口 | 不得直接访问数据库，必须通过 Service 层调用                  |
| **Service**    | 实现业务逻辑、事务管理与数据校验   | 必须通过 Repository 或 Mapper 层访问数据库；返回 DTO 而非 Entity（除非必要） |
| **Mapper**     | 数据库访问与持久化操作             | 使用 MyBatis 的 XML 映射文件或注解方式；避免 N+1 查询问题     |
| **Entity**     | 映射数据库表结构                   | 不得直接返回给前端（需转换为 DTO）；包名统一为 `entity`         |
| **DTO**        | 数据传输对象                       | 用于前后端数据交互；包名统一为 `dto`                             |
| **VO**         | 视图展示对象                       | 用于前端页面展示；包名统一为 `vo`                                |

### 接口与实现分离

- 所有接口实现类需放在接口所在包下的 `impl` 子包中。

## 五、安全与性能规范

### 输入校验

- 使用 `@Valid` 与 JSR-303 校验注解（如 `@NotBlank`, `@Size` 等）
  - 注意：Spring Boot 3.x 中校验注解位于 `jakarta.validation.constraints.*`

- 禁止手动拼接 SQL 字符串，防止 SQL 注入攻击。

### 事务管理

- `@Transactional` 注解仅用于 **Service 层**方法。
- 避免在循环中频繁提交事务，影响性能。

### 缓存管理

- 使用 Redis 进行数据缓存，注意缓存击穿、穿透和雪崩问题。

## 六、代码风格规范

### 命名规范

| 类型       | 命名方式             | 示例                  |
|------------|----------------------|-----------------------|
| 类名       | UpperCamelCase       | `UserServiceImpl`     |
| 方法/变量  | lowerCamelCase       | `saveUser()`          |
| 常量       | UPPER_SNAKE_CASE     | `MAX_LOGIN_ATTEMPTS`  |

### 注释规范

- 所有类、方法、字段需添加 **Javadoc** 注释，使用中文进行注释。

### 类型命名规范（阿里巴巴风格）

| 后缀 | 用途说明                     | 示例         |
|------|------------------------------|--------------|
| DTO  | 数据传输对象                 | `UserDTO`    |
| DO   | 数据库实体对象               | `UserDO`     |
| BO   | 业务逻辑封装对象             | `UserBO`     |
| VO   | 视图展示对象                 | `UserVO`     |
| Query| 查询参数封装对象             | `UserQuery`  |

### 实体类简化工具

- 使用 Lombok 注解替代手动编写 getter/setter/构造方法：
  - `@Data`
  - `@NoArgsConstructor`
  - `@AllArgsConstructor`

## 七、扩展性与日志规范

### 接口优先原则

- 所有业务逻辑通过接口定义（如 `UserService`），具体实现放在 `impl` 包中（如 `UserServiceImpl`）。

### 日志记录

- 使用 `@Slf4j` 注解代替 `System.out.println`

## 八、编码原则总结

| 原则       | 说明                                       |
|------------|--------------------------------------------|
| **SOLID**  | 高内聚、低耦合，增强可维护性与可扩展性     |
| **DRY**    | 避免重复代码，提高复用性                   |
| **KISS**   | 保持代码简洁易懂                           |
| **YAGNI**  | 不实现当前不需要的功能                     |
| **OWASP**  | 防范常见安全漏洞，如 SQL 注入、XSS 等      |

## 九、其他依赖与配置

- **MyBatis**：版本 3.0.3，用于数据库操作。
- **JWT**：版本 0.11.5，用于 Token 的生成与解析。
- **Knife4j**：版本 4.3.0，用于增强 Swagger 文档功能。
- **MinIO**：版本 8.5.7，用于对象存储操作。

### 数据库配置

- 使用 MySQL 数据库，配置信息如下：
  - 数据库驱动：`com.mysql.cj.jdbc.Driver`
  - 数据库 URL：`jdbc:mysql://localhost:3306/qimo_sjk?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai`
  - 用户名：`root`
  - 密码：`123456`

### Redis 配置

- Redis 主机：`localhost`
- Redis 端口：`6379`
- Redis 数据库：`0`

### MyBatis 配置

- XML 映射文件路径：`classpath:mapper/*.xml`
- 实体类包别名：`com.dorm.system.entity`
- 开启驼峰命名自动映射：`map-underscore-to-camel-case: true`
- 控制台打印 SQL 日志：`log-impl: org.apache.ibatis.logging.stdout.StdOutImpl`

### Swagger/Knife4j 配置

- 启用 Knife4j：`enable: true`
- 设置语言为中文：`language: zh_cn`
