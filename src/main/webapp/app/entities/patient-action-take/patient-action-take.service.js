(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('PatientActionTake', PatientActionTake);

    PatientActionTake.$inject = ['$resource', 'DateUtils'];

    function PatientActionTake ($resource, DateUtils) {
        var resourceUrl =  'api/patient-action-takes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                         data.sentDate = DateUtils.convertLocalDateFromServer(data.sentDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.sentDate = DateUtils.convertLocalDateToServer(copy.sentDate);
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
            'cancel': { url: 'api/patient-action-takes/cancel/:id', method: 'GET'}
        });
    }
})();
