(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('ChartToIcd10', ChartToIcd10);

    ChartToIcd10.$inject = ['$resource'];

    function ChartToIcd10 ($resource) {
        var resourceUrl =  'api/chart-to-icd-10-s/:id';

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
            'update': { method:'PUT' },
            'getAllByPatientId': { 'url': 'api/chart-to-icd-10-s-by-patient/:id', method: 'GET', isArray: true},
        });
    }
})();
