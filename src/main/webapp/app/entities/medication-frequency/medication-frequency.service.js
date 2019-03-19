(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('MedicationFrequency', MedicationFrequency);

    MedicationFrequency.$inject = ['$resource'];

    function MedicationFrequency ($resource) {
        var resourceUrl =  'api/medication-frequencies/:id';

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
            'update': { method:'PUT' }
        });
    }
})();
