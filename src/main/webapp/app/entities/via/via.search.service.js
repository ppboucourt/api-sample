(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('ViaSearch', ViaSearch);

    ViaSearch.$inject = ['$resource'];

    function ViaSearch($resource) {
        var resourceUrl =  'api/_search/vias/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
