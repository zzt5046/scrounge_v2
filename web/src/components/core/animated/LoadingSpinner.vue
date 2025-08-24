<template>
  <div
    class="spinner"
    :style="{
      width: size + 'px',
      height: size + 'px',
      border: lineSize + 'px solid',
      borderColor: color + ' transparent transparent transparent'
    }"
  ></div>
</template>

<script>
export default {
  name: 'LoadingSpinner',
  props: {
    size: {
      type: Number,
      default: 40
    },
    lineSize: {
      type: Number,
      default: 4
    },
    color: {
      type: String,
      default: 'black'
    }
  }
}
</script>

<style scoped>
    .spinner {
        border-radius: 50%;
        display: inline-block;
        animation: spin 1s linear infinite;
        border-color: gray transparent transparent transparent;
    }
    @keyframes spin {
        0% {
            transform: rotate(0deg);
        }
        100% {
            transform: rotate(360deg);
        }
    }
</style>