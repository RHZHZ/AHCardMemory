# AHCardMemory - 智能记忆卡片应用

## 📱 项目简介

AHCardMemory（AH记忆卡片）是一款基于uni-app框架开发的智能记忆卡片应用，旨在帮助用户通过科学的记忆方法（艾宾浩斯遗忘曲线）高效学习和记忆知识点。

## ✨ 核心功能

### 🎴 智能记忆卡片
- **卡片展示**：支持图文结合的卡片展示方式
- **记忆评估**：提供"记住了"和"遗忘了"两个评估选项
- **进度追踪**：实时显示学习进度和完成百分比
- **智能复习**：基于艾宾浩斯遗忘曲线的复习提醒机制

### 📅 学习计划管理
- **计划创建**：自定义学习目标和计划名称
- **倒计时显示**：可视化显示距离计划完成的剩余时间
- **学习分类**：支持按类别管理不同的学习内容
- **卡片管理**：添加、编辑、删除学习卡片

### 🌟 每日激励
- **每日一句**：自动获取中英文双语励志语句
- **诗词欣赏**：展示经典诗词作品
- **成就系统**：学习完成的成就提示

## 🛠️ 技术栈

- **框架**：uni-app（Vue 2.x版本）
- **UI组件**：
  - uv-ui（uView Plus）
  - uni-ui
- **HTTP请求**：axios
- **后端API**：本地开发服务器（localhost:8081）

## 📁 项目结构

```
AHfronted/
├── pages/                 # 页面目录
│   ├── index/            # 记忆卡片页面
│   │   └── index.vue
│   └── memory/           # 主页面
│       └── memory.vue
├── App.vue               # 应用入口文件
├── main.js               # 主程序入口
├── manifest.json         # 应用配置
├── pages.json            # 页面路由配置
├── package.json          # 项目依赖
└── uni.scss              # 全局样式
```

## 🚀 开始使用

### 前置要求
- HBuilderX 3.1.0 或更高版本
- Node.js 环境
- 本地后端服务器（端口8081）

### 安装步骤

1. **克隆项目**
   ```bash
   git clone https://github.com/RHZHZ/AHCardMemory.git AHCardMemory
   ```

2. **安装依赖**
   ```bash
   cd AHfronted
   npm install
   ```

3. **配置后端API**
   确保后端服务器运行在 `http://localhost:8081`，并提供以下接口：
   - `POST /card/search` - 搜索卡片
   - `POST /card/add` - 添加卡片
   - `POST /card/del` - 删除卡片
   - `POST /card/updatecard` - 更新卡片状态
   - `GET /card/getAll` - 获取所有卡片
   - `GET /plan/getPlan` - 获取学习计划
   - `POST /plan/add` - 添加学习计划

4. **运行项目**
   在HBuilderX中打开项目，选择运行方式（微信小程序、H5、App等）

## 📊 数据库设置

### 数据库概述
AHCardMemory应用需要后端数据库来存储学习卡片、学习计划等数据。

### 数据库表结构

#### 1. 卡片表 (cards)
```sql
CREATE TABLE cards (
    id INT PRIMARY KEY AUTO_INCREMENT,
    category VARCHAR(100) NOT NULL COMMENT '卡片分类',
    content TEXT COMMENT '卡片内容',
    image VARCHAR(255) COMMENT '图片URL',
    chooseday INT DEFAULT 1 COMMENT '复习间隔天数',
    lastReview INT DEFAULT 1 COMMENT '上次复习天数',
    remembered BOOLEAN DEFAULT FALSE COMMENT '是否已记住',
    startTime BIGINT COMMENT '创建时间戳',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_category (category)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学习卡片表';
```

#### 2. 学习计划表 (plans)
```sql
CREATE TABLE plans (
    id INT PRIMARY KEY AUTO_INCREMENT,
    planName VARCHAR(100) NOT NULL COMMENT '计划名称',
    planTime BIGINT NOT NULL COMMENT '计划完成时间戳',
    is_active BOOLEAN DEFAULT TRUE COMMENT '是否激活',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_active (is_active)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学习计划表';
```

### 快速设置指南

#### MySQL/MariaDB 设置
1. **创建数据库**
   ```sql
   CREATE DATABASE ahcard_memory CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```

2. **创建用户并授权**
   ```sql
   CREATE USER 'ahcard_user'@'localhost' IDENTIFIED BY 'your_password';
   GRANT ALL PRIVILEGES ON ahcard_memory.* TO 'ahcard_user'@'localhost';
   FLUSH PRIVILEGES;
   ```

3. **导入表结构**
   ```bash
   mysql -u ahcard_user -p ahcard_memory < database/schema.sql
   ```

#### SQLite 设置（开发环境推荐）
1. **创建数据库文件**
   ```bash
   sqlite3 ahcard_memory.db
   ```

2. **执行表结构**
   ```sql
   -- 卡片表
   CREATE TABLE cards (
       id INTEGER PRIMARY KEY AUTOINCREMENT,
       category TEXT NOT NULL,
       content TEXT,
       image TEXT,
       chooseday INTEGER DEFAULT 1,
       lastReview INTEGER DEFAULT 1,
       remembered INTEGER DEFAULT 0,
       startTime INTEGER,
       created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
       updated_at DATETIME DEFAULT CURRENT_TIMESTAMP
   );

   -- 学习计划表
   CREATE TABLE plans (
       id INTEGER PRIMARY KEY AUTOINCREMENT,
       planName TEXT NOT NULL,
       planTime INTEGER NOT NULL,
       is_active INTEGER DEFAULT 1,
       created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
       updated_at DATETIME DEFAULT CURRENT_TIMESTAMP
   );

   CREATE INDEX idx_category ON cards(category);
   CREATE INDEX idx_active ON plans(is_active);
   ```

### 示例数据插入

**卡片数据示例：**
```sql
INSERT INTO cards (category, content, image, chooseday, lastReview, startTime) VALUES
('JavaScript', '闭包是指有权访问另一个函数作用域中变量的函数', 'https://example.com/js.jpg', 1, 1, UNIX_TIMESTAMP()*1000),
('Vue.js', 'Vue使用虚拟DOM来高效更新真实DOM', 'https://example.com/vue.jpg', 1, 1, UNIX_TIMESTAMP()*1000),
('CSS', 'Flexbox布局提供了更加有效的方式来布置、对齐和分配容器中项目的空间', 'https://example.com/css.jpg', 1, 1, UNIX_TIMESTAMP()*1000);
```

**学习计划示例：**
```sql
INSERT INTO plans (planName, planTime, is_active) VALUES
('30天掌握前端基础', UNIX_TIMESTAMP(DATE_ADD(NOW(), INTERVAL 30 DAY))*1000, 1);
```

## 📋 主要页面功能

### 📖 主页面 (pages/memory/memory.vue)

- **学习计划展示**：显示当前学习计划的倒计时
- **每日激励**：展示每日一句和诗词
- **内容管理**：
  - 查看所有学习分类
  - 添加新的学习卡片
  - 设置学习计划
  - 删除不需要的卡片

### 🎯 记忆页面 (pages/index/index.vue)

- **卡片学习**：展示当前需要复习的卡片
- **记忆评估**：用户可以选择"记住了"或"遗忘了"
- **进度追踪**：显示当前学习进度
- **自动跳转**：学习完成后自动返回主页面

## 🔧 配置说明

### manifest.json 配置项
- 应用名称：ahmemory
- 应用ID：__UNI__75A72C0
- 支持平台：App、微信小程序
- 权限配置：包含相机、存储、网络等权限

### pages.json 页面路由
- `/pages/memory/memory` - 主页面
- `/pages/index/index` - 记忆学习页面

## 🎨 UI设计特点

- **圆角设计**：大量使用圆角元素（24rpx-34rpx）
- **卡片布局**：采用卡片式布局展示内容
- **响应式设计**：适配不同屏幕尺寸
- **渐变色背景**：使用动态壁纸作为背景

## 📈 功能亮点

1. **科学记忆**：基于艾宾浩斯遗忘曲线的智能复习提醒
2. **数据持久化**：学习进度和卡片状态实时保存到后端
3. **激励系统**：通过每日一句和成就提示保持学习动力
4. **灵活配置**：支持自定义学习计划和分类
5. **友好交互**：流畅的动画效果和直观的操作界面

## 🔍 API接口说明

### 卡片相关接口
- 搜索卡片：根据分类搜索相关卡片
- 添加卡片：创建新的学习卡片
- 删除卡片：移除不需要的卡片
- 更新卡片：更新卡片的学习状态和复习时间

### 计划相关接口
- 获取计划：获取当前激活的学习计划
- 添加计划：创建新的学习计划

## 💡 使用说明

1. **创建学习计划**：点击"自定义计划"设置学习目标
2. **添加学习卡片**：点击右下角悬浮按钮添加新的学习内容
3. **开始学习**：点击分类进入记忆学习模式
4. **评估记忆**：根据记忆情况选择"记住了"或"遗忘了"
5. **查看进度**：通过进度条了解学习完成情况

## 📝 注意事项

- 确保后端服务器正常运行
- 首次使用需要添加至少一个学习分类
- 建议每天坚持学习，系统会自动安排复习计划
- 可以根据需要调整学习计划的难度和时间安排

## 🛠️ 开发环境设置

### 使用Docker快速部署
1. **创建docker-compose.yml**
   ```yaml
   version: '3'
   services:
     mysql:
       image: mysql:8.0
       container_name: ahcard_mysql
       environment:
         MYSQL_ROOT_PASSWORD: root_password
         MYSQL_DATABASE: ahcard_memory
         MYSQL_USER: ahcard_user
         MYSQL_PASSWORD: your_password
       ports:
         - "3306:3306"
       volumes:
         - mysql_data:/var/lib/mysql
         - ./database/schema.sql:/docker-entrypoint-initdb.d/schema.sql

   volumes:
     mysql_data:
   ```

2. **启动服务**
   ```bash
   docker-compose up -d
   ```

### 使用Node.js + Express快速搭建后端
1. **初始化项目**
   ```bash
   mkdir ahcard-backend && cd ahcard-backend
   npm init -y
   npm install express mysql2 body-parser cors
   ```

2. **创建server.js**
   ```javascript
   const express = require('express');
   const mysql = require('mysql2/promise');
   const bodyParser = require('body-parser');
   const cors = require('cors');

   const app = express();
   app.use(cors());
   app.use(bodyParser.json());

   // 数据库连接配置
   const pool = mysql.createPool({
     host: 'localhost',
     user: 'ahcard_user',
     password: 'your_password',
     database: 'ahcard_memory',
     waitForConnections: true,
     connectionLimit: 10,
     queueLimit: 0
   });

   // API接口实现
   app.get('/card/getAll', async (req, res) => {
     try {
       const [rows] = await pool.query('SELECT * FROM cards ORDER BY startTime DESC');
       res.json(rows);
     } catch (error) {
       res.status(500).json({ error: error.message });
     }
   });

   // 其他API接口...

   const PORT = 8081;
   app.listen(PORT, () => {
     console.log(`Server running on http://localhost:${PORT}`);
   });
   ```

## 🤝 贡献指南

欢迎提交问题和功能建议，也欢迎提交Pull Request来改进这个项目。

## 📄 许可证

该项目采用 MIT 许可证。

---

**AHCardMemory** - 让记忆变得更科学、更高效！🎯
