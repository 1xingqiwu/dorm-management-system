#!/usr/bin/env bash
# Codespaces 首次创建时执行：预装前后端依赖，让后续启动更快
REPO="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"

echo "[demo] 安装前端依赖 ..."
cd "$REPO/qimo_qianduan" && npm install || echo "[demo] 前端依赖安装失败，可稍后手动执行 npm install"

echo "[demo] 预下载后端依赖 ..."
cd "$REPO/qimo_hoduan" && mvn -B -q -DskipTests dependency:go-offline || echo "[demo] 后端依赖预下载失败，可稍后手动执行 mvn spring-boot:run"

echo "[demo] 初始化完成，等待容器启动脚本拉起服务"
