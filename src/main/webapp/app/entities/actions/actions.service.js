(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('Actions', Actions);

    Actions.$inject = ['$resource'];

    function Actions ($resource) {
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
