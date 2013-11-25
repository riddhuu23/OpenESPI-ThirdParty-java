INSERT INTO application_information (uuid, dataCustodianId, dataCustodianThirdPartyId, thirdPartyApplicationName, dataCustodianThirdPartySecret, dataCustodianDefaultScopeResource, dataCustodianAuthorizationResource, dataCustodianTokenResource) VALUES ('550e8400-e29b-41d4-a716-4466554413a0', 'data_custodian', 'third_party', 'ConEdison (Heroku)', 'secret', 'http://datacustodian-dev.herokuapp.com/DataCustodian/RetailCustomer/ScopeSelectionList', 'http://datacustodian-dev.herokuapp.com/DataCustodian/oauth/authorize', 'http://datacustodian-dev.herokuapp.com/DataCustodian/oauth/token');
INSERT INTO application_information_scopes (application_information_id, scope) VALUES (1, 'FB=4_5_15;IntervalDuration=3600;BlockDuration=monthly;HistoryLength=13');
INSERT INTO application_information_scopes (application_information_id, scope) VALUES (1, 'FB=4_5_16;IntervalDuration=3600;BlockDuration=monthly;HistoryLength=13');


