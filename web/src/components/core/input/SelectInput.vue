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

<template>
  <label v-if="label" for="selectInput" class="form-label">{{ label }}</label>
  <select
    :id=id
    class="form-select"
    @change="this.$emit('update:modelValue', $event.target.value)"
    :value="modelValue"
    v-bind="$attrs"
  >
    <option disabled value>{{ placeholder }}</option>
    <option v-for="option in options" :key="option.id" :value="option.id" :selected="autoselect(option)">{{ option.name }}</option>
  </select>
</template>

<style scoped></style>
