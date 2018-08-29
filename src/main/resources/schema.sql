drop table AUDIT if exists;
create table AUDIT (RECORD_ID UUID NOT NULL, OPERATION varchar NOT NULL, AUDIT_TIMESTAMP BIGINT NOT NULL);

drop table MESSAGE if exists;
create table MESSAGE (MESSAGE_ID UUID NOT NULL, FROM_USER varchar, TO_USER varchar, TEXT varchar NOT NULL,
                      TIMESTMP BIGINT NOT NULL);
                      
drop table MARKET_ORDERS if exists;
create table MARKET_ORDERS (ORDER_ID UUID NOT NULL, TRADER_ID UUID NOT NULL,
							COMPANY_ID UUID NOT NULL, ORDER_SIDE varchar NOT NULL, 
							ORDER_TYPE varchar NOT NULL, PRICE FLOAT NOT NULL,
                      		VOLUME INTEGER NOT NULL, PLACEMENT_TIME DATE NOT NULL,
                      		ORDER_STATUS varchar NOT NULL);

