<template>
  <div class="login-page">
    <!-- 页面背景星空 -->
    <div ref="bgParticlesContainer" class="bg-particles-container"></div>

    <div class="login-card">
      <!-- 左侧背景展示区 -->
      <div class="left-section">
        <div ref="cardParticlesContainer" class="particles-container"></div>
        <div class="brand-content">
          <img src="/novachat-icon.svg" alt="" class="brand-icon">
          <h1 class="brand-title">NovaChat</h1>
          <p class="brand-slogan">连接你我，畅聊无限</p>
          <div class="typewriter-container">
            <p class="typewriter-text">{{ currentSentence }}</p>
          </div>
        </div>
      </div>
      <!-- 右侧表单区 -->
      <div class="right-section">
        <!-- 登录标题 -->
        <div class="form-header">
          <h2>星主，欢迎回来</h2>
          <p>请登录您的账号</p>
        </div>
        <!-- 登录表单 -->
        <div class="form-content">
          <div class="form-group">
            <input
              type="text"
              v-model="loginForm.username"
              class="form-input"
              :class="{ error: usernameTouched && usernameError }"
              placeholder="请输入账号"
              @blur="handleUsernameBlur"
            >
            <transition name="fade">
              <div v-if="usernameTouched && usernameError" class="error-text">
                {{ usernameError }}
              </div>
            </transition>
          </div>
          <div class="form-group">
            <input
              type="password"
              v-model="loginForm.password"
              class="form-input"
              :class="{ error: passwordTouched && passwordError }"
              placeholder="请输入密码"
              @blur="handlePasswordBlur"
            >
            <transition name="fade">
              <div v-if="passwordTouched && passwordError" class="error-text">
                {{ passwordError }}
              </div>
            </transition>
          </div>
          <button @click="handleLogin" class="submit-btn">立 即 登 录</button>
          <!-- 登录选项链接 -->
          <div class="login-options">
            <router-link to="/forget-password">忘记密码</router-link>
            <router-link to="/register" class="register-link">没有账号？立即注册</router-link>
          </div>
        </div>

        <div class="footer">
          <p>NovaChat 星聊 · 新一代IM</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import request from '@/utils/request'

// 错误消息
const usernameError = ref('')
const passwordError = ref('')

// 触摸状态
const usernameTouched = ref(false)
const passwordTouched = ref(false)

const loginForm = ref({
  username: '',
  password: ''
})

const router = useRouter()  // 用于编程式导航
const route = useRoute()  // 用于获取当前路由信息

const bgParticlesContainer = ref(null)
const cardParticlesContainer = ref(null)

// 打字机效果相关数据
const sentences = [
  "NovaChat — 在星际间建立连接。",
  "跨越光年，只为这一句你好。",
  "每一次发送，都是对星空的告白。",
  "在浩瀚星空中，总有人在等你说晚安。",
  "把想说的话，发射到宇宙深处。"
]
const currentSentence = ref('')
const sentenceIndex = ref(0)
const charIndex = ref(0)
const isDeleting = ref(false)

// 监听账号输入
watch(
    () => loginForm.value.username,
    (newVal) => {
      if (usernameTouched.value) {
        if (!newVal) {
          usernameError.value = '请输入账号'
        } else if (newVal.length < 3 || newVal.length > 20) {
          usernameError.value = '账号长度应在3-20个字符之间'
        } else {
          usernameError.value = ''
        }
      }
    }
)

// 监听密码输入
watch(
    () => loginForm.value.password,
    (newVal) => {
      if (passwordTouched.value) {
        if (!newVal) {
          passwordError.value = '请输入密码'
        } else if (newVal.length < 4) {
          passwordError.value = '密码长度不能少于4个字符'
        } else {
          passwordError.value = ''
        }
      }
    }
)

// 失去焦点处理函数
const handleUsernameBlur = () => {
  usernameTouched.value = true
  if (!loginForm.value.username) {
    usernameError.value = '请输入账号'
  } else if (loginForm.value.username.length < 3 || loginForm.value.username.length > 20) {
    usernameError.value = '账号长度应在3-20个字符之间'
  } else {
    usernameError.value = ''
  }
}

const handlePasswordBlur = () => {
  passwordTouched.value = true
  if (!loginForm.value.password) {
    passwordError.value = '请输入密码'
  } else if (loginForm.value.password.length < 4) {
    passwordError.value = '密码长度不能少于4个字符'
  } else {
    passwordError.value = ''
  }
}

onMounted(() => {
  // 打字机效果
  const typeWriter = () => {
    const current = sentences[sentenceIndex.value]
    
    if (isDeleting.value) {
      // 删除字符
      currentSentence.value = current.substring(0, charIndex.value - 1)
      charIndex.value -= 1
      
      // 删除完成后开始下一句
      if (charIndex.value === 0) {
        isDeleting.value = false
        sentenceIndex.value = (sentenceIndex.value + 1) % sentences.length
      }
    } else {
      // 添加字符
      currentSentence.value = current.substring(0, charIndex.value + 1)
      charIndex.value += 1
      
      // 完成当前句子后暂停，然后开始删除
      if (charIndex.value === current.length) {
        setTimeout(() => {
          isDeleting.value = true
        }, 2000) // 完整句子显示2秒
      }
    }
    
    // 控制打字和删除速度
    const typingSpeed = isDeleting.value ? 50 : 100
    setTimeout(typeWriter, typingSpeed)
  }
  
  // 启动打字机效果
  typeWriter()
  
  // 页面背景星空（更多粒子）
  createParticles(bgParticlesContainer.value, 500)
  // 卡片左侧星空
  createParticles(cardParticlesContainer.value, 150)
})

function createParticles(container, count) {
  if (!container) return
  const colors = ['#ffffff', '#e0f2fe', '#ddd6fe']
  for (let i = 0; i < count; i++) {
    const particle = document.createElement('div')
    const size = Math.random() * 2 + 1
    const opacity = Math.random() * 0.6 + 0.2
    const left = Math.random() * 100
    const top = Math.random() * 100
    const delay = Math.random() * 15
    const colorIndex = Math.floor(Math.random() * colors.length)
    // 直接设置内联样式，避免 scoped CSS 问题
    particle.style.cssText = `
      position: absolute;
      border-radius: 50%;
      width: ${size}px;
      height: ${size}px;
      background-color: ${colors[colorIndex]};
      opacity: ${opacity};
      left: ${left}%;
      top: ${top}%;
      animation: particle-animate 15s infinite ease-in-out;
      animation-delay: ${delay}s;
    `
    container.appendChild(particle)
  }
}

/**
 * 用户登录
 * @returns {Promise<void>}
 */
const handleLogin = async () => {
  // 标记所有字段为已触摸
  usernameTouched.value = true
  passwordTouched.value = true
  // 手动触发一次所有验证
  handleUsernameBlur()
  handlePasswordBlur()
  
  // 检查是否有错误
  if (usernameError.value || passwordError.value) {
    return
  }
  try {
    const response = await request.post('/user/login', loginForm.value)
    if (response.code === 200) {
      // 获取重定向路径，如果没有则默认跳转到首页
      const redirectPath = route.query.redirect || '/home'
      router.push(redirectPath)
    } else {
      ElMessage.error(response.message || '登录失败')
    }
  } catch (error) {
    // 处理网络错误或服务器错误
    if (error.response && error.response.status === 401) {
      ElMessage.error('用户名或密码错误')
    } else {
      ElMessage.error('登录异常')
    }
  }
}

</script>

<style>
/* 粒子动画 - 必须是全局样式才能应用到动态创建的元素 */
@keyframes particle-animate {
  0% {
    transform: translate(0, 0);
    opacity: 0.2;
  }
  50% {
    transform: translate(20px, -20px);
    opacity: 0.8;
  }
  100% {
    transform: translate(0, 0);
    opacity: 0.2;
  }
}
</style>

<style scoped>
/* 页面容器 */
.login-page {
  position: relative;
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 1rem;
  background: linear-gradient(to bottom right, #0f172a, #6899ea);
  overflow: hidden;
}

/* 页面背景星空 */
.bg-particles-container {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: 0;
}

/* 登录卡片 */
.login-card {
  position: relative;
  z-index: 1;
  width: 100%;
  max-width: 64rem;
  display: flex;
  flex-direction: column;
  border-radius: 1.5rem;
  overflow: hidden;
  box-shadow: 0 25px 50px -12px rgba(0, 0, 0, 0.5);
}

@media (min-width: 768px) {
  .login-card {
    flex-direction: row;
  }
}

/* 左侧展示区 */
.left-section {
  position: relative;
  width: 100%;
  height: 16rem;
  overflow: hidden;
  background: linear-gradient(135deg, #1e293b 0%, #162240 100%);
}

@media (min-width: 768px) {
  .left-section {
    width: 60%;
    height: 600px;
  }
}

.particles-container {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
}

.brand-content {
  position: relative;
  z-index: 10;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 100%;
  padding: 0 1.5rem;
}

.brand-icon {
  width: 150px;
  height: 150px;
  font-size: 3rem;
  margin-bottom: 1.5rem;
  padding: 1rem;
  border-radius: 20%;
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(4px);
}

.brand-title {
  color: #ffffff;
  font-size: 1.875rem;
  font-weight: 400;
  letter-spacing: 0.1em;
  margin: 0 0 0.5rem 0;
}

.brand-slogan {
  color: #d1d5db;
  font-size: 1.125rem;
  font-weight: 400;
  margin: 0;
}

.typewriter-container {
  height: 30px;
  margin-top: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.typewriter-text {
  font-size: 1rem;
  color: rgba(255, 255, 255, 0.8);
  font-weight: 300;
  letter-spacing: 0.5px;
  position: relative;
}

.typewriter-text::after {
  content: '|';
  position: absolute;
  right: -10px;
  animation: blink 1s infinite;
}

@keyframes blink {
  0%, 50% {
    opacity: 1;
  }
  51%, 100% {
    opacity: 0;
  }
}

/* 右侧表单区 */
.right-section {
  position: relative;
  width: 100%;
  padding: 2rem;
  background: rgba(29, 45, 85, 0.7);
  backdrop-filter: blur(10px);
}

@media (min-width: 768px) {
  .right-section {
    width: 40%;
  }
}

/* 表单头部 */
.form-header {
  margin-bottom: 2rem;
  text-align: center;
}

.form-header h2 {
  color: #ffffff;
  font-size: 1.5rem;
  font-weight: 600;
  margin: 0 0 0.5rem 0;
}

.form-header p {
  color: #9ca3af;
  font-size: 0.875rem;
  margin: 0;
}

/* 登录选项链接 */
.login-options {
  display: flex;
  justify-content: space-between;
  margin-top: 1rem;
}

.login-options a {
  color: #ffffff;
  font-size: 0.875rem;
  text-decoration: none;
  transition: color 0.3s ease;
}

.login-options a:hover {
  color: skyblue;
  text-decoration: underline;
}

/* 表单样式 */
.form-content {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.form-group {
  display: flex;
  flex-direction: column;
}

.form-input {
  width: 100%;
  padding: 0.75rem 1rem;
  background: rgba(255, 255, 255, 0.05);
  border: 1px solid rgba(255, 255, 255, 0.1);
  border-radius: 0.75rem;
  color: #ffffff;
  font-size: 1rem;
  outline: none;
  transition: all 0.3s ease;
  box-sizing: border-box;
}

.form-input::placeholder {
  color: #6b7280;
}

.form-input:focus {
  border-color: #08f628;
  box-shadow: 0 0 0 3px rgba(99, 102, 241, 0.2);
}

/* 错误文本样式 */
.error-text {
  color: #ff4d4f;
  font-size: 0.8rem;
  margin-top: 0.25rem;
  padding-left: 0.25rem;
  transition: all 0.3s ease;
}

/* 表单输入框错误状态 */
.form-input.error {
  border-color: #ff4d4f;
}

/* 过渡动画 */
.fade-enter-active, .fade-leave-active {
  transition: opacity 0.3s ease;
}
.fade-enter-from, .fade-leave-to {
  opacity: 0;
}

/* 提交按钮 */
.submit-btn {
  width: 100%;
  padding: 0.75rem 1rem;
  margin-top: 0.5rem;
  background: #ffffff; /*linear-gradient(to right, #1592ec, rgba(14, 213, 206, 0.7));*/
  border: none;
  border-radius: 0.75rem;
  color: black;
  font-size: 1rem;
  font-weight: 600;
  cursor: pointer;
  transition: all 0.3s ease;
}

.submit-btn:hover {
  opacity: 0.9;
  transform: translateY(-1px);
}

.submit-btn:active {
  transform: scale(0.98);
}

/* 底部版权 */
.footer {
  position: absolute;
  bottom: 1.5rem;
  left: 2rem;
  right: 2rem;
}

.footer p {
  color: #8a93a6;
  font-size: 1rem;
  font-family:  "SF Pro Display", "PingFang SC", "Microsoft YaHei", "Helvetica Neue", Helvetica, Arial, sans-serif;
  text-align: center;
  margin: 0;
}

</style>