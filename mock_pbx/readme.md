## PBX SERVER MOCK API
This application mocks some APIs of a PBX server, and emulates webhook activities for a call behavior.
The purpose of this application is to replicate all the required endpoints required to complete the assignment given to you.  
For simplicity reasons, all the CDRs are outbound calls made by a random calling number to random destinations (contacts).

Prerequisites
-
Latest nodeJS - https://nodejs.org/en/download/  

Setup & Run
-
Open a shell window on the root directory and run the following commands  
$> npm i  
$> node ./mock_server.js

Notes:
1. The settings of this application can be found under ./lib/settings.js
2. The application default port is 3030
3. You will need to modify `webhook_url` under settings.js to your service endpoint
4. All the response headers have been modified to contain the tenant-uuid 

How to use
-
After running the service you can call `/get_cdr` endpoint to load some initial data (5 records) into your service. Any consecutive calls to this endpoint will generate more off-line CDRs.  
Once the initial data is stored, and your microservice ready to handle call events, you should manually call `/trigger_webhooks`. This endpoint will fire the webhooks randomly like they could be fired in a real server. Note that a phone call might not finish.
Finally, after making several calls to `/trigger_webhooks` you can call `/get_cdr` endpoint to retrieve more data. This will help you simulate the sync functionality.

Endpoints / Methods
-
Route:

    /get_cdr

Curl Example:
  
    curl -X GET "https://localhost:3030/get_cdr"

Response:

    {
        "total": 1,
        "data": [
            {
                "uuid": "6c4c0ae0-e99c-466a-8dce-d99207ac2688",
                "domain_name": "mockpbx.impactpbx.com",
                "caller_name": "",
                "caller_number": 35725262136,
                "destination_number": 35799123458,
                "direction": "outbound",
                "call_start": "2020-08-26T15:49:32.000Z",
                "ring_start": "2020-08-26T15:49:36.000Z",
                "answer_start": "2020-08-26T15:49:37.000Z",
                "call_end": "2020-08-26T15:50:36.000Z",
                "duration": 64,
                "recording": "3aad34f5-5915-4b74-9ab3-131feb14f067",
                "click_to_call": false,
                "click_to_call_data": "",
                "action": "HANGUP"
            }
        ]
    }
    
> Note: Usually a CDR record contains more details  
---

Route:

    /trigger_webhooks
    
Curl Example:
   
     curl -X GET "https://localhost:3030/trigger_webhooks"
 
 Response:
 
     {
         "status": "firing webhooks"
     }
     
 > Note: This endpoint will fire consecutive webhook calls randomly. When a call is made, the PBX fires different webhooks based on the event of the call. Those events are usually: "ORIGINATE", "RING", "ANSWERED", "HANGUP".  
 > The console of this application will contain the `response.data` of the request to help you with debugging.
