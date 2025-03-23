<script>
export default {
  name: 'TextInput',

  data() {
    return {
      value: null,
      errorMessage: null
    }
  },

  props: {
    id: {
      type: String,
      required: true
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
      type: String
    }
  }
}
</script>

<template>
  <label v-if="label" :for="id" class="form-label">{{ label }}</label>
  <input
    :id="id"
    class="form-control"
    type="text"
    :placeholder="placeholder"
    :value="modelValue"
    @input="$emit('update:modelValue', $event.target.value)"
    v-bind="$attrs"
  />
  <span v-if="error" class="error-text">{{ error }}}</span>
</template>
