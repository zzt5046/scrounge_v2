<template>
    <div class="home-card" v-show="!closed">
        <div class="home-card-label">
            <h3 v-if="label">{{ label }}</h3>
            <img src="../../../../assets/icon/x-icon.png" v-if="closable"
                class="remove-item-icon" :title="$t('actions.remove')" @click="closed = true" />
        </div>
        <div class="home-card-content">
            <h1 v-if="type === 'stat'" class="stat" :id="statId">{{ statNum }}</h1>
            <span v-else> {{ body }} </span>
        </div>
    </div>
</template>
<script>
export default {
    name: 'HomeCard',

    props: {
        id: {
            type: String,
            required: true,
        },
        type: {
            type: String,
            required: true,
        },
        label: {
            type: String,
            required: false,
        },
        body: {
            type: String,
            required: false,
        },
        statNum: {
            type: Number,
            required: false,
            default: 0,
        },
        closable: {
            type: Boolean,
            required: false,
            default: false,
        },
    },

    data() {
        return {
            closed: false,
        };
    },

    computed: {
        // Computed property to handle the stat number
        statId() {
            return this.id + '-stat';
        },
    },

    mounted() {
        //animate a count on the stat number
        if (this.type === 'stat' && this.statNum > 0) {
            const statElement = document.getElementById(this.statId);
            if (statElement) {
                statElement.animate(
                    [
                        { transform: 'scale(1)' },
                        { transform: 'scale(1.2)' },
                        { transform: 'scale(1)' }
                    ],
                    {
                        duration: 500,
                        easing: 'ease-in-out',
                    }
                );
            }
        }
    },
};
</script>
<style scoped>
    .panel {
        padding: 0 !important;
    }
</style>