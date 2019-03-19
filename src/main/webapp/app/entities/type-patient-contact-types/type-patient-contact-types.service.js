(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('TypePatientContactTypes', TypePatientContactTypes);

    TypePatientContactTypes.$inject = ['$resource'];

    function TypePatientContactTypes ($resource) {
        var resourceUrl =  'api/type-patient-contact-types/:id';

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
