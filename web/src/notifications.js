import { reactive } from 'vue';

const default_type = 'success'
const default_duration = 5000

export const notifications = reactive({
    toasts: [],

    success(message, duration = this.default_duration) {
        this.showToast(message, 'success', duration)
    },
    error(message, duration = this.default_duration) {
        this.showToast(message, 'error', duration)
    },
    info(message, duration = this.default_duration) {
        this.showToast(message, 'info', duration)
    },

    showToast(message, type = this.default_type, duration = this.default_duration) {
        this.toasts.push({ message, type, duration });
    },
    removeToast(index) {
        this.toasts.splice(index, 1);
    }
});