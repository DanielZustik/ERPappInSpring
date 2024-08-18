package com.zustik.erpappinspring.controllers;

import com.zustik.erpappinspring.exceptions.InvoiceNotFoundException;
import com.zustik.erpappinspring.models.Invoice;
import com.zustik.erpappinspring.dtos.InvoiceDTO;
import com.zustik.erpappinspring.services.InvoicesService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor //dochazi skrze to k DI... a neni nutne ani uvest autowire
@RequestMapping("invoices")
public class InvoicesController {
    private final InvoicesService invoicesService;

    @GetMapping("/findall")
    public ResponseEntity<List<InvoiceDTO>> findAllInvoices () {
        List<InvoiceDTO> invoices = invoicesService.findAllInvoices();
        if (invoices.isEmpty()) {
            return ResponseEntity.noContent().build(); //informuje se klient ze vse probehlo oka nicmene nejsou zadna data
        }
        return ResponseEntity.ok(invoices);
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addInvoice (
            @RequestBody @Valid InvoiceDTO invoiceDTO) { //pokud neodpovida pravidlum validace pak vyho exception kterou zacytamvam advicem
            Long id = invoicesService.addInvoice(invoicesService.convertToEntity(invoiceDTO));
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("id", String.valueOf(id))
                .body(null);

    }
// !!! jeste bude zpotrebi resit validaci clientem poslane veci zda neni body empty a zda obsahuje vse
    @PutMapping("/update")
    public ResponseEntity<Void> updateInvoice (
             @RequestBody @Valid InvoiceDTO invoiceDTOClient) {
        Invoice inv;
//        try {
        inv = invoicesService.findByNumberForUpdate(invoiceDTOClient.getNumber());
//            neni treba protoze je controller advice ktery prevezme cinnost kdyz vyskoci tato excp.
//        } catch (InvoiceNotFoundException e) {
//            return ResponseEntity.notFound().build();
//        } catch (Exception e) { //pro sich zachyceni vseho neocekavaneho kontrolovane vraceni reposnse nez ponechani defaultni error response ktera muze exponsnout detaily
//            return ResponseEntity
//                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(null);
//        }
        inv.setAmount(invoiceDTOClient.getAmount());
        inv.setNumber(invoiceDTOClient.getNumber());
        inv.setDueDate(invoiceDTOClient.getDueDate());
        inv.setRecipientOrSupplier(invoiceDTOClient.getRecipientOrSupplier());
        inv.setSubject(invoiceDTOClient.getSubject());
        invoicesService.updateInvoice(inv);

        return ResponseEntity.noContent().build();

//        Invoice i; //bez new protoze nechci vytvorit objetk novy pouze referenci ktera posleze bude odkazovat na nalezeny objekt
//        lepsi by bylo thrownout exception v servisce aby zde byla klasicka klausle try catch aby se sjednotilo reseni problemu tj ne jednou pres optional a ejdnou pres try catch
//        if (oI.isPresent()) {
//            i = oI.get();
//            i.setAmount(invoiceDTO.getAmount());
//            i.setNumber(invoiceDTO.getNumber());
//            invoicesService.updateInvoice(i);
//            return ResponseEntity.noContent().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
    }

    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<Void> deleteInvoice (
            @PathVariable Long id) { //its ok to catch excp here... not int traansacational service though...

        invoicesService.deleteInvoice(id);
        return ResponseEntity.noContent().build(); //204 success... idempotency dalsi delete request stale bude vracet response 204.. jakoze je to oka pryc...

    }

    @GetMapping("/id/{id}")
    public ResponseEntity<InvoiceDTO> findByIdInvoice (
            @PathVariable Long id) {

            InvoiceDTO invoiceDTO = invoicesService.findByID(id);
            return ResponseEntity.ok(invoiceDTO);
    }

    @GetMapping("/number/{number}")
    public ResponseEntity<InvoiceDTO> findByNumber (
            @PathVariable Integer number) {

            InvoiceDTO invoiceDTO = invoicesService.findByNumber(number);
            return ResponseEntity.ok(invoiceDTO);
    }
}
