package com.zustik.erpappinspring.repos;

import com.zustik.erpappinspring.models.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

//JpaRepo<datamodel, data type of ID>
public interface InvoicesRepo extends JpaRepository<Invoice, Long> {
    //JPA nabizi zakladni CRUD operace, nicmene nabizi taky i dalsi, staci nadefinovat metodu
    //v jejiz signature v nazvu bude uveden nazev atributu
    //v pripade specifictejsich potreb je nutne napsat vlastni query skrze @Query() a jazyk JPA podobny SQL
    Optional<Invoice> findByNumber (Integer n);
    Optional<Invoice> findBySubject(String n);
    Optional<Invoice> findByNumberGreaterThan (Integer n);
    Optional<Invoice> findByAmountGreaterThan (BigDecimal n);
    Optional<Invoice> findByDueAmountGreaterThan (BigDecimal n);
    List<Invoice> findByAmountGreaterThanAndAmountLessThan (BigDecimal lowerBound, BigDecimal upperBound);


}
