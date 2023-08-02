CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

create table if not exists transaction_files(
    id uuid primary key default uuid_generate_v4(),
    file_name varchar(255) not null unique,
    status VARCHAR(50) DEFAULT 'WAITING' NULL,
    start_process_time timestamp with time zone default current_timestamp not null,
    end_process_time timestamp with time zone,
    file_data bytea NOT NULL
)