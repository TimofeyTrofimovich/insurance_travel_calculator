package lv.javaguru.travel.insurance.core.validations.calculate.premium.agreement;

import lv.javaguru.travel.insurance.core.api.dto.agreement.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import lv.javaguru.travel.insurance.core.util.Placeholder;
import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CountryValidation extends TravelAgreementFieldValidationImpl {
    @Autowired
    private ValidationErrorFactory errorFactory;
    @Autowired
    private ClassifierValueRepository classifierValueRepository;

    @Override
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreement) {
        if (countryIsEmpty(agreement)) {
            return Optional.of(errorFactory.buildError("ERROR_CODE_10"));
        } else if (!countryIsSupported(agreement)) {
            return Optional.of(errorFactory.buildError("ERROR_CODE_11", List.of(new Placeholder("country", agreement.getCountry()))));
        }
        return Optional.empty();
    }

    private boolean countryIsEmpty(AgreementDTO agreement) {
        return agreement.getCountry() == null || agreement.getCountry().isBlank();
    }

    private boolean countryIsSupported(AgreementDTO agreement) {
        String country = agreement.getCountry();
        return countryIsSupported(country);
    }


    private boolean countryIsSupported(String country) {
        return classifierValueRepository.findByClassifierTitleAndIc("COUNTRY", country).isPresent();
    }

}