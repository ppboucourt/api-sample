(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('PatientMedicationTake', PatientMedicationTake);

    PatientMedicationTake.$inject = ['$resource', 'DateUtils'];

    function PatientMedicationTake ($resource, DateUtils) {
        var resourceUrl =  'api/patient-medication-takes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                // transformResponse: function (data) {
                //     if (data) {
                //         data = angular.fromJson(data);
                //         data.collectedDate = DateUtils.convertLocalDateTFromServer(data.collectedDate);
                //         data.scheduleDate = DateUtils.convertLocalDateFromServer(data.scheduleDate);
                //         data.sentDate = DateUtils.convertLocalDateFromServer(data.sentDate);
                //     }
                //     return data;
                // }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    // copy.collectedDate = DateUtils.convertLocalDateToServer(copy.collectedDate);
                    // copy.scheduleDate = DateUtils.convertLocalDateToServer(copy.scheduleDate);
                    // copy.sentDate = DateUtils.convertLocalDateToServer(copy.sentDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.collectedDate = DateUtils.convertLocalDateToServer(copy.collectedDate);
                    copy.scheduleDate = DateUtils.convertLocalDateToServer(copy.scheduleDate);
                    copy.sentDate = DateUtils.convertLocalDateToServer(copy.sentDate);
                    return angular.toJson(copy);
                }
            },
            'cancel': { url: 'api/patient-medication-takes/cancel/:id', method: 'GET'}
        });
    }
})();
