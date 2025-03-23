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
    }
  },

  methods: {
    updateValue() {
      this.value = event.target.value
      this.$emit('update:$id', this.value)
    }
  }
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
    <option disabled selected value>- Select a question - </option>
    <option v-for="option in options" :value="option.id">{{ option.name }}</option>
  </select>
</template>

<style scoped></style>
