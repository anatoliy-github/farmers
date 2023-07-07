package ru.net.pakhomov.farmers.api.model.farmer;

public enum LegalForm {
    COMPANY("ЮЛ"),
    PERSON("ФЛ"),
    INDIVIDUAL("ИП");

    private final String code;

    LegalForm(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static LegalForm fromCode(String code) {
        for (LegalForm legalForm : LegalForm.values()) {
            if(legalForm.getCode().equals(code)) {
                return legalForm;
            }
        }
        throw new UnsupportedOperationException("\nThe value " + code + " is not supported\n");
    }
}
