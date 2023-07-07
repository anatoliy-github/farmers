package ru.net.pakhomov.farmers.api.exception.district;

public class DistrictIsAlreadyTakenException extends DistrictException {

    public DistrictIsAlreadyTakenException(String districtName, String districtCode) {
        super("name=" + districtName, " and code=" + districtCode);
    }
}
