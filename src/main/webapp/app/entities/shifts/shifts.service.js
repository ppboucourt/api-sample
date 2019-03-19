(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('Shifts', Shifts);

    Shifts.$inject = ['$resource'];

    function Shifts ($resource) {
        var resourceUrl =  'api/shifts/:id';

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
            'currentPatients': {url: 'api/charts-current-patients/:id/:date' ,method: 'GET', isArray: true},
            'update': { method:'PUT' }
        });
    }
})();
