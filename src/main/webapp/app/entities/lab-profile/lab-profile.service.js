(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('LabProfile', LabProfile);

    LabProfile.$inject = ['$resource'];

    function LabProfile ($resource) {
        var resourceUrl =  'api/lab-profiles/:id';

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
            'byfacility': {url: 'api/lab-profiles-facility/:id' ,method: 'GET', isArray: true},
            'update': { method:'PUT' }
        });
    }
})();
