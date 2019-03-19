(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('ServiceProviderSearch', ServiceProviderSearch);

    ServiceProviderSearch.$inject = ['$resource'];

    function ServiceProviderSearch($resource) {
        var resourceUrl =  'api/_search/service-providers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
