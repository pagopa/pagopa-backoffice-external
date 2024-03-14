package it.pagopa.selfcare.pagopa.service.impl;

import it.pagopa.selfcare.pagopa.entities.OperativeTableEntity;
import it.pagopa.selfcare.pagopa.model.tavoloop.OperativeTable;
import it.pagopa.selfcare.pagopa.model.tavoloop.OperativeTableResourceList;
import it.pagopa.selfcare.pagopa.repository.OperativeTableRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OperativeTableServiceImplTest {

    @Mock
    private OperativeTableRepository operativeTableRepositoryMock;

    @InjectMocks
    private OperativeTableServiceImpl sut;

    @Test
    void getOperativeTablesSuccess() {
        OperativeTableEntity operativeTable = buildOperativeTableEntity();

        when(operativeTableRepositoryMock.findByTaxCodeLikeAndNameLikeIgnoreCase(anyString(), anyString()))
                .thenReturn(Collections.singletonList(operativeTable));

        OperativeTableResourceList result = sut.getOperativeTables(anyString(), anyString());

        assertNotNull(result);
        assertNotNull(result.getOperativeTableList());
        assertEquals(1, result.getOperativeTableList().size());

        OperativeTable resultTable = result.getOperativeTableList().get(0);
        assertEquals(operativeTable.getName(), resultTable.getName());
        assertEquals(operativeTable.getEmail(), resultTable.getEmail());
        assertEquals(operativeTable.getTaxCode(), resultTable.getTaxCode());
    }

    @Test
    void getOperativeTablesSuccessWithoutResult() {
        when(operativeTableRepositoryMock.findByTaxCodeLikeAndNameLikeIgnoreCase(anyString(), anyString()))
                .thenReturn(Collections.emptyList());

        OperativeTableResourceList result = sut.getOperativeTables(anyString(), anyString());

        assertNotNull(result);
        assertNotNull(result.getOperativeTableList());
        assertTrue(result.getOperativeTableList().isEmpty());
    }

    private static OperativeTableEntity buildOperativeTableEntity() {
        OperativeTableEntity operativeTable = new OperativeTableEntity();
        operativeTable.setName("name");
        operativeTable.setEmail("email");
        operativeTable.setTaxCode("taxCode");
        return operativeTable;
    }
}