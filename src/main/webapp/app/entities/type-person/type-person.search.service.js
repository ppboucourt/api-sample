(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('TypePersonSearch', TypePersonSearch);

    TypePersonSearch.$inject = ['$resource'];

    function TypePersonSearch($resource) {
        var resourceUrl =  'api/_search/type-people/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
