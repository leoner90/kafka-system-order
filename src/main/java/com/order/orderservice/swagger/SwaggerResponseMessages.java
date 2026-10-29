package com.order.orderservice.swagger;

public final class SwaggerResponseMessages {

    private SwaggerResponseMessages() {}

    public static final String HTTP_200 = "Request completed successfully";
    public static final String HTTP_201 = "Resource created successfully";
    public static final String HTTP_202 = "Request accepted for processing";
    public static final String HTTP_204 = "Request completed successfully without response body";

    public static final String HTTP_400 = "Invalid request data";
    public static final String HTTP_401 = "Authentication required";
    public static final String HTTP_403 = "Access denied";
    public static final String HTTP_404 = "Requested resource not found";
    public static final String HTTP_409 = "Resource conflict";
    public static final String HTTP_422 = "Request cannot be processed due to semantic errors";
    public static final String HTTP_500 = "Internal server error";
    public static final String HTTP_503 = "Service temporarily unavailable";
}