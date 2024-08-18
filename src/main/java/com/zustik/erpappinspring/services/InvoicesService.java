package com.zustik.erpappinspring.services;

import com.zustik.erpappinspring.dtos.InvoiceDTO;
import com.zustik.erpappinspring.exceptions.InvoiceNotFoundException;
import com.zustik.erpappinspring.models.Invoice;
import com.zustik.erpappinspring.repos.InvoicesRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional //aspect intercept volani vsech metod zde zapocne transakci a pokud nevyskoci runtime eexp. tak commitne po dokonceni metody
public class InvoicesService {
    private final InvoicesRepo invoicesRepo;

   public Long addInvoice (Invoice invoice) {
       return invoicesRepo.save(invoice).getId();
   }

    public void updateInvoice (Invoice invoice) throws ConstraintViolationException, OptimisticLockingFailureException {
        invoicesRepo.save(invoice);
    }
// catching some of the possible excp. but there must be better way.. there are too many to catch
    public String deleteInvoice (Long id) throws EmptyResultDataAccessException, DataIntegrityViolationException {
        invoicesRepo.deleteById(id);
        return "invoice deleted";
    }

    public List<InvoiceDTO> findAllInvoices () {
        return convertInvToInvDto(invoicesRepo.findAll());
    }

    //optinonal pro pripad, ze by se vratila null.. aby nedoslo k null pointer excp. Jinak by se dalo osetrit klasicky if == null
    public InvoiceDTO findByID (Long id) {
        return convertInvToInvDto(invoicesRepo.findById(id));
    }

    public InvoiceDTO findByNumber (Integer n) {
        return convertInvToInvDto(invoicesRepo.findByNumber(n));
    }

    public Invoice findByNumberForUpdate(Integer n) {
        Optional<Invoice> inv =  invoicesRepo.findByNumber(n);
        if (inv.isPresent())
            return inv.get();
        else throw new InvoiceNotFoundException("Invoice not found");
    }

    private List<InvoiceDTO> convertInvToInvDto(List<Invoice> allInvoices) {
        return allInvoices.stream()
                // map(functional interface Function<T, R>) protoze jde o funckni interf. je monze uzit generics cimz compiler kontroluje zda poskytujeme spravne vstupy, vystupu, zaroven nemusime pak castovat vysledek, muzeme pouzit intreface pro ruzne datove typy aniz bychom definovali nove interface, a hned je take patrne jake vstupy a vystpupy vlastne jdou do jedine abstraktni metody v danem interfacu
                .map(this::convertToDto)//reference na metodu tohoto objektu convert..
                .toList();
    }

    private InvoiceDTO convertInvToInvDto(Optional<Invoice> invoice) throws InvoiceNotFoundException{ //neni nutne specifikovat protoze jde o unchecked excp. resp. kdyby bylo checked musel bcyh catchnout nebo propagovat timto vyse. ale pro dokuemntaci to je dobre uvest
       if (invoice.isPresent())
        return convertToDto(invoice.get());
       else throw new InvoiceNotFoundException("Invoice not found"); //mohl jsem nechat puvodni excp. entity not found... ale tohle je vice clear pro callera k cemu doslo
    }

    private InvoiceDTO convertToDto(Invoice inv) {
        InvoiceDTO invDTO = new InvoiceDTO();
        invDTO.setNumber(inv.getNumber());
        invDTO.setAmount(inv.getAmount());
        invDTO.setSubject(inv.getSubject());
        invDTO.setDueDate(inv.getDueDate());
        invDTO.setRecipientOrSupplier(inv.getRecipientOrSupplier());
        return invDTO;
    }

    public Invoice convertToEntity(InvoiceDTO invDTO) {
        Invoice inv = new Invoice();
        inv.setNumber(inv.getNumber());
        inv.setAmount(inv.getAmount());
        inv.setSubject(inv.getSubject());
        inv.setDueDate(inv.getDueDate());
        inv.setRecipientOrSupplier(inv.getRecipientOrSupplier());
        return inv;
    }
}
