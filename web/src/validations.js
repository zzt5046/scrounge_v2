import { required, email, sameAs, minLength } from '@vuelidate/validators'

export function hasError(v$, fieldName) {
  return v$.$errors.some(error => (error.$propertyPath == fieldName))
}

export function getErrorMessage(v$, fieldName) {
  const error = v$.$errors.find(error => (error.$propertyPath == fieldName))
  if (error) {
    return validationMessages[error.$uid] || validationMessages[error.$validator]
  }
  return null
}

export const validationMessages = {
  required: 'This field is required.',
  email: 'Please enter a valid email address.',
  'password-minLength': 'Password must be at least 6 characters long.',
  'confirmPassword-sameAs': 'Must match the password.',
}

//custom validators
function hasElements(value) {
  if (Array.isArray(value)) {
    return value.length > 0
  } else if (value instanceof Set) {
    return value.size > 0
  } else if (value instanceof Map) {
    return value.size > 0
  }
  return false
}

export function getValidations(componentName, args = {}) {

  const passwordMinLength = minLength(6)

  const validations = {
    LoginPanel: {
      username: { required },
      password: {
        required,
        minLength: passwordMinLength
      }
    },
    RegisterPanel: {
      username: { required },
      password: {
        required,
        minLength: passwordMinLength,
      },
      email: { email },
      confirmPassword: {
        required,
        ...(args.password ? {
          sameAs: sameAs(args.password)
        } : {})
      },
      securityQuestion: { required },
      securityAnswer: { required }
    },
    RecipeCreateView: {
      recipe: {
        name: { required },
        ingredients: { hasElements },
        directions: { hasElements },
      },
    },
  }

  if (!validations[componentName]) {
    throw new Error(`No validations found for component: ${componentName}`)
  }

  return validations[componentName]
}