<script>

export default {
    methods: {
        hashStrings(...args) {
            const enc = new TextEncoder();
            return window.crypto.subtle.digest('SHA-256', enc.encode(args.join('')))
                .then((hash) => {
                    return hash;
                });
        }
    }
};
</script>