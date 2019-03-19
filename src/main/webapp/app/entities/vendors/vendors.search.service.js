(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('VendorsSearch', VendorsSearch);

    VendorsSearch.$inject = ['$resource'];

    function VendorsSearch($resource) {
        var resourceUrl =  'api/_search/vendors/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
