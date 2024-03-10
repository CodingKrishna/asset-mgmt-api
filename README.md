# Asset Management -api

Asset Management Module
Sample Asset Management Module with Multi Tenancy
Build an Asset Management module to manage assets of each client’s using following stacks
Spring Boot 3, Postgres, Lombok, Flyway

Schemas	Tables

app_main	clients
id
tenant_id
company
email
mobile
address
status

cli_{tenant_id}	assets_type
id
type
status

 	assets_
 	id
 	type_id
 	asset_name
 	sl_no
 	ref_no
 	issued_date
 	more_details
 	status

Reference:
https://github.com/dkambale/multi-tenant-microservice/tree/main
https://www.youtube.com/watch?v=4jqfbiEXUtI


![image](https://github.com/CodingKrishna/asset-mgmt-api/assets/11484609/c1ae47d4-aad4-45f0-b5c2-d9a632c1943a)

![image](https://github.com/CodingKrishna/asset-mgmt-api/assets/11484609/d72386e4-4765-435a-96f3-9475240e63c2)
