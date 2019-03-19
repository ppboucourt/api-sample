(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('XMLConfig', XMLConfig);

    XMLConfig.$inject = ['$resource'];

    function XMLConfig ($resource) {
        var resourceUrl =  'api/x-ml-configs/:id';

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
            'update': { method:'PUT' },
            'environmental': { url: 'api/x-ml-configs/environmental/default', method: 'GET'},
            'patient': { url: 'api/x-ml-configs/patient/default', method: 'GET'}
        });
    }
})();
