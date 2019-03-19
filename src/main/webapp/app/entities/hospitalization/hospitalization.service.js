(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('Hospitalization', Hospitalization);

    Hospitalization.$inject = ['$resource', 'DateUtils'];

    function Hospitalization ($resource, DateUtils) {
        var resourceUrl =  'api/hospitalizations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.admissionDate = DateUtils.convertDateTimeFromServer(data.admissionDate);
                        data.dischargeDate = DateUtils.convertDateTimeFromServer(data.dischargeDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
