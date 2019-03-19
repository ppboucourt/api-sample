(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('TypeFormRulesSearch', TypeFormRulesSearch);

    TypeFormRulesSearch.$inject = ['$resource'];

    function TypeFormRulesSearch($resource) {
        var resourceUrl =  'api/_search/type-form-rules/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
