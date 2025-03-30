<script>

export default {
    methods: {
        async hashStrings(...args) {
            const enc = new TextEncoder();
            const hashBuffer = await window.crypto.subtle.digest('SHA-256', enc.encode(args.join('')));
            const hashArray = Array.from(new Uint8Array(hashBuffer)); // convert buffer to byte array
            const hashHex = hashArray
                .map((b) => b.toString(16).padStart(2, "0"))
                .join(""); // convert bytes to hex string
            return hashHex;
        }
    }
};
</script>