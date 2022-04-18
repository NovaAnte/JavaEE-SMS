package se.iths.rest;

import se.iths.entity.Teacher;
import se.iths.exceptions.EmailInUseException;
import se.iths.service.TeacherService;

import javax.inject.Inject;
import javax.validation.ValidationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("teachers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TeacherRest {

    TeacherService teacherService;

    @Inject
    public TeacherRest(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Path("")
    @POST
    public Response createTeacher(Teacher teacher){
        String emailExistsJson = "{\"Error\": \"Email already exists\"}";
        if (teacherService.isEmailRegistered(teacher.getEmail())) {
            return Conflict(emailExistsJson);
        }
        try {
            teacherService.createTeacher(teacher);
        } catch (ValidationException e) {
            String invalidDetails = "{\"Error\": \"You have not entered your details correctly. \" + \"Exception:" + e + "\"}";

            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity(
                    invalidDetails).build());
        }

        return OK("{\"Success\": \"Teacher successfully saved to database.\"}");
    }

    @Path("{id}")
    @GET
    public Response findTeacherById(@PathParam("id") Long id){
        Teacher foundTeacher = teacherService.findTeacherById(id);
        if (foundTeacher == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return Response.ok(foundTeacher).build();
    }

    @Path("update/{id}")
    @PUT
    public Response updateTeacher(@PathParam("id") Long id, Teacher teacher)
            throws EmailInUseException {
        Teacher foundTeacher = teacherService.findTeacherById(id);
        if (foundTeacher == null) {
            return NotFound("{\"Error\": \"No teacher with that ID found.\"}");
        }

        if (foundTeacher.getFirstName() != null && !foundTeacher.getFirstName().isEmpty()) {
            foundTeacher.setFirstName(teacher.getFirstName());
        } else {
            return BadRequest("{\"Error\": \"First name can not be null or empty.\"}");
        }
        if (foundTeacher.getLastName() != null && !foundTeacher.getLastName().isEmpty()) {
            foundTeacher.setLastName(teacher.getLastName());
        } else {
            return BadRequest("{\"Error\": \"Last name can not be null or empty.\"}");
        }

        if (foundTeacher.getEmail() != null && !foundTeacher.getEmail().isEmpty()) {
            if (teacherService.isEmailRegistered(teacher.getEmail()) && (id != foundTeacher.getId())) {
                throw new EmailInUseException("Email in use.");
            }
            foundTeacher.setEmail(teacher.getEmail());
        } else {
            return BadRequest("{\"Error\": \"Email can not be null or empty.\"}");
        }


        foundTeacher.setPhoneNumber(teacher.getPhoneNumber());

        teacherService.updateTeacher(foundTeacher);

        return Response.ok(teacher).build();
    }

    @Path("delete/{id}")
    @DELETE
    public Response deleteSubject(@PathParam("id") Long id) {
        Teacher foundTeacher = teacherService.findTeacherById(id);

        if (foundTeacher == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        teacherService.deleteTeacher(id);

        return OK("{\"Success\": \"Teacher deleted from database.\"}");
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
