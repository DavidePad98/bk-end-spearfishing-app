package capstonepj_bkend.bkendcpj.exceptions;

import capstonepj_bkend.bkendcpj.payloads.ErrorDTO;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice

public class ExceptionsHandler {

    @ExceptionHandler(BadRequestException.class)

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleBadRequest(BadRequestException ex){
        if(ex.getErrors() != null) {

            String message = ex.getErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage())
                    .collect(Collectors.joining(". " ));
            return new ErrorDTO(message, LocalDateTime.now());

        } else {

            return new ErrorDTO(ex.getMessage(), LocalDateTime.now());
        }
    }


    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)

    public ErrorDTO handleNotFound(ChangeSetPersister.NotFoundException ex){
        return new ErrorDTO(ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleGenericErrors(Exception ex){
        ex.printStackTrace();
        return new ErrorDTO("There are some server problems", LocalDateTime.now());
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    public ErrorDTO handleMaxSizeException(MaxUploadSizeExceededException exc) {
        return new ErrorDTO("File too large! Maximum allowed size is 10MB.",LocalDateTime.now() );
    }
}
