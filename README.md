## Ad-juster-Homework
> A client wants you to retrieve data from a RESTful API. The data contains multiple items that are of two types, Campaigns and Creatives. The relationship between the two types are that of a Parent and Child relationship, Campaign is the parent and Creative is the child. The Creatives contain specific data that can be used to describe the overall performance of the parent Campaign. 

> The client wants their resulting data represented in a CSV file. The CSV file must contain each Parent Campaign along with the summed data of the corresponding child’s data. 

> Your project is to write a HTTP Client, in Java, that pulls the client’s data from the API, and then performs the basic requested transformations to the data. Once the data is in the appropriate format, save it locally to a database of choice and also post the csv to the API endpoint. 

###### API Endpoints:

> http://54.172.67.92:8080/api/campaign GET – gets all the campaigns in a JSON array.

> http://54.172.67.92:8080/api/creative GET – gets all the creatives in a JSON array.

> http://54.172.67.92:8080/api/csv POST – send in the csv file with the following filename format: lastname_firstname.csv
