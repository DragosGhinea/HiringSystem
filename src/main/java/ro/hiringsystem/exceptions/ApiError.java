package ro.hiringsystem.exceptions;

import java.time.LocalDateTime;

public record ApiError(
        String message,
        String path,
        int status,
        LocalDateTime timestamp
) {

}
