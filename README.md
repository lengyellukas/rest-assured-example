# Simple example of using REST-assured

REST-assured homepage can be found under: http://rest-assured.io/

The example contains two files:
- RestApiJsonTest
- RestApiXmlTest

### RestApiJsonTest

The file contains simple tests of API Zippopotam.us (http://zippopotam.us/). API returns JSON results, so in these tests REST-assured functionality to easily validate JSON response is demonstrated.

### RestApiXmlTest

The file contains simple tests of API The BNB Linked Data Platform (https://bnb.data.bl.uk/) which provides access to the British National Bibliography. API returns XML, so in these tests REST-assured XML functionality to validate also XML is shown.

## To try it
1. Clone the repo
2. Execute **mvn test** in the project folder
