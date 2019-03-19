(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('ActionsSearch', ActionsSearch);

    ActionsSearch.$inject = ['$resource'];

    function ActionsSearch($resource) {
        var resourceUrl =  'api/_search/actions/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
