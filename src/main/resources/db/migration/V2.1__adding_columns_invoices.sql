-- Step 1: Add the new columns with default values
ALTER TABLE invoices
    ADD COLUMN due_amount DECIMAL(12,2) NOT NULL DEFAULT 0,
    ADD COLUMN due_date DATE NOT NULL DEFAULT '2024-01-01',
    ADD COLUMN recipient_or_supplier VARCHAR(255) NOT NULL DEFAULT '',
    ADD COLUMN subject VARCHAR(255) NOT NULL DEFAULT '',
    ADD COLUMN project_number INT NOT NULL DEFAULT 0,
    ADD COLUMN project_manager VARCHAR(255) NOT NULL DEFAULT '';

-- Step 2: Remove the default values for new records
ALTER TABLE invoices
    ALTER COLUMN due_amount DROP DEFAULT,
    ALTER COLUMN due_date DROP DEFAULT,
    ALTER COLUMN recipient_or_supplier DROP DEFAULT,
    ALTER COLUMN subject DROP DEFAULT,
    ALTER COLUMN project_number DROP DEFAULT,
    ALTER COLUMN project_manager DROP DEFAULT;
