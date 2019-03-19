(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('Fields', Fields);

    Fields.$inject = ['$resource'];

    function Fields ($resource) {
        var resourceUrl =  'api/fields/:id';

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
