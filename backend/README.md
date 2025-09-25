# TodoApp 后端

基于 Java Spring Boot 3.0 的 RESTful 待办事项管理 API

## 技术栈

- Java 17
- Spring Boot 3.0
- Spring Data JPA
- MySQL 5.7+
- Lombok
- Swagger (springdoc-openapi)
- Hibernate Validator

## 目录结构

```
backend/
├── pom.xml
├── spec-req.md
├── README.md
├── src/
│   ├── main/
│   │   ├── java/com/example/todoapp/
│   │   │   ├── TodoAppApplication.java
│   │   │   ├── config/CorsConfig.java
│   │   │   ├── controller/HealthController.java
│   │   │   ├── controller/TaskController.java
│   │   │   ├── model/Task.java
│   │   │   ├── repository/TaskRepository.java
│   │   │   ├── service/TaskService.java
│   │   │   └── service/impl/TaskServiceImpl.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── schema.sql
│   └── test/java/com/example/todoapp/
│       ├── controller/TaskControllerTest.java
│       └── service/TaskServiceTest.java
└── target/
```

## 快速开始

### 1. 数据库准备

- 确保 MySQL 已安装并启动。
- 创建数据库：
  ```sql
  CREATE DATABASE todoapp;
  ```
- 执行建表 SQL（可用 `src/main/resources/schema.sql`）。

### 2. 配置数据库连接

编辑 `src/main/resources/application.properties`：

```
spring.datasource.url=jdbc:mysql://localhost:3306/todoapp?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=123456
```

### 3. 构建与运行

```sh
mvn clean package
mvn spring-boot:run
```

服务启动后，访问：

- Swagger 文档：http://localhost:8000/swagger-ui.html
- 健康检查：http://localhost:8000/api/v1/health

### 4. 单元测试

```sh
mvn test
```

## 主要 API

- GET `/api/v1/todos` 获取所有待办事项（支持分页、过滤）
- POST `/api/v1/todos` 创建待办事项
- PUT `/api/v1/todos/{id}` 更新待办事项
- DELETE `/api/v1/todos/{id}` 删除待办事项
- PATCH `/api/v1/todos/{id}/toggle` 切换完成状态
- DELETE `/api/v1/todos/completed` 批量删除已完成项
- DELETE `/api/v1/todos/all` 清空所有待办事项
- GET `/api/v1/health` 健康检查

详细接口说明见 Swagger 文档或 `spec-req.md`。

## 跨域支持

已全局允许所有来源跨域（CORS），便于前后端分离开发。

## 其他

- Lombok 需在 IDE 安装插件以支持注解。
- 代码风格与接口返回格式严格遵循 `spec-req.md`。
