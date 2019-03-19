(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('AllOrdersSearch', AllOrdersSearch);

    AllOrdersSearch.$inject = ['$resource'];

    function AllOrdersSearch($resource) {
        var resourceUrl =  'api/_search/all-orders/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
