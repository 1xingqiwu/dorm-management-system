# 智慧宿舍管理系统

[![Open in GitHub Codespaces](https://github.com/codespaces/badge.svg)](https://codespaces.new/1xingqiwu/dorm-management-system)

基于 Spring Boot 3 + Vue 3 的高校宿舍管理系统，面向**学生 / 辅导员 / 宿管 / 超级管理员**四类角色，覆盖宿舍分配、报修处理、请假审批、访客登记、卫生检查、电量充值、公告通知与 AI 智能助手等功能。

## 在线体验

不想装环境？点上面的 **Open in GitHub Codespaces** 徽章即可：GitHub 会在云端为你创建一套完整环境（MySQL + Redis + 后端 + 前端），并自动导入演示数据。

1. 首次创建需要等待镜像构建与依赖安装（约几分钟），期间页面会显示初始化进度
2. 环境就绪后浏览器会自动打开 `3000` 端口预览，那就是系统界面
3. 用下方[测试账号](#测试账号)登录即可，例如超级管理员 `admin` / `123456`

几点说明：

- 需要你自己的 GitHub 账号，Codespaces 每月提供免费额度，体验本项目绰绰有余
- 若没自动弹出预览，可在 Codespaces 的「端口 / Ports」面板中找到 `3000`，点「在浏览器中打开」
- 演示环境未内置第三方密钥，因此 **AI 智能助手**与**邮箱验证码**功能不可用，其余功能均可正常使用
- 想让服务重新跑起来：重启 Codespace 即可，启动脚本会自动拉起后端与前端

## 技术栈

| 分层 | 技术 |
| --- | --- |
| 后端 | Spring Boot 3.2.0、Java 17、MyBatis 3.0.3（XML 映射）、MySQL 8、Redis、Spring Security + JWT、Knife4j 4.3.0、MinIO、spring-boot-starter-mail |
| 前端 | Vue 3.5、Vite 7、Element Plus 2.11、Pinia 3、Vue Router 4、Axios、ECharts 6、wangEditor |
| 数据库 | MySQL 8.0（库名 `qm_sjk`） |

## 目录结构

```
.
├── qimo_hoduan/                    # 后端工程（Spring Boot）
│   └── src/main/java/com/ylb/      # controller → service → mapper → entity
├── qimo_qianduan/                  # 前端工程（Vue 3 + Vite）
├── qimo_sjk.sql                    # 数据库结构 + 初始数据
├── 智慧宿舍管理系统开发文档.md      # 详细开发文档
├── 智慧宿舍管理系统产品使用手册.md  # 面向使用者的操作手册
└── 项目熟悉报告.md                 # 项目结构梳理与审查报告
```

## 快速开始

### 1. 环境依赖

| 软件 | 最低版本 | 推荐版本 |
| --- | --- | --- |
| JDK | 17 | 17.0.x |
| Maven | 3.8+ | 3.9.x |
| Node.js | 18+ | 20.x LTS |
| MySQL | 8.0 | 8.0.42 |
| Redis | 6.0+ | 7.x |

### 2. 初始化数据库

```bash
mysql -u root -p -e "CREATE DATABASE qm_sjk DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;"
mysql -u root -p qm_sjk < qimo_sjk.sql
```

### 3. 启动后端

`qimo_hoduan/src/main/resources/application-local.yml` 存放本地真实密钥，已被 `.gitignore` 排除，**克隆仓库后该文件不存在，必须自己创建**，否则启动会因缺少 `jwt.secret`、数据库密码等配置而失败：

```yaml
# qimo_hoduan/src/main/resources/application-local.yml
jwt:
  secret: 自定义JWT密钥至少32字节
spring:
  datasource:
    password: 你的MySQL密码
  mail:
    password: 你的QQ邮箱授权码
ai:
  deepseek:
    api-key: 你的DeepSeek_API_Key
```

完成后启动：

```bash
cd qimo_hoduan
mvn spring-boot:run
```

- 服务端口：`8080`
- 接口文档（Knife4j）：<http://localhost:8080/doc.html>

### 4. 启动前端

```bash
cd qimo_qianduan
npm install
npm run dev
```

访问 <http://localhost:3000>。开发服务器会把 `/api` 前缀代理到 `http://localhost:8080`，无需额外配置跨域。

## 测试账号

| 角色 | 账号 | 密码 | 说明 |
| --- | --- | --- | --- |
| 超级管理员 | `admin` | `123456` | 超级管理员 |
| 辅导员 | `tea01` | `123456` | 赵老师（计算机学院） |
| 宿管 | `man01` | `123456` | 宿管张姨（南苑1号楼） |
| 学生 | `stu01` | `123456` | 学生甲 |

种子数据中的密码均以 BCrypt 哈希存储，上表为对应明文，仅用于本地开发调试。

## 相关文档

- [开发文档](智慧宿舍管理系统开发文档.md)：架构设计、数据库表结构、接口清单、部署流程
- [产品使用手册](智慧宿舍管理系统产品使用手册.md)：各角色的功能操作说明
- [项目熟悉报告](项目熟悉报告.md)：目录结构梳理与代码审查结论
