<template>
    <div class="input">
        <div class="input-label">
            <label v-if="label && !boldLabel" :for="id" class="form-label">{{ label }}</label>
            <h5 v-if="label && boldLabel" class="form-label">{{ label }}</h5>
            <span v-if="required" class="required-mark">*</span>
        </div>
      <textarea 
        :id="id" 
        :class="{ 'form-control': true, 'input-error': error }" 
        :placeholder="placeholder"
        :value="modelValue" 
        @input="$emit('update:modelValue', $event.target.value)"
        :rows="rows"
        :cols="cols"
        v-bind="$attrs">
        </textarea>
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
      boldLabel: {
        type: Boolean,
        default: false
    },
      rows: {
        type: Number,
        required: true,
      },
      cols: {
        type: Number,
        required: true,
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