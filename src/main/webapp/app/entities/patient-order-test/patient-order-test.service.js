(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('PatientOrderTest', PatientOrderTest);

    PatientOrderTest.$inject = ['$resource', 'DateUtils'];

    function PatientOrderTest ($resource, DateUtils) {
        var resourceUrl =  'api/patient-order-tests/:id';

        return $resource(resourceUrl, {}, {
            'order': { url: 'api/patient-order-tests-from-order/:id', method: 'GET', isArray: true},
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
            }
        });
    }
})();
