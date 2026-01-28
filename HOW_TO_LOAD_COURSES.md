# 🔧 How to Load the New Unassigned Courses

## The Problem
The new courses aren't showing because your database already has data, and the DataLoader only runs when the database is empty.

## ✅ SOLUTION (Choose One)

---

## **Option 1: Automatic Reload (EASIEST)** ⭐ RECOMMENDED

I've already updated the `DataLoader.java` file with a `FORCE_RELOAD` flag set to `true`.

### Steps:
1. **Stop your application** (if running)
2. **Restart your application**
3. **Check the console** - you should see:
   ```
   FORCE_RELOAD is enabled - clearing existing data...
   Deleting all existing data...
   All data cleared successfully!
   Loading sample data...
   ```
4. **Done!** The app will automatically:
   - Delete all old data
   - Load fresh data including 6 unassigned courses

**After testing, you can turn off auto-reload:**
- Open `DataLoader.java`
- Change `private static final boolean FORCE_RELOAD = true;` to `false`
- Save and restart

---

## **Option 2: Manual SQL Script**

### Steps:
1. **Stop your application**
2. **Open pgAdmin**
3. **Connect to your database**: `student_management_db`
4. **Open Query Tool**
5. **Run the script** `clear_data.sql` (located in your project root):
   ```sql
   -- Clear all data
   SET session_replication_role = 'replica';
   DELETE FROM enrollments;
   DELETE FROM courses;
   DELETE FROM students;
   DELETE FROM teachers;
   DELETE FROM users;
   DELETE FROM departments;
   SET session_replication_role = 'origin';
   ```
6. **Restart your application**
7. **Done!** Fresh data will be loaded

---

## 🧪 How to Verify It Worked

After restarting, check the **console logs**. You should see:

```
Sample data loaded successfully!
===========================================
LOGIN CREDENTIALS:
Teacher Login: john.smith@university.edu / teacher123
Teacher Login: sarah.johnson@university.edu / teacher123
Student Login (Active): alice.brown@student.edu / student123
Student (Pending): bob.wilson@student.edu / student123
===========================================
COURSES INFO:
Total Courses: 10
Assigned Courses: 4 (already have teachers)
Unassigned Courses: 6 (available for teacher assignment)
===========================================
```

---

## 🎯 Test the Feature

1. **Login as teacher**:
   - Email: `john.smith@university.edu`
   - Password: `teacher123`

2. **Go to Teacher Dashboard**

3. **Click "➕ Assign Courses" tab**

4. **You should see 6 courses**:
   - ✅ Operating Systems (CS202)
   - ✅ Computer Networks (CS203)
   - ✅ Software Engineering (CS302)
   - ✅ Cloud Computing (CS401)
   - ✅ AI Lab (CS301L)
   - ✅ Cybersecurity Fundamentals (CS303)

5. **Click "Assign to Me"** on any course

6. **Verify**:
   - Course disappears from "Assign Courses"
   - Course appears in "My Courses"

---

## 🔍 If Still Not Working

### Check 1: Database Connection
```sql
-- In pgAdmin, run:
SELECT name, course_code, teacher_id FROM courses;
```
You should see 10 courses, 6 with `teacher_id = NULL`

### Check 2: API Endpoint
- Open browser: `http://localhost:8081/api/courses/unassigned`
- You should see JSON array with 6 courses

### Check 3: Console Errors
- Look for errors in the application console
- Look for errors in browser console (F12)

---

## 📝 What Was Changed

### File: `DataLoader.java`
- Added `FORCE_RELOAD` flag (currently set to `true`)
- Added `clearAllData()` method to delete old data
- Added 6 new unassigned courses

### File: `clear_data.sql` (NEW)
- Created SQL script to manually clear data
- Safe deletion order (handles foreign keys)

---

## ⚙️ After Testing

Once you've verified everything works, **disable auto-reload**:

1. Open: `src/main/java/.../config/DataLoader.java`
2. Find line: `private static final boolean FORCE_RELOAD = true;`
3. Change to: `private static final boolean FORCE_RELOAD = false;`
4. Save file

This prevents data from being cleared every time you restart the app.

---

## 🎉 Summary

**EASIEST WAY:**
1. Just **restart your application**
2. The `FORCE_RELOAD` flag will automatically clear and reload data
3. Login and test!

The 6 unassigned courses will now be visible in the "Assign Courses" tab!
