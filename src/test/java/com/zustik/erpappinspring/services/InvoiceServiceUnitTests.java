package com.zustik.erpappinspring.services;

import com.zustik.erpappinspring.models.Invoice;
import com.zustik.erpappinspring.repos.InvoicesRepo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class InvoiceServiceUnitTests {

    @Test
    @DisplayName("tests the invoice was added and that its id was retrieved") //additional info
    public void addInvoiceHappyFlow() {
        InvoicesRepo invoicesRepo = Mockito.mock(InvoicesRepo.class);
        InvoicesService invoicesService = new InvoicesService(invoicesRepo);

        Invoice i = new Invoice();
        i.setId(1L);
        i.setAmount(new BigDecimal(1000));
        i.setDueAmount(new BigDecimal(0));
        i.setDateOfIssueOrReceipt(LocalDate.of(2024,1,1));
        i.setDueDate(LocalDate.of(2024,1,31));
        i.setNumber(202400001);
        i.setSubject("hriste");
        i.setRecipientOrSupplier("Skola v Dobre");

        when(invoicesRepo.save(i)).thenReturn(i); //prozatim jen modelovani chovani repa ze vraci presne to co do nej bylo vlozeno

        Long id = invoicesService.addInvoice(i);

        verify(invoicesRepo).save(i); //overeni ze save metoda byla v repu zavolana s parametrem i
        assertEquals(i.getId(), id);
    }

    @Test
    @DisplayName("tests the invoice was deleted and msg 'deleted' obtained") //additional info
    public void deleteInvoiceHappyFlow() {
        InvoicesRepo invoicesRepo = Mockito.mock(InvoicesRepo.class);
        InvoicesService invoicesService = new InvoicesService(invoicesRepo);

        Invoice i = new Invoice();
        i.setId(1L);
        i.setAmount(new BigDecimal(1000));
        i.setDueAmount(new BigDecimal(0));
        i.setDateOfIssueOrReceipt(LocalDate.of(2024,1,1));
        i.setDueDate(LocalDate.of(2024,1,31));
        i.setNumber(202400001);
        i.setSubject("hriste");
        i.setRecipientOrSupplier("Skola v Dobre");

        doNothing().when(invoicesRepo).deleteById(i.getId());

        String result = invoicesService.deleteInvoice(i.getId());

        verify(invoicesRepo).deleteById(i.getId());
        assertEquals("invoice deleted", result);
    }
}
