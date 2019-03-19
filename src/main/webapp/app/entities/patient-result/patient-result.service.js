(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('PatientResult', PatientResult);

    PatientResult.$inject = ['$resource', 'DateUtils', 'DataUtils'];

    function PatientResult ($resource, DateUtils, DataUtils) {
        var resourceUrl =  'api/patient-results/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'byClinic': {url : "api/patient-results-by-clinic/:id", method: 'GET', isArray: true},
            'byPatient': {url : "api/patient-results-by-patient/:id", method: 'GET', isArray: true,
                transformResponse:  function(data, headers) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.createdDate = DateUtils.convertLocalDateFromServer(data.createdDate);
                    }
                    return data;
                }},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dob = DateUtils.convertLocalDateFromServer(data.dob);
                        data.collectionData = DateUtils.convertLocalDateFromServer(data.collectionData);
                        data.reviewedOn = DateUtils.convertLocalDateFromServer(data.reviewedOn);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dob = DateUtils.convertLocalDateToServer(copy.dob);
                    copy.collectionData = DateUtils.convertLocalDateToServer(copy.collectionData);
                    copy.reviewedOn = DateUtils.convertLocalDateToServer(copy.reviewedOn);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.dob = DateUtils.convertLocalDateToServer(copy.dob);
                    copy.collectionData = DateUtils.convertLocalDateToServer(copy.collectionData);
                    copy.reviewedOn = DateUtils.convertLocalDateToServer(copy.reviewedOn);
                    return angular.toJson(copy);
                }
            },
            'final': {url : "api/dfresults/:id", method: 'GET', isArray: true},
            'partial': {url : "api/dpresults/:id", method: 'GET', isArray: true},
            'unassigned': {url : "api/unassigned/:id", method: 'GET', isArray: true},
            'assign': {
                url: 'api/assign', method: 'POST'
            },
            'get_pdf': { url: 'api/presult/getpdf/:id', transformResponse: function (data) {
                    if (data) {
                        DataUtils.openFile('application/pdf', data);
                    }

                    return data;
                }
            },
            'critical': {url: 'api/patient-results-by-clinic/critical/:id', method: 'GET', isArray: true},
            'nonperform': {url: 'api/patient-results-by-clinic/nonperform/:id', method: 'GET', isArray: true},
            'kinetics' : {url: 'api/patient-results/kinetics/:id', method: 'GET', isArray: true}
        });
    }
})();
