(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('CorporationSearch', CorporationSearch);

    CorporationSearch.$inject = ['$resource'];

    function CorporationSearch($resource) {
        var resourceUrl =  'api/_search/corporations/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
