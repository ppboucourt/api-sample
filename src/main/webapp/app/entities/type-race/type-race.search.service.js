(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('TypeRaceSearch', TypeRaceSearch);

    TypeRaceSearch.$inject = ['$resource'];

    function TypeRaceSearch($resource) {
        var resourceUrl =  'api/_search/type-races/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
