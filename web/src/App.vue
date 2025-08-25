<script>
import Notification from './components/core/animated/Notification.vue';
import { notifications } from "@/notifications.js";
export default {
  name: 'App',
  components: { Notification },

  created() {
    this.$router.push('/')
  },

  setup() {
    return { notifications };
  }
}
</script>

<template>
  <div class="wrapper">
    <RouterView />
    <Notification
      v-for="(toast, index) in notifications.toasts"
      :key="index"
      :message="toast.message"
      :type="toast.type"
      :duration="toast.duration"
      @close="notifications.removeToast(index)"
    />
  </div>
</template>

<style scoped>
</style>
