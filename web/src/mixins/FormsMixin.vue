<script>
import { hasError, getErrorMessage } from '../validations'
export default {
    methods:{
        error(v$, fieldName) {
            return hasError(v$, fieldName)
        },
        errorMessage(v$, fieldName) {
            return getErrorMessage(v$, fieldName)
        },
    }
};
</script>