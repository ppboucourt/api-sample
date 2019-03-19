(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('BedSearch', BedSearch);

    BedSearch.$inject = ['$resource'];

    function BedSearch($resource) {
        var resourceUrl =  'api/_search/beds/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
