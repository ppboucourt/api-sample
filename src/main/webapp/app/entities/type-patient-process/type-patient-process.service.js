(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('TypePatientProcess', TypePatientProcess);

    TypePatientProcess.$inject = ['$resource'];

    function TypePatientProcess ($resource) {
        var resourceUrl =  'api/type-patient-processes/:id';

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
            'byTypePage': {url: 'api/type-patient-processes/by-type-page/:pagId/:facId', method: 'GET', isArray: true},
            'update': { method:'PUT' }
        });
    }
})();
