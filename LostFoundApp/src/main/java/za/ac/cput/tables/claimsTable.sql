   CREATE TABLE CLAIMS (
       claim_id          INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
       item_id           INT NOT NULL,
       claimant_user_id  INT NOT NULL,
       claim_date        DATE NOT NULL,
       proof_description VARCHAR(500),
       status            VARCHAR(20) NOT NULL DEFAULT 'Pending',
       handover_date     DATE
   );
