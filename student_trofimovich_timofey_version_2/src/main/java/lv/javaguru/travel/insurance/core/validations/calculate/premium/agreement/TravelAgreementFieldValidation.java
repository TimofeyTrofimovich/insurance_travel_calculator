package lv.javaguru.travel.insurance.core.validations.calculate.premium.agreement;

import lv.javaguru.travel.insurance.core.api.dto.agreement.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface TravelAgreementFieldValidation {
    Optional<ValidationErrorDTO> validate(AgreementDTO request);

    List<ValidationErrorDTO> validateList(AgreementDTO request);
}
