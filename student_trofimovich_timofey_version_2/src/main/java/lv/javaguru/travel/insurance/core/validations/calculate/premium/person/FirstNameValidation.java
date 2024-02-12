package lv.javaguru.travel.insurance.core.validations.calculate.premium.person;

import lv.javaguru.travel.insurance.core.api.dto.agreement.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.person.PersonDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
class FirstNameValidation extends TravelPersonFieldValidationImpl {
    @Autowired
    private ValidationErrorFactory factory;
    @Override
    public Optional<ValidationErrorDTO> validate(PersonDTO person, AgreementDTO agreement) {
        return (person.getPersonFirstName() == null || person.getPersonFirstName().isBlank())
                ? Optional.of(factory.buildError("ERROR_CODE_1"))
                : Optional.empty();
    }
}
