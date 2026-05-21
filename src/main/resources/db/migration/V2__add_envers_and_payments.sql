CREATE TABLE IF NOT EXISTS transaction_payments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(120),
    updated_by VARCHAR(120),
    version BIGINT,
    ledger_entry_id BIGINT NOT NULL,
    collected_by_id BIGINT,
    payment_mode VARCHAR(20) NOT NULL,
    amount DECIMAL(12,2) NOT NULL,
    external_reference VARCHAR(120),
    received_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_tx_payment_ledger FOREIGN KEY (ledger_entry_id) REFERENCES transaction_ledgers (id),
    CONSTRAINT fk_tx_payment_collected_by FOREIGN KEY (collected_by_id) REFERENCES users (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS revinfo (
    rev INT NOT NULL AUTO_INCREMENT,
    revtstmp BIGINT,
    PRIMARY KEY (rev)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS users_aud (
    id BIGINT NOT NULL,
    rev INT NOT NULL,
    revtype TINYINT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(120),
    updated_by VARCHAR(120),
    version BIGINT,
    full_name VARCHAR(120),
    phone_number VARCHAR(20),
    email VARCHAR(120),
    password_hash VARCHAR(255),
    role VARCHAR(20),
    owner_type VARCHAR(20),
    kyc_document_url VARCHAR(255),
    kyc_last_four VARCHAR(20),
    kyc_approved BIT,
    PRIMARY KEY (id, rev),
    CONSTRAINT fk_users_aud_rev FOREIGN KEY (rev) REFERENCES revinfo (rev)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS buildings_aud (
    id BIGINT NOT NULL,
    rev INT NOT NULL,
    revtype TINYINT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(120),
    updated_by VARCHAR(120),
    version BIGINT,
    name VARCHAR(120),
    address_line VARCHAR(255),
    property_type VARCHAR(20),
    PRIMARY KEY (id, rev),
    CONSTRAINT fk_buildings_aud_rev FOREIGN KEY (rev) REFERENCES revinfo (rev)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS units_aud (
    id BIGINT NOT NULL,
    rev INT NOT NULL,
    revtype TINYINT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(120),
    updated_by VARCHAR(120),
    version BIGINT,
    building_id BIGINT,
    unit_number VARCHAR(50),
    category VARCHAR(20),
    base_rent DECIMAL(12,2),
    rent_override DECIMAL(12,2),
    electricity_rate_override DECIMAL(12,4),
    meter_number VARCHAR(50),
    status VARCHAR(20),
    expected_vacate_date DATE,
    current_renter_id BIGINT,
    active BIT,
    PRIMARY KEY (id, rev),
    CONSTRAINT fk_units_aud_rev FOREIGN KEY (rev) REFERENCES revinfo (rev)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS transaction_ledgers_aud (
    id BIGINT NOT NULL,
    rev INT NOT NULL,
    revtype TINYINT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(120),
    updated_by VARCHAR(120),
    version BIGINT,
    unit_id BIGINT,
    renter_id BIGINT,
    billing_period VARCHAR(7),
    rent_amount DECIMAL(12,2),
    utility_amount DECIMAL(12,2),
    additional_charges DECIMAL(12,2),
    previous_balance DECIMAL(12,2),
    total_due DECIMAL(12,2),
    total_paid DECIMAL(12,2),
    balance_carry_forward DECIMAL(12,2),
    status VARCHAR(20),
    PRIMARY KEY (id, rev),
    CONSTRAINT fk_tx_ledgers_aud_rev FOREIGN KEY (rev) REFERENCES revinfo (rev)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS transaction_payments_aud (
    id BIGINT NOT NULL,
    rev INT NOT NULL,
    revtype TINYINT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    created_by VARCHAR(120),
    updated_by VARCHAR(120),
    version BIGINT,
    ledger_entry_id BIGINT,
    collected_by_id BIGINT,
    payment_mode VARCHAR(20),
    amount DECIMAL(12,2),
    external_reference VARCHAR(120),
    received_at TIMESTAMP,
    PRIMARY KEY (id, rev),
    CONSTRAINT fk_tx_payments_aud_rev FOREIGN KEY (rev) REFERENCES revinfo (rev)
) ENGINE=InnoDB;

