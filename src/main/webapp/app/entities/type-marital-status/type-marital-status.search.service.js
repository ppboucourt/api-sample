(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('TypeMaritalStatusSearch', TypeMaritalStatusSearch);

    TypeMaritalStatusSearch.$inject = ['$resource'];

    function TypeMaritalStatusSearch($resource) {
        var resourceUrl =  'api/_search/type-marital-statuses/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
