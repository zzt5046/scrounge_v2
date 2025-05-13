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

  methods: {
    toggle() {
      this.$emit('update:modelValue', !this.modelValue)
    },
  },
}
</script>

<template>
  <input :id="id" type="checkbox" class="checkbox-input" @click="toggle" v-bind="$attrs" :checked="modelValue" />
  <label class="checkbox-input-label" :for="id">
    {{ label }}
  </label>
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