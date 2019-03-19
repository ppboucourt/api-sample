(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('TypeDosageSearch', TypeDosageSearch);

    TypeDosageSearch.$inject = ['$resource'];

    function TypeDosageSearch($resource) {
        var resourceUrl =  'api/_search/type-dosages/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true}
        });
    }
})();
