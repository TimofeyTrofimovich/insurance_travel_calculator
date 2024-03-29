package lv.javaguru.travel.insurance.core.services.calculate.premium;

import lv.javaguru.travel.insurance.core.api.command.calculate.premium.TravelCalculatePremiumCoreCommand;
import lv.javaguru.travel.insurance.core.api.command.calculate.premium.TravelCalculatePremiumCoreResult;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.api.dto.agreement.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.person.PersonDTO;
import lv.javaguru.travel.insurance.core.api.dto.risk.RiskDTO;
import lv.javaguru.travel.insurance.core.services.entities.AgreementEntityFactory;
import lv.javaguru.travel.insurance.core.underwriting.TravelPremiumCalculationResult;
import lv.javaguru.travel.insurance.core.underwriting.TravelPremiumUnderwriting;
import lv.javaguru.travel.insurance.core.validations.calculate.premium.TravelAgreementValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Component
@Transactional
class TravelCalculatePremiumServiceImpl implements TravelCalculatePremiumService {

    @Autowired
    private TravelAgreementValidator agreementValidator;
    @Autowired
    private TravelPremiumUnderwriting premiumUnderwriting;
    @Autowired
    private AgreementEntityFactory agreementEntityFactory;

    @Override
    public TravelCalculatePremiumCoreResult calculatePremium(TravelCalculatePremiumCoreCommand command) {
        List<ValidationErrorDTO> errors = agreementValidator.validate(command.getAgreement());
        return errors.isEmpty()
                ? buildResponse(command.getAgreement())
                : buildResponse(errors);
    }

    private TravelCalculatePremiumCoreResult buildResponse(List<ValidationErrorDTO> errors) {
        return new TravelCalculatePremiumCoreResult(errors);
    }

    private TravelCalculatePremiumCoreResult buildResponse(AgreementDTO agreement) {

        calculateRiskPremiumsForAllPersons(agreement);

        BigDecimal totalAgreementPremium = calculateTotalAgreementPremium(agreement);
        agreement.setAgreementPremium(totalAgreementPremium);
        agreement.setUuid(UUID.randomUUID().toString());

        agreementEntityFactory.createAgreementEntity(agreement);

        TravelCalculatePremiumCoreResult coreResult = new TravelCalculatePremiumCoreResult();
        coreResult.setAgreement(agreement);
        return coreResult;
    }


    private void calculateRiskPremiumsForAllPersons(AgreementDTO agreement) {
        agreement.getPersons().forEach(person -> {
            TravelPremiumCalculationResult calculationResult = premiumUnderwriting.calculatePremium(agreement, person);
            person.setSelectedRisks(calculationResult.getRisks());
        });
    }

    private BigDecimal calculateTotalAgreementPremium(AgreementDTO agreement) {
        return agreement.getPersons().stream()
                .map(PersonDTO::getSelectedRisks)
                .flatMap(Collection::stream)
                .map(RiskDTO::getPremium)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
