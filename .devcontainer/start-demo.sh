#!/usr/bin/env bash
# Codespaces 每次容器启动时执行：等待 MySQL 就绪并拉起后端与前端
REPO="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
LOG=/tmp/dorm-demo
mkdir -p "$LOG"

# 1. 等待 MySQL 就绪（首次启动需导入 qimo_sjk.sql，耗时较长）
echo "[demo] 等待 MySQL (mysql:3306) ..."
for _ in $(seq 1 90); do
  if (echo > /dev/tcp/mysql/3306) 2>/dev/null; then
    echo "[demo] MySQL 已就绪"
    break
  fi
  sleep 2
done

# 2. 后端（8080）
echo "[demo] 启动后端 ..."
cd "$REPO/qimo_hoduan" || exit 1
nohup mvn -B spring-boot:run > "$LOG/backend.log" 2>&1 &

# 3. 前端（3000）
echo "[demo] 启动前端 ..."
cd "$REPO/qimo_qianduan" || exit 1
nohup npm run dev > "$LOG/frontend.log" 2>&1 &

echo "[demo] 服务已在后台启动，日志：$LOG/backend.log、$LOG/frontend.log"
