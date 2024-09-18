package com.finEasy.models.utilities;

public enum ResponseEnum {

    SUCCESS("000", "Successful"),
    SUCCESS_FILE_UPLOAD("000", "Successful, your request will be updated in the background."),
    USER_CREATION_SUCCESS("000", "User creation Successful"),

    USER_NOT_FOUND("100", "User not found."),
    INVALID_USER_CREDENTIALS("200", "Invalid User credentials provided"),
    INVALID_HEADER_VALUES("200", "Invalid Header values provided"),
    RECORD_NOT_FOUND("201", "Record not found."),
    ERROR("900", "Error creating user."),
    FATAL_ERROR("900", "Fatal error occurred."),
    INVALID_REQUEST("999", "Invalid Request"),
    REVERSED_TRANSACTION("200", "Transaction has been reversed"),
    NO_TRANSACTION("201", "No Transaction details available for REF"),
    SERVICE_UNAVAILABLE("900", "Internal Middleware unavailable"),
    EMPTY_SENDER_OR_RECEIVER_NAME("400","Sender's or Receiver's Name Cannot be null"),
    PENDING("","");


    private String message;
    private String code;

    private ResponseEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
