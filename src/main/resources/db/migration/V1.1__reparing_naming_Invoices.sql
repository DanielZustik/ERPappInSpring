ALTER TABLE invoices
    CHANGE dateOfIssueOrReceipt date_of_issue_or_receipt DATE NOT NULL;

-- hibernate automaticky prjmenonava fields v modelu timto stylem _ proto bud musim zmenit jak nyni delam nazev column v DB, nebo to lze anotovat ten nazev v modelu