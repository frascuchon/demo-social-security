create table USER_ACCOUNTS (id identity,
						username varchar unique,
						password varchar not null,
						last_name varchar not null,
						first_name varchar not null,
						role varchar not null default 'ROLE_USER',
						sign_in_provider varchar,
						primary key (id));