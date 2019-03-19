(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('EvaluationContent', EvaluationContent);

    EvaluationContent.$inject = ['$resource'];

    function EvaluationContent ($resource) {
        var resourceUrl =  'api/evaluation-contents/:id';

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
