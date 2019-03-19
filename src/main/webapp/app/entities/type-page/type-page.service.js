(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('TypePage', TypePage);

    TypePage.$inject = ['$resource'];

    function TypePage ($resource) {
        var resourceUrl =  'api/type-pages/:id';

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
