package lv.javaguru.travel.insurance.core.validations.calculate.premium.agreement;

import lv.javaguru.travel.insurance.core.api.dto.agreement.AgreementDTO;
import lv.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import lv.javaguru.travel.insurance.core.util.DateTimeUtil;
import lv.javaguru.travel.insurance.core.validations.ValidationErrorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DateFromIsBeforeDateToValidationTest {
    @Mock
    private ValidationErrorFactory factory;
    @InjectMocks
    private DateFromIsBeforeDateToValidation validation;
    private AgreementDTO agreement;

    @BeforeEach
    void init() {
        agreement = mock(AgreementDTO.class);
    }

    @Test
    void shouldReturnErrorWhenAgreementDateToIsBeforeAgreementDateFrom() {
        when(agreement.getAgreementDateFrom()).thenReturn(DateTimeUtil.createDate("20.12.2025"));
        when(agreement.getAgreementDateTo()).thenReturn(DateTimeUtil.createDate("19.12.2025"));
        when(factory.buildError("ERROR_CODE_7")).thenReturn(new ValidationErrorDTO("ERROR_CODE_7", "Date from must be before date to!"));
        Optional<ValidationErrorDTO> validationError = validation.validate(agreement);
        assertThat(validationError).isPresent();
        assertThat(validationError.get().getErrorCode()).isEqualTo("ERROR_CODE_7");
        assertThat(validationError.get().getDescription()).isEqualTo("Date from must be before date to!");

    }

    @Test
    void shouldNotReturnError() {
        when(agreement.getAgreementDateFrom()).thenReturn(DateTimeUtil.createDate("12.03.2025"));
        when(agreement.getAgreementDateTo()).thenReturn(DateTimeUtil.createDate("13.03.2025"));
        Optional<ValidationErrorDTO> validationError = validation.validate(agreement);
        assertThat(validationError).isEmpty();
    }
    
}
