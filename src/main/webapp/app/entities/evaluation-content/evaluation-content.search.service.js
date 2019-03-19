(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('EvaluationContentSearch', EvaluationContentSearch);

    EvaluationContentSearch.$inject = ['$resource'];

    function EvaluationContentSearch($resource) {
        var resourceUrl =  'api/_search/evaluation-contents/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
