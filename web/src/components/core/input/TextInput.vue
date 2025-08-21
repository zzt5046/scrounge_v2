<template>
  <div class="input">
    <div class="input-label">
      <label v-if="label && !boldLabel" :for="id" class="form-label">{{ label }}</label>
      <h5 v-if="label && boldLabel" class="form-label">{{ label }}</h5>
      <span v-if="required" class="required-mark">*</span>
    </div>
    <input :id="id" :class="{ 'form-control': true, 'input-error': error }" :type="type" :placeholder="placeholder"
      :value="modelValue" @input="$emit('update:modelValue', $event.target.value)" @keyup.enter="$emit('enter')"
      :min="min" :max="max" v-bind="$attrs" />
      <div class="error-height">
        <span class="input-error-text">{{ errorMessage }}</span>
      </div>
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
    type: {
      type: String,
      default: 'text',
      validator: (value) => {
        return ['search', 'text'].includes(value);
      }
    },
    min: {
      type: Number,
      default: null
    },
    max: {
      type: Number,
      default: null
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
    boldLabel: {
      type: Boolean,
      default: false
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
  
  /* try to pad the input and error so nothing jumps around */
  .error-height {
    height: 0.5rem;
  }
</style>