(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('PatientMedicationPres', PatientMedicationPres);

    PatientMedicationPres.$inject = ['$resource', 'DateUtils'];

    function PatientMedicationPres ($resource, DateUtils) {
        var resourceUrl =  'api/patient-medication-pres/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.staringDate = DateUtils.convertLocalDateFromServer(data.staringDate);
                        data.endDate = DateUtils.convertLocalDateFromServer(data.endDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.staringDate = DateUtils.convertLocalDateToServer(copy.staringDate);
                    copy.endDate = DateUtils.convertLocalDateToServer(copy.endDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.staringDate = DateUtils.convertLocalDateToServer(copy.staringDate);
                    copy.endDate = DateUtils.convertLocalDateToServer(copy.endDate);
                    return angular.toJson(copy);
                }
            },
            'medication': { url: 'api/patient-medication-pres/medication/:id', method: 'GET', isArray: true},
            'medicationDetails': { url: 'api/patient-medication-details-by-press-id/:id', method: 'GET'},

        });
    }
})();
