# Tools to support backend-assignment

The list of tools for backed assessment task for candidates for BE Java Developer:

1. [PBX mockup server](mock_pbx/readme.md) to emulate PBX service to integrate into solution.
2. [Phone-Book data file](mock_pbx/pb_directory.csv) to pre-load into Phone-Book solution.

# Solution Guidelines
The solution is a (part of a) CRM system.
It supports connecting with PBXs and synchronizing the CDRs in three ways:
1. When configuring the PBX integration
2. At each end-of-day, at 20:00
3. Through an exposed webhook which the PBXs invoke on a call's state change

## Endpoints
There is exposed CRUD functionality for CDRs, PBXs and Contacts at the paths /cdr, /pbx and /contact, respectively.

Furthermore, a summary report can be generated for a tenant at the path /report.

These can be found through the Swagger UI found at: http://localhost:8080/swagger-ui/.
