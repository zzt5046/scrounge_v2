<script>
export default {
  name: 'CheckboxField',

  props: {
    id: {
        type: String,
        required: true
    },
    label: {
        type: String
    },
    placeholder: {
        type: String,
    },
    modelValue: {
        type: Boolean,
        required: true,
    },
  },

  data() {
    return {
      value: null
    }
  },

  methods: {
    toggle() {
      this.value = !this.value
      this.$emit('update:modelValue', this.value)
    },
  },
}
</script>

<template>
    <input
        :id="id"
        type="checkbox"
        class="checkbox-input"
        @change="toggle"
        v-bind="$attrs"
    />
    <span class="checkbox-input-label" :for="id" @click="toggle">
        {{ label }}
    </span>
</template>

<style scoped>
    .checkbox-input {
        cursor: pointer;
        color: #D36135;
    }
    .checkbox-input::before {
        transform-origin: bottom left;
        clip-path: polygon(14% 44%, 0 65%, 50% 100%, 100% 16%, 80% 0%, 43% 62%);
    }
    .checkbox-input-label {
        cursor: pointer;
        margin-left: 0.5rem;
    }
</style>