package com.zustik.erpappinspring.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
//musi to byt bean? je to neco jako bean tim ze je to entity
@Entity //jpa si automaticky sam kdyz pouziji repo bude vytvaret instance tohoto a vkladat je do persistence contextu
@Data //JPA potrebuje jen defaultni noargs konstruktor ktery java poskytne automaticky, kdyz nezadam vlastni konstruktor... takze bude to fungovat
@Table(name = "invoices") //bez toho by chtelo pracovat s table invoice ktery neni
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version //diky tomuto se zamezi tomu aby dve thread zaroven neco menili protoze jpa vzycky zkontroluje nejprve verzi a vzdy ji inkrementuje pote co se provede nejaky update
    private Integer version;

    private Integer number;
    private LocalDate dateOfIssueOrReceipt;
    private LocalDate dueDate;
    private BigDecimal amount;
    private BigDecimal dueAmount;
    private String recipientOrSupplier;
    private String subject;
    private Integer projectNumber;
    private String projectManager;


//    What Happens When Two Threads Modify the Invoice:
//    Thread A:
//
//    Fetches the Invoice entity from the database.
//    Modifies the amount field.
//    When the transaction commits, the version field is incremented, and the entity is saved to the database.
//    Thread B:
//
//    Fetches the same Invoice entity from the database, but before Thread A's transaction has committed.
//    Modifies the date field.
//    Attempts to commit the transaction. However, since Thread A has already committed its changes and incremented the version field, JPA detects a version mismatch and throws an OptimisticLockException.
//    Result: Thread B's transaction is rolled back, and Thread A's changes are successfully persisted. Thread B can catch the exception, and decide whether to retry the operation or handle the conflict in another way.

}
