package it.pagopa.selfcare.pagopa.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IbanAggregate {

    private List<IbanEntity> ibansSlice;
    private Long total;

}
