(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('TypePatientPropertyCondition', TypePatientPropertyCondition);

    TypePatientPropertyCondition.$inject = ['$resource'];

    function TypePatientPropertyCondition ($resource) {
        var resourceUrl =  'api/type-patient-property-conditions/:id';

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
