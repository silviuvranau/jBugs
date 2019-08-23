package configuration;

import javax.print.attribute.standard.Media;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    /**
     * method that intercepts the constraint violation exception
     * and returns the response
     * @param exception
     * @return
     */
    @Override
    public Response toResponse(ConstraintViolationException exception) {
        // customize response
        StringBuilder stringBuilder = new StringBuilder();
        for(ConstraintViolation cv : exception.getConstraintViolations()){
            stringBuilder.append(cv.getMessage() + "<br>");
        }
//        System.out.println(exception.getConstraintViolations().);
//        return Response.status(Response.Status.BAD_REQUEST).entity();
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(stringBuilder.toString())
                .type(MediaType.TEXT_HTML)
                .build();
    }

}