(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('EvaluationTemplateSearch', EvaluationTemplateSearch);

    EvaluationTemplateSearch.$inject = ['$resource'];

    function EvaluationTemplateSearch($resource) {
        var resourceUrl =  'api/_search/evaluation-templates/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
