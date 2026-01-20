# Database Setup Script for PostgreSQL

## Step-by-Step Database Setup

### Method 1: Using psql Command Line

1. Open Command Prompt (Windows) or Terminal (Mac/Linux)
2. Login to PostgreSQL:
```bash
psql -U postgres
```

3. Enter your PostgreSQL password when prompted

4. Create the database:
```sql
CREATE DATABASE student_management_db;
```

5. Verify the database was created:
```sql
\l
```

6. Exit psql:
```sql
\q
```

### Method 2: Using pgAdmin (GUI)

1. Open pgAdmin
2. Right-click on "Databases"
3. Select "Create" → "Database"
4. Enter database name: `student_management_db`
5. Click "Save"

---

## Update application.properties

After creating the database, update your password in:
`src/main/resources/application.properties`

Change this line:
```properties
spring.datasource.password=postgres
```

To your actual PostgreSQL password:
```properties
spring.datasource.password=YOUR_ACTUAL_PASSWORD
```

---

## Common PostgreSQL Default Settings

- **Username**: postgres
- **Password**: (the one you set during PostgreSQL installation)
- **Port**: 5432
- **Host**: localhost

---

## Test Database Connection

### Using psql:
```bash
psql -U postgres -d student_management_db
```

### Using SQL:
```sql
-- List all databases
SELECT datname FROM pg_database WHERE datname = 'student_management_db';

-- Check connection
\conninfo
```

---

## Troubleshooting

### Error: "password authentication failed"
- You need to use YOUR PostgreSQL password (set during installation)
- Update `spring.datasource.password` in application.properties

### Error: "database does not exist"
- Create the database using the commands above

### Error: "connection refused"
- Make sure PostgreSQL service is running
- Windows: Check Services → PostgreSQL should be running
- Mac: `brew services list` → postgresql should be started

### Start PostgreSQL Service:
**Windows:**
```bash
net start postgresql-x64-[version]
```

**Mac:**
```bash
brew services start postgresql
```

**Linux:**
```bash
sudo systemctl start postgresql
```

---

## Quick Verification

Run this in psql after creating the database:
```sql
\c student_management_db
\dt
```

If database is empty, that's fine! Spring Boot will create tables automatically when you run the application.
