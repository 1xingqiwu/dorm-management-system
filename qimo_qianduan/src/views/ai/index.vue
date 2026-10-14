<template>
  <div class="chat-dashboard">
    <!-- 背景装饰光斑 -->
    <div class="bg-glow glow-1"></div>
    <div class="bg-glow glow-2"></div>

    <!-- 主聊天窗口 -->
    <div class="chat-window">

      <!-- 1. 头部：深色玻璃 -->
      <div class="window-header">
        <div class="bot-info">
          <div class="avatar-wrapper">
            <img src="../../../public/AI.png" alt="AI" />
            <span class="status-dot"></span>
            <div class="avatar-glow"></div>
          </div>
          <div class="text-info">
            <h3 class="name">智慧宿管 AI <span class="badge">PRO</span></h3>
          </div>
        </div>
        <el-tooltip content="清空上下文" placement="bottom" effect="dark">
          <div class="action-btn" @click="clearHistory">
            <el-icon><Delete /></el-icon>
          </div>
        </el-tooltip>
      </div>

      <!-- 2. 消息列表区域 -->
      <div class="message-container" ref="scrollRef">
        <!-- 欢迎缺省页 -->
        <div v-if="messageList.length === 0" class="welcome-placeholder">
          <div class="icon-circle">
            <img src="https://cdn-icons-png.flaticon.com/512/2040/2040946.png" class="placeholder-icon" />
          </div>
          <h2>欢迎回来，<span class="highlight">{{ userStore.userInfo.nickname }}</span></h2>
          <p>我是您的全天候宿舍助理，请下达指令...</p>
        </div>

        <!-- 消息流 -->
        <div v-else class="message-list">
          <div
              v-for="(msg, index) in messageList"
              :key="index"
              class="message-row"
              :class="msg.role"
          >
            <!-- 头像 -->
            <div class="avatar">
              <img v-if="msg.role === 'ai'" src="../../../public/AI.png" />
              <img v-else :src="userStore.userInfo.avatar || '../../../public/OIP.jpg'" />
            </div>

            <!-- 气泡 -->
            <div class="bubble-wrapper">
              <div class="bubble">
                <!-- 渲染 Markdown 内容 -->
                <div class="markdown-body" v-html="formatContent(msg.content)"></div>
                <span class="time">{{ msg.time }}</span>
              </div>
            </div>
          </div>

          <!-- AI 正在输入动画 -->
          <div v-if="loading" class="message-row ai">
            <div class="avatar">
              <img src="../../../public/AI.png" />
            </div>
            <div class="bubble-wrapper">
              <div class="bubble typing">
                <span class="dot"></span><span class="dot"></span><span class="dot"></span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 3. 底部操作区 -->
      <div class="input-area">
        <!-- 快捷提问胶囊 -->
        <div class="suggestions" v-if="!loading">
          <div
              v-for="chip in suggestions"
              :key="chip"
              class="chip"
              @click="quickAsk(chip)"
          >
            {{ chip }}
          </div>
        </div>

        <!-- 输入框容器 -->
        <div class="input-box" :class="{ 'is-focus': isInputFocus }">
          <el-input
              v-model="inputText"
              type="textarea"
              :rows="1"
              autosize
              resize="none"
              placeholder="输入指令或问题..."
              @keydown.enter.prevent="handleEnter"
              @focus="isInputFocus = true"
              @blur="isInputFocus = false"
              class="custom-textarea"
          />
          <button class="send-btn" :class="{ disabled: loading || !inputText.trim() }" @click="handleSend">
            <el-icon v-if="!loading"><Position /></el-icon>
            <el-icon v-else class="is-loading"><Loading /></el-icon>
          </button>
        </div>
      </div>

    </div>
  </div>
</template>

<script setup>
import { ref, nextTick, onMounted } from 'vue';
import { useUserStore } from '../../store/user';
import dayjs from 'dayjs';
import { Delete, Position, Loading } from '@element-plus/icons-vue';
import { marked } from "marked";

const userStore = useUserStore();
const scrollRef = ref(null);
const inputText = ref('');
const loading = ref(false);
const isInputFocus = ref(false);

const suggestions = [
  "⚡ 充值 100 元电费",
  "🔧 报修：空调漏水",
  "🧹 查询卫生检查记录",
  "📅 请假申请流程",
  "💰 查询我的余额"
];

const messageList = ref([
  {
    role: 'ai',
    content: `系统已就绪。你好，**${userStore.userInfo.nickname}**。\n我是您的智能宿管助理，已连接至宿舍中枢数据库。请问有什么指令？`,
    time: dayjs().format('HH:mm')
  }
]);

const scrollToBottom = () => {
  nextTick(() => {
    if (scrollRef.value) {
      scrollRef.value.scrollTop = scrollRef.value.scrollHeight;
    }
  });
};

const formatContent = (text) => {
  if (!text) return '';
  return marked.parse(text);
};

const clearHistory = () => {
  messageList.value = [];
};

const quickAsk = (text) => {
  inputText.value = text;
  handleSend();
};

const handleEnter = (e) => {
  if (!e.shiftKey) {
    handleSend();
  }
};

const handleSend = () => {
  const text = inputText.value.trim();
  if (!text || loading.value) return;

  messageList.value.push({
    role: 'user',
    content: text,
    time: dayjs().format('HH:mm')
  });
  inputText.value = '';
  scrollToBottom();
  loading.value = true;

  const aiMessageIndex = messageList.value.length;
  messageList.value.push({
    role: 'ai',
    content: '',
    time: dayjs().format('HH:mm')
  });

  const token = userStore.token;
  const eventSource = new EventSource(`/api/ai/stream?question=${encodeURIComponent(text)}&token=${token}`);

  eventSource.onmessage = (event) => {
    messageList.value[aiMessageIndex].content += event.data;
    scrollToBottom();
  };

  eventSource.onerror = (error) => {
    eventSource.close();
    loading.value = false;
    if (!messageList.value[aiMessageIndex].content) {
      messageList.value[aiMessageIndex].content = "连接中断：无法连接至主服务器。";
    }
  };
};

onMounted(() => scrollToBottom());
</script>

<style scoped lang="scss">
/* 全局深色背景，带径向渐变，呼应登录页 */
.chat-dashboard {
  height: calc(100vh - 50px);
  background: radial-gradient(circle at top left, #1e293b 0%, #0f172a 100%);
  padding: 20px;
  box-sizing: border-box;
  display: flex;
  justify-content: center;
  position: relative;
  overflow: hidden;
  margin: -20px;

}

/* 动态背景光斑 */
.bg-glow {
  position: absolute;
  width: 600px; height: 600px;
  border-radius: 50%;
  filter: blur(100px);
  opacity: 0.15;
  z-index: 0;
}
.glow-1 { top: -100px; left: -100px; background: var(--color-primary); }
.glow-2 { bottom: -100px; right: -100px; background: #7928CA; }

/* 聊天主窗口：深色毛玻璃 */
.chat-window {
  width: 100%;
  max-width: 1000px;
  background: rgba(30, 41, 59, 0.6); /* 深蓝灰半透明 */
  backdrop-filter: blur(20px);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 16px;
  box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.5);
  display: flex;
  flex-direction: column;
  overflow: hidden;
  position: relative;
  z-index: 1;
}

/* 1. 头部 */
.window-header {
  height: 70px;
  padding: 0 25px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.1);
  display: flex;
  align-items: center;
  justify-content: space-between;
  background: rgba(15, 23, 42, 0.4);

  .bot-info {
    display: flex;
    align-items: center;
    gap: 15px;

    .avatar-wrapper {
      position: relative;
      img { width: 45px; height: 45px; border-radius: 50%; border: 2px solid rgba(255,255,255,0.2); }
      .status-dot {
        position: absolute; bottom: 2px; right: 2px; width: 10px; height: 10px;
        background: #00f2ff; border: 2px solid #0f172a; border-radius: 50%;
        box-shadow: 0 0 8px #00f2ff;
      }
    }
    .text-info {
      .name {
        margin: 0; font-size: 16px; font-weight: bold; color: #e2e8f0;
        .badge { font-size: 10px; background: linear-gradient(45deg, var(--color-primary), #7928CA); padding: 2px 6px; border-radius: 4px; margin-left: 5px; }
      }
      .status-text { font-size: 12px; color: #94a3b8; letter-spacing: 1px; }
    }
  }

  .action-btn {
    width: 36px; height: 36px; display: flex; align-items: center; justify-content: center;
    border-radius: 50%; color: #94a3b8; cursor: pointer; transition: 0.3s;
    border: 1px solid rgba(255,255,255,0.05);
    &:hover { background: rgba(255,255,255,0.1); color: #fff; border-color: rgba(255,255,255,0.2); }
  }
}

/* 2. 消息区域 */
.message-container {
  flex: 1;
  overflow-y: auto;
  padding: 20px;
  scroll-behavior: smooth;

  &::-webkit-scrollbar { width: 6px; }
  &::-webkit-scrollbar-thumb { background: rgba(255,255,255,0.1); border-radius: 3px; }
  &::-webkit-scrollbar-track { background: transparent; }
}

/* 缺省页 */
.welcome-placeholder {
  height: 100%; display: flex; flex-direction: column; align-items: center; justify-content: center; opacity: 0.8;
  .icon-circle {
    width: 100px; height: 100px; background: rgba(64, 158, 255, 0.1); border-radius: 50%;
    display: flex; align-items: center; justify-content: center; margin-bottom: 20px;
    box-shadow: 0 0 30px rgba(64, 158, 255, 0.2);
  }
  .placeholder-icon { width: 60px; opacity: 0.9; }
  h2 { color: #e2e8f0; margin-bottom: 10px; font-weight: 300; }
  .highlight { color: var(--color-primary); font-weight: bold; }
  p { color: #94a3b8; }
}

/* 消息行 */
.message-row {
  display: flex; margin-bottom: 25px; animation: fadeInUp 0.3s ease;
  .avatar {
    width: 40px; height: 40px; flex-shrink: 0;
    img { width: 100%; height: 100%; border-radius: 50%; border: 1px solid rgba(255,255,255,0.1); }
  }
  .bubble-wrapper { max-width: 75%; display: flex; flex-direction: column; }
  .bubble {
    padding: 12px 18px; border-radius: 12px; font-size: 15px; line-height: 1.6; position: relative;
    .time { display: block; font-size: 11px; margin-top: 5px; opacity: 0.6; text-align: right; }
  }

  /* AI 样式 */
  &.ai {
    .avatar { margin-right: 15px; }
    .bubble {
      background: rgba(255, 255, 255, 0.05); /* 深色半透明 */
      border: 1px solid rgba(255, 255, 255, 0.05);
      color: #e2e8f0;
      border-top-left-radius: 2px;
    }
  }

  /* 用户样式 */
  &.user {
    flex-direction: row-reverse;
    .avatar { margin-left: 15px; }
    .bubble {
      /* 渐变蓝紫色 */
      background: linear-gradient(135deg, var(--color-primary) 0%, #7928CA 100%);
      color: #fff;
      border-top-right-radius: 2px;
      box-shadow: 0 4px 15px rgba(64, 158, 255, 0.3);
      .time { color: rgba(255,255,255,0.8); }
      :deep(p) { margin: 0; }
    }
  }
}

/* 3. 底部输入区 */
.input-area {
  background: rgba(15, 23, 42, 0.6);
  padding: 20px;
  border-top: 1px solid rgba(255, 255, 255, 0.05);
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.suggestions {
  display: flex; gap: 10px; flex-wrap: wrap;
  .chip {
    padding: 6px 16px;
    background: rgba(255, 255, 255, 0.05);
    color: #94a3b8;
    border: 1px solid rgba(255, 255, 255, 0.1);
    border-radius: 20px;
    font-size: 13px; cursor: pointer; transition: 0.3s;
    &:hover {
      background: rgba(64, 158, 255, 0.2);
      color: var(--color-primary);
      border-color: var(--color-primary);
      box-shadow: 0 0 10px rgba(64, 158, 255, 0.2);
    }
  }
}

.input-box {
  display: flex; align-items: flex-end; gap: 12px;
  background: rgba(0, 0, 0, 0.3);
  padding: 10px;
  border-radius: 12px;
  border: 1px solid rgba(255, 255, 255, 0.1);
  transition: 0.3s;

  &.is-focus {
    border-color: var(--color-primary);
    box-shadow: 0 0 15px rgba(64, 158, 255, 0.15);
    background: rgba(0, 0, 0, 0.5);
  }

  :deep(.el-textarea__inner) {
    box-shadow: none !important;
    background: transparent;
    padding: 0;
    font-size: 15px;
    color: #fff; /* 输入文字变白 */
    &::placeholder { color: rgba(255,255,255,0.3); }
  }

  .send-btn {
    width: 40px; height: 40px; border-radius: 10px;
    border: none;
    background: linear-gradient(135deg, var(--color-primary) 0%, #7928CA 100%);
    color: #fff;
    cursor: pointer; display: flex; align-items: center; justify-content: center;
    transition: 0.3s;
    box-shadow: 0 0 10px rgba(64, 158, 255, 0.3);

    &:hover { transform: scale(1.05); filter: brightness(1.2); }
    &.disabled { background: #334155; cursor: not-allowed; transform: none; box-shadow: none; color: #64748b; }
    .el-icon { font-size: 18px; }
  }
}

.footer-note { text-align: center; font-size: 12px; color: #475569; }

/* 正在输入 */
.typing .dot { background: #94a3b8; }
@keyframes fadeInUp { from { opacity: 0; transform: translateY(10px); } to { opacity: 1; transform: translateY(0); } }

/* Markdown 深色适配 */
:deep(.markdown-body) {
  line-height: 1.7; color: #e2e8f0;
  p { margin: 5px 0; }
  ul, ol { padding-left: 20px; margin: 8px 0; }
  li { margin-bottom: 4px; }
  strong { color: #00f2ff; font-weight: 600; } /* 重点高亮改为霓虹色 */
  a { color: var(--color-primary); }
  code { background: rgba(0,0,0,0.3); padding: 2px 5px; border-radius: 4px; color: #ff79c6; border: 1px solid rgba(255,255,255,0.1); }
}

/* 针对手机端的媒体查询 */
@media (max-width: 768px) {
  /* 1. 调整容器高度，适应手机浏览器地址栏 */
  .ai-container {
    height: calc(100vh - 100px) !important; /* 手机端 Header 较矮 */
  }

  /* 2. 气泡最大宽度变大，利用屏幕空间 */
  .chat-body .message-row .bubble {
    max-width: 85% !important; /* PC是70%，手机改大点 */
    font-size: 14px;
    padding: 10px;
  }

  /* 3. 隐藏一些不重要的信息 */
  .chat-header .info .status {
    display: none; /* 手机端屏幕小，把在线状态隐藏 */
  }

  /* 4. 快捷指令按钮变成横向滚动 */
  /* 如果你有快捷按钮的话，设为 flex-wrap: nowrap; overflow-x: auto; */
}
</style>