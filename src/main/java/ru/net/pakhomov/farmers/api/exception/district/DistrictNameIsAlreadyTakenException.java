package ru.net.pakhomov.farmers.api.exception.district;

public class DistrictNameIsAlreadyTakenException extends DistrictException {
    public DistrictNameIsAlreadyTakenException(String districtName) {
        super("name=", districtName);
    }
}
