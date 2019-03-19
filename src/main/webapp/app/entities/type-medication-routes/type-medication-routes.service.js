(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('TypeMedicationRoutes', TypeMedicationRoutes);

    TypeMedicationRoutes.$inject = ['$resource'];

    function TypeMedicationRoutes ($resource) {
        var resourceUrl =  'api/type-medication-routes/:id';

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
