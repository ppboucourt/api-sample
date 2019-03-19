(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('XMLConfigSearch', XMLConfigSearch);

    XMLConfigSearch.$inject = ['$resource'];

    function XMLConfigSearch($resource) {
        var resourceUrl =  'api/_search/x-ml-configs/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
