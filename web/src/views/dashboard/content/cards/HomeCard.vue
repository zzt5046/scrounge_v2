<template>
    <div class="home-card" v-show="!closed">
        <div class="home-card-label">
            <h3 v-if="label">{{ label }}</h3>
            <img src="../../../../assets/icon/x-icon.png" v-if="closable"
                class="remove-item-icon" :title="$t('actions.remove')" @click="closed = true" />
        </div>
        <div class="home-card-content" v-if="!loading">
            <h1 v-if="type === 'stat'" class="stat" :id="statId">{{ displayStatValue }}</h1>
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
        loading: {
            type: Boolean,
            required: false,
            default: false,
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
            targetStatValue: this.statNum,
            displayStatValue: 0,
        };
    },

    computed: {
        // Computed property to handle the stat number
        statId() {
            return this.id + '-stat';
        },
    },

    mounted() {
        this.animateCountUp();
    },

    methods: {
        animateCountUp() {
            const startTime = performance.now();
            const target = this.statNum;
            const duration = 1000; // Default duration in milliseconds

            // Scale duration based on number size (log10 is a good approximation)
            const scaledDuration = duration * Math.log10(target + 10);

            const update = (currentTime) => {
                const elapsed = currentTime - startTime;
                const progress = Math.min(elapsed / scaledDuration, 1);
                this.displayStatValue = Math.floor(progress * target);

                if (progress < 1) {
                    requestAnimationFrame(update);
                }
            };

            requestAnimationFrame(update);
        }
    }
};
</script>
<style scoped>
    .panel {
        padding: 0 !important;
    }
</style>