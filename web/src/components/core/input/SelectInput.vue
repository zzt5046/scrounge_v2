<template>
  <div>
    <label v-if="label" for="selectInput" class="form-label">{{ label }}</label>
    <span v-if="required" class="required-mark">*</span>
    <select :id=id :class="{ 'form-select': true, 'input-error': error }"
      @change="this.$emit('update:modelValue', $event.target.value)" :value="modelValue" v-bind="$attrs">

      <option disabled value>{{ placeholder }}</option>
      <option value="" v-if="showNullOption">{{ nullOptionLabel }}</option>
      <option v-for="option in options" :key="option.id" :value="option.id" :selected="autoselect(option)">
        {{ option.name }}
      </option>
    </select>
    <span v-if="error" class="input-error-text">{{ errorMessage }}</span>
  </div>
</template>

<script>
export default {
  name: 'SelectInput',

  data() {
    return {
      value: null
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
    options: {
      type: Array,
      required: true
    },
    showNullOption: {
      type: Boolean,
      default: false
    },
    nullOptionLabel: {
      type: String,
      default: 'N/A'
    },
    label: {
      type: String
    },
    placeholder: {
      type: String,
      default: 'Select an option'
    },
    error: {
      type: Boolean,
      default: false
    },
    errorMessage: {
      type: String,
      default: null
    },
    modelValue: {
      type: String,
      default: null
    }
  },

  methods: {
    autoselect(option) {
      return (option.id == this.modelValue)
    }
  },
}
</script>