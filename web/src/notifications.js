import { reactive } from 'vue';

export const notifications = reactive({
    toasts: [],
    showToast(message, type = 'success', duration = 3000) {
        this.toasts.push({ message, type, duration });
    },
    removeToast(index) {
        this.toasts.splice(index, 1);
    }
});