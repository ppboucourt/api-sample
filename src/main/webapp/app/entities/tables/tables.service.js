(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('Tables', Tables);

    Tables.$inject = ['$resource'];

    function Tables ($resource) {
        var resourceUrl =  'api/tables/:id';

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
