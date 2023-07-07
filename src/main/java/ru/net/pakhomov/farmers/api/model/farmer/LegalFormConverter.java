package ru.net.pakhomov.farmers.api.model.farmer;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LegalFormConverter implements AttributeConverter<LegalForm, String> {
    @Override
    public String convertToDatabaseColumn(LegalForm legalForm) {
        if(legalForm == null) {
            return null;
        }
        return legalForm.getCode();
    }

    @Override
    public LegalForm convertToEntityAttribute(String code) {
        if(code == null) {
            return null;
        }
        return LegalForm.fromCode(code);
    }
}
