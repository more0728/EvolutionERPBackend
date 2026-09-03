
package com.evolutionerp.exception;

import java.time.LocalDateTime;

public record CustomErrorRecord(LocalDateTime timestamp, String message, String details) {
}
