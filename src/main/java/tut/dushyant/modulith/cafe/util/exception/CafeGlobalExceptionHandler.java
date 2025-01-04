package tut.dushyant.modulith.cafe.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CafeGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handle CafeAppException
     * @param ex CafeAppException
     * @return ProblemDetail
     */
    @ExceptionHandler(CafeAppException.class)
    public ProblemDetail handleException(CafeAppException ex) {
        return switch (ex) {
            case NotFoundException e -> getProblemDetails(HttpStatus.NOT_FOUND, e);
            case UpdateFailedException e -> getProblemDetails(HttpStatus.CONFLICT, e);
            case BadRequestException e -> getProblemDetails(HttpStatus.BAD_REQUEST, e);
            default -> getProblemDetails(HttpStatus.INTERNAL_SERVER_ERROR, ex);
        };
    }

    /**
     * Handle CafeAppException
     * @param ex CafeAppException
     * @return ProblemDetail
     */
    public ProblemDetail getProblemDetails(HttpStatus status, CafeAppException ex) {
        return ProblemDetail.forStatusAndDetail(status, ex.getMessage());
    }
}
