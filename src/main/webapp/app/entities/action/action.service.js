(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('Action', Action);

    Action.$inject = ['$resource'];

    function Action ($resource) {
        var resourceUrl =  'api/actions/:id';

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
