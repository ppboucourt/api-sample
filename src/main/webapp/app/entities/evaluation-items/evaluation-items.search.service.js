(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('EvaluationItemsSearch', EvaluationItemsSearch);

    EvaluationItemsSearch.$inject = ['$resource'];

    function EvaluationItemsSearch($resource) {
        var resourceUrl =  'api/_search/evaluation-items/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
