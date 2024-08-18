package com.zustik.erpappinspring;

import com.zustik.erpappinspring.models.Invoice;
import com.zustik.erpappinspring.services.InvoicesService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ErPappInSpringApplication implements CommandLineRunner {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = SpringApplication.run(ErPappInSpringApplication.class, args);
//        kdybych si ulozil tuto entitu nalezenoua  nasledne menil jeji filds tak JPA
//        uplne sam automaticky provede stejne operace v DB! viz persisent context
//        ucinit tak v momente kdy se provede posledni radek metody oznacene jako transactional (nebo ihned pokud nejsou provadeny kroky v ramci metody/ramce transakce)
//        nasledne se kontext vycisti a jakekoliv dalsi zmeny v entity se uz nepromitnou
        System.out.println(context.getBean(InvoicesService.class).findByID(1L));

//        @Transactional
//        public void updateInvoiceAmount(Integer id, BigDecimal newAmount) {
//            Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Invoice not found"));
//            invoice.setAmount(newAmount);
//            // Changes are automatically persisted at the end of the transaction

//        pokud nejde ale o soucast transakce tak se to nemusi provest a je lepsi explicitne savnout
//        Invoice invoice = invoiceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Invoice not found"));
//        invoice.setAmount(newAmount);
//        invoiceRepository.save(invoice); // Explicitly save the changes
    }

    @Override
    public void run(String... args) throws Exception {
//        zde lze incializovat ruzne objekty apod. provede se to pred tim nez se appka plne nacte
    }
}
