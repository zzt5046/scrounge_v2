const messages = {
  en: {
    common: {
      required: 'Required'
    },
    fields: {
      username: 'Username',
      password: 'Password',
      confirmPassword: 'Confirm Password',
      firstName: 'First Name',
      lastName: 'Last Name',
      emailAddress: 'Email Address',
      phoneNumber: 'Phone Number',
      securityQuestion: 'Security Question',
      selectNewSecurityQuestion: 'Select a new security question',
      securityAnswer: 'Security Question Answer',
      confirmSecurityAnswer: 'Confirm Answer',
      newSecurityAnswer: 'New Answer',
    },
    actions: {
      login: 'Login',
      logout: 'Logout',
      register: 'Register',
      save: 'Save',
      submit: 'Submit',
      edit: 'Edit',
      update: 'Update',
      view: 'View',
      search: 'Search',
      clear: 'Clear',
      filter: 'Filter',
      sort: 'Sort',
      refresh: 'Refresh',
      load_more: 'Load More',
      load_less: 'Load Less',
      load: 'Load',
      upload: 'Upload',
      download: 'Download',
      print: 'Print',
      share: 'Share',
      cancel: 'Cancel',
      back: 'Back',
      confirm: 'Confirm',
      continue: 'Continue',
      add: 'Add',
      remove: 'Remove',
      delete: 'Delete',
      yes: 'Yes',
      no: 'No',
      done: 'Done',
      move_up: 'Move Up',
      move_down: 'Move Down',
      show: 'Show',
      hide: 'Hide',
    },
    splash: {
      login: {
        header: 'LOGIN',
        misc: {
          registerDisclaimer1: "Don't have an account?",
          registerDisclaimer2: 'SIGN UP'
        },
        failed: 'Username or password is incorrect.',
        error: 'An error has occurred. Please try again later.'
      },
      register: {
        header: 'SIGN UP',
        nameTaken: 'Sorry, that username is not available.',
        success: 'Account created successfully. Please login to continue.',
        error: 'Error while creating account. Please try again later.',
      },
    },
    dashboard: {
      header: 'Dashboard',
      navbar: {
        home: 'Home',
        recipes: 'Recipes',
        settings: 'Settings',
        logout: 'Logout'
      },
      footer: 'Footer',
    },
    home: {
      'welcome-label': 'Welcome Home!',
      welcome: 'This is your personal dashboard where you can manage your inventory, view stats, and access other features.',
      'inventory-items-label': 'Inventory Items',
      'recipes-stored-label': "Recipes Created",
      'recipes-favorited-label': "Recipes Favorited",
      inventory: {
        header: 'My Inventory',
        'no-items': 'No items found.',
        actions: {
          add: 'Add Item',
          refresh: 'Refresh',
        }
      },
    },
    recipe: {
      list: {
        header: 'My Recipes',
        header_fav: 'Favorite Recipes',
        no_recipes: 'No recipes found. Add a recipe to see it listed here!',
        no_recipes_fav: 'No recipes found. Favorite a recipe to see it listed here!',
        deleted: 'Deleted recipe: ',
      },
      information: 'Information',
      name: 'Name',
      description: 'Description',
      add_description: 'Add description here.',
      ingredients: 'Ingredients',
      no_ingredients: 'No ingredients found.',
      directions: 'Directions',
      add_direction: 'Add direction.',
      no_directions: 'No directions found.',
      notes: 'Notes',
      add_notes: 'Add notes here.',
      no_notes: 'No notes found.',
      public: 'Public Recipe',
      make_public: 'Make this recipe public?',
      make_public_desc: 'This will allow other users to see this recipe.',
      actions: {
        add: '+ Add Recipe',
        edit: 'Edit Recipe',
        show_favorites: 'Show Favorites',
        save: 'Save Recipe',
        favorite: 'Favorite',
        unfavorite: 'Remove Favorite',
        delete: 'Delete Recipe',
        cancel: 'Cancel'
      },
      ingredient: {
        name: 'Name',
        quantity: 'Quantity',
        unit: 'Unit',
        add: '+ Add',
      },
      direction: {
        add: '+ Add',
      },
      inspect: {
        submitted_by: 'Submitted by:',
      },
      create: {
        header: 'Create Recipe',
        success: 'Recipe created successfully.',
        error: 'An error has occurred. Please try again later.'
      },
      update: {
        success: 'Recipe updated successfully.',
        error: 'An error has occurred. Please try again later.'
      },
    },
    account: {
      information: {
        header: 'Account Information',
      },
      settings: {
        header: 'Account Settings',
        language: {
          header: 'Language',
          tooltip: 'Change the language of the application (Not yet implemented)',
          placeholder: 'Select a language',
          english: 'English',
          spanish: 'Español',
        },
        measurement_system: {
          header: 'Measurement System',
          tooltip: 'Change the measurement system of the application (Automatic recipe conversions not yet implemented)',
          placeholder: 'Select a measurement system',
          metric: 'Metric',
          imperial: 'Imperial',
        },
        theme: {
          header: 'Theme',
          tooltip: 'Change the theme of the application (Not yet implemented)',
          placeholder: 'Select a theme',
          light: 'Light',
          toasted: 'Toasted',
          dark: 'Dark',
        },
      },
      update: {
        success: 'Account updated successfully.',
        error: 'An error has occurred. Please try again later.'
      },

    },
    validations: {
      required_note: 'Indicates a required field',
      required: 'This field is required',
      minLength: 'This field must be at least {min} characters long',
      maxLength: 'This field must be at most {max} characters long',
      email: 'Please enter a valid email address',
      passwordStrength: 'Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character',
    },
  }
}

export default messages
