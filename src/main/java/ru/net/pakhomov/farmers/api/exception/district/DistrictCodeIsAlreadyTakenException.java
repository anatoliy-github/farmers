package ru.net.pakhomov.farmers.api.exception.district;

public class DistrictCodeIsAlreadyTakenException extends DistrictException {
    public DistrictCodeIsAlreadyTakenException(String districtCode) {
        super("code=", districtCode);
    }
}
