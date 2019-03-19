(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('ActionSearch', ActionSearch);

    ActionSearch.$inject = ['$resource'];

    function ActionSearch($resource) {
        var resourceUrl =  'api/_search/actions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
