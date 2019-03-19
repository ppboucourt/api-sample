(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('PatientToShift', PatientToShift);

    PatientToShift.$inject = ['$resource'];

    function PatientToShift ($resource) {
        var resourceUrl =  'api/patient-to-shifts/:id';

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
