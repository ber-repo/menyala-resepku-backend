## Configuration

1. **Database**  
   Make sure PostgreSQL is installed and create a new database for this application.

2. **Application Configuration**  
   Edit `src/main/resources/application.yml` to set up your database connection and JWT secret.

3. **Environment Variables**  
   - `api.prefix` (e.g., `/api/v1`)
   - `auth.token.jwtSecret` (JWT secret, base64)

## Running the Application

### Using Maven Wrapper

```sh
./mvnw spring-boot:run
```

### Or with Maven

```sh
mvn spring-boot:run
```

## API Endpoints

- REST API: `${api.prefix}/...` (see controllers for details)
- GraphQL: `/graphql`

## Example GraphQL Query

```graphql
query {
  allRecipe {
    id
    recipeName
    category { name }
    ingredients { ingredientName }
    steps { stepDescription }
  }
}
```

## Testing

Run unit tests with:

```sh
mvn test
```
