(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('TubeSearch', TubeSearch);

    TubeSearch.$inject = ['$resource'];

    function TubeSearch($resource) {
        var resourceUrl =  'api/_search/tubes/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
