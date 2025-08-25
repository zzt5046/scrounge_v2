<template>
    <button 
        :disabled="disabled || loading"
        :class="['btn', `btn-${type}`, { 'loading': loading }]"
        @click="$emit('click')"
            >

        <div class="content">
            <LoadingSpinner :size="20" :lineSize="1" color="black" v-if="loading"/>
            <span :class="{'ml': loading}"> {{ label }} </span>
        </div>
    </button>
</template>
<script>
import LoadingSpinner from '@/components/core/animated/LoadingSpinner.vue';
export default {
    name: 'LoadingButton',
    components: {
        LoadingSpinner
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

<style scoped>
    .content {
        display: inline-flex;
        align-items: center;
        justify-content: center;
    }
    .ml {
        margin-left: 0.5rem;
    }
</style>