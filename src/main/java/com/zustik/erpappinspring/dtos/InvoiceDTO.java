package com.zustik.erpappinspring.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Data //zahrunje requiredargsConstructor pozadujici input pro vsechny final fields field v entity nemohou byt final JPA s nimi pracuje
public class InvoiceDTO {
    @Pattern(regexp = "20241\\d{5}$", message = "invoice number  needed and must start with 20241..")
    private Integer number;
    @NotNull(message = "issue/receipt date needed")
    private LocalDate dateOfIssueOrReceipt;
    @NotNull(message = "due date needed")
    private LocalDate dueDate;
    @NotNull(message = "amount needed")
    private BigDecimal amount;
    @NotNull(message = "due amount needed")
    private BigDecimal dueAmount;
    @NotBlank(message = "recipient/supplier needed")
    private String recipientOrSupplier;
    @NotBlank(message = "subject needed")
    private String subject;

}
