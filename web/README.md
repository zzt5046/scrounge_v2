# scrounge-web


## Customize configuration

See [Vite Configuration Reference](https://vitejs.dev/config/).

## Frontend Setup

```sh
npm install
```

### Compile and Hot-Reload for Development

```sh
npm run dev
```

### Compile and Minify for Production

```sh
npm run build
```

### Lint with [ESLint](https://eslint.org/)

```sh
npm run lint
```

## Backend Setup

### PostgreSQL

This app uses Postgres to manage data.
<br />
Install Postgres, and make sure to create an initial database.
```sql
CREATE DATABASE scrounge;
```

Create a role for the app to use the database.
```sql
CREATE ROLE scrounge;
```
