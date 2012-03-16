UT_AR_CM_CC_CORE_PKG

UT_ARP_CC_AVAIL_CREDIT_PKG

UT_ARP_CC_AUTH_PKG

AP_APPROVAL_MATCHED_PKG


SELECT  * FROM ar_lookup_values WHERE lookup_type LIKE 'AR_CC%'

SELECT  * FROM ar_lookup_types WHERE lookup_type LIKE 'AR_CC%'

   AR_CUST_CREDIT_AUTHORIZATION

   AR_CUST_AVAILABLE_CREDIT

exec utplsql.TEST ('ARP_CC_AVAIL_CREDIT_PKG',recompile_in=>FALSE);

exec utplsql.TEST ('AR_CM_CC_CORE_PKG',recompile_in=>FALSE);

select * from dba_objects where object_name like 'ARP_CC%' and object_type like 'PACKAGE%'