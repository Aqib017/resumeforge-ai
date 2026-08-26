package com.resumeforge.common.response;

public record ApiResponse (boolean success, String message, Object data) {

}
