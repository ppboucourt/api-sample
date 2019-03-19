(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('Weight', Weight);

    Weight.$inject = ['$resource'];

    function Weight ($resource) {
        var resourceUrl =  'api/weights/:id';

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
            'byChart': { url: 'api/weights/chart/:id', method: 'GET', isArray: true },
            'update': { method:'PUT' }
        });
    }
})();
