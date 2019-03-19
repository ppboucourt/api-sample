(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('EvaluationSearch', EvaluationSearch);

    EvaluationSearch.$inject = ['$resource'];

    function EvaluationSearch($resource) {
        var resourceUrl =  'api/_search/evaluations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
