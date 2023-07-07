package ru.net.pakhomov.farmers.api.exception.farmer;

public class FarmerException extends RuntimeException {
    private final static String MESSAGE = "Farmer %s%s is already taken";

    public FarmerException(String str1, String str2) {
        super(String.format(MESSAGE, str1, str2));
    }
}
