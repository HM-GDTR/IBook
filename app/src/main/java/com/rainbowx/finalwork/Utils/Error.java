package com.rainbowx.finalwork.Utils;

public class Error {
    public static enum ErrorType{
        TYPE_INTERNAL_ERROR(100),
        TYPE_SERVER_NO_RESPONSE(105),
        TYPE_CONNECTION_NOT_SUCCESS(107),
        TYPE_NOT_SUCCESS(113);
        private final int code;

        ErrorType(int num) {
            this.code = num;
        }

        public int getCode() {
            return code;
        }

        @Override
        public String toString() {
            return ""+code;
        }
    }
    protected static String lastError;

    public static void setLastError(String lastError) {
        Error.lastError = lastError;
    }

    public static String getLastError() {
        return lastError;
    }
}
