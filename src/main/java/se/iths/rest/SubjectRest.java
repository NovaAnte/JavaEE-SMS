package se.iths.rest;

import se.iths.entity.Student;
import se.iths.entity.Subject;
import se.iths.entity.Teacher;
import se.iths.service.SubjectService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("subjects")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SubjectRest {

    SubjectService subjectService;

    @Inject
    public SubjectRest(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Path("")
    @POST
    public Response addSubject(Subject subject) {
        Subject subjectResult = subjectService.createSubject(subject);
        return OK("{\"Success\": \"Subject successfully saved to database.\"}");
    }

    @Path("{id}")
    @GET
    public Response findSubjectById(@PathParam("id") Long id) {
        Subject foundSubject = subjectService.findSubjectById(id);

        if (foundSubject == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        return Response.ok(foundSubject).build();
    }

    @Path("getall")
    @GET
    public Response getAllSubjects(){
        List<Subject> allSubjects = subjectService.findAllSubjects();
        if (allSubjects.isEmpty()){
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.ok(allSubjects).build();
        }

    }

    @Path("update/{id}")
    @PUT
    public Response updateSubject(@PathParam("id") Long id, Subject subject) {
        Subject foundSubject = subjectService.findSubjectById(id);

        if (foundSubject == null) {
            return NotFound("{\"Error\": \"Could not find a subject with that ID.\"}");
        }

        foundSubject.setSubjectName(subject.getSubjectName());

        subjectService.updateSubject(foundSubject);

        return Response.ok(foundSubject).build();
    }

    @Path("delete/{id}")
    @DELETE
    public Response deleteSubject(@PathParam("id") Long id) {
        Subject foundSubject = subjectService.findSubjectById(id);

        if (foundSubject == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        subjectService.deleteSubject(id);

        return OK("{\"Success\": \"Subject deleted from database.\"}");
    }

    @Path("linkstudent")
    @PATCH
    public Response linkStudentToSubject(@QueryParam("studentId") Long studentId, @QueryParam("subjectId") Long subjectId){
        Subject foundSubject = subjectService.findSubjectById(subjectId);
        Student foundStudent = subjectService.findStudentById(studentId);

        if (foundSubject != null && foundStudent != null){
            subjectService.linkStudent(foundSubject, foundStudent);
        }
        // add errorhandling here
        return Response.ok().build();
    }

    @Path("linkteacher")
    @PATCH
    public Response linkTeacherToSubject(@QueryParam("teacherId") Long teacherId, @QueryParam("subjectId") Long subjectId){
        Subject foundSubject = subjectService.findSubjectById(subjectId);
        Teacher foundTeacher = subjectService.findTeacherById(teacherId);

        if (foundSubject != null && foundTeacher != null){
            subjectService.linkTeacher(foundSubject, foundTeacher);
        }
        // add errorhandling here

        return Response.ok().build();
    }


    private Response OK(String message) {
        return Response.ok("Success").entity(message).build();
    }

    private Response NotFound(String message) {
        return Response.status(Response.Status.NOT_FOUND).entity(message).build();
    }

    private Response BadRequest(String message) {
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    private Response Conflict(String message) {
        return Response.status(Response.Status.CONFLICT).build();
    }


}
