drop table AUDIT if exists;
create table AUDIT (RECORD_ID UUID NOT NULL, OPERATION varchar NOT NULL, AUDIT_TIMESTAMP BIGINT NOT NULL);

drop table MESSAGE if exists;
create table MESSAGE (MESSAGE_ID UUID NOT NULL, FROM_USER varchar, TO_USER varchar, TEXT varchar NOT NULL,
                      TIMESTMP BIGINT NOT NULL);
                      
drop table MARKET_ORDERS if exists;
create table MARKET_ORDERS (ORDER_ID UUID NOT NULL, TRADER_ID UUID NOT NULL,
							TICKER_SYMBOL varchar NOT NULL, ORDER_SIDE varchar NOT NULL, 
							ORDER_TYPE varchar NOT NULL, PRICE FLOAT NOT NULL,
                      		VOLUME INTEGER NOT NULL, PLACEMENT_TIME DATE NOT NULL,
                      		ORDER_STATUS varchar NOT NULL);

                      		
drop table LISTED_COMPANIES if exists;
create table LISTED_COMPANIES(COMPANY_ID UUID NOT NULL, COMPANY_NAME varchar NOT NULL,
							  COMPANY_SECTOR varchar NOT NULL, TICKER_SYMBOL varchar NOT NULL);
							 
INSERT INTO LISTED_COMPANIES (COMPANY_ID, COMPANY_NAME,COMPANY_SECTOR, TICKER_SYMBOL )
					VALUES ('838f9b60-ab83-11e8-98d0-529269fb1459', 'Credit Suisse', 'Financial', 'CS');
