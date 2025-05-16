<template>
  <div class="input">
    <label v-if="label" :for="id" class="form-label">{{ label }}</label>
    <span v-if="required" class="required-mark">*</span>
    <input :id="id" :class="{ 'form-control': true, 'input-error': error }" type="text" :placeholder="placeholder"
      :value="modelValue" @input="$emit('update:modelValue', $event.target.value)" @keyup.enter="$emit('enter')"
      v-bind="$attrs" />
    <span v-if="error" class="input-error-text">{{ errorMessage }}</span>
  </div>
</template>

<script>
export default {
  name: 'TextInput',

  data() {
    return {
      value: null,
    }
  },

  props: {
    id: {
      type: String,
      required: true
    },
    required: {
      type: Boolean,
      default: false
    },
    modelValue: {
      type: String,
      default: '',
      required: true
    },
    label: {
      type: String,
      default: ''
    },
    placeholder: {
      type: String,
      default: ''
    },
    error: {
      type: Boolean,
      default: false
    },
    errorMessage: {
      type: String,
      default: null
    }
  }
}
</script>
<style scoped>
.input {
  width: 100%;
  border-radius: 0.25rem;
}
</style>