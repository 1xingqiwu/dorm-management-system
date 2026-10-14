<template>
  <div class="login-container">
    <transition name="fade">
      <div v-if="isLoading" class="loading-overlay">
        <div class="preloader">
          <div class="crack crack1"></div>
          <div class="crack crack2"></div>
          <div class="crack crack3"></div>
          <div class="crack crack4"></div>
          <div class="crack crack5"></div>
        </div>

        <div class="loader-content">
          <div class="loading-text">
            <span class="system-text">正在加载中...</span>
            <span class="percent">{{ loadingProgress }}%</span>
          </div>
          <div class="progress-bar-bg">
            <div class="progress-bar-fill" :style="{ width: loadingProgress + '%' }"></div>
          </div>
        </div>
      </div>
    </transition>

    <div ref="threeContainer" class="three-bg"></div>

    <div class="tips-overlay animate__animated animate__fadeIn" v-if="!isFocused && !isLoading">
      提示：点击星球进入【跟随观察模式】，拖拽登录框可移动
    </div>
    <div class="tips-overlay back-btn animate__animated animate__fadeIn" v-else-if="isFocused" @click="resetCamera">
      <el-icon><Back /></el-icon> 退出跟随模式 (Esc)
    </div>

    <div
        v-show="!isLoading"
        ref="loginBoxRef"
        class="login-box animate__animated animate__fadeInUp"
        :style="boxStyle"
        @mousedown.stop="startDrag"
    >
      <div class="corner top-left"></div>
      <div class="corner top-right"></div>
      <div class="corner bottom-left"></div>
      <div class="corner bottom-right"></div>

      <div class="header">
        <img src="../../../public/logo.png" class="logo" alt="logo">
        <div class="title-group">
          <h2>智慧宿舍管理系统</h2>
        </div>
      </div>

      <el-form :model="loginForm" :rules="rules" ref="loginFormRef" size="large" class="sci-fi-form">
        <el-form-item prop="username">
          <div class="input-wrapper">
            <el-icon><User /></el-icon>
            <input v-model="loginForm.username" type="text" placeholder="请输入学号/工号" />
            <div class="scan-line"></div>
          </div>
        </el-form-item>

        <el-form-item prop="password">
          <div class="input-wrapper">
            <el-icon><Lock /></el-icon>
            <input v-model="loginForm.password" type="password" placeholder="请输入密码" />
            <div class="scan-line"></div>
          </div>
        </el-form-item>

        <el-form-item prop="userType">
          <div class="hologram-switch">
            <div class="glider" :style="{ transform: `translateX(${loginForm.userType * 100}%)` }"></div>
            <div
                v-for="(role, index) in ['学生', '辅导员', '宿管']"
                :key="index"
                class="switch-item"
                :class="{ active: loginForm.userType === index }"
                @click="loginForm.userType = index"
            >
              {{ role }}
            </div>
          </div>
        </el-form-item>

        <button class="cyber-btn" @click.prevent="handleLogin">
          <span class="btn-text">
            <span v-if="loading">正在登录...</span>
            <span v-else>登录</span>
          </span>
          <span class="glitch"></span>
        </button>

        <div class="footer-links">
          <span v-if="isRegisterOpen" class="link register" @click="registerVisible = true">注册账号</span>
          <span class="link forgot" @click="forgotVisible = true">忘记密码?</span>
        </div>
      </el-form>
    </div>

    <!-- 注册弹窗 -->
    <el-dialog v-model="registerVisible" title="注册账号" width="450px" align-center class="sci-fi-dialog">
      <el-form :model="regForm" :rules="regRules" ref="regFormRef" label-width="80px">
        <el-form-item label="学号" prop="username"><el-input v-model="regForm.username" placeholder="请输入学号" /></el-form-item>
        <el-form-item label="姓名" prop="name"><el-input v-model="regForm.name" placeholder="请输入姓名" /></el-form-item>
        <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="regForm.gender">
            <el-radio :label="1">男</el-radio>
            <el-radio :label="2">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="手机" prop="phone"><el-input v-model="regForm.phone" placeholder="请输入手机号" /></el-form-item>
        <el-form-item label="邮箱" prop="email"><el-input v-model="regForm.email" placeholder="请输入邮箱" /></el-form-item>
        <el-form-item label="密码" prop="password"><el-input v-model="regForm.password" type="password" show-password placeholder="设置密码" /></el-form-item>
        <el-form-item label="确认" prop="confirmPass"><el-input v-model="regForm.confirmPass" type="password" show-password placeholder="确认密码" /></el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="registerVisible = false" class="cancel-btn">取消</el-button>
          <el-button type="primary" @click="handleRegister" class="confirm-btn">立即注册</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 找回密码弹窗 -->
    <el-dialog v-model="forgotVisible" title="找回密码" width="450px" align-center class="sci-fi-dialog">
      <el-form :model="resetForm" :rules="resetRules" ref="resetFormRef" label-width="0">
        <el-form-item prop="username">
          <el-input v-model="resetForm.username" placeholder="账号" prefix-icon="User" />
        </el-form-item>
        <el-form-item prop="email">
          <el-input v-model="resetForm.email" placeholder="邮箱" prefix-icon="Message" />
        </el-form-item>
        <el-form-item prop="code">
          <div style="display: flex; width: 100%; gap:15px">
            <el-input v-model="resetForm.code" placeholder="验证码" prefix-icon="Key" style="flex:1" />
            <el-button type="primary" class="code-btn" :disabled="timer > 0" @click="handleSendCode">
              {{ timer > 0 ? `${timer}s` : '获取验证码' }}
            </el-button>
          </div>
        </el-form-item>
        <el-form-item prop="newPassword">
          <el-input v-model="resetForm.newPassword" type="password" placeholder="新密码" prefix-icon="Lock" show-password />
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="forgotVisible = false" class="cancel-btn">取消</el-button>
          <el-button type="primary" @click="handleReset" class="confirm-btn">重置密码</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted, onUnmounted } from 'vue';
import { ElMessage } from 'element-plus';
import { User, Lock, Message, Key, Back } from '@element-plus/icons-vue';
import { loginApi, getUserInfoApi, sendResetCodeApi, resetPasswordByEmailApi, getPublicRegisterStatusApi, registerApi } from '../../api/auth';
import { useUserStore } from '../../store/user';
import { useRouter } from 'vue-router';
import { getMyBuildingsApi } from '../../api/building';
import * as THREE from 'three';
import { OrbitControls } from 'three/examples/jsm/controls/OrbitControls.js';
import { EffectComposer } from 'three/examples/jsm/postprocessing/EffectComposer.js';
import { RenderPass } from 'three/examples/jsm/postprocessing/RenderPass.js';
import { UnrealBloomPass } from 'three/examples/jsm/postprocessing/UnrealBloomPass.js';
import gsap from 'gsap';

// --- 1. 加载动画 ---
const isLoading = ref(true);
const loadingProgress = ref(0);

const loadingManager = new THREE.LoadingManager();
loadingManager.onProgress = (url, itemsLoaded, itemsTotal) => {
  const progress = (itemsLoaded / itemsTotal) * 100;
  gsap.to(loadingProgress, { value: Math.round(progress), duration: 0.2, onUpdate: () => { loadingProgress.value = Math.round(loadingProgress.value); } });
};
loadingManager.onLoad = () => {
  setTimeout(() => { isLoading.value = false; }, 800);
};

// --- 2. 星球配置 ---
const planetConfig = [
  { name: 'mercury', texture: '8k_moon', size: 3, distance: 70, speed: 0.01 },
  { name: 'venus', texture: '8k_venus_surface', size: 5.8, distance: 100, speed: 0.008 },
  { name: 'earth', texture: '8k_earth_daymap', size: 6, distance: 140, speed: 0.006, hasMoon: true },
  { name: 'mars', texture: '8k_mars', size: 4, distance: 190, speed: 0.005 },
  { name: 'jupiter', texture: '8k_saturn', size: 18, distance: 280, speed: 0.003 },
  { name: 'uranus', texture: '2k_neptune', size: 10, distance: 480, speed: 0.002 },
  { name: 'neptune', texture: '2k_neptune', size: 10, distance: 560, speed: 0.0015 },
];

// --- 基础业务 ---
const loginFormRef = ref(null);
const loading = ref(false);
const userStore = useUserStore();
const router = useRouter();
const isRegisterOpen = ref(false);
const registerVisible = ref(false);
const regFormRef = ref(null);
const forgotVisible = ref(false);
const resetFormRef = ref(null);
const timer = ref(0);
const loginBoxRef = ref(null);
const loginForm = reactive({ username: '', password: '', userType: 0 });
const rules = { username: [{required:true, message:'请输入账号', trigger:'blur'}], password: [{required:true, message:'请输入密码', trigger:'blur'}] };
const regForm = reactive({ username: '', name: '', gender: 1, phone: '', email: '', password: '', confirmPass: '' });
const validatePass2 = (rule, value, callback) => { if (value !== regForm.password) callback(new Error('两次密码不一致')); else callback(); };
const regRules = { username: [{required:true}], name: [{required:true}], phone: [{required:true}], email: [{required:true, type:'email'}], password: [{required:true, min:6}], confirmPass: [{validator:validatePass2}] };
const resetForm = reactive({ username: '', email: '', code: '', newPassword: '' });
const resetRules = { username: [{required:true}], email: [{required:true, type:'email'}], code: [{required:true}], newPassword: [{required:true, min:6}] };

const handleLogin = () => {
  if (!loginFormRef.value) return;
  loginFormRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true;
      try {
        const token = await loginApi(loginForm);
        userStore.setToken(token);
        const userInfo = await getUserInfoApi();
        const realUserType = userInfo.userType;
        userStore.setUserInfo(userInfo, realUserType);
        ElMessage.success('登录成功');

        if (realUserType === 3) router.push('/admin/settings');
        else if (realUserType === 2) {
          try {
            const buildings = await getMyBuildingsApi();
            userStore.setBuildings(buildings);
            if (buildings && buildings.length > 0) router.push(`/manager/building/${buildings[0].buildingId}`);
            else router.push('/manager/repair-audit');
          } catch(e) { router.push('/manager/repair-audit'); }
        }
        else if (realUserType === 1) router.push('/counselor/dashboard');
        else router.push('/student/dashboard');
      } catch (error) { console.error(error); } finally { loading.value = false; }
    }
  });
};
const handleRegister = () => { regFormRef.value.validate(async (valid) => { if (valid) { await registerApi(regForm); ElMessage.success('注册成功'); registerVisible.value = false; } }); };
const handleSendCode = async () => { if(!resetForm.username || !resetForm.email) { ElMessage.warning("请先填写账号和邮箱"); return; } await sendResetCodeApi(resetForm.username, resetForm.email); ElMessage.success("验证码已发送"); timer.value = 60; const interval = setInterval(() => { timer.value--; if (timer.value <= 0) clearInterval(interval); }, 1000); };
const handleReset = () => { resetFormRef.value.validate(async (valid) => { if(valid) { await resetPasswordByEmailApi(resetForm); ElMessage.success("密码重置成功"); forgotVisible.value = false; }}); };

// --- 拖拽逻辑 ---
const boxStyle = reactive({ top: '50%', left: '50%', transform: 'translate(-50%, -50%)', margin: '0' });
let isDragging = false;
let dragOffset = { x: 0, y: 0 };
const startDrag = (e) => {
  if (['INPUT', 'BUTTON', 'A', 'I', 'SPAN'].includes(e.target.tagName)) return;
  isDragging = true;
  const box = loginBoxRef.value;
  const rect = box.getBoundingClientRect();
  dragOffset.x = e.clientX - rect.left;
  dragOffset.y = e.clientY - rect.top;
  boxStyle.transform = 'none'; boxStyle.margin = '0'; boxStyle.left = `${rect.left}px`; boxStyle.top = `${rect.top}px`;
  document.addEventListener('mousemove', onDragMove); document.addEventListener('mouseup', stopDrag);
};
const onDragMove = (e) => {
  if (!isDragging) return;
  const maxX = window.innerWidth - 420;
  const maxY = window.innerHeight - 500;
  let newX = e.clientX - dragOffset.x; let newY = e.clientY - dragOffset.y;
  if (newX < 0) newX = 0; if (newX > maxX) newX = maxX; if (newY < 0) newY = 0; if (newY > maxY) newY = maxY;
  boxStyle.left = `${newX}px`; boxStyle.top = `${newY}px`;
};
const stopDrag = () => { isDragging = false; document.removeEventListener('mousemove', onDragMove); document.removeEventListener('mouseup', stopDrag); };

// --- 3D 场景 ---
const threeContainer = ref(null);
let scene, camera, renderer, composer, controls;
let sun, asteroidBelt;
const planets = [];
const textureLoader = new THREE.TextureLoader(loadingManager);
let currentFocusTarget = null;
let isTransitioning = false;
const raycaster = new THREE.Raycaster();
const mouse = new THREE.Vector2();
const isFocused = ref(false);
let mouseDownPos = { x: 0, y: 0 };

const initThree = () => {
  scene = new THREE.Scene();
  const bgTexture = textureLoader.load('/textures/8k_stars_milky_way.jpg');
  bgTexture.mapping = THREE.EquirectangularReflectionMapping;
  bgTexture.colorSpace = THREE.SRGBColorSpace;
  scene.background = bgTexture;
  scene.environment = bgTexture;

  camera = new THREE.PerspectiveCamera(45, window.innerWidth / window.innerHeight, 0.1, 200000);
  // 初始视角：俯视高位
  camera.position.set(-393, 81, -119);
  camera.lookAt(0, 0, 0);

  // 调试：节流打印计数器
  // let logCounter = 0;
  //
  // const animate = () => {
  //   requestAnimationFrame(animate);
  //   sun.rotation.y += 0.001;
  //   planets.forEach(p => {
  //     const oldPos = p.mesh.position.clone();
  //     p.angle += p.speed;
  //     p.mesh.position.x = Math.cos(p.angle) * p.distance;
  //     p.mesh.position.z = Math.sin(p.angle) * p.distance;
  //     p.mesh.rotation.y += p.selfSpeed;
  //     if (currentFocusTarget && p.mesh === currentFocusTarget && !isTransitioning) {
  //       const delta = p.mesh.position.clone().sub(oldPos);
  //       camera.position.add(delta);
  //       controls.target.add(delta);
  //     }
  //   });
  //   if (asteroidBelt) asteroidBelt.rotation.y += 0.0002;
  //   controls.update();
  //   composer.render();
  //
  //   // 调试代码：每 60 帧 (约1秒) 打印一次视角参数
  //   logCounter++;
  //   if (logCounter > 60) {
  //     logCounter = 0;
  //     console.log('--- 摄像机视角调试参数 ---');
  //     console.log(`camera.position.set(${Math.round(camera.position.x)}, ${Math.round(camera.position.y)}, ${Math.round(camera.position.z)});`);
  //     // 如果你需要 controls.target 的位置，也可以取消注释下面这行
  //     console.log(`controls.target.set(${Math.round(controls.target.x)}, ${Math.round(controls.target.y)}, ${Math.round(controls.target.z)});`);
  //   }
  // };

  renderer = new THREE.WebGLRenderer({ antialias: true, logarithmicDepthBuffer: true });
  renderer.setSize(window.innerWidth, window.innerHeight);
  renderer.setPixelRatio(window.devicePixelRatio); // 恢复原始像素比，为了画质
  renderer.shadowMap.enabled = true;
  renderer.shadowMap.type = THREE.PCFSoftShadowMap;
  threeContainer.value.appendChild(renderer.domElement);

  controls = new OrbitControls(camera, renderer.domElement);
  controls.enableDamping = true;
  controls.dampingFactor = 0.05;
  controls.enableZoom = true;   // 启用鼠标滚轮缩放
  controls.enablePan = false;
  controls.minDistance = 5;
  controls.maxDistance = 5000;

  const renderScene = new RenderPass(scene, camera);
  const bloomPass = new UnrealBloomPass(new THREE.Vector2(window.innerWidth, window.innerHeight), 1.5, 0.4, 0.85);
  bloomPass.threshold = 0.6;
  bloomPass.strength = 1.2;
  bloomPass.radius = 0;
  composer = new EffectComposer(renderer);
  composer.addPass(renderScene);
  composer.addPass(bloomPass);

  const sunGeo = new THREE.SphereGeometry(40, 64, 64);
  const sunTex = textureLoader.load('/textures/8k_sun.jpg');
  const sunMat = new THREE.MeshBasicMaterial({ map: sunTex });
  sun = new THREE.Mesh(sunGeo, sunMat);
  sun.userData = { isSun: true, radius: 40 };
  scene.add(sun);

  const sunLight = new THREE.PointLight(0xffffff, 2.0, 5000, 0);
  sunLight.castShadow = true;
  // 恢复高画质阴影
  sunLight.shadow.mapSize.width = 2048;
  sunLight.shadow.mapSize.height = 2048;
  sun.add(sunLight);

  const ambientLight = new THREE.AmbientLight(0x404040, 0.4);
  scene.add(ambientLight);

  planetConfig.forEach(cfg => {
    createPlanet(cfg.texture, cfg.size, cfg.distance, cfg.speed, cfg.hasMoon);
  });
  createAsteroidBelt();
  createSaturn(15, 380, 0.0025);
  [70, 100, 140, 190, 280, 380, 480, 560].forEach(r => createOrbit(r));

  renderer.domElement.addEventListener('mousedown', (e) => { mouseDownPos.x = e.clientX; mouseDownPos.y = e.clientY; });
  renderer.domElement.addEventListener('mouseup', onCanvasMouseUp);
  window.addEventListener('resize', onWindowResize);
  animate();
};

const createAsteroidBelt = () => {
  // 陨石数量4000
  const count = 4000;
  const geometry = new THREE.DodecahedronGeometry(0.5, 0);
  const material = new THREE.MeshStandardMaterial({ color: 0xaaaaaa, roughness: 0.8, metalness: 0.1 });
  asteroidBelt = new THREE.InstancedMesh(geometry, material, count);
  const dummy = new THREE.Object3D();
  for (let i = 0; i < count; i++) {
    const r = 210 + Math.random() * 50;
    const angle = Math.random() * Math.PI * 2;
    const y = (Math.random() - 0.5) * 15;
    dummy.position.set(Math.cos(angle) * r, y, Math.sin(angle) * r);
    dummy.rotation.set(Math.random() * Math.PI, Math.random() * Math.PI, Math.random() * Math.PI);
    const scale = 0.5 + Math.random() * 1.5;
    dummy.scale.set(scale, scale, scale);
    dummy.updateMatrix();
    asteroidBelt.setMatrixAt(i, dummy.matrix);
  }
  asteroidBelt.castShadow = false;
  asteroidBelt.receiveShadow = true;
  scene.add(asteroidBelt);
};

const createPlanet = (textureName, size, distance, speed, hasMoon = false) => {
  const geo = new THREE.SphereGeometry(size, 64, 64);
  const tex = textureLoader.load(`/textures/${textureName}.jpg`);
  const mat = new THREE.MeshStandardMaterial({ map: tex, roughness: 0.8, metalness: 0.1 });
  const mesh = new THREE.Mesh(geo, mat);
  mesh.castShadow = false; mesh.receiveShadow = true;
  mesh.userData = { isPlanet: true, radius: size };
  const planetObj = { mesh, distance, speed, angle: Math.random() * Math.PI * 2, selfSpeed: 0.005 + Math.random() * 0.01 };
  planets.push(planetObj);
  scene.add(mesh);
  if (hasMoon) {
    const moonGeo = new THREE.SphereGeometry(1.5, 32, 32);
    const moonTex = textureLoader.load('/textures/8k_moon.jpg');
    const moonMat = new THREE.MeshStandardMaterial({ map: moonTex });
    const moon = new THREE.Mesh(moonGeo, moonMat);
    moon.position.x = size + 5; moon.castShadow = true; moon.receiveShadow = true;
    mesh.add(moon);
  }
  return mesh;
};

const createSaturn = (size, distance, speed) => {
  const mesh = createPlanet('8k_saturn', size, distance, speed);
  mesh.userData = { isPlanet: true, radius: size + 10 };
  const ringGeo = new THREE.RingGeometry(size + 3, size + 12, 64);
  const ringTex = textureLoader.load('/textures/8k_saturn_ring_alpha.png');
  ringTex.rotation = Math.PI / 2; ringTex.center.set(0.5, 0.5);
  const ringMat = new THREE.MeshBasicMaterial({ map: ringTex, color: 0xffffff, side: THREE.DoubleSide, transparent: true, opacity: 0.9 });
  const ring = new THREE.Mesh(ringGeo, ringMat);
  ring.rotation.x = Math.PI / 2.5; ring.receiveShadow = true;
  mesh.add(ring);
};

const createOrbit = (radius) => {
  const curve = new THREE.EllipseCurve(0, 0, radius, radius, 0, 2 * Math.PI, false, 0);
  const points = curve.getPoints(128);
  const geometry = new THREE.BufferGeometry().setFromPoints(points);
  const material = new THREE.LineBasicMaterial({ color: 0x666666, transparent: true, opacity: 0.3 });
  const orbit = new THREE.Line(geometry, material);
  orbit.rotation.x = -Math.PI / 2;
  scene.add(orbit);
};

const onCanvasMouseUp = (event) => {
  const diffX = Math.abs(event.clientX - mouseDownPos.x);
  const diffY = Math.abs(event.clientY - mouseDownPos.y);
  if (diffX > 5 || diffY > 5) return;
  const rect = renderer.domElement.getBoundingClientRect();
  mouse.x = ((event.clientX - rect.left) / rect.width) * 2 - 1;
  mouse.y = -((event.clientY - rect.top) / rect.height) * 2 + 1;
  raycaster.setFromCamera(mouse, camera);
  const intersects = raycaster.intersectObjects(scene.children);
  if (intersects.length > 0) {
    const object = intersects[0].object;
    if (object.userData.isPlanet || object.userData.isSun) {
      focusOnPlanet(object);
      return;
    }
  }
  resetCamera();
};

const focusOnPlanet = (targetMesh) => {
  if (isTransitioning) return;
  isTransitioning = true;
  isFocused.value = true;
  currentFocusTarget = targetMesh;
  const startTarget = controls.target.clone();
  const startCamPos = camera.position.clone();
  const radius = targetMesh.userData.radius || 20;
  const offsetVec = new THREE.Vector3(radius * 3, radius * 1, radius * 3);
  const anim = { t: 0 };
  gsap.to(anim, {
    t: 1, duration: 2.0, ease: "power3.inOut",
    onUpdate: () => {
      const currentPlanetPos = targetMesh.position.clone();
      const idealCamPos = currentPlanetPos.clone().add(offsetVec);
      controls.target.lerpVectors(startTarget, currentPlanetPos, anim.t);
      camera.position.lerpVectors(startCamPos, idealCamPos, anim.t);
      controls.update();
    },
    onComplete: () => { isTransitioning = false; }
  });
};

const resetCamera = () => {
  if (isTransitioning) return;
  isTransitioning = true;
  isFocused.value = false;
  currentFocusTarget = null;
  gsap.to(controls.target, { x: 0, y: 0, z: 0, duration: 2.0, ease: "power3.inOut" });

  // 重置回高位俯视
  gsap.to(camera.position, { x: -393, y: 81, z: -119, duration: 2.0, ease: "power3.inOut",
    onUpdate: () => controls.update(),
    onComplete: () => { isTransitioning = false; }
  });
};

const onWindowResize = () => {
  camera.aspect = window.innerWidth / window.innerHeight;
  camera.updateProjectionMatrix();
  renderer.setSize(window.innerWidth, window.innerHeight);
  composer.setSize(window.innerWidth, window.innerHeight);
};

const animate = () => {
  requestAnimationFrame(animate);
  sun.rotation.y += 0.001;

  planets.forEach(p => {
    const oldPos = p.mesh.position.clone();
    p.angle += p.speed;
    p.mesh.position.x = Math.cos(p.angle) * p.distance;
    p.mesh.position.z = Math.sin(p.angle) * p.distance;
    p.mesh.rotation.y += p.selfSpeed;
    if (currentFocusTarget && p.mesh === currentFocusTarget && !isTransitioning) {
      const delta = p.mesh.position.clone().sub(oldPos);
      camera.position.add(delta);
      controls.target.add(delta);
    }
  });
  if (asteroidBelt) asteroidBelt.rotation.y += 0.0002;
  controls.update();
  composer.render();
};

onMounted(async () => {
  try { const res = await getPublicRegisterStatusApi(); isRegisterOpen.value = res; } catch (e) {}
  initThree();
  window.addEventListener('keydown', (e) => { if (e.key === 'Escape') resetCamera(); });
});

onUnmounted(() => {
  window.removeEventListener('resize', onWindowResize);
  if (renderer && renderer.domElement) {
    renderer.domElement.removeEventListener('mousedown', (e) => {mouseDownPos.x = e.clientX; mouseDownPos.y = e.clientY;});
    renderer.domElement.removeEventListener('mouseup', onCanvasMouseUp);
  }
  if (renderer) { renderer.dispose(); renderer.forceContextLoss(); }
  if (controls) controls.dispose();
  if (scene) {
    scene.traverse((child) => {
      if (child.geometry) child.geometry.dispose();
      if (child.material) {
        if (Array.isArray(child.material)) {
          child.material.forEach(m => { if(m.map) m.map.dispose(); m.dispose(); });
        } else {
          if (child.material.map) child.material.map.dispose();
          child.material.dispose();
        }
      }
    });
  }
});
</script>

<style scoped lang="scss">
/* 容器 */
.login-container {
  height: 100vh;
  width: 100vw;
  position: relative;
  overflow: hidden;
  background: #000;
}
.three-bg {
  position: absolute;
  top: 0; left: 0; width: 100%; height: 100%;
  z-index: 1;
}

/* 加载层 */
.loading-overlay {
  position: absolute;
  top: 0; left: 0; width: 100%; height: 100%;
  background: #000;
  z-index: 9999;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}

/* 动画 */
.preloader {
  position: relative;
  width: 200px; /* 控制大小 */
  height: 200px;
  display: flex;
  align-items: center;
  justify-content: center;
  filter: drop-shadow(0 0 2px #fff);
  margin-bottom: 30px;
}

.crack {
  position: absolute;
  width: 100%;
  height: 100%;
  background-color: #fef3fc;
  clip-path: polygon(50% 0%, 61% 35%, 98% 35%, 68% 57%, 79% 91%, 50% 70%, 21% 91%, 32% 57%, 2% 35%, 39% 35%);
  animation: rotate 6s infinite linear;
}

.crack2 { width: 90%; animation-delay: -1s; opacity: 0.8; }
.crack3 { width: 80%; animation-delay: -2s; opacity: 0.6; }
.crack4 { width: 70%; animation-delay: -3s; opacity: 0.4; }
.crack5 { width: 60%; animation-delay: -4s; opacity: 0.2; }

@keyframes rotate {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}

.loader-content {
  position: relative;
  z-index: 2;
  text-align: center;
  width: 400px;
}

.loading-text {
  display: flex; justify-content: space-between; color: var(--color-primary);
  font-family: 'Courier New', monospace; font-size: 14px; margin-bottom: 8px;
  .percent { font-weight: bold; color: #fff; }
}
.progress-bar-bg {
  width: 100%; height: 4px; background: rgba(255, 255, 255, 0.1); border-radius: 2px; overflow: hidden;
}
.progress-bar-fill {
  height: 100%; background: linear-gradient(90deg, var(--color-primary), #00f2ff); box-shadow: 0 0 10px var(--color-primary); transition: width 0.1s linear;
}
.loading-detail {
  margin-top: 8px; color: rgba(255, 255, 255, 0.4); font-size: 12px;
}

.fade-leave-active { transition: opacity 0.8s; }
.fade-leave-to { opacity: 0; }

/* 提示文字 */
.tips-overlay {
  position: absolute;
  top: 20px;
  left: 50%;
  transform: translateX(-50%);
  z-index: 5;
  color: rgba(255, 255, 255, 0.6);
  font-size: 14px;
  background: rgba(0, 0, 0, 0.3);
  padding: 8px 20px;
  border-radius: 20px;
  backdrop-filter: blur(4px);
  pointer-events: none;

  &.back-btn {
    pointer-events: auto;
    cursor: pointer;
    background: rgba(64, 158, 255, 0.2);
    border: 1px solid rgba(64, 158, 255, 0.5);
    color: var(--color-primary);
    display: flex;
    align-items: center;
    gap: 5px;
    transition: 0.3s;
    &:hover { background: rgba(64, 158, 255, 0.4); }
  }
}

/* 登录卡片 */
.login-box {
  position: absolute;
  top: 50%;
  left: 50%; /* 居中 */
  transform: translate(-50%, -50%);
  z-index: 10;
  width: 420px;
  padding: 40px;
  background: rgba(13, 20, 40, 0.75);
  backdrop-filter: blur(15px);
  border: 1px solid rgba(64, 158, 255, 0.3);
  border-radius: 12px;
  box-shadow: 0 0 30px rgba(0, 0, 0, 0.5), inset 0 0 20px rgba(64, 158, 255, 0.05);
  cursor: grab;
  user-select: none;
  transition: box-shadow 0.3s;

  &:active {
    cursor: grabbing;
    box-shadow: 0 0 50px rgba(64, 158, 255, 0.2);
  }
}

/* 📱 移动端适配：保持悬浮卡片风格 */
@media screen and (max-width: 768px) {
  .login-box {
    /* 1. 宽度控制：不占满，留出边距 */
    width: 85% !important;
    max-width: 380px;

    /* 2. 高度控制：自适应，不占满全屏 */
    height: auto !important;
    min-height: auto !important;
    padding: 30px 20px !important;

    /* 3. 强制居中定位 (覆盖可能的 JS 拖拽残留位置) */
    /* 注意：如果你想让它能被拖拽移动，可以去掉 !important，但初始化时建议居中 */
    position: absolute !important;
    top: 50% !important;
    left: 50% !important;
    transform: translate(-50%, -50%) !important;
    margin: 0 !important;

    /* 4. 外观保持 */
    border-radius: 16px !important;
    background: rgba(13, 20, 40, 0.8); /* 稍微加深背景，保证文字清晰 */
  }

  /* 隐藏 PC 端的操作提示，手机屏幕小放不下 */
  .tips-overlay {
    display: none !important;
  }

  /* 调整内部元素大小 */
  .header {
    margin-bottom: 20px;
    .logo { width: 50px; }
    .title-group h2 { font-size: 20px; }
  }

  /* 弹窗适配：防止弹窗太大超出手机屏幕 */
  :deep(.sci-fi-dialog) {
    width: 90% !important;
  }
}

/* 装饰角标 */
.corner {
  position: absolute;
  width: 20px;
  height: 20px;
  border: 2px solid var(--color-primary);
  transition: 0.3s;
}
.top-left { top: -2px; left: -2px; border-bottom: none; border-right: none; }
.top-right { top: -2px; right: -2px; border-bottom: none; border-left: none; }
.bottom-left { bottom: -2px; left: -2px; border-top: none; border-right: none; }
.bottom-right { bottom: -2px; right: -2px; border-top: none; border-left: none; }

.login-box:hover .corner {
  width: 30px; height: 30px;
  box-shadow: 0 0 10px var(--color-primary);
}

.header {
  text-align: center;
  margin-bottom: 35px;
  .logo {
    width: 60px;
    margin-bottom: 10px;
    filter: drop-shadow(0 0 5px rgba(64,158,255,0.8));
    animation: floatLogo 3s ease-in-out infinite;
  }
  .title-group h2 {
    margin: 0;
    color: #fff;
    font-size: 24px;
    letter-spacing: 2px;
    text-shadow: 0 0 10px rgba(64,158,255,0.5);
  }
  .sub-title {
    margin: 5px 0;
    color: var(--color-primary);
    font-size: 10px;
    letter-spacing: 4px;
    opacity: 0.8;
  }
}

@keyframes floatLogo {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-5px); }
}

.input-wrapper {
  position: relative;
  width: 100%;
  display: flex;
  align-items: center;
  margin-bottom: 10px;
  .el-icon { position: absolute; left: 12px; color: var(--color-primary); font-size: 18px; z-index: 2; }
  input {
    width: 100%;
    background: rgba(0, 0, 0, 0.3);
    border: 1px solid rgba(255, 255, 255, 0.1);
    border-radius: 4px;
    padding: 12px 12px 12px 42px;
    color: #fff;
    font-size: 15px;
    outline: none;
    transition: 0.3s;
    font-family: 'Courier New', monospace;
    &::placeholder { color: rgba(255,255,255,0.2); }
    &:focus { border-color: var(--color-primary); background: rgba(0, 0, 0, 0.5); box-shadow: 0 0 15px rgba(64, 158, 255, 0.2); }
  }
  .scan-line {
    position: absolute;
    bottom: 0; left: 0;
    width: 0%; height: 2px;
    background: var(--color-primary);
    transition: 0.3s;
    box-shadow: 0 0 10px var(--color-primary);
  }
  input:focus ~ .scan-line { width: 100%; }
}

.hologram-switch {
  position: relative;
  display: flex;
  width: 100%;
  height: 40px;
  background: rgba(0,0,0,0.5);
  border: 1px solid rgba(255,255,255,0.1);
  border-radius: 4px;
  margin-top: 10px;
  .switch-item {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 2;
    color: rgba(255,255,255,0.5);
    cursor: pointer;
    font-size: 14px;
    transition: 0.3s;
    &.active { color: #fff; text-shadow: 0 0 8px var(--color-primary); font-weight: bold; }
    &:hover:not(.active) { color: #ccc; }
  }
  .glider {
    position: absolute;
    top: 2px; left: 2px;
    width: calc((100% - 4px) / 3);
    height: calc(100% - 4px);
    background: rgba(64, 158, 255, 0.2);
    border: 1px solid var(--color-primary);
    box-shadow: inset 0 0 10px rgba(64, 158, 255, 0.5);
    z-index: 1;
    transition: transform 0.3s cubic-bezier(0.645, 0.045, 0.355, 1);
  }
}

.cyber-btn {
  width: 100%;
  margin-top: 30px;
  height: 48px;
  background: linear-gradient(90deg, transparent 5%, var(--color-primary) 5%, var(--color-primary) 95%, transparent 95%);
  border: none;
  color: #fff;
  font-size: 16px;
  letter-spacing: 2px;
  cursor: pointer;
  position: relative;
  clip-path: polygon(10px 0, 100% 0, 100% calc(100% - 10px), calc(100% - 10px) 100%, 0 100%, 0 10px);
  transition: 0.3s;
  font-family: 'Arial', sans-serif;
  font-weight: bold;
  &:hover {
    background: linear-gradient(90deg, transparent 0%, #66b1ff 0%, #66b1ff 100%, transparent 100%);
    text-shadow: 0 0 10px #fff;
    transform: scale(1.02);
  }
  .glitch {
    position: absolute;
    top: 0; left: 0; width: 100%; height: 100%;
    background: transparent;
    z-index: -1;
  }
}

.footer-links {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
  font-size: 12px;
  .link {
    color: rgba(255,255,255,0.4);
    cursor: pointer;
    transition: 0.3s;
    &:hover { color: var(--color-primary); text-shadow: 0 0 5px var(--color-primary); }
  }
}

/* 弹窗样式 */
:deep(.sci-fi-dialog) {
  background: rgba(16, 28, 48, 0.85) !important;
  backdrop-filter: blur(20px);
  border: 1px solid rgba(64, 158, 255, 0.3);
  box-shadow: 0 0 50px rgba(0, 0, 0, 0.8), inset 0 0 20px rgba(64, 158, 255, 0.05);
  border-radius: 16px;
  .el-dialog__header {
    margin-right: 0;
    border-bottom: 1px solid rgba(255, 255, 255, 0.1);
    padding-bottom: 20px;
    .el-dialog__title { color: #fff; font-size: 20px; letter-spacing: 2px; text-shadow: 0 0 10px rgba(64, 158, 255, 0.5); }
    .el-dialog__close { color: rgba(255, 255, 255, 0.6); font-size: 20px; &:hover { color: var(--color-primary); transform: rotate(90deg); transition: 0.3s; } }
  }
  .el-dialog__body { padding: 30px 25px 10px 25px; }
  .el-form-item__label { color: rgba(255, 255, 255, 0.7); }
  .el-input__wrapper {
    background-color: rgba(0, 0, 0, 0.3) !important;
    box-shadow: none !important;
    border: 1px solid rgba(255, 255, 255, 0.15);
    border-radius: 6px;
    transition: 0.3s;
    .el-input__inner { color: #fff; &::placeholder { color: rgba(255, 255, 255, 0.3); } }
    .el-input__prefix-inner { color: rgba(255, 255, 255, 0.5); }
    &.is-focus { border-color: var(--color-primary); box-shadow: 0 0 15px rgba(64, 158, 255, 0.3) !important; background-color: rgba(0, 0, 0, 0.5) !important; }
  }
  .el-radio {
    color: rgba(255, 255, 255, 0.8);
    margin-right: 20px;
    .el-radio__inner { background: transparent; border-color: rgba(255, 255, 255, 0.4); }
    &.is-checked {
      .el-radio__inner { background: var(--color-primary); border-color: var(--color-primary); box-shadow: 0 0 8px var(--color-primary); }
      .el-radio__label { color: var(--color-primary); font-weight: bold; }
    }
  }
  .dialog-footer { display: flex; justify-content: flex-end; gap: 15px; padding-top: 10px; }
  .el-button { border-radius: 20px; padding: 10px 25px; font-weight: bold; letter-spacing: 1px; }
  .cancel-btn {
    background: transparent; border: 1px solid rgba(255, 255, 255, 0.3); color: rgba(255, 255, 255, 0.8);
    &:hover { border-color: #fff; color: #fff; background: rgba(255, 255, 255, 0.1); }
  }
  .confirm-btn {
    background: linear-gradient(90deg, var(--color-primary), #3a8ee6); border: none; box-shadow: 0 4px 15px rgba(64, 158, 255, 0.4);
    &:hover { transform: translateY(-2px); box-shadow: 0 6px 20px rgba(64, 158, 255, 0.6); }
  }
  .code-btn {
    background: rgba(64, 158, 255, 0.2); border: 1px solid var(--color-primary); color: var(--color-primary);
    &:hover { background: var(--color-primary); color: #fff; box-shadow: 0 0 10px var(--color-primary); }
    &.is-disabled { background: rgba(255, 255, 255, 0.1); border-color: rgba(255, 255, 255, 0.2); color: rgba(255, 255, 255, 0.4); }
  }
}
</style>