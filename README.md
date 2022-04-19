# JavaEE Student Management System

## Base URL

http://localhost:8080/student-management-system/api/v1/

### Endpoints

#### Students

```
Create: http://localhost:8080/student-management-system/api/v1/students/ (add JSON-body)
Read: http://localhost:8080/student-management-system/api/v1/students/id
Read all students: http://localhost:8080/student-management-system/api/v1/students/getall
Read by last name: http://localhost:8080/student-management-system/api/v1/students/studentbylastname?lastName=
Update: http://localhost:8080/student-management-system/api/v1/students/update/id (add JSON-body)
Delete: http://localhost:8080/student-management-system/api/v1/students/delete/id
```

#### Teachers
```
Create: http://localhost:8080/student-management-system/api/v1/teachers/ (add JSON-body)
Read: http://localhost:8080/student-management-system/api/v1/teachers/id
Update: http://localhost:8080/student-management-system/api/v1/teachers/update/id (add JSON-body)
Delete: http://localhost:8080/student-management-system/api/v1/teachers/delete/id
```

#### Subjects
```
Create: http://localhost:8080/student-management-system/api/v1/subjects (add JSON-body)
Read: http://localhost:8080/student-management-system/api/v1/subjects/id
Read all subjects: http://localhost:8080/student-management-system/api/v1/subjects/getall
Update: http://localhost:8080/student-management-system/api/v1/subjects/update/id (add JSON-body)
Delete: http://localhost:8080/student-management-system/api/v1/subjects/delete
Link teacher to subject: http://localhost:8080/student-management-system/api/v1/subjects/linkteacher?teacherId=x&subjectId=x
Link student to subject: http://localhost:8080/student-management-system/api/v1/subjects/linkteacher?studentId=x&subjectId=x
```
***
### JSON body examples

#### Students

```
{
"firstName": "First name",
"lastName": "Last name",
"email": "sample@mail.com",
"phoneNumber": "123"
}
```

#### Teachers
```
{
"firstName": "First name",
"lastName": "Last name",
"email": "sample@mail.com",
"phoneNumber": "123"
}
```

#### Subjects
```
{
"subjectName": "Subject name"
}
```
