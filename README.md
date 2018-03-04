# WebServiceUtility

Its an easy utility to handle http post requist (Request -> Response). 


## How to integrate into your app?

Step 1. : Create JSON Object 

```java
JSONObject jsonObject = new JSONObject();
jsonObject.put(WSConstants.PARAM_USERNAME, "user");
        
```
Step 2. 
```java
 new WSAsync(this, jsonObject, WSConstants.API_URL, new WSResponseListener() {

            @Override
            public void onResponse(WSResponse response) {
              // here you can receive response 
            }

            @Override
            public void onError(Exception error) {

            }
        }).execute();
```

#### Done 

*Side note i use Class WSResponse as responce object u can chage it in code easy 
