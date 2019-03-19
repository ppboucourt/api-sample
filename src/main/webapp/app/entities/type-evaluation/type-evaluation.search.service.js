(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('TypeEvaluationSearch', TypeEvaluationSearch);

    TypeEvaluationSearch.$inject = ['$resource'];

    function TypeEvaluationSearch($resource) {
        var resourceUrl =  'api/_search/type-evaluations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
