(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('TypeLevelCareSearch', TypeLevelCareSearch);

    TypeLevelCareSearch.$inject = ['$resource'];

    function TypeLevelCareSearch($resource) {
        var resourceUrl =  'api/_search/type-level-cares/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
