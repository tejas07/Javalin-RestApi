# Javalin-RestApi
Building rest api using javalin, Javalin is a lightweight framework an alternative to
build microservices.

#API-Details

 **/fetch-account-holders** : Retrives list of all the account holders.<br />
 **/fetch-individual-account/:accountno**: Retrives account info w.r.t account no.<br />
 **/transfer-money**: Account holders can transfer money to other account.<br />
 **/create-account**: New account can be created.<br />
 
 #Json Specifications
 
 **To create account** <br />
` localhost:7000/create-account
{	
 	"accName":"Simon Sinek",
 	"amount":5000
 }`<br />
 
 **To Transfer Money**<br /> 
`localhost:7000/transfer-money
{
 	"fromAccNo":1,
 	"toAccNo":2,
 	"amount":5000
 }`<br /> 
 **To list all the account holder username**<br /> 
 `localhost:7000/fetch-account-holders`<br /> 
 **To fetch individual account**<br /> 
 `localhost:7000//fetch-individual-account/1`<br />
 
 
 
 