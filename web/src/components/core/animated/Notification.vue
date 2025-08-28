<template>
  <transition name="fade">
    <div v-if="visible" class="notification" :class="type">
      <span>{{ message }}</span>
      <button class="close-btn" @click="hide">&times;</button>
    </div>
  </transition>
</template>

<script>
export default {
  name: "Notification",
  props: {
    message: { type: String, required: true },
    type: { type: String, default: "success" }, // success, error, info
    duration: { type: Number, default: 5000 } // ms before auto-hide
  },
  data() {
    return { visible: true };
  },
  mounted() {
    setTimeout(() => {
      this.hide();
    }, this.duration);
  },
  methods: {
    hide() {
      this.visible = false;
      this.$emit("close");
    }
  }
};
</script>

<style scoped>
.notification {
  position: fixed;
  top: 1rem;
  right: 1rem;
  padding: 12px 16px;
  border-radius: 6px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.15);
  color: white;
  font-weight: 500;
  display: flex;
  align-items: center;
  gap: 8px;
  z-index: 9999;
}

.notification.success { background-color: var(--vt-c-fawn); }
.notification.error { background-color: #dc3545; }
.notification.info { background-color: var(--vt-c-yellow-gold); }

.close-btn {
  background: transparent;
  border: none;
  font-size: 16px;
  color: white;
  cursor: pointer;
}

.fade-enter-active, .fade-leave-active { transition: opacity 0.3s; }
.fade-enter, .fade-leave-to { opacity: 0; }
</style>