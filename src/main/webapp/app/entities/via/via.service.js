(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('Via', Via);

    Via.$inject = ['$resource'];

    function Via ($resource) {
        var resourceUrl =  'api/vias/:id';

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
