(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('GlucoseSearch', GlucoseSearch);

    GlucoseSearch.$inject = ['$resource'];

    function GlucoseSearch($resource) {
        var resourceUrl =  'api/_search/glucoses/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
