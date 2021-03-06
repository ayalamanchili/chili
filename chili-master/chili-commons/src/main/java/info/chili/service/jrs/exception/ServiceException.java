package info.chili.service.jrs.exception;

import info.chili.service.jrs.types.Errors;

import java.util.List;

public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private int statusCode;
    private Errors errors;

    public ServiceException(StatusCode statusCode, Errors errors) {
        this.statusCode = statusCode.value();
        this.errors = errors;
    }

    public ServiceException(StatusCode statusCode, String source, String reasonCode, String description) {
        this(statusCode, new info.chili.service.jrs.types.Error(source, reasonCode, description));
    }

    public ServiceException(StatusCode statusCode, String source, String reasonCode, String description, Throwable e) {
        this(e, statusCode, new info.chili.service.jrs.types.Error(source, reasonCode, description));
    }

    public ServiceException(StatusCode statusCode, List<info.chili.service.jrs.types.Error> errs) {
        this.statusCode = statusCode.value();
        if (this.errors == null) {
            this.errors = new Errors();
        }
        for (info.chili.service.jrs.types.Error error : errs) {
            this.errors.addError(error);
        }
    }

    public ServiceException(Throwable e, StatusCode statusCode, List<info.chili.service.jrs.types.Error> errs) {
        super(e);
        this.statusCode = statusCode.value();
        if (this.errors == null) {
            this.errors = new Errors();
        }
        for (info.chili.service.jrs.types.Error error : errs) {
            this.errors.addError(error);
        }
    }

    public ServiceException(Throwable e, StatusCode statusCode, info.chili.service.jrs.types.Error... errs) {
        super(e);
        this.statusCode = statusCode.value();
        if (this.errors == null) {
            this.errors = new Errors();
        }
        for (info.chili.service.jrs.types.Error error : errs) {
            this.errors.addError(error);
        }
    }

    public ServiceException(StatusCode statusCode, info.chili.service.jrs.types.Error... errs) {
        this.statusCode = statusCode.value();
        if (this.errors == null) {
            this.errors = new Errors();
        }
        for (info.chili.service.jrs.types.Error error : errs) {
            this.errors.addError(error);
        }
    }

    @Override
    public String toString() {
        return "ServiceException [statusCode=" + statusCode + ", errors=" + errors + "]";
    }

    public Errors getErrors() {
        return errors;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public enum StatusCode {

        OK(200), NEW_RESOURCE_CREATED(201), INVALID_REQUEST(400), NOT_AUTHORIZED_REQUEST(401), RESOURCE_NOT_FOUND(404), INTERNAL_SYSTEM_ERROR(
        500);
        private int statusCode;

        StatusCode(int statusCode) {
            this.statusCode = statusCode;
        }

        public int value() {
            return statusCode;
        }

        public String toString() {
            return String.valueOf(statusCode);
        }
    }

    public enum ReasonCode {

        /**
         * The request processing time took too long and it was determined that
         * request should be timed out.
         */
        REQUEST_TIMEOUT,
        /**
         * An error that occurs due to a hardware, network, or connectivity
         * failure.
         */
        SYSTEM_ERROR,
        /**
         * An error that occurs if a client makes too many requests and exceeds
         * the throttling setting for a service.
         */
        VOLUME_THRESHOLD_EXCEEDED,
        /**
         * An error that occurs when the client's request does not match a known
         * service URL.
         */
        URL_UNKNOWN,
        /**
         * An error that occurs when the client's OAuth credentials are not
         * authorized to make the request.
         */
        AUTHORIZATION_FAILED,
        /**
         * An error that occurs when the client's OAuth credentials are not
         * successfully validated.
         */
        AUTHENTICATION_FAILED,
        /**
         * An error that indicated that a piece of data required in the request
         * was not present.
         */
        MISSING_REQUIRED_INPUT,
        /**
         * An error that indicated that a peice of data in the request does not
         * match the expected format or mask.
         */
        INVALID_INPUT_FORMAT,
        /**
         * An error that indicated that a piece of data in the request is too
         * long or too short.
         */
        INVALID_INPUT_LENGTH,
        /**
         * An error that indicates that the value of a piece of data in the
         * request is invalid.
         */
        INVALID_INPUT_VALUE,
        /**
         * An error that indicates that a specific resource instance can not be
         * found.
         */
        RESOURCE_UNKNOWN;
    }
}
