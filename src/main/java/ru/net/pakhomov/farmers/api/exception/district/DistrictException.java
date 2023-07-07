package ru.net.pakhomov.farmers.api.exception.district;

public class DistrictException extends RuntimeException {
    private final static String MESSAGE = "District %s%s is already taken";

    public DistrictException(String str1, String str2) {
        super(String.format(MESSAGE, str1, str2));
    }
}
