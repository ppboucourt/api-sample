(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('PatientActionPre', PatientActionPre);

    PatientActionPre.$inject = ['$resource', 'DateUtils'];

    function PatientActionPre ($resource, DateUtils) {
        var resourceUrl =  'api/patient-action-pres/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
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
            'action': { url: 'api/patient-action-pres/action/:id', method: 'GET', isArray: true},
        });
    }
})();
