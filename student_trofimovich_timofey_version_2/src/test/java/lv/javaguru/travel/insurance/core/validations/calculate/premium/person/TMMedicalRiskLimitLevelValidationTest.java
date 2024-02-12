package lv.javaguru.travel.insurance.core.validations.calculate.premium.person;


import lv.javaguru.travel.insurance.core.api.dto.agreement.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.person.PersonDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.domain.ClassifierValue;
import lv.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TMMedicalRiskLimitLevelValidationTest {
    @Mock
    private ValidationErrorFactory factory;
    @Mock
    private ClassifierValueRepository repository;
    @InjectMocks
    private MedicalRiskLimitLevelValidation validation;
    private PersonDTO person;

    private AgreementDTO agreement;

    @BeforeEach
    void init() {
        person = mock(PersonDTO.class);
        agreement = mock(AgreementDTO.class);
    }

    @Test
    void shouldNotReturnErrorWhenMedicalRiskIsNotIncluded() {
        when(agreement.getSelectedRisks()).thenReturn(List.of());
        Optional<ValidationErrorDTO> error = validation.validate(person, agreement);
        assertThat(error).isEmpty();
    }


    @ParameterizedTest
    @NullAndEmptySource
    void shouldReturnEmptyOrNullLimitLevelError(String limit_level) {
        when(agreement.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICAL"));
        when(person.getMedicalRiskLimitLevel()).thenReturn(limit_level);
        when(factory.buildError("ERROR_CODE_14")).thenReturn(new ValidationErrorDTO());
        assertThat(validation.validate(person, agreement)).isPresent();
    }


    @Test
    void shouldReturnLimitNotSupportedError() {
        when(agreement.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICAL"));
        when(person.getMedicalRiskLimitLevel()).thenReturn("level");
        when(repository.findByClassifierTitleAndIc("MEDICAL_RISK_LIMIT_LEVEL",
                person.getMedicalRiskLimitLevel()))
                .thenReturn(Optional.empty());
        when(factory.buildError(eq("ERROR_CODE_15"), anyList())).thenReturn(new ValidationErrorDTO());
        assertThat(validation.validate(person, agreement)).isPresent();
    }

    @Test
    void shouldNotReturnError() {
        when(agreement.getSelectedRisks()).thenReturn(List.of("TRAVEL_MEDICAL"));
        when(person.getMedicalRiskLimitLevel()).thenReturn("level");
        when(repository.findByClassifierTitleAndIc("MEDICAL_RISK_LIMIT_LEVEL",
                person.getMedicalRiskLimitLevel()))
                .thenReturn(Optional.of(mock(ClassifierValue.class)));
        assertThat(validation.validate(person, agreement)).isEmpty();
    }


}
