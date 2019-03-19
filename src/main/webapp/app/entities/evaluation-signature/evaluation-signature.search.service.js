(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('EvaluationSignatureSearch', EvaluationSignatureSearch);

    EvaluationSignatureSearch.$inject = ['$resource'];

    function EvaluationSignatureSearch($resource) {
        var resourceUrl =  'api/_search/evaluation-signatures/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
