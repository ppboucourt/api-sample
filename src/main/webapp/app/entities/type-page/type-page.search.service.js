(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('TypePageSearch', TypePageSearch);

    TypePageSearch.$inject = ['$resource'];

    function TypePageSearch($resource) {
        var resourceUrl =  'api/_search/type-pages/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
