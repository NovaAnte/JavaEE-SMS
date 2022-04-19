package se.iths.rest;

import se.iths.entity.Student;

import se.iths.exceptions.EmailInUseException;
import se.iths.exceptions.JsonFormatter;
import se.iths.service.StudentService;

import javax.inject.Inject;

import javax.validation.ValidationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

@Path("students")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StudentRest {
    StudentService studentService;

    @Inject
    public StudentRest(StudentService studentService) {
        this.studentService = studentService;
    }

    @Path("")
    @POST
    public Response addStudent(Student student) {
        if (studentService.isEmailRegistered(student.getEmail())) {
            return Conflict("Email already exists");
        }
        try {
            studentService.addStudent(student);
        } catch (ValidationException e) {
            String invalidDetails = "You have not entered your details correctly. + Exception:" + e;

            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity(
                    invalidDetails).build());
        }

        return OK("Student successfully saved to database.");
    }

    @Path("update/{id}")
    @PUT
    public Response updateStudent(@PathParam("id") Long id, Student student)
            throws EmailInUseException {
        Student foundStudent = studentService.findStudentById(id);
        if (foundStudent == null) {
            return NotFound("No student with that ID found.");
        }

        if (student.getFirstName() != null && !student.getFirstName().isEmpty()) {
            foundStudent.setFirstName(student.getFirstName());
        } else {
            return BadRequest("First name can not be null or empty.");
        }
        if (student.getLastName() != null && !student.getLastName().isEmpty()) {
            foundStudent.setLastName(student.getLastName());
        } else {
            return BadRequest("Last name can not be null or empty.");
        }
        if (student.getEmail() != null && !student.getEmail().isEmpty()) {
            if (studentService.isEmailRegistered(student.getEmail()) && (id != foundStudent.getId())) {
                throw new EmailInUseException("Email in use.");
            }
            foundStudent.setEmail(student.getEmail());
        } else {
            return BadRequest("Email can not be null or empty.");
        }
        foundStudent.setPhoneNumber(student.getPhoneNumber());

        studentService.updateStudent(foundStudent);

        return Response.ok(student).build();
    }

    @Path("{id}")
    @GET
    public Response findStudentById(@PathParam("id") Long id) {
        Student foundStudent = studentService.findStudentById(id);
        if (foundStudent == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return Response.ok(foundStudent).build();
    }

    @Path("studentbylastname")
    @GET
    public List<Student> getStudentByLastName(@QueryParam("lastName") String lastName) {
        List<Student> foundStudents = studentService.getStudentByLastName(lastName);
        if (foundStudents.size() == 0) {
            throw new WebApplicationException(NotFound("Could not find a student with that name."));
        }
        return studentService.getStudentByLastName(lastName);
    }

    @Path("getall")
    @GET
    public Response getAllStudents() {
        List<Student> foundStudents = studentService.getAllStudents();
        if (foundStudents.isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.ok(foundStudents).build();
        }
    }

    @Path("delete/{id}")
    @DELETE
    public Response deleteStudent(@PathParam("id") Long id) {
        Student foundStudent = studentService.findStudentById(id);

        try {
            studentService.deleteStudent(id);
        } catch (Exception e){
            if (foundStudent == null) {
                throw new WebApplicationException(NotFound("Could not find a student with that ID."));
            }
        }

        return OK("Student deleted from database.");
    }

    private Response OK(String message) {
        return Response.ok("Success").entity(new JsonFormatter(Response.Status.OK.getStatusCode(), message)).build();
    }

    private Response NotFound(String message) {
        return Response.status(Response.Status.NOT_FOUND).entity(new JsonFormatter(Response.Status.NOT_FOUND.getStatusCode(), message)).build();
    }

    private Response BadRequest(String message) {
        return Response.status(Response.Status.BAD_REQUEST).entity(new JsonFormatter(Response.Status.BAD_REQUEST.getStatusCode(), message)).build();
    }

    private Response Conflict(String message) {
        return Response.status(Response.Status.CONFLICT).entity(new JsonFormatter(Response.Status.CONFLICT.getStatusCode(), message)).build();
    }

}