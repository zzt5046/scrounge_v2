import { required, email, sameAs, minLength, helpers } from '@vuelidate/validators'
import { accountService } from '@/service/.service-registry'

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
  nonNumber: 'Must be a number.',
  'username-isUnique': 'User already exists.'
}

//custom validators

const usernameNotTaken = helpers.withAsync(async (value) => {
  if (!value) return false
  const exists = await accountService.exists(value)
  return !exists  // return true only if user does NOT exist
})

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

  const validations = {
    LoginPanel: {
      username: { required },
      password: { required }
    },
    RegisterPanel: {
      username: {
        required,
        isUnique: usernameNotTaken
      },
      password: {
        required,
        minLength: minLength(6),
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