(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('Patient_properties', Patient_properties);

    Patient_properties.$inject = ['$resource'];

    function Patient_properties ($resource) {
        var resourceUrl =  'api/patient-properties/:id';

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
