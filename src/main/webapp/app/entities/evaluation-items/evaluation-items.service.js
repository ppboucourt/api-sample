(function() {
    'use strict';
    angular
        .module('bluebookApp')
        .factory('EvaluationItems', EvaluationItems);

    EvaluationItems.$inject = ['$resource'];

    function EvaluationItems ($resource) {
        var resourceUrl =  'api/evaluation-items/:id';

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
