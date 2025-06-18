<script>
export default {
  name: 'RangeInput',

  props: {
    id: {
        type: String,
        required: true
    },
    min: {
        type: Number,
        default: 0,
    },
    max: {
        type: Number,
        default: 100,
    },
    modelValue: {
        type: Boolean,
        required: true,
    },
  },
}
</script>

<template>
  <input :id="id" type="range" class="range-input" v-bind="$attrs" :min="min" :max="max" />
</template>