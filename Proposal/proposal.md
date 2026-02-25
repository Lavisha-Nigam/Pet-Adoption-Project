Project proposal: Pet-Connect Addoption System 

Application Description - Pet-Connect is a database application designed to manage the daily operations of an animal rescue shelter. 
The goal is to move away from maual record-keeping to a relational database that tracks animals, their medical history, and adoption transactions.

Database Schema (initial design) :
* Pet Table - Pet_ID (Primary Key), Name, Species, Breed, Age, Status (e.g. Available, Pending, Adopted)
* Adopters Table - Adopter_ID (Primary Key), FirstName, LastName, Phone, email
* Adoption Table - Adoption_ID (Primary Key), Pet_ID (Foreign Key), Adopter_ID (Foreign Key), Adoption_Date, Fee 
* Medical_Records Table - Record_ID (Primary Key), Pet_ID (Foreign Key), Condition, Date_Administered
