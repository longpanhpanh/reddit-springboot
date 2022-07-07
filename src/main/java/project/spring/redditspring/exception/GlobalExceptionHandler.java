package project.spring.redditspring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // handle SpringRedditException
    @ExceptionHandler(SpringRedditException.class)
    public ErrorDetail handleSpringRedditException(SpringRedditException exception) {
        return new ErrorDetail(Instant.now(), exception.getMessage());
    }

    // handle username not found exception
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ErrorDetail handleUsernameNotFound(UsernameNotFoundException exception) {
        return new ErrorDetail(Instant.now(), exception.getMessage());
    }

    // handle post not found exception
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PostNotFoundException.class)
    public ErrorDetail handlePostNotFound(PostNotFoundException exception) {
        return new ErrorDetail(Instant.now(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(SubredditNotFoundException.class)
    public ErrorDetail handleSubredditNotFound(SubredditNotFoundException exception) {
        return new ErrorDetail(Instant.now(), exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidMethodArgument(MethodArgumentNotValidException exception) {
        Map<String, String> errMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(fieldError -> errMap.put(fieldError.getField(), fieldError.getDefaultMessage()));
        return errMap;
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public Set<?> handleConstraintViolation(ConstraintViolationException exception) {
        return exception.getConstraintViolations();
    }

}
