(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('TypePatientPrograms', TypePatientPrograms);

    TypePatientPrograms.$inject = ['$resource'];

    function TypePatientPrograms ($resource) {
        var resourceUrl =  'api/type-patient-programs/:id';

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
