package ru.net.pakhomov.farmers.api.exception.farmer;

public class FarmerInnAlreadyTakenException extends FarmerException {

    public FarmerInnAlreadyTakenException(String inn) {
        super("inn=", inn);
    }
}
