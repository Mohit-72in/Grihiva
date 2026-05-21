CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(120),
    updated_by VARCHAR(120),
    version BIGINT,
    full_name VARCHAR(120) NOT NULL,
    phone_number VARCHAR(20) NOT NULL,
    email VARCHAR(120),
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    owner_type VARCHAR(20),
    kyc_document_url VARCHAR(255),
    kyc_last_four VARCHAR(20),
    kyc_approved BIT NOT NULL,
    CONSTRAINT uk_users_phone UNIQUE (phone_number),
    CONSTRAINT uk_users_email UNIQUE (email)
) ENGINE=InnoDB;

CREATE TABLE buildings (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(120),
    updated_by VARCHAR(120),
    version BIGINT,
    name VARCHAR(120) NOT NULL,
    address_line VARCHAR(255),
    property_type VARCHAR(20) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE units (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(120),
    updated_by VARCHAR(120),
    version BIGINT,
    building_id BIGINT NOT NULL,
    unit_number VARCHAR(50) NOT NULL,
    category VARCHAR(20) NOT NULL,
    base_rent DECIMAL(12,2) NOT NULL,
    rent_override DECIMAL(12,2),
    electricity_rate_override DECIMAL(12,4),
    meter_number VARCHAR(50),
    status VARCHAR(20) NOT NULL,
    expected_vacate_date DATE,
    current_renter_id BIGINT,
    active BIT NOT NULL,
    CONSTRAINT fk_units_building FOREIGN KEY (building_id) REFERENCES buildings (id),
    CONSTRAINT fk_units_current_renter FOREIGN KEY (current_renter_id) REFERENCES users (id),
    CONSTRAINT uk_building_unit_number UNIQUE (building_id, unit_number)
) ENGINE=InnoDB;

CREATE TABLE transaction_ledgers (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    created_by VARCHAR(120),
    updated_by VARCHAR(120),
    version BIGINT,
    unit_id BIGINT NOT NULL,
    renter_id BIGINT NOT NULL,
    billing_period VARCHAR(7) NOT NULL,
    rent_amount DECIMAL(12,2) NOT NULL,
    utility_amount DECIMAL(12,2) NOT NULL,
    additional_charges DECIMAL(12,2) NOT NULL,
    previous_balance DECIMAL(12,2) NOT NULL,
    total_due DECIMAL(12,2) NOT NULL,
    total_paid DECIMAL(12,2) NOT NULL,
    balance_carry_forward DECIMAL(12,2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    CONSTRAINT fk_tx_ledger_unit FOREIGN KEY (unit_id) REFERENCES units (id),
    CONSTRAINT fk_tx_ledger_renter FOREIGN KEY (renter_id) REFERENCES users (id)
) ENGINE=InnoDB;

CREATE INDEX idx_tx_ledger_unit_period ON transaction_ledgers (unit_id, billing_period);
CREATE INDEX idx_tx_ledger_renter_period ON transaction_ledgers (renter_id, billing_period);
