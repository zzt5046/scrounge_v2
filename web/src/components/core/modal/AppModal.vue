<template>
  <dialog :open="show" class="app-modal fade-dialog" @close="close">
    <div class="modal-content">
      <button class="close-button" @click="close">X</button>
      <component :is="component" v-bind="form" />
    </div>
  </dialog>
</template>

<script>
export default {
  name: 'AppModal',
  props: {
    show: {
      type: Boolean,
      required: true
    },
    component: {
      required: false
    },
  },
  methods: {
    close() {
      this.$emit('close-modal')
    },
  }
}
</script>

<!-- <style scoped>
.fade-enter-active, .fade-leave-active {
  transition: opacity 0.3s ease;
}
.fade-enter-from, .fade-leave-to {
  opacity: 0;
}
</style> -->
<style scoped>
::backdrop {
  background-color: rgba(0, 0, 0, 0.5);
  transition: opacity 0.3s ease;
}

/* Fade animation for dialog */
.fade-dialog {
  opacity: 0;
  transition: opacity 0.3s ease;
}

.fade-dialog[open] {
  opacity: 1;
}

.close-button{
  max-width: 1.5rem;
  max-height: 1.5rem;
  float: right;
}
</style>