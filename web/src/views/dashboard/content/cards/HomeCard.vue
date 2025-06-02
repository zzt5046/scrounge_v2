<template>
    <div class="home-card">
        <h3 v-if="label">{{ label }}</h3>
        <StatCard v-if="type === 'stat'" />
        <InfoCard v-else-if="type ===  'info'"/>
    </div>
    
</template>
<script>
import { store } from '../../../../store'
export default {
    name: 'HomeCard',

    props: {
        type: {
            type: String,
            required: true,
            validator: (value) => {
                return cardTypes.includes(value);
            }
        },
        label: {
            type: String,
            required: false,
        }
    },

    data() {
        return {
            cardTypes: ['stat', 'info'],
        };
    },
};
</script>