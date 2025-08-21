<template>
    <button :disabled="disabled || loading"
            :class="['btn', `btn-${type}`, { 'loading': loading }]"
            @click="$emit('click')">

        <VueSpinner v-if="loading" size="20" color="black" />
        {{ label }}
    </button>
</template>
<script>
import { VueSpinner } from 'vue3-spinners';
export default {
    name: 'LoadingButton',
    components: {
        VueSpinner
    },
    props: {
        label: {
            type: String,
            required: true
        },
        disabled: {
            type: Boolean,
            default: false
        },
        loading: {
            type: Boolean,
            default: false
        },
        type: {
            type: String,
            default: 'primary',
            validator: function(value) {
                return ['primary', 'secondary'].includes(value);
            }
        },
        class: {
            type: String,
            default: ''
        }
    },
};
</script>