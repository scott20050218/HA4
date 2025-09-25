# Todo 应用后端 API

基于 Java Spring Boot 3.0 的 RESTful 待办事项管理 API，支持完整的 CRUD、状态切换、批量删除、分页、过滤、健康检查、Swagger 文档等。

## 技术栈

- Java 17+
- Spring Boot 3.0
- Spring Data JPA
- MySQL 5.7+
- Lombok
- Swagger (springdoc-openapi)

## 数据库配置

- 地址：localhost:3306
- 用户名：root
- 密码：123456
- 数据库名：todoapp
- 建表 SQL：见 3. 数据库设计

## 3. 数据库设计

### 3.1 表结构设计

#### tasks 表

```sql
CREATE TABLE tasks (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title VARCHAR(255) NOT NULL,
    completed BOOLEAN DEFAULT FALSE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- 创建索引以提高查询性能
CREATE INDEX idx_tasks_completed ON tasks(completed);
CREATE INDEX idx_tasks_created_at ON tasks(created_at);
```

### 3.2 数据模型说明

| 字段名     | 类型         | 约束                      | 说明                    |
| ---------- | ------------ | ------------------------- | ----------------------- |
| id         | INTEGER      | PRIMARY KEY AUTOINCREMENT | 主键，自增整数          |
| title      | VARCHAR(255) | NOT NULL                  | 任务标题，最大 255 字符 |
| completed  | BOOLEAN      | DEFAULT FALSE             | 完成状态，布尔值        |
| created_at | DATETIME     | DEFAULT CURRENT_TIMESTAMP | 创建时间，自动生成      |
| updated_at | DATETIME     | DEFAULT CURRENT_TIMESTAMP | 更新时间，自动更新      |

## API 接口规范

### 基础信息

- **基础 URL**: `http://localhost:8000`
- **API 前缀**: `/api/v1`
- **数据格式**: JSON
- **HTTP 状态码**: 遵循 RESTful 规范

### 接口列表

#### 1. 获取所有待办事项

```http
GET /api/v1/tasks
```

**查询参数**:

- `completed` (可选): boolean - 过滤完成状态
- `limit` (可选): integer - 限制返回数量，默认 100
- `offset` (可选): integer - 偏移量，默认 0

**响应示例**:

```json
{
  "code": 200,
  "message": "success",
  "data": [
    {
      "id": 1,
      "title": "学习React",
      "description": "完成React基础教程",
      "completed": false,
      "created_at": "2024-01-01T10:00:00Z",
      "updated_at": "2024-01-01T10:00:00Z",
      "priority": 1,
      "due_date": null
    }
  ],
  "total": 1
}
```

#### 2. 创建待办事项

```http
POST /api/v1/tasks
```

**请求体**:

```json
{
  "title": "新的待办事项",
  "description": "描述信息（可选）",
  "priority": 0,
  "due_date": null
}
```

**响应示例**:

```json
{
  "code": 201,
  "message": "Todo created successfully",
  "data": {
    "id": 2,
    "title": "新的待办事项",
    "description": "描述信息（可选）",
    "completed": false,
    "created_at": "2024-01-01T11:00:00Z",
    "updated_at": "2024-01-01T11:00:00Z",
    "priority": 0,
    "due_date": null
  }
}
```

#### 3. 更新待办事项

```http
PUT /api/v1/tasks/{todo_id}
```

**路径参数**:

- `todo_id`: integer - 待办事项 ID

**请求体**:

```json
{
  "title": "更新的标题",
  "description": "更新的描述",
  "completed": true,
  "priority": 2,
  "due_date": "2024-12-31T23:59:59Z"
}
```

**响应示例**:

```json
{
  "code": 200,
  "message": "Todo updated successfully",
  "data": {
    "id": 1,
    "title": "更新的标题",
    "description": "更新的描述",
    "completed": true,
    "created_at": "2024-01-01T10:00:00Z",
    "updated_at": "2024-01-01T12:00:00Z",
    "priority": 2,
    "due_date": "2024-12-31T23:59:59Z"
  }
}
```

#### 4. 删除待办事项

```http
DELETE /api/v1/tasks/{todo_id}
```

**路径参数**:

- `todo_id`: integer - 待办事项 ID

**响应示例**:

```json
{
  "code": 200,
  "message": "Todo deleted successfully"
}
```

#### 5. 标记完成/取消完成

```http
PATCH /api/v1/tasks/{todo_id}/toggle
```

**路径参数**:

- `todo_id`: integer - 待办事项 ID

**响应示例**:

```json
{
  "code": 200,
  "message": "Todo status toggled successfully",
  "data": {
    "id": 1,
    "completed": true,
    "updated_at": "2024-01-01T13:00:00Z"
  }
}
```

#### 6. 批量删除已完成项

```http
DELETE /api/v1/tasks/completed
```

**响应示例**:

```json
{
  "code": 200,
  "message": "Completed todos deleted successfully",
  "deleted_count": 5
}
```

#### 7. 清空所有待办事项

```http
DELETE /api/v1/tasks/all
```

**响应示例**:

```json
{
  "code": 200,
  "message": "All todos deleted successfully",
  "deleted_count": 10
}
```

#### 8. 用户注册

```http
POST /api/v1/auth/register
```

**请求体**:

```json
{
  "username": "用户名",
  "password": "密码"
}
```

**响应示例**:

```json
{
  "code": 201,
  "message": "Todo register successfully",
  "data": {
    "id": 0,
    "username": "string",
    "role": "user"
  }
}
```

### 错误响应格式

```json
{
  "code": 422,
  "message": "Todo not found",
  "detail": [
    {
      "loc": ["string", 0],
      "msg": "string",
      "type": "string"
    }
  ]
}
```

#### 9. 用户登陆

```http
POST /api/v1/auth/login
```

**请求体**:

```json
{
  "username": "用户名",
  "password": "密码"
}
```

**响应示例**:

```json
{
  "code": 200,
  "message": "Todo login successfully",
  "data": {
    "access_token": "string",
    "token_type": "bearer"
  }
}
```

### 错误响应格式

```json
{
  "code": 422,
  "message": "Todo not found",
  "detail": [
    {
      "loc": ["string", 0],
      "msg": "string",
      "type": "string"
    }
  ]
}
```

#### 10. 用户

```http
GET /api/v1/auth/me
```

**响应示例**:

```json
{
  "code": 200,
  "message": "Todo me successfully",
  "data": {
    "id": 0,
    "username": "string",
    "role": "user"
  }
}
```

### 错误响应格式

```json
{
  "code": 422,
  "message": "Todo not found",
  "detail": [
    {
      "loc": ["string", 0],
      "msg": "string",
      "type": "string"
    }
  ]
}
```

## 测试策略

1. **单元测试**:
2. **集成测试**: API 接口测试
3. **E2E 测试**:
4. **性能测试**: 大量数据场景测试
