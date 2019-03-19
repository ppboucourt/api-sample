(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('TypeEvaluation', TypeEvaluation);

    TypeEvaluation.$inject = ['$resource'];

    function TypeEvaluation ($resource) {
        var resourceUrl =  'api/type-evaluations/:id';

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
