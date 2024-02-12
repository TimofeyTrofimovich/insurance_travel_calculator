package lv.javaguru.travel.insurance.dto.internal;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lv.javaguru.travel.insurance.dto.CoreResponse;
import lv.javaguru.travel.insurance.dto.ValidationError;
import lv.javaguru.travel.insurance.dto.util.BigDecimalSerializer;
import lv.javaguru.travel.insurance.dto.v2.PersonResponseDTO;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TravelGetAgreementResponse extends CoreResponse {

    private String uuid;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date agreementDateFrom;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date agreementDateTo;

    private String country;

    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal agreementPremium;

    @JsonAlias("persons")
    private List<PersonResponseDTO> persons;

    private Optional<ValidationError> error;

    public TravelGetAgreementResponse(List<ValidationError> errors) {
        super(errors);
    }

    public TravelGetAgreementResponse(Optional<ValidationError> error) {
        this.error = error;
    }
}
