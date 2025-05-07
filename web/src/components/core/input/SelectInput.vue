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
    options: {
      type: Array,
      required: true
    },
    label: {
      type: String
    },
    placeholder: {
      type: String,
      default: 'Select an option'
    },
  },
}
</script>

<template>
  <label v-if="label" for="selectInput" class="form-label">{{ label }}</label>
  <select
    :id=id
    class="form-select"
    @change="$emit('update:modelValue', $event.target.value)"
    v-bind="$attrs"
  >
    <option disabled selected value>{{ placeholder }}</option>
    <option v-for="option in options" :key="option.id" :value="option.id">{{ option.name }}</option>
  </select>
</template>

<style scoped></style>
